package model;

import consts.ClassificacaoCelula;
import consts.TiposCelula;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Celula {

    private int coluna;
    private int linha;
    private int tipo; // Representa a direção da estrada, é correspondente ao número na matriz
    private String classificacao; // Representa se a estrada é uma ENTRADA/SAIDA/CRUZAMENTO/VAZIO
    private Carro carroAtual = null;
    private int ultimaLinhaDaMalha;
    private int ultimaColunaDaMalha;

    private boolean celulaDisponivel = true;

    private Lock lock;


    public Celula(int coluna, int linha, int tipo, int qtdTotalLinhas, int qtdTotalColunas) {
        this.lock = new ReentrantLock();
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

    public boolean estaDisponivel(){
        return this.carroAtual == null && this.celulaDisponivel;
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
        if (this.carroAtual != null) {
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

    public boolean tentarReservar(){
        if (this.carroAtual != null)
            return false;
        try{
            return this.lock.tryLock(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public void liberar(){
        try{
            this.lock.unlock();
        }catch (Exception e){}
    }

}
