package com.mycompany.armeriafx.clases;

import java.util.ArrayList;

public class Listas {

    public static ArrayList<String> categorias() {
        ArrayList<String> listaCategoria = new ArrayList<>();

        listaCategoria.add("Espada");
        listaCategoria.add("Hacha");
        listaCategoria.add("Cuchillo");
        listaCategoria.add("Estoque");
        listaCategoria.add("Sable");
        listaCategoria.add("Maza");
        listaCategoria.add("Lanza");
        listaCategoria.add("Mandoble");
        listaCategoria.add("Bastón");
        listaCategoria.add("Arco");
        listaCategoria.add("Ballesta");

        return listaCategoria;
    }

    public static ArrayList<String> calidad() {
        ArrayList<String> listaCalidad = new ArrayList<>();

        listaCalidad.add("Común");
        listaCalidad.add("Poco común");
        listaCalidad.add("Rara");
        listaCalidad.add("Épica");
        listaCalidad.add("Legendaria");
        listaCalidad.add("Mítica");

        return listaCalidad;
    }

    public static ArrayList<String> tipoDanho() {
        ArrayList<String> listaTipoDanho = new ArrayList<>();

        listaTipoDanho.add("Corte");
        listaTipoDanho.add("Golpe");
        listaTipoDanho.add("Penetración");
        listaTipoDanho.add("Fuego");
        listaTipoDanho.add("Sangrado");
        listaTipoDanho.add("Veneno");

        return listaTipoDanho;
    }
}
