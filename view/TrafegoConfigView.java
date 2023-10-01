package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TrafegoConfigView extends JFrame {

    private JPanel containerPanel;
    private JTextField tfIntervalo;
    private JTextField tfQuantidadeCarros;
    private JTextField tfLimiteCarros;
    private JButton btnIniciarTrafego;
    private JComboBox cbMalha;

    public TrafegoConfigView() {
        super("Tráfego em Malha Viária");
        super.setSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.containerPanel);

        //Gerando botões de malhas dinâmicamente
        loadMalhas(this.containerPanel);

        btnIniciarTrafego.addActionListener((ActionEvent e) -> {

            // #TODO Renderizar tela de Tráfego

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