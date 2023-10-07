package model;

import consts.ClassificacaoCelula;
import controller.MalhaController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Carro extends Thread {

    private int tempoEspera; // em milissegundos
    private MalhaController malhaController;
    private Celula celulaAtual;

    private boolean finalizado = false;
    private final Random random = new Random();

    public Carro(MalhaController malhaController, Celula celulaAtual){
        this.malhaController = malhaController;
        this.celulaAtual = celulaAtual;
        this.tempoEspera = (random.nextInt(10)*100) + 200; // sorteio de 0,2 segundos a 1,2 segundos de espera
        celulaAtual.setCarroAtual(this);
    }

    @Override
    public void run() {
        while (Config.getInstance().emExecucao && !this.finalizado){
            Celula proximaCelula = Malha.getInstance().getProximaCelula(celulaAtual);
            if (proximaCelula == null){
                aguardar();
                this.finalizado = true;
                break;
            }
            if (proximaCelula.getClassificacao().equals(ClassificacaoCelula.CRUZAMENTO)){
                this.locomoverRegiaoCriticaReservandoTodaArea(proximaCelula);
//                this.locomoverRegiaoCriticaReservandoSomenteRegiao(proximaCelula);
            }
            else{
                this.locomover(proximaCelula);
            }
        }
        this.finalizar();
    }



    private void locomoverRegiaoCriticaReservandoTodaArea(Celula proximaCelula) {
        // esse metodo reserva toda a região critica para o carro andar

        List<Celula> regiaoCritia = Malha.getInstance().getRegiaoCritica(proximaCelula);
        if (tentarReservarCruzamento(regiaoCritia)) {
            LinkedList<Celula> rotaCruzamento = this.getRotaCruzamento(proximaCelula);
            if (rotaCruzamento.getLast().estaDisponivel())
                andarNoCruzamento(rotaCruzamento);
            liberarCelulas(regiaoCritia);
        }
    }
    private void locomoverRegiaoCriticaReservandoSomenteRegiao(Celula proximaCelula) {
        // esse metodo reserva somente o seu trajeto na região critica para o carro andar, descomente para funcionar

        LinkedList<Celula> rotaCruzamento = this.getRotaCruzamento(proximaCelula);
        if (tentarReservarCruzamento(rotaCruzamento)){
            andarNoCruzamento(rotaCruzamento);
            liberarCelulas(rotaCruzamento);
        }
    }

    private LinkedList<Celula> getRotaCruzamento(Celula proximaCelula){
        LinkedList<Celula> rota = new LinkedList<>();
        rota.add(proximaCelula);
        while (rota.getLast().getClassificacao().equals(ClassificacaoCelula.CRUZAMENTO)) {
            if (rota.size() >=3)
                proximaCelula = Malha.getInstance().getCelulaSaidaMaisProxima(proximaCelula);
            else
                proximaCelula = Malha.getInstance().getProximaCelula(proximaCelula);
            rota.add(proximaCelula);
        }
        return rota;
    }
    private void andarNoCruzamento(LinkedList<Celula> rota){
        for (Celula celula:rota)
            andar(celula);
    }

    private synchronized boolean tentarReservarCruzamento(List<Celula> celulasCruzamento){
        ArrayList<Celula> celulasReservadas = new ArrayList<>();

        // tenta reservar todas celulas do cruzamento, se alguma falhar, libera geral que reservou
        for (Celula celula: celulasCruzamento){
            if (!celula.estaDisponivel()){
                this.liberarCelulas(celulasReservadas);
                return false;
            }
            celula.reservar();
            celulasReservadas.add(celula);
        }
        return true;
    }

    private synchronized void liberarCelulas(List<Celula> celulas){
        for (Celula celula : celulas){
            celula.liberar();
        }
    }

    private synchronized void locomover(Celula proximaCelula){
        if (!proximaCelula.estaDisponivel()) {
            return;
        }
        this.andar(proximaCelula);
    }

    public void aguardar() {
        try {
            Thread.sleep(tempoEspera);
        } catch (InterruptedException ignored) {
            // Ocorreu tudo OK! Ela foi interrompida
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Celula getCelulaAtual() {
        return celulaAtual;
    }

    public void printInformacoes(){
        System.out.println(
                "Adicionado novo carro. [linha/coluna]"+this.celulaAtual.getLinha()+"/"+this.celulaAtual.getColuna()+". "+
                    this.celulaAtual.getClassificacao()+". "+this.tempoEspera+
                    ". Total de carros: "+this.malhaController.getQtdCarrosCirculacao()
        );
    }

    public synchronized void printCruzamentos(List<Celula> regiaoCritia){
                for (Celula c: regiaoCritia){
            System.out.print(c.getLinha()+","+c.getColuna()+"  ");
        }
        System.out.println("  ");
    }

    public void finalizar(){
        this.malhaController.removerCarroDaMalha(this);
        this.interrupt();
    }

    private void andar(Celula celulaDestino){
        this.aguardar();

        this.celulaAtual.setCarroAtual(null);
        this.malhaController.atualizarIconeDaCelula(celulaAtual);

        celulaDestino.setCarroAtual(this);
        this.celulaAtual = celulaDestino;
        this.malhaController.atualizarIconeDaCelula(celulaDestino);
    }

}
