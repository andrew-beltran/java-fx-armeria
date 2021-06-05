package com.mycompany.armeriafx.modoTexto;

import com.mycompany.armeriafx.clases.Armero;
import com.mycompany.armeriafx.clases.Listas;
import javax.xml.bind.JAXBException;

public class MainText {

    private static final int maximoArmas = 10;
    private static final int numOpcionesMenu = 7;

    public static void main(String[] args) throws JAXBException {
        Armero armero = new Armero(maximoArmas);
        int salir = -1;
        while (salir != numOpcionesMenu) {
            EntradaSalida.textoMenuOpciones();
            switch (salir = EntradaSalida.seleccionarNumeroDentroDeRango(numOpcionesMenu)) {
                case 1:
                    armero.ordenarArmasAlfabeticamente();
                    EntradaSalida.mostrarArrayArmas(armero.getArmas());
                    break;
                case 2:
                    EntradaSalida.textoMenuBuscar();
                    String filtro;
                    double cantidad;
                    switch (EntradaSalida.scInt()) {
                        case 1:
                            String nombre = EntradaSalida.getNombreArma();
                            EntradaSalida.buscarArmaPorNombre(armero.getArmaPorNombre(nombre));
                            break;
                        case 2:
                            filtro = EntradaSalida.getCadenaLista(Listas.categorias());
                            EntradaSalida.mostrarArrayArmas(armero.getArmasPorCategoria(filtro));
                            break;
                        case 3:
                            filtro = EntradaSalida.getCadenaLista(Listas.calidad());
                            EntradaSalida.mostrarArrayArmas(armero.getArmasPorCalidad(filtro));
                            break;
                        case 4:
                            filtro = EntradaSalida.getCadenaLista(Listas.categorias());
                            EntradaSalida.mostrarArrayArmas(armero.getArmasPorTipoDanho(filtro));
                            break;
                        case 5:
                            cantidad = EntradaSalida.getPesoArma();
                            if (EntradaSalida.seleccionarEntreMenorMayor(cantidad) == 1) {
                                EntradaSalida.mostrarArrayArmas(armero.getArmasPorMenorIgualPesoQue(cantidad));
                            } else {
                                EntradaSalida.mostrarArrayArmas(armero.getArmasPorMayorIgualPesoQue(cantidad));
                            }
                            break;
                        case 6:
                            cantidad = EntradaSalida.getCosteArma();
                            if (EntradaSalida.seleccionarEntreMenorMayor(cantidad) == 1) {
                                EntradaSalida.mostrarArrayArmas(armero.getArmasPorMenorIgualCosteQue(cantidad));
                            } else {
                                EntradaSalida.mostrarArrayArmas(armero.getArmasPorMayorIgualCosteQue(cantidad));
                            }
                            break;

                    }
                    break;
                case 3:
                    if (armero.getArmasTotales() < armero.getNumMaximoArmas()) {
                        String nombre = EntradaSalida.getNombreArma();
                        boolean nombreCorrecto = false;
                        do {
                            if (armero.getArmaPorNombre(nombre) != null) {
                                EntradaSalida.nombreArmaNuevaYaExistente(nombre);
                                nombre = EntradaSalida.getNombreArma();
                            } else {
                                nombreCorrecto = true;
                            }
                        } while (!nombreCorrecto);
                        String categoria = EntradaSalida.getCadenaLista(Listas.categorias());
                        String calidad = EntradaSalida.getCadenaLista(Listas.calidad());
                        String tipoDanho = EntradaSalida.getCadenaLista(Listas.tipoDanho());
                        Double peso = EntradaSalida.getPesoArma();

                        armero.armaNueva(nombre, categoria, calidad, tipoDanho, peso);
                    } else {
                        EntradaSalida.armeroLleno();
                    }
                    break;
                case 4:
                    EntradaSalida.listarArmasNumeradas(armero.getArmas());
                    int armaEliminar = (EntradaSalida.seleccionarNumeroDentroDeRango(armero.getArmasTotales()) - 1);
                    armero.eliminarArma(armaEliminar);
                    break;
                case 5:
                    armero.guardarArmeriaEnXML();
                    break;
                case 6:
                    armero.cargarArmeriaDeXML();
                    break;

            }

        }
    }

}
