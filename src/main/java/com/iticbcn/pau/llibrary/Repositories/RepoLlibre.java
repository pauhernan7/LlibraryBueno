package com.iticbcn.pau.llibrary.Repositories;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.iticbcn.pau.llibrary.Model.Llibre;

@Repository
public class RepoLlibre {

    private ArrayList<Llibre> llibres = new ArrayList<>();

    public RepoLlibre() {
        llibres.add(new Llibre(1, "HARRY POTTER I EL PRESONER D'AZKABAN", "JK Rowling", "Salamandra", "26/9/2006", "fantastica"));
        llibres.add(new Llibre(2, "CODI DA VINCI", "Dan Brown", "Ariel", "26/9/2006", "ficcio"));
    }

    public ArrayList<Llibre> getAllLlibres() {
        return llibres;
    }

    public void InsertaLlibre(Llibre llibre) {
        llibres.add(llibre);
    }

    public Llibre getLlibreID(int id) {
        for (Llibre l1 : llibres) {
            if (l1.getIdLlibre() == id) {
                return l1;
            }
        }
        return null;
    }
}
