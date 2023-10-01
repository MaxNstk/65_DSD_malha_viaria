package view;

import model.Celula;
import model.Config;
import model.Malha;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

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
