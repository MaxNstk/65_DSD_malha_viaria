package model;

import controller.Estrada;

public class Carro extends Thread {


    public static final String ICONS_PATH = "icons/";

    public static final int AGUA = 0;
    public static final int ESTRADA_CIMA  = 1;
    public static final int ESTRADA_DIREITA  = 2;
    public static final int ESTRADA_BAIXO  = 3;
    public static final int ESTRADA_ESQUERDA  = 4;
    public static final int CRUZAMENTO_CIMA  = 5;
    public static final int CRUZAMENTO_DIREITA  = 6;
    public static final int CRUZAMENTO_BAIXO  = 7;
    public static final int CRUZAMENTO_ESQUERDA  = 8;
    public static final int CRUZAMENTO_CIMA_E_DIREITA  = 9;
    public static final int CRUZAMENTO_CIMA_E_ESQUERDA = 10;
    public static final int CRUZAMENTO_DIREITA_E_BAIXO = 11;
    public static final int CRUZAMENTO_BAIXO_E_ESQUERDA = 12;

    private int velocidade; // em milissegundos

    private Estrada controller;

    public Carro(Estrada controller){
        this.controller = controller;
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
