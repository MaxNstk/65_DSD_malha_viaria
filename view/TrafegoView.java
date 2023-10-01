package view;

import model.Config;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class SimulacaoView extends JFrame {

    private JPanel containerPanel;
    private JTable tbMalha;
    private JButton btnEncerrar;
    private JTextField tfCarrosNaMalha;

    private Config config;

    public SimulacaoView(Config config) {
        this.config = config;
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);
        super.setUndecorated(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setContentPane(this.containerPanel);

        btnEncerrar.addActionListener((ActionEvent e) -> {
            new TrafegoConfigView();
            this.config.reset();
            super.dispose();
        });

        this.loadTableModel();
        super.setVisible(true);
    }

    private void loadTableModel() {

    }

}
