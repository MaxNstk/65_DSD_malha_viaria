package model;

import consts.ClassificacaoCelula;
import consts.TiposCelula;

import java.util.Arrays;
import java.util.List;

public class Celula {

    private int coluna;
    private int linha;
    private int tipo;
    private String classificacao;
    private Carro carroAtual = null;
    private int ultimaLinhaDaMalha;
    private int ultimaColunaDaMalha;

    private boolean celulaLiberada = true;


    public Celula(int coluna, int linha, int tipo, int qtdTotalLinhas, int qtdTotalColunas) {
        this.coluna = coluna;
        this.linha = linha;
        this.tipo = tipo;
        this.ultimaLinhaDaMalha = qtdTotalLinhas - 1;
        this.ultimaColunaDaMalha = qtdTotalColunas - 1;
        this.setClassificacao();
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isOcupada(){
        return this.carroAtual != null && this.celulaLiberada;
    }

    public void liberar(){
        this.carroAtual = null;
        this.celulaLiberada = true;
    }

    public int getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    private void setClassificacao() {

        switch (this.tipo){
            case TiposCelula.VAZIO:
                this.classificacao = ClassificacaoCelula.VAZIO;
                break;
            case TiposCelula.ESTRADA_BAIXO:
                if (this.linha == ultimaLinhaDaMalha)
                    this.classificacao = ClassificacaoCelula.SAIDA;
                else if (this.linha == 0)
                    this.classificacao = ClassificacaoCelula.ENTRADA;
                else
                    this.classificacao = ClassificacaoCelula.ESTRADA;
                break;
            case TiposCelula.ESTRADA_CIMA:
                if (this.linha == ultimaLinhaDaMalha)
                    this.classificacao = ClassificacaoCelula.ENTRADA;
                else if (this.linha == 0)
                    this.classificacao = ClassificacaoCelula.SAIDA;
                else
                    this.classificacao = ClassificacaoCelula.ESTRADA;
                break;
            case TiposCelula.ESTRADA_DIREITA:
                if (this.coluna == ultimaColunaDaMalha)
                    this.classificacao = ClassificacaoCelula.SAIDA;
                else if (this.coluna == 0)
                    this.classificacao = ClassificacaoCelula.ENTRADA;
                else
                    this.classificacao = ClassificacaoCelula.ESTRADA;
                break;
            case TiposCelula.ESTRADA_ESQUERDA:
                if (this.coluna == ultimaColunaDaMalha)
                    this.classificacao = ClassificacaoCelula.ENTRADA;
                else if (this.coluna == 0)
                    this.classificacao = ClassificacaoCelula.SAIDA;
                else
                    this.classificacao = ClassificacaoCelula.ESTRADA;
                break;
            default:
                this.classificacao = ClassificacaoCelula.CRUZAMENTO;
        }
    }

    public boolean celulaEstaVazia(){
        return this.carroAtual == null;
    }

    public Carro getCarroAtual() {
        return carroAtual;
    }

    public void setCarroAtual(Carro carroAtual) {
        this.carroAtual = carroAtual;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public String getIcon(){
        if (this.isOcupada()) {
            return Config.ICONS_PATH + "icon-carro.png";
        }
        else {
            return Config.ICONS_PATH + "icon" + this.tipo + ".png";
        }
    }

    @Override
    public String toString() {
        return ""+this.tipo;
    }

    public List<Celula> getCruzamentos() {
        return null;
//        if (this.classificacao)
    }
}
