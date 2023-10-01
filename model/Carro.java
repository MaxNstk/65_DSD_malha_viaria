package model;

import controller.MalhaController;

public class Carro extends Thread {

    private int velocidade; // em milissegundos

    private MalhaController malhaController;

    public Carro(MalhaController malhaController){
        this.malhaController = malhaController;
    }

    @Override
    public void run() {
        super.run();
    }

    public void delay() {
        try {
            Thread.sleep(velocidade);
        } catch (InterruptedException ignored) {
            // Ocorreu tudo OK! Ela foi interrompida
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
