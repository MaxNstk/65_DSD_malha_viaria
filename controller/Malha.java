package controller;

import model.Carro;
import model.Config;

import java.util.ArrayList;
import java.util.List;

public class Malha extends Thread {

    List<Carro> carrosEmCirculacao;

    public Malha() {
        this.carrosEmCirculacao = new ArrayList<>();
        inicializar();
    }

    private void inicializar() {
        while (carrosEmCirculacao.size() < Config.getInstance().getQtdCarrosSimultaneos()){

        }
    }
}
