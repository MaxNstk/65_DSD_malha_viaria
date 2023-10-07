package model;

import consts.ClassificacaoCelula;
import consts.TiposCelula;
import controller.MalhaController;

import java.util.Random;

public class Carro extends Thread {

    private int tempoEspera; // em milissegundos
    private MalhaController malhaController;
    private Celula celulaAtual;
    private final Random random = new Random();

    public Carro(MalhaController malhaController, Celula celulaAtual){
        this.malhaController = malhaController;
        this.celulaAtual = celulaAtual;
        this.tempoEspera = (random.nextInt(10)*100) + 200; // sorteio de 0,2 segundos a 1,2 segundos de espera
        celulaAtual.setCarroAtual(this);
    }

    @Override
    public void run() {
        while (!this.isInterrupted()){
            Celula proximaCelula = Malha.getInstance().getProximaCelula(celulaAtual);
            aguardar();

            if (proximaCelula.getClassificacao().equals(ClassificacaoCelula.CRUZAMENTO)){
                this.locomoverRegiaoCritica(proximaCelula);
            }
            else{
                this.locomover(proximaCelula);
            }
        }
        this.malhaController.removerCarroDaMalha(this);
    }

    private synchronized void locomoverRegiaoCritica(Celula proximaCelula) {
        // pega as outras celulas
        // define o caminho
        // na mula
        
        RegiaoCritica regiaoCritica = Malha.getInstance().getRegiaoCritica(proximaCelula);
    }

    private synchronized void locomover(Celula proximaCelula){
        if (!Config.getInstance().emExecucao || proximaCelula == null){
            this.finalizar();
        }
        else {
            if (proximaCelula.isOcupada())
                return;

            this.celulaAtual.setCarroAtual(null);
            this.malhaController.atualizarIconeDaCelula(celulaAtual);

            proximaCelula.setCarroAtual(this);
            this.celulaAtual = proximaCelula;
            this.malhaController.atualizarIconeDaCelula(proximaCelula);
        }
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

    public void finalizar(){
        this.celulaAtual.setCarroAtual(null);
        this.malhaController.atualizarIconeDaCelula(celulaAtual);
        this.interrupt();
    }

}
