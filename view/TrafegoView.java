package view;

import controller.MalhaController;
import model.Celula;
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
    private JButton btnSpawnarCarros;
    private JLabel jlQtdCarros;

    private MalhaController malhaController;
    
    public TrafegoView() {
        super("Tráfego em Malha");
        super.setSize(new Dimension(1000, 1000));
        super.setContentPane(this.containerPanel);
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnEncerrar.addActionListener((ActionEvent e) -> {
            Config.getInstance().emExecucao = false;
            Config.reset();
            super.dispose();
            new TrafegoConfigView();
        });

        btnSpawnarCarros.addActionListener((ActionEvent e) -> {
            if(Config.getInstance().getSpawnarNovosCarros()){
                Config.getInstance().setSpawnarNovosCarros(false);
                btnSpawnarCarros.setText("Continuar Tráfego");
            }
            else{
                Config.getInstance().setSpawnarNovosCarros(true);
                btnSpawnarCarros.setText("Pausar Tráfego");
            }
        });

        this.loadTableModel();
        malhaController = new MalhaController();
        malhaController.anexarObserver(this);
        malhaController.start();
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
        jlQtdCarros.setText("Quantidade de Carros na Malha: " + String.valueOf(qtdCarrosMalha));
    }

    @Override
    public void atualizandoIconeDaCelula(Celula celula) {
        MalhaTableModel malhaTableModel = (MalhaTableModel) tbMalha.getModel();
        malhaTableModel.fireTableCellUpdated(celula.getLinha(), celula.getColuna());
        malhaTableModel.fireTableDataChanged();
    }

    @Override
    public void encerrarSimulacao() {
        btnEncerrar.doClick();
    }
}
