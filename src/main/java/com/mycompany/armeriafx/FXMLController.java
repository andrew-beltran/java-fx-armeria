package com.mycompany.armeriafx;

import com.mycompany.armeriafx.clases.Arma;
import com.mycompany.armeriafx.clases.Armero;
import com.mycompany.armeriafx.clases.ConeccionesBaseDeDatos;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.xml.bind.JAXBException;

public class FXMLController implements Initializable {
    
    private Label label;
    @FXML
    private TextField nuevaNombre;
    @FXML
    private TextField nuevaCategoria;
    @FXML
    private TextField nuevaTipoDanho;
    @FXML
    private TextField nuevaPeso;
    @FXML
    private ListView<String> listaArmas;
    @FXML
    private Pane panelArmaNueva;
    @FXML
    private Button botonCrearArma;
    @FXML
    private Button botonLimpiarMenuNuevaArma;
    @FXML
    private TextField nuevaCalidad;
    @FXML
    private Pane PanelInfoArma;
    @FXML
    private Label nombreArma;
    @FXML
    private Label categoriaArma;
    @FXML
    private Label calidadArma;
    @FXML
    private Label tipoDanhoArma;
    @FXML
    private Label pesoArma;
    @FXML
    private Label costeArma;
    @FXML
    private Pane panelFiltrarArma;
    @FXML
    private TextField buscarNombre;
    @FXML
    private TextField buscarCategoria;
    @FXML
    private TextField buscarTipoDanho;
    @FXML
    private Button botonBuscarArma;
    @FXML
    private TextField buscarCalidad;
    
    Armero armero = new Armero(10);
    int armaSeleccionada;
    String nombreArmaSeleccionada = "";
    Arma[] armasMostrar = new Arma[0];
    @FXML
    private TextField buscarPesoMin;
    @FXML
    private TextField buscarCosteMin;
    @FXML
    private TextField buscarPesoMax;
    @FXML
    private TextField buscarCosteMax;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarArmero();
        } catch (JAXBException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarInfo();
    }
    
    // VVV MODIFICACIONES AL ARMERO ACTUAL VVV

    @FXML
    private void crearArmaNueva(ActionEvent event) {
        try {
            armero.armaNueva(nuevaNombre.getText(), nuevaCategoria.getText(), nuevaCalidad.getText(), nuevaTipoDanho.getText(), Double.valueOf(nuevaPeso.getText()));
            actualizarInfo();
            limpiarMenuCrear();
        } catch (java.lang.NumberFormatException e) {
            alertaErrorConDouble(nuevaPeso.getPromptText());
        }
    }

    @FXML
    private void eliminarArma(ActionEvent event) {
        if (armasMostrar.length > 0) {
            armero.eliminarArmaPorNombre(nombreArmaSeleccionada);
            if (armaSeleccionada > 0) {
                armaSeleccionada--;
            }
            actualizarInfo();
        }
    }
    
    // ^^^  MODIFICACIONES AL ARMERO ACTUAL ^^^
    
    // VVV GESTIÓN DE PANEL VISIBLE VVV

    @FXML
    private void menuFiltrarArma(ActionEvent event) {
        panelArmaNueva.setVisible(false);
        PanelInfoArma.setVisible(false);
        panelFiltrarArma.setVisible(true);
    }

    @FXML
    private void menuCrearArma(ActionEvent event) {
        panelArmaNueva.setVisible(true);
        PanelInfoArma.setVisible(false);
        panelFiltrarArma.setVisible(false);
    }
    
    private void menuInfoArma() {
        panelArmaNueva.setVisible(false);
        PanelInfoArma.setVisible(true);
        panelFiltrarArma.setVisible(false);
    }
    
    // ^^^ GESTIÓN DE PANEL VISIBLE ^^^

    // VVVV GUARDAR Y CARGAR VVV

    @FXML
    private void guardarArmeria(ActionEvent event) throws JAXBException {
        armero.guardarArmeriaEnXML();
    }

    @FXML
    private void cargarArmeria(ActionEvent event) throws JAXBException {
        cargarArmero();
        actualizarInfo();
    }
    
    @FXML
    private void guardarArmaEnBaseDeDatos(ActionEvent event) throws SQLException {
        if (armasMostrar.length > 0) {
            try {
                if (!ConeccionesBaseDeDatos.comprobarArmaExistenteEnBaseDeDatos(nombreArma.getText())){
                    ConeccionesBaseDeDatos.guardarArmaEnBaseDeDatos
                    (nombreArma.getText(), categoriaArma.getText(), calidadArma.getText(), 
                            tipoDanhoArma.getText(), Double.valueOf(pesoArma.getText()), Double.valueOf(costeArma.getText()));
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Advertencia");
                    alert.setHeaderText("Arma con el mismo nombre ya registrada en Favoritos");
                    alert.setContentText("Alerte à l'égalité");

                    alert.showAndWait();
                }
            } catch (Exception e) {
                alertErrorConectarConBaseDeDatos();
            }
        }
    }

    @FXML
    private void cargarArmeriaDesdeBaseDeDatos(ActionEvent event) throws SQLException {
        try {
            armero.cargarArmeriaDesdeArray(ConeccionesBaseDeDatos.cargarArmasFavoritasDesdeBaseDeDatos());
            actualizarInfo();
        } catch (Exception e) {
            alertErrorConectarConBaseDeDatos();
        }
    }
    
    public void cargarArmero() throws JAXBException {
        armero.cargarArmeriaDeXML();
    }
    
    // ^^^ GUARDAR Y CARGAR ^^^
    
    // VVV LISTVIEW VVV

    @FXML
    private void buscarArma(ActionEvent event) {
        listaMostrarArmasConFiltro();
    }

    private void mostrarArrayArmas(Arma[] armas) {
        listaArmas.getItems().clear();
        for (Arma arma : armas) {
            listaArmas.getItems().add(arma.getNombre());
        }
    }

    public void listaMostrarArmasConFiltro() {
        armasMostrar = armero.getArmas();
        if (!buscarNombre.getText().equals("")) {
            armasMostrar = armero.getArmasPorNombreParcial(armasMostrar, buscarNombre.getText());
        }
        if (!buscarCategoria.getText().equals("")) {
            armasMostrar = armero.getArmasPorCategoriaParcial(armasMostrar, buscarCategoria.getText());
        }
        if (!buscarCalidad.getText().equals("")) {
            armasMostrar = armero.getArmasPorCalidadParcial(armasMostrar, buscarCalidad.getText());
        }
        if (!buscarTipoDanho.getText().equals("")) {
            armasMostrar = armero.getArmasPorTipoDanhoParcial(armasMostrar, buscarTipoDanho.getText());
        }
        try {
            if (!buscarPesoMin.getText().equals("")) {
                armasMostrar = armero.getArmasPorPesoMinimoParcial(armasMostrar, Double.valueOf(buscarPesoMin.getText()));
            }
        } catch (Exception e) {
            alertaErrorConDouble(buscarPesoMin.getPromptText());
        }
        try {
            if (!buscarPesoMax.getText().equals("")) {
                armasMostrar = armero.getArmasPorPesoMaximoParcial(armasMostrar, Double.valueOf(buscarPesoMax.getText()));
            }
        } catch (Exception e) {
            alertaErrorConDouble(buscarPesoMax.getPromptText());
        }
        try {
            if (!buscarCosteMin.getText().equals("")) {
                armasMostrar = armero.getArmasPorCosteMinimoParcial(armasMostrar, Double.valueOf(buscarCosteMin.getText()));
            }
        } catch (Exception e) {
            alertaErrorConDouble(buscarCosteMin.getPromptText());
        }
        try {
            if (!buscarCosteMax.getText().equals("")) {
                armasMostrar = armero.getArmasPorCosteMaximoParcial(armasMostrar, Double.valueOf(buscarCosteMax.getText()));
            }
        } catch (Exception e) {
            alertaErrorConDouble(buscarCosteMax.getPromptText());
        }
        mostrarArrayArmas(armasMostrar);
    }
    
    // ^^^ LISTVIEW ^^^

    // VVV MOSTRAR INFORMACIÓN DEL ARMA SELECCIONADA VVV

    @FXML
    private void mostrarInfoArma(MouseEvent event) {
        menuInfoArma();
        establecerArmaSeleccionada();
        mostrarInfoArmaEnLabels();
    }

    private void establecerArmaSeleccionada() {
        if (armasMostrar.length > 0 && listaArmas.getSelectionModel().getSelectedIndices().get(0) < armasMostrar.length){
            armaSeleccionada = listaArmas.getSelectionModel().getSelectedIndices().get(0);
            EstablecerNombreArmaSeleccionada();
        }
    }

    public void EstablecerNombreArmaSeleccionada() {
        if (armasMostrar.length > 0 && armaSeleccionada < armasMostrar.length && armaSeleccionada > -1){
            nombreArmaSeleccionada = listaArmas.getItems().get(armaSeleccionada).toString();
        } else {
            nombreArmaSeleccionada = "";
        }
    }

    private void mostrarInfoArmaEnLabels() {
        if (armasMostrar.length > 0 && armaSeleccionada >= 0 && !nombreArmaSeleccionada.equals("")){
            Arma arma = armero.getArmaPorNombre(nombreArmaSeleccionada);
            nombreArma.setText(arma.getNombre());
            categoriaArma.setText(arma.getCategoria());
            calidadArma.setText(arma.getCalidad());
            tipoDanhoArma.setText(arma.getTipoDanho());
            pesoArma.setText(String.valueOf(arma.getPeso()));
            costeArma.setText(String.valueOf(arma.getCoste()));
        }
    }
    
    private void actualizarInfo() {
        listaMostrarArmasConFiltro();
        if (armasMostrar.length >0) {
            EstablecerNombreArmaSeleccionada();
            mostrarInfoArmaEnLabels();
        } else {
            listaArmas.getItems().clear();
            limpiarInfoArmaEnLabels();
        }
    }
    
    // ^^^ MOSTRAR INFORMACIÓN DEL ARMA SELECCIONADA ^^^
    
    // VVV BORRADO DATOS DE TEXTFIELD Y LABELS VVV

    @FXML
    private void limpiarMenu(ActionEvent event) {
        limpiarMenuCrear();
    }

    @FXML
    private void limpiarMenuBuscar(ActionEvent event) {
        buscarNombre.setText("");
        buscarCategoria.setText("");
        buscarTipoDanho.setText("");
        buscarCalidad.setText("");
        buscarPesoMin.setText("");
        buscarPesoMax.setText("");
        buscarCosteMin.setText("");
        buscarCosteMax.setText("");
        actualizarInfo();
    }
    
    public void limpiarMenuCrear() {
        nuevaNombre.clear();
        nuevaCategoria.clear();
        nuevaCalidad.clear();
        nuevaTipoDanho.clear();
        nuevaPeso.clear();
    }
    
    private void limpiarInfoArmaEnLabels() {
        nombreArma.setText("");
        categoriaArma.setText("");
        calidadArma.setText("");
        tipoDanhoArma.setText("");
        pesoArma.setText("");
        costeArma.setText("");
    }
    
    // ^^^ BORRADO DATOS DE TEXTFIELD Y LABELS ^^^
    
    // VVV ALERTAS VVV
    
    public static void alertErrorConectarConBaseDeDatos(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al conectar con la base de datos");
        alert.setContentText("Para mas información consulte al servicio técnico");

        alert.showAndWait();
    }

    public void alertaErrorConDouble(String doubleInvalido) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Advertencia");
        alert.setHeaderText("Introduzca un número válido en la casilla '" + doubleInvalido + "'");
        alert.setContentText("Que no se vuelva a repetir!!");
        
        alert.showAndWait();
    }
    
    // ^^^ ALERTAS ^^^
    
}