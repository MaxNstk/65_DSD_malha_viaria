package model;

public class Celula {

    private String iconPath;
    private int coluna;
    private int linha;
    private int tipo;
    private String veiculo = null;

    public Celula(int coluna, int linha, int tipo) {
        this.coluna = coluna;
        this.linha = linha;
        this.tipo = tipo;
        this.setIcon();
    }

    public String getIcon(){
        return this.iconPath;
    }

    private void setIcon(){
        if (this.veiculo == null)
            this.iconPath = Config.ICONS_PATH + "icon-carrro.png";
        this.iconPath = Config.ICONS_PATH + "icon" + this.tipo + ".png";
    }

    @Override
    public String toString() {
        return ""+this.tipo;
    }
}
