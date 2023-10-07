package model;

import java.util.ArrayList;
import java.util.List;

public class RegiaoCritica {

    List<Celula> celulas = new ArrayList<>();

    public void addCelula(Celula celula){
        if (isCompleto())
            return;
        this.celulas.add(celula);
    }

    public boolean contains(Celula celula){
        return celulas.contains(celula);
    }

    public boolean isCompleto(){
        return this.celulas.size() == 4;
    }

}
