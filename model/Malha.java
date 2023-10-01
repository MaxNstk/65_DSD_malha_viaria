package model;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Malha {

    private Celula matrizMalha[][];
    private int qtdLinhas;
    private int qtdColunas;
    private Scanner matrizScanner;

    private Malha() {
        this.inicializarVariaveis();
        this.inicializarMalha();
        this.printMatriz();
    }

    private static Malha instance;

    public synchronized static Malha getInstance() {
        if (instance == null){
            reset();
        }
        return instance;
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
}
