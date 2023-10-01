package view;

import model.Config;
import model.Malha;
import observer.Observer;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TrafegoView extends JFrame implements Observer {

    private JPanel containerPanel;
    private JTable tbMalha;
    private JButton btnEncerrar;
    private JTextField tfCarrosNaMalha;
    private JButton btnSpawnarCarros;
    
    public TrafegoView() {
        super("Tráfego em Malha");
        super.setSize(new Dimension(1000, 1000));
        super.setContentPane(this.containerPanel);
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnEncerrar.addActionListener((ActionEvent e) -> {
            new TrafegoConfigView();
            Config.reset();
            super.dispose();
        });

        btnSpawnarCarros.addActionListener((ActionEvent e) -> {
            if(Config.getInstance().getSpawnarNovosCarros()){
                Config.getInstance().setSpawnarNovosCarros(false);
                btnSpawnarCarros.setText("Continuar Tráfego");
            }
            else{
                Config.getInstance().setSpawnarNovosCarros(false);
                btnSpawnarCarros.setText("Pausar Tráfego");
            }
        });

        this.loadTableModel();
        super.setVisible(true);
    }

    private void loadTableModel() {

        tbMalha.setModel(new MalhaTableModel());
        tbMalha.setRowHeight(32);

        tbMalha.setDefaultRenderer(Object.class, new MalhaTableModelCellRenderer());

        TableColumnModel columnModel = tbMalha.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setMaxWidth(32);
        }
    }

    @Override
    public void atualizandoCarrosNaMalha(int qtdCarrosMalha) {
        tfCarrosNaMalha.setText(String.valueOf(qtdCarrosMalha));
    }
}
