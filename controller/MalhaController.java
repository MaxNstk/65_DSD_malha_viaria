package controller;

import consts.ClassificacaoCelula;
import model.Carro;
import model.Celula;
import model.Config;
import model.Malha;
import observer.Observer;

import java.util.ArrayList;
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
        while (Config.getInstance().getSpawnarNovosCarros()){
            for (int linha = 0; linha < Malha.getInstance().getQtdLinhas(); linha++) {
                for (int coluna = 0; coluna < Malha.getInstance().getQtdColunas(); coluna++) {
                    this.AtualizarCelula(linha,coluna);
                }
            }
        }
    }

    private void AtualizarCelula(int linha, int coluna){
        Celula celulaAtual = Malha.getInstance().getMatrizMalha()[linha][coluna];
        if (!celulaAtual.getClassificacao().equals(ClassificacaoCelula.ENTRADA)) // Tem de ser entrada
            return;
        if (!celulaAtual.celulaEstaVazia()) // Tem de estar vazia
            return;
        if (carrosEmCirculacao.size() == Config.getInstance().getQtdCarrosSimultaneos()) // Não pode ultrapassar o limite estabelecido
            return;
        try{
            sleep(Config.getInstance().getIntervaloInsercao());
            adicionarNovoCarroAMalha(celulaAtual);
        } catch (Exception e){
            System.out.println(e.getMessage()+"  -   "+e.getStackTrace());
        }
    }

    private void adicionarNovoCarroAMalha(Celula celulaInicial){
        Carro carro = new Carro(this, celulaInicial);
        carrosEmCirculacao.add(carro);
        this.atualizarObservers();
        carro.printInformacoes();
        carro.start();
    }

    public void removerCarroDaMalha(Carro carro){
        this.carrosEmCirculacao.remove(carro);
    }

    public void anexarObserver(Observer observer){
        this.observers.add(observer);
    }

    public void atualizarObservers(){
        for (Observer obs: observers){
            obs.atualizandoCarrosNaMalha(this.getQtdCarrosCirculacao());
        }
    }

    public int getQtdCarrosCirculacao(){
        return this.carrosEmCirculacao.size();
    }

}