package model;

public class Celula {

    private String iconPath;
    private int coluna;
    private int linha;
    private int tipo;

    public Celula(String iconPath, int coluna, int linha, int tipo) {
        this.iconPath = iconPath;
        this.coluna = coluna;
        this.linha = linha;
        this.tipo = tipo;
    }
}
