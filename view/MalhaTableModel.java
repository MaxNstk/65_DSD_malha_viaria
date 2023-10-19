package view;

import model.Malha;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class MalhaTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return Malha.getInstance().getQtdLinhas();
    }

    @Override
    public int getColumnCount() {
        return Malha.getInstance().getQtdColunas();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return new ImageIcon(Malha.getInstance().getMatrizMalha()[rowIndex][columnIndex].getIcon());
    }
}
