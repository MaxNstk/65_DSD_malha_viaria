package model;

import java.nio.file.Paths;

public class Config {

    private static Config instance;
    private String malhaAtual;
    private int qtdCarrosSimultaneos;
    private int intervaloInsercao;

    public static final String ICONS_PATH = "icons/";
    public static final String MALHA_PATH = "malhas/";

    public int getQtdCarrosSimultaneos(){
        return qtdCarrosSimultaneos;
    }

    public int getIntervaloInsercao() {
        return intervaloInsercao;
    }

    private Config() {
    }

    public static synchronized Config getInstance(){
        if (instance == null)
            reset();
        return instance;
    }

    public static synchronized void reset(){
        instance =  new Config();
    }

    public static void setInstance(Config instance) {
        Config.instance = instance;
    }

    public String getMalhaAtual() {
        return MALHA_PATH + malhaAtual;
    }

    public Config setMalhaAtual(String malhaAtual) {

        this.malhaAtual = malhaAtual;
        return instance;
    }

    public Config setqtdCarrosSimultaneos(int qtdCarrosSimultaneos) {
        this.qtdCarrosSimultaneos = qtdCarrosSimultaneos;
        return instance;
    }

    public Config setIntervaloInsercao(int intervaloInsercao) {
        this.intervaloInsercao = intervaloInsercao;
        return instance;
    }

}
