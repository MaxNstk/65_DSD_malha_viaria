package observer;

import model.Celula;

public interface Observer {
    void atualizandoCarrosNaMalha(int qtdCarrosMalha);
    void atualizandoIconeDaCelula(Celula celula);
}
