package model;

import consts.ClassificacaoCelula;
import consts.TiposCelula;

import java.io.File;
import java.util.*;

public class Malha {

    private Celula matrizMalha[][];
    private int qtdLinhas;
    private int qtdColunas;
    private Scanner matrizScanner;
    private static Malha instance;

    private Random random = new Random();

    private Malha() {
        this.inicializarVariaveis();
        this.inicializarMalha();
        this.printMatriz();
    }

    public synchronized static Malha getInstance() {
        if (instance == null){
            reset();
        }
        return instance;
    }

    public void setCell(Celula celula){
        matrizMalha[celula.getLinha()][celula.getColuna()] = celula;
    }

    public synchronized static void reset(){
        instance = new Malha();
    }

    private void inicializarVariaveis(){
        File arquivoMalha = new File(Config.getInstance().getMalhaAtual());
        try{
            matrizScanner = new Scanner(arquivoMalha);
            this.qtdLinhas = matrizScanner.nextInt();
            this.qtdColunas = matrizScanner.nextInt();
            this.matrizMalha = new Celula[qtdLinhas][qtdColunas];
        }catch (Exception e){
            System.out.println(e.getMessage()+" - "+ Arrays.toString(e.getStackTrace()));
        }
    }

    private void inicializarMalha(){
        while (matrizScanner.hasNextInt()){
            for (int linha = 0; linha < this.qtdLinhas; linha++) {
                for (int coluna = 0; coluna < this.qtdColunas; coluna++) {
                    int tipo = matrizScanner.nextInt();
                    Celula celulaAtual = new Celula(coluna, linha, tipo, qtdLinhas, qtdColunas);
                    this.matrizMalha[linha][coluna] = celulaAtual;
                }
            }
        }
        matrizScanner.close();
    }
    private void printMatriz(){
        for (int linha = 0; linha < this.qtdLinhas; linha++) {
            for (int coluna = 0; coluna < this.qtdColunas; coluna++) {
                System.out.printf(this.matrizMalha[linha][coluna].toString()+" ");
            }
            System.out.println("");
        }
    }

    public Celula[][] getMatrizMalha() {
        return matrizMalha;
    }

    public int getQtdLinhas() {
        return qtdLinhas;
    }

    public int getQtdColunas() {
        return qtdColunas;
    }

    public Celula getProximaCelula(Celula celulaAtual) {
            if (celulaAtual.getClassificacao().equals(ClassificacaoCelula.SAIDA))
                return null;
            switch (celulaAtual.getTipo()){

                case TiposCelula.ESTRADA_CIMA:
                case TiposCelula.CRUZAMENTO_CIMA:
                    return getCelulaACima(celulaAtual);

                case TiposCelula.ESTRADA_DIREITA:
                case TiposCelula.CRUZAMENTO_DIREITA:
                    return getCelulaADireita(celulaAtual);

                case TiposCelula.ESTRADA_BAIXO:
                case TiposCelula.CRUZAMENTO_BAIXO:
                    return getCelulaABaixo(celulaAtual);

                case TiposCelula.ESTRADA_ESQUERDA:
                case TiposCelula.CRUZAMENTO_ESQUERDA:
                    return getCelulaAEsquerda(celulaAtual);


                case TiposCelula.CRUZAMENTO_CIMA_E_DIREITA:
                    if (random.nextInt(2) == 0)
                        return getCelulaACima(celulaAtual);
                    else
                        return getCelulaADireita(celulaAtual);

                case TiposCelula.CRUZAMENTO_CIMA_E_ESQUERDA:
                    if (random.nextInt(2) == 0)
                        return getCelulaACima(celulaAtual);
                    else
                        return getCelulaAEsquerda(celulaAtual);

                case TiposCelula.CRUZAMENTO_DIREITA_E_BAIXO:
                    if (random.nextInt(2) == 0)
                        return getCelulaADireita(celulaAtual);
                    else
                        return getCelulaABaixo(celulaAtual);

                case TiposCelula.CRUZAMENTO_BAIXO_E_ESQUERDA:
                    if (random.nextInt(2) == 0)
                        return getCelulaABaixo(celulaAtual);
                    else
                        return getCelulaAEsquerda(celulaAtual);
                default:
                    return null;
        }
    }

    public Celula getCelulaSaidaMaisProxima(Celula celula) {
        switch (celula.getTipo()){
            case TiposCelula.CRUZAMENTO_CIMA_E_DIREITA:
            case TiposCelula.CRUZAMENTO_DIREITA:
            case TiposCelula.ESTRADA_CIMA:
                return getCelulaADireita(celula);

            case TiposCelula.CRUZAMENTO_CIMA_E_ESQUERDA:
            case TiposCelula.CRUZAMENTO_CIMA:
            case TiposCelula.ESTRADA_DIREITA:
                return getCelulaACima(celula);

            case TiposCelula.CRUZAMENTO_DIREITA_E_BAIXO:
            case TiposCelula.CRUZAMENTO_BAIXO:
            case TiposCelula.ESTRADA_BAIXO:
                return getCelulaABaixo(celula);

            case TiposCelula.CRUZAMENTO_BAIXO_E_ESQUERDA:
            case TiposCelula.CRUZAMENTO_ESQUERDA:
            case TiposCelula.ESTRADA_ESQUERDA:
                return getCelulaAEsquerda(celula);
            default:
                return null;
        }
    }

    private Celula getCelulaACima(Celula celula){
        return matrizMalha[celula.getLinha()-1][celula.getColuna()];
    }
    private Celula getCelulaADireita(Celula celula){
        return matrizMalha[celula.getLinha()][celula.getColuna()+1];
    }
    private Celula getCelulaABaixo(Celula celula){
        return matrizMalha[celula.getLinha()+1][celula.getColuna()];
    }
    private Celula getCelulaAEsquerda(Celula celula){
        return matrizMalha[celula.getLinha()][celula.getColuna()-1];
    }
    private Celula getCelulaADiagonalEsquerdaCima(Celula celula){
        return matrizMalha[celula.getLinha()-1][celula.getColuna()-1];
    }
    private Celula getCelulaADiagonalEsquerdaBaixo(Celula celula){
        return matrizMalha[celula.getLinha()+1][celula.getColuna()-1];
    }
    private Celula getCelulaADiagonalDireitaCima(Celula celula){
        return matrizMalha[celula.getLinha()-1][celula.getColuna()+1];
    }
    private Celula getCelulaADiagonalDireitaBaixo(Celula celula){
        return matrizMalha[celula.getLinha()+1][celula.getColuna()+1];
    }

    public ArrayList<Celula> getRegiaoCritica(Celula proximaCelula) {

        ArrayList<Celula> regiaoCritica = new ArrayList<>();

        regiaoCritica.add(proximaCelula);

        //Realizando mapeamento das celulas do cruzamento
        switch (proximaCelula.getTipo()){
            case TiposCelula.CRUZAMENTO_CIMA:
                regiaoCritica.add(this.getCelulaACima(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalEsquerdaCima(proximaCelula));
                regiaoCritica.add(this.getCelulaAEsquerda(proximaCelula));
                break;
            case TiposCelula.CRUZAMENTO_DIREITA:
                regiaoCritica.add(this.getCelulaADireita(proximaCelula));
                regiaoCritica.add(this.getCelulaACima(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalDireitaCima(proximaCelula));
                break;
            case TiposCelula.CRUZAMENTO_BAIXO:
                regiaoCritica.add(this.getCelulaABaixo(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalDireitaBaixo(proximaCelula));
                regiaoCritica.add(this.getCelulaADireita(proximaCelula));
                break;
            case TiposCelula.CRUZAMENTO_ESQUERDA:
            case TiposCelula.CRUZAMENTO_CIMA_E_ESQUERDA:
                regiaoCritica.add(this.getCelulaAEsquerda(proximaCelula));
                regiaoCritica.add(this.getCelulaABaixo(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalEsquerdaBaixo(proximaCelula));
                break;
            case TiposCelula.CRUZAMENTO_CIMA_E_DIREITA:
                regiaoCritica.add(this.getCelulaAEsquerda(proximaCelula));
                regiaoCritica.add(this.getCelulaACima(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalEsquerdaCima(proximaCelula));
                break;
            case TiposCelula.CRUZAMENTO_DIREITA_E_BAIXO:
                regiaoCritica.add(this.getCelulaACima(proximaCelula));
                regiaoCritica.add(this.getCelulaADireita(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalDireitaCima(proximaCelula));
                break;
            case TiposCelula.CRUZAMENTO_BAIXO_E_ESQUERDA:
                regiaoCritica.add(this.getCelulaABaixo(proximaCelula));
                regiaoCritica.add(this.getCelulaADireita(proximaCelula));
                regiaoCritica.add(this.getCelulaADiagonalDireitaBaixo(proximaCelula));
                break;
        }

        return regiaoCritica;
    }
}
