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
        while (this.celulaAtual != null){
            Celula proximaCelula = Malha.getInstance().getProximaCelula(celulaAtual);
            if (proximaCelula == null)
                continue;
            aguardar();
            proximaCelula.setCarroAtual(this);
            this.celulaAtual.setCarroAtual(null);
            this.malhaController.atualizarIconeDaCelula(celulaAtual);

            this.celulaAtual = proximaCelula;
            this.malhaController.atualizarIconeDaCelula(proximaCelula);

        }
        this.malhaController.removerCarroDaMalha(this);
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

}
