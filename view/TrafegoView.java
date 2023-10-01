package view;

import model.Config;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;

public class TrafegoView extends JFrame {

    private JPanel containerPanel;
    private JTable tbMalha;
    private JButton btnEncerrar;
    private JTextField tfCarrosNaMalha;

    public TrafegoView() {
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //super.setUndecorated(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setContentPane(this.containerPanel);

        btnEncerrar.addActionListener((ActionEvent e) -> {
            new TrafegoConfigView();
            Config.reset();
            super.dispose();
        });

        this.loadTableModel();
        super.setVisible(true);
    }

    private void loadTableModel() {
        tbMalha.setModel(new MalhaTableModel());
        tbMalha.setRowHeight(25);

        tbMalha.setDefaultRenderer(Object.class, new MalhaTableModelCellRenderer());

        TableColumnModel columnModel = tbMalha.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setMaxWidth(25);
        }
        System.out.println("");
    }
}
