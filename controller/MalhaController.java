package controller;

import consts.ClassificacaoCelula;
import model.Carro;
import model.Celula;
import model.Config;
import model.Malha;

import java.util.ArrayList;
import java.util.List;

public class MalhaController extends Thread {

    List<Carro> carrosEmCirculacao;

    public MalhaController() {
        this.carrosEmCirculacao = new ArrayList<>();
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
        if (
            celulaAtual.getClassificacao().equals(ClassificacaoCelula.ENTRADA) &&
            celulaAtual.celulaEstaVazia() &&
            carrosEmCirculacao.size() < Config.getInstance().getQtdCarrosSimultaneos()
        ){

        }
    }

}
