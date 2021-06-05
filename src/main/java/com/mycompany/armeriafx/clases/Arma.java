package com.mycompany.armeriafx.clases;

import javax.xml.bind.annotation.XmlElement;

public class Arma implements Comparable<Arma> {
    
    @XmlElement(name = "nombre")
    private final String nombre;
    @XmlElement(name = "categoria")
    private final String categoria;
    @XmlElement(name = "calidad")
    private final String calidad;
    @XmlElement(name = "tipoDanho")
    private final String tipoDanho;
    @XmlElement(name = "peso")
    private final double peso;
    @XmlElement(name = "coste")
    private double coste;
    
    //  Por hacer:
    //      Añadir al constructor comprobar que "categoria", "calidad" y 
    //          "tipoDanho" son datos válidos comparándolos con las listas.
    
    public Arma() {
        this("", "", "", "", 0);
    }

    public Arma(String nombre, String categoria, String calidad, String tipoDanho, double peso) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.calidad = calidad;
        this.tipoDanho = tipoDanho;
        this.peso = peso;
        this.coste = calcularCoste();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCalidad() {
        return calidad;
    }

    public String getTipoDanho() {
        return tipoDanho;
    }

    public double getPeso() {
        return peso;
    }

    public double getCoste() {
        return coste;
    }

    @Override
    public int compareTo(Arma o) {
        return this.getNombre().compareTo(o.getNombre());
    }

    private double calcularCoste() {
        coste = (Constantes.IMPUESTOS + Constantes.GANANCIA)
                * ((Listas.calidad().indexOf(this.calidad) + 1) * peso);
        int redondeo = (int) Math.round((coste * Constantes.DECIMALES) + 0.5);

        return (double) redondeo / Constantes.DECIMALES;
    }

}
