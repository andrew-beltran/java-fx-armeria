package com.mycompany.armeriafx.modoTexto;

import com.mycompany.armeriafx.clases.Arma;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EntradaSalida {

    private static final Scanner sc = new Scanner(System.in);

    // vvv SCANNER vvv
    public static int scInt() {
        int salida = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                salida = sc.nextInt();
                numeroValido = true;
            } catch (InputMismatchException ime) {
                imprimirCadena("introduce un valor corecto" + System.lineSeparator());
                sc.nextLine();
            }
        }
        sc.nextLine();
        return salida;
    }

    public static double scDouble() {
        double salida = 0;
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                salida = sc.nextDouble();
                numeroValido = true;
            } catch (InputMismatchException ime) {
                imprimirCadena("introduce un valor corecto" + System.lineSeparator());
                sc.nextLine();
            }
        }
        sc.nextLine();
        return salida;
    }

    public static String scLine() {
        return sc.nextLine();
    }

    public static int seleccionarNumeroDentroDeRango(int identificador) {
        int eleccion;
        while ((eleccion = scInt()) < 1 || eleccion > identificador) {
            imprimirCadena("Introduce un valor en el rango disponible 1-" + identificador + System.lineSeparator());
        }
        return eleccion;
    }

    // ^^^ SCANNER ^^^
    // vvvv IMPRIMIR vvv
    private static void imprimirCadena(String cadena) {
        System.out.print(cadena);
    }

    // ^^^ IMPRIMIR ^^^
    // vvv TEXTOS DE MENUS vvv
    public static void textoMenuOpciones() {
        imprimirCadena(
                "1- Mostrar lista de armas" + System.lineSeparator()
                + "2- Buscar" + System.lineSeparator()
                + "3- Añadir arma" + System.lineSeparator()
                + "4- Quitar arma" + System.lineSeparator()
                + "5- Guardar" + System.lineSeparator()
                + "6- Cargar" + System.lineSeparator()
                + "7- Salir" + System.lineSeparator());
    }

    public static void textoMenuBuscar() {
        imprimirCadena("Selecciona parámetro por el que desea Buscar:" + System.lineSeparator()
                + "1- Nombre" + System.lineSeparator()
                + "2- Categoría" + System.lineSeparator()
                + "3- Calidad" + System.lineSeparator()
                + "4- Tipo de daño" + System.lineSeparator()
                + "5- Peso" + System.lineSeparator()
                + "6- Coste" + System.lineSeparator());
    }

    // ^^^ TEXTOS DE MENUS ^^^
    // vvv AVISOS vvv
    public static void armeroLleno() {
        imprimirCadena("Armero lleno no se puede introducir mas armas" + System.lineSeparator());
    }

    public static void nombreArmaNuevaYaExistente(String nombre) {
        imprimirCadena("El nombre \"" + nombre + "\" ya ha sido asignado a otra arma" + System.lineSeparator());
    }

    // ^^^ AVISOS ^^^
    // vvv "GETTERS" vvv
    public static String getNombreArma() {
        imprimirCadena("Introduce nombre del Arma: " + System.lineSeparator());
        return scLine();
    }

    public static Double getPesoArma() {
        double peso;
        imprimirCadena("Introduce peso del arma" + System.lineSeparator());
        while ((peso = scDouble()) <= 0) {
            imprimirCadena("Introduce un peso vádido" + System.lineSeparator());
        }
        return peso;
    }

    public static Double getCosteArma() {
        double coste;
        imprimirCadena("Introduce coste del arma" + System.lineSeparator());
        while ((coste = scDouble()) <= 0) {
            imprimirCadena("Introduce un coste vádido" + System.lineSeparator());
        }
        return coste;
    }

    public static String getCadenaLista(ArrayList<String> arrayList) {
        int identificador = 0;
        for (String cadena : arrayList) {
            imprimirCadena(++identificador + "- " + cadena + System.lineSeparator());
        }

        int eleccion = seleccionarNumeroDentroDeRango(identificador);

        return arrayList.get(eleccion - 1);
    }

    public static int seleccionarEntreMenorMayor(Double cantidad) {
        int numOpciones = 2;
        imprimirCadena(
                "1- Menor o igual que " + cantidad + System.lineSeparator()
                + "2- Mayor o igual que " + cantidad + System.lineSeparator());
        return seleccionarNumeroDentroDeRango(numOpciones);

    }

    // ^^^ "GETTERS" ^^^
    // POR IDENTIFICAR ewe
    public static void mostrarInfoArma(Arma arma) {
        imprimirCadena(arma.getNombre() + ": " + arma.getCategoria() + " - "
                + arma.getCalidad() + " - " + arma.getTipoDanho() + " - "
                + arma.getPeso() + " - " + arma.getCoste() + System.lineSeparator());
    }

    public static void listarArmasNumeradas(Arma[] armas) {
        for (int i = 0; i < armas.length; i++) {
            imprimirCadena((i + 1) + "- " + armas[i].getNombre() + System.lineSeparator());
        }
    }

    public static void mostrarArrayArmas(Arma[] armas) {
        for (Arma arma : armas) {
            mostrarInfoArma(arma);
        }
    }

    public static void buscarArmaPorNombre(Arma arma) {
        if (arma != null) {
            mostrarInfoArma(arma);
        } else {
            imprimirCadena("Arma no existente" + System.lineSeparator());
        }
    }

}
