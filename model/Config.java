package model;

public class Config {

    public int getQtdVeiculosSimultaneos() {
        return qtdVeiculosSimultaneos;
    }

    public int getIntervaloInsercao() {
        return intervaloInsercao;
    }

    public Config(int qtdVeiculosSimultaneos, int intervaloInsercao) {
        this.qtdVeiculosSimultaneos = qtdVeiculosSimultaneos;
        this.intervaloInsercao = intervaloInsercao;
    }

    private int qtdVeiculosSimultaneos;
    private int intervaloInsercao;

}
