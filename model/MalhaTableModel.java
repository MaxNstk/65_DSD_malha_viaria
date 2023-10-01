package model;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class MalhaTableModel extends AbstractTableModel {

    private Celula matrizMalha[][];

    private int qtdLinhas;
    private int qtdColunas;


    public MalhaTableModel(int qtdLinhas, int qtdColunas) {
        this.qtdLinhas = qtdLinhas;
        this.qtdColunas = qtdColunas;
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
        return new ImageIcon(this.matrizMalha[columnIndex][rowIndex].getIcon());
    }
}
