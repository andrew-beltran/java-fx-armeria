package com.mycompany.armeriafx.clases;

import com.mycompany.armeriafx.clases.Arma;
import java.util.Comparator;

public class OrdenarArmeroPorPeso implements Comparator<Arma> {

    @Override
    public int compare(Arma o1, Arma o2) {
        int salida = 0;
        double peso = (o1.getPeso() - o2.getPeso());
        if (peso < 0) {
            salida = -1;
        } else if (peso > 0) {
            salida = 1;
        } else {
            salida = o1.compareTo(o2);
        }
        return salida;
    }

}
