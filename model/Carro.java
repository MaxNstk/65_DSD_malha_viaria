package model;

import consts.ClassificacaoCelula;
import controller.MalhaController;

import java.util.ArrayList;
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
                this.locomoverRegiaoCritica(proximaCelula);
            }
            else{
                this.locomover(proximaCelula);
            }
        }
        this.finalizar();
    }

    private void locomoverRegiaoCritica(Celula proximaCelula) {

        List<Celula> regiaoCritia = Malha.getInstance().getRegiaoCritica(proximaCelula);

        // tentativa de reservar todos cruzamentos é sincrona, andar sobre eles não
        if (tentarReservarCruzamento(regiaoCritia)){
            printCruzamentos(regiaoCritia);
            andarNoCruzamento(proximaCelula);
            liberarCelulas(regiaoCritia);
        }
    }

    private void andarNoCruzamento(Celula proximaCelula){

        andar(proximaCelula);
        while (this.celulaAtual.getClassificacao().equals(ClassificacaoCelula.CRUZAMENTO)){
            proximaCelula = Malha.getInstance().getProximaCelula(this.celulaAtual);
            andar(proximaCelula);
        }
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
