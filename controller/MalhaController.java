package controller;

import consts.ClassificacaoCelula;
import model.Carro;
import model.Celula;
import model.Config;
import model.Malha;
import observer.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MalhaController extends Thread {

    private List<Carro> carrosEmCirculacao;
    private List<Observer> observers;


    public MalhaController() {
        this.carrosEmCirculacao = new ArrayList<>();
        this.observers= new ArrayList<>();
    }

    @Override
    public void run() {
        inicializar();
    }

    private void inicializar() {
        Config.getInstance().emExecucao = true;
        while (Config.getInstance().emExecucao && Config.getInstance().getSpawnarNovosCarros()){
            for (int linha = 0; linha < Malha.getInstance().getQtdLinhas(); linha++) {
                for (int coluna = 0; coluna < Malha.getInstance().getQtdColunas(); coluna++) {
                    this.AtualizarCelula(linha,coluna);
                }
            }
        }
        this.interrupt();
    }

    private void AtualizarCelula(int linha, int coluna){
        Celula celulaAtual = Malha.getInstance().getMatrizMalha()[linha][coluna];
        if (!celulaAtual.getClassificacao().equals(ClassificacaoCelula.ENTRADA)) // Tem de ser entrada
            return;
        if (!celulaAtual.celulaEstaVazia()) // Tem de estar vazia
            return;
        if (this.getQtdCarrosCirculacao() == Config.getInstance().getQtdCarrosSimultaneos()) // Não pode ultrapassar o limite estabelecido
            return;
        try{
            sleep(Config.getInstance().getIntervaloInsercao()* 1000L);
            adicionarNovoCarroAMalha(celulaAtual);
        } catch (Exception e){
            System.out.println(e.getMessage()+"   -   "+ Arrays.toString(e.getStackTrace()));
        }
    }

    private void adicionarNovoCarroAMalha(Celula celulaInicial){
        Carro carro = new Carro(this, celulaInicial);

        carrosEmCirculacao.add(carro);
        this.atualizarQuantidadeDeCarrosDaMalha();
        this.atualizarIconeDaCelula(celulaInicial);
        carro.printInformacoes();
        carro.start();
    }

    public void removerCarroDaMalha(Carro carro){
        this.carrosEmCirculacao.remove(carro);
        Celula celula = carro.getCelulaAtual();
        celula.setCarroAtual(null);
        this.atualizarIconeDaCelula(celula);
    }

    public void anexarObserver(Observer observer){
        this.observers.add(observer);
    }

    public int getQtdCarrosCirculacao(){
        return this.carrosEmCirculacao.size();
    }

    public void atualizarIconeDaCelula(Celula celula) {
        for (Observer obs: observers){
            obs.atualizandoIconeDaCelula(celula);
        }
    }

    public void atualizarQuantidadeDeCarrosDaMalha(){
        for (Observer obs: observers){
            obs.atualizandoCarrosNaMalha(this.getQtdCarrosCirculacao());
        }
    }

}
