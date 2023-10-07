package view;

import model.Config;
import model.Malha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class TrafegoConfigView extends JFrame {

    private JPanel containerPanel;
    private JTextField tfIntervalo;
    private JTextField tfLimiteCarrosSimultaneos;
    private JButton btnIniciarTrafego;
    private JComboBox cbMalha;
    private JComboBox cbExclusaoMutua;

    public TrafegoConfigView() {
        super("Tráfego em Malha");
        super.setSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.containerPanel);

        //Gerando malhas dinâmicamente
        loadMalhas(this.containerPanel);

        btnIniciarTrafego.addActionListener((ActionEvent e) -> {
            Config.reset();
            Config.getInstance()
                    .setMalhaAtual(getMalhaSelecionada())
                    .setMecanismoExclusaoMutua(getMecanismoSelecionado())
                    .setqtdCarrosSimultaneos(Integer.parseInt(tfLimiteCarrosSimultaneos.getText()))
                    .setIntervaloInsercao(Double.parseDouble(tfIntervalo.getText()));
            Malha.reset();
            new TrafegoView();
            super.dispose();
        });

        // Carregando opções de Exclusão Mutua
        cbExclusaoMutua.addItem("Semaforo");
        cbExclusaoMutua.addItem("Monitor");

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);
    }

    private void loadMalhas(JPanel containerPanel) {
        File folder = new File("malhas");

        if (folder.exists() && folder.isDirectory()) {

            // Lista das malhas na pasta
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    cbMalha.addItem(file.getName());
                }
            }
        }
    }

    public String getMalhaSelecionada(){
        return (String) cbMalha.getSelectedItem();
    }

    public String getMecanismoSelecionado(){
        return (String) cbExclusaoMutua.getSelectedItem();
    }
}