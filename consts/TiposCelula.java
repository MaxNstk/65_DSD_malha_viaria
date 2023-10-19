package consts;

public class TiposCelula {

    public static final int VAZIO = 0;
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

    public static final int[] ESTRADAS = {ESTRADA_CIMA, ESTRADA_DIREITA, ESTRADA_BAIXO, ESTRADA_ESQUERDA};
    public static final int[] CRUZAMENTOS = {
            CRUZAMENTO_CIMA, CRUZAMENTO_DIREITA, CRUZAMENTO_BAIXO,
            CRUZAMENTO_ESQUERDA, CRUZAMENTO_CIMA_E_DIREITA, CRUZAMENTO_CIMA_E_ESQUERDA,
            CRUZAMENTO_DIREITA_E_BAIXO, CRUZAMENTO_BAIXO_E_ESQUERDA,
    };


}
