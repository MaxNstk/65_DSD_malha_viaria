package view;

import model.Config;

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

    public TrafegoConfigView() {
        super("Tráfego em Malha Viária");
        super.setSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.containerPanel);

        //Gerando malhas dinâmicamente
        loadMalhas(this.containerPanel);

        btnIniciarTrafego.addActionListener((ActionEvent e) -> {
            Config.getInstance()
                    .setMalhaAtual(getMalhaSelecionada())
                    .setqtdCarrosSimultaneos(Integer.parseInt(tfLimiteCarrosSimultaneos.getText()))
                    .setIntervaloInsercao(Integer.parseInt(tfIntervalo.getText()));

            new TrafegoView();

            super.dispose();
        });

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
}