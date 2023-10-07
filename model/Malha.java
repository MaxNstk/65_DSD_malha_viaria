package model;

import consts.ClassificacaoCelula;
import consts.TiposCelula;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Malha {

    private Celula matrizMalha[][];
    private int qtdLinhas;
    private int qtdColunas;
    private Scanner matrizScanner;
    private static Malha instance;


    private Malha() {
        this.inicializarVariaveis();
        this.inicializarMalha();
        this.printMatriz();
    }

    public synchronized static Malha getInstance() {
        if (instance == null){
            reset();
        }
        return instance;
    }

    public void setCell(Celula celula){
        matrizMalha[celula.getLinha()][celula.getColuna()] = celula;
    }

    public synchronized static void reset(){
        instance = new Malha();
    }

    private void inicializarVariaveis(){
        File arquivoMalha = new File(Config.getInstance().getMalhaAtual());
        try{
            matrizScanner = new Scanner(arquivoMalha);
            this.qtdLinhas = matrizScanner.nextInt();
            this.qtdColunas = matrizScanner.nextInt();
            this.matrizMalha = new Celula[qtdLinhas][qtdColunas];
        }catch (Exception e){
            System.out.println(e.getMessage()+" - "+ Arrays.toString(e.getStackTrace()));
        }
    }

    private void inicializarMalha(){
        while (matrizScanner.hasNextInt()){
            for (int linha = 0; linha < this.qtdLinhas; linha++) {
                for (int coluna = 0; coluna < this.qtdColunas; coluna++) {
                    int tipo = matrizScanner.nextInt();
                    Celula celulaAtual = new Celula(coluna, linha, tipo, qtdLinhas, qtdColunas);
                    this.matrizMalha[linha][coluna] = celulaAtual;
                }
            }
        }
        matrizScanner.close();
    }
    private void printMatriz(){
        for (int linha = 0; linha < this.qtdLinhas; linha++) {
            for (int coluna = 0; coluna < this.qtdColunas; coluna++) {
                System.out.printf(this.matrizMalha[linha][coluna].toString()+" ");
            }
            System.out.println("");
        }
    }

    public Celula[][] getMatrizMalha() {
        return matrizMalha;
    }

    public int getQtdLinhas() {
        return qtdLinhas;
    }

    public int getQtdColunas() {
        return qtdColunas;
    }

    public Celula getProximaCelula(Celula celulaAtual) {
            if (celulaAtual.getClassificacao().equals(ClassificacaoCelula.SAIDA))
                return null;
            switch (celulaAtual.getTipo()){
                case TiposCelula.ESTRADA_CIMA:
                    return matrizMalha[celulaAtual.getLinha()-1][celulaAtual.getColuna()];
                case TiposCelula.ESTRADA_DIREITA:
                    return matrizMalha[celulaAtual.getLinha()][celulaAtual.getColuna()+1];
                case TiposCelula.ESTRADA_BAIXO:
                return matrizMalha[celulaAtual.getLinha()+1][celulaAtual.getColuna()];
                case TiposCelula.ESTRADA_ESQUERDA:
                    return matrizMalha[celulaAtual.getLinha()][celulaAtual.getColuna()-1];
                case TiposCelula.CRUZAMENTO_CIMA:
                    return null;
                case TiposCelula.CRUZAMENTO_DIREITA:
                    return null;
                case TiposCelula.CRUZAMENTO_BAIXO:
                    return null;
                case TiposCelula.CRUZAMENTO_ESQUERDA:
                    return null;
                case TiposCelula.CRUZAMENTO_CIMA_E_DIREITA:
                    return null;
                case TiposCelula.CRUZAMENTO_CIMA_E_ESQUERDA:
                    return null;
                case TiposCelula.CRUZAMENTO_DIREITA_E_BAIXO:
                    return null;
                case TiposCelula.CRUZAMENTO_BAIXO_E_ESQUERDA:
                    return null;
                default:
                    return null;
        }
    }
}
