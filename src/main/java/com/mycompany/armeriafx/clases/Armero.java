package com.mycompany.armeriafx.clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrew Nauzet Beltrán Pérez
 */

@XmlRootElement(name = "armero")
public class Armero {

    @XmlAttribute(name = "numMaximoArmas")
    private int numMaximoArmas;
    @XmlAttribute(name = "armasTotales")
    private int armasTotales;
    @XmlElement(name = "arma")
    private Arma[] armas;
    private static final String fichero = "armas.txt";
    private final String separador = "#";
    private static final String xml = "armas.xml";

    /**
     *
     * @param numMaximoArmas Para indicar el tamaño máximo del armero
     */
    
    public Armero() {
        this(0);
    }
    
    public Armero(int numMaximoArmas) {
        this.numMaximoArmas = numMaximoArmas;
        this.armasTotales = 0;
        this.armas = new Arma[numMaximoArmas];
    }

    public int getNumMaximoArmas() {
        return numMaximoArmas;
    }

    public int getArmasTotales() {
        return armasTotales;
    }

    public Arma getArma(int num) {
        return armas[num];
    }

    /**
     * Método para añadir armas al armero.
     *
     * @param nombre indica el nombre del arma y es usado como un identificador.
     * @param categoria indica la caregoría del arma.
     * @param calidad indica la calidad el arma.
     * @param tipoDanho indica el tipo de daño del arma
     * @param peso indica el peso del arma.
     */
    public void armaNueva(String nombre, String categoria, String calidad, String tipoDanho, double peso) {
        if (armasTotales < numMaximoArmas) {
            if (getArmaPorNombre(nombre) == null) {
                armas[armasTotales++] = new Arma(nombre, categoria, calidad, tipoDanho, peso);
            }
        }
    }

    /**
     * Método para obtener un objeto de la clase Arma.
     *
     * @param nombre se comparará con los nombres de las armas que estén en el
     * array "armas" hasta encontrar uno igual.
     * @return si el Arma es encontrada la devuelve si no devuelve null.
     */
    public Arma getArmaPorNombre(String nombre) {
        boolean buscandoArma = true;
        Arma arma = null;
        for (int i = 0; i < armasTotales && buscandoArma; i++) {
            if (nombre.equals(armas[i].getNombre())) {
                arma = armas[i];
                buscandoArma = false;
            }
        }
        return arma;
    }
    
    public int getPosicionArmaPorNombre(String nombre) {
        boolean buscandoArma = true;
        int arma = -1;
        for (int i = 0; i < armasTotales && buscandoArma; i++) {
            if (nombre.equals(armas[i].getNombre())) {
                arma = i;
                buscandoArma = false;
            }
        }
        return arma;
    }

    /**
     * Método para ordenar el array "armas" alfabeticamente.
     */
    public void ordenarArmasAlfabeticamente() {
        Arrays.sort(armas, 0, armasTotales);
    }

    /**
     *
     * @return Devuelve el array "armas".
     */
    public Arma[] getArmas() {

        Arma[] salida = reducirArrayArmasParaFiltrar(armasTotales, armas);

        return salida;
    }

    /**
     * Método para eliminar un Arma del array "armas".
     *
     * @param posicion indica la posicion del arma a eliminar.
     */
    public void eliminarArma(int posicion) {
        for (int i = posicion; i < armasTotales; i++) {
            if (i < numMaximoArmas - 1) {
                armas[i] = armas[i + 1];
            }
        }
        armas[--armasTotales] = null;
    }
    
    public void eliminarArmaPorNombre(String nombre) {
        int posicion = getPosicionArmaPorNombre(nombre);
        eliminarArma(posicion);
    }

    // vvv GUARDAR Y CARGAR vvv
    public void cargarArmeriaDesdeArray(Arma[] armasNuevas) {
        if (numMaximoArmas <= armasNuevas.length) {
            numMaximoArmas = armasNuevas.length;
        }
        
        for (armasTotales = 0; armasTotales < armasNuevas.length; armasTotales++) {
            armas[armasTotales] = armasNuevas[armasTotales];
        }
    }
    
    public void guardarArmeriaEnFichero() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(fichero));

            for (int i = 0; i < armasTotales; i++) {
                bw.write(armas[i].getNombre() + separador
                        + armas[i].getCategoria() + separador
                        + armas[i].getCalidad() + separador
                        + armas[i].getTipoDanho() + separador
                        + armas[i].getPeso() + System.lineSeparator());
            }
        } catch (IOException ex) {
            Logger.getLogger(Armero.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null){
                    bw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Armero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cargarArmeriaDeFichero() {

        String cadena;
        int armasTotalesTemp = 0;
        Arma[] armasTemp = new Arma[numMaximoArmas];
            BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fichero));
            while ((cadena = br.readLine()) != null) {
                String[] cadenaSplit = cadena.split(separador);
                String nombre = cadenaSplit[0];
                String categoria = cadenaSplit[1];
                String calidad = cadenaSplit[2];
                String tipoDanho = cadenaSplit[3];
                double peso = Double.valueOf(cadenaSplit[4]);
                armasTemp[armasTotalesTemp++] = new Arma(nombre, categoria, calidad, tipoDanho, peso);
            }
            this.armas = armasTemp;
            this.armasTotales = armasTotalesTemp;
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        } catch (ArrayIndexOutOfBoundsException aoe) {
            System.out.println("Los datos en la armería guardada exeden las capacidades de la armería");
        } catch (IOException ex) {
            Logger.getLogger(Armero.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Armero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void guardarArmeriaEnXML() throws JAXBException {
        File ficheroXML = new File(xml);
        
        JAXBContext contexto = JAXBContext.newInstance(Armero.class);
        Marshaller miMarshaler = contexto.createMarshaller();
        miMarshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        miMarshaler.marshal(this, ficheroXML);
    }
    
    public void cargarArmeriaDeXML() throws JAXBException {
        Armero nuevoArmero;
        File ficheroXML = new File(xml);
        
        JAXBContext contexto = JAXBContext.newInstance(Armero.class);
        
        Unmarshaller miUnmarshaller = contexto.createUnmarshaller();
        nuevoArmero = (Armero) miUnmarshaller.unmarshal(ficheroXML);
        
        armasTotales = nuevoArmero.getArmasTotales();
        if (armasTotales > 0){
        numMaximoArmas = nuevoArmero.getNumMaximoArmas();
        Arma[] armasNuevas = nuevoArmero.getArmas();
            for (int i = 0; i < armasTotales; i++) {
                armas[i] = armasNuevas[i];
            }
        }
    }

    // ^^^ GUARDAR Y CARGAR ^^^
    // vvv FILTROS vvv

    public Arma[] reducirArrayArmasParaFiltrar(int temporalArmasTotales, Arma[] temporal) {
        Arma[] salida = new Arma[temporalArmasTotales];
        for (int i = 0; i < temporalArmasTotales; i++) {
            salida[i] = temporal[i];
        }
        return salida;
    }
    
    public Arma[] getArmasPorNombreParcial(Arma[] armasParcial, String nombre) {
        int temporalArmasTotales = 0;
        String regEx = "\\w*" + nombre + "\\w*";
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (Pattern.matches(regEx, armasParcial[i].getNombre())) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorCategoriaParcial(Arma[] armasParcial, String categoria) {
        int temporalArmasTotales = 0;
        categoria = "\\w*" + categoria + "\\w*";
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (Pattern.matches(categoria, armasParcial[i].getCategoria())) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorCalidadParcial(Arma[] armasParcial, String calidad) {
        int temporalArmasTotales = 0;
        calidad = "\\w*" + calidad + "\\w*";
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (Pattern.matches(calidad, armasParcial[i].getCalidad())) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorTipoDanhoParcial(Arma[] armasParcial, String tipoDanho) {
        int temporalArmasTotales = 0;
        tipoDanho = "\\w*" + tipoDanho + "\\w*";
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (Pattern.matches(tipoDanho, armasParcial[i].getCategoria())) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorPesoMinimoParcial(Arma[] armasParcial, Double peso) {
        int temporalArmasTotales = 0;
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (peso <= armasParcial[i].getPeso()) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorPesoMaximoParcial(Arma[] armasParcial, Double peso) {
        int temporalArmasTotales = 0;
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (peso >= armasParcial[i].getPeso()) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorCosteMinimoParcial(Arma[] armasParcial, Double coste) {
        int temporalArmasTotales = 0;
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (coste <= armasParcial[i].getCoste()) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorCosteMaximoParcial(Arma[] armasParcial, Double coste) {
        int temporalArmasTotales = 0;
        
        Arma[] temporal = new Arma[armasParcial.length];
        
        for (int i = 0; i < armasParcial.length; i++) {
            if (coste >= armasParcial[i].getCoste()) {
                temporal[temporalArmasTotales++] = armasParcial[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
    
    public Arma[] getArmasPorCategoria(String categoria) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (categoria.equals(armas[i].getCategoria())) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }

    public Arma[] getArmasPorCalidad(String calidad) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (calidad.equals(armas[i].getCalidad())) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }

    public Arma[] getArmasPorTipoDanho(String tipoDanho) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (tipoDanho.equals(armas[i].getCategoria())) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }

    public Arma[] getArmasPorMenorIgualPesoQue(double peso) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (peso >= armas[i].getPeso()) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }

    public Arma[] getArmasPorMayorIgualPesoQue(double peso) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (peso <= armas[i].getPeso()) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }

    public Arma[] getArmasPorMenorIgualCosteQue(double coste) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (coste >= armas[i].getCoste()) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }

    public Arma[] getArmasPorMayorIgualCosteQue(double coste) {
        Arma[] temporal = new Arma[armasTotales];
        int temporalArmasTotales = 0;

        for (int i = 0; i < armasTotales; i++) {
            if (coste <= armas[i].getCoste()) {
                temporal[temporalArmasTotales++] = armas[i];
            }
        }

        return reducirArrayArmasParaFiltrar(temporalArmasTotales, temporal);
    }
}