package view;

import model.Celula;
import model.Config;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MalhaTableModel extends AbstractTableModel {

    private Celula matrizMalha[][];

    private File arquivoMalha;
    private int qtdLinhas;
    private int qtdColunas;
    private Scanner matrizScanner;

    public MalhaTableModel() {
        this.inicializarVariaveis();
        this.inicializarMalha();
        this.printMatriz();
    }

    private void inicializarVariaveis(){
        this.arquivoMalha = new File(Config.getInstance().getMalhaAtual());
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
                        Celula celulaAtual = new Celula(coluna, linha, tipo);
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


    @Override
    public int getRowCount() {
        return qtdLinhas;
    }

    @Override
    public int getColumnCount() {
        return qtdColunas;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return new ImageIcon(this.matrizMalha[rowIndex][columnIndex].getIcon());
    }
}
