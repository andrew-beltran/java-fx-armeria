package com.mycompany.armeriafx.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConeccionesBaseDeDatos {
    
    private static final String URL = "jdbc:mysql://localhost:3306/armero";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    
    public static void guardarArmaEnBaseDeDatos
        (String nombre, String calidad, String categoria, 
                String tipoDanho, Double peso, Double coste) throws SQLException {
            System.out.print("test");
        Connection aConnection = coneccionBaseDeDatos();

        String crearTabla = "CREATE TABLE IF NOT EXISTS Armas ("
                + "nombre VARCHAR(20) NOT NULL, "
                + "calidad VARCHAR(20), "
                + "categoria VARCHAR(20), "
                + "tipoDanho VARCHAR(20), "
                + "peso DOUBLE, "
                + "coste DOUBLE, "
                + "CONSTRAINT armas_pk PRIMARY KEY (nombre));";
        Statement stmt = aConnection.createStatement();
        stmt.executeUpdate(crearTabla);
        
        String insertardatosTabla = "INSERT INTO Armas VALUES('" 
                + nombre + "', '" + calidad + "', '" + categoria + "', '" 
                + tipoDanho + "', " + peso + ", " + coste + ")";
        
        stmt.executeUpdate(insertardatosTabla);
        stmt.close();
    }

    public static Connection coneccionBaseDeDatos() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
         
    }
        
    public static boolean comprobarArmaExistenteEnBaseDeDatos(String nombre) throws SQLException {
        Connection aConnection = coneccionBaseDeDatos();
        
        String consulta = "SELECT Armas.nombre FROM Armas";
        
        Statement sentencia = aConnection.createStatement();
        ResultSet resultado = sentencia.executeQuery(consulta);
        boolean coincidenciaEncontrada = false;
        
        while (resultado.next() && !coincidenciaEncontrada) {
            if (nombre.equals(resultado.getString("nombre"))) {
                coincidenciaEncontrada = true;
            }
        }
        
        return coincidenciaEncontrada;
    }
    
    public static Arma[] cargarArmasFavoritasDesdeBaseDeDatos() throws SQLException {
        Connection aConnection = coneccionBaseDeDatos();
        String consulta = "SELECT * FROM Armas";
        
        Statement sentencia = aConnection.createStatement();
        ResultSet resultado = sentencia.executeQuery(consulta);
        ArrayList<Arma> armasTemporal = new ArrayList<Arma>();
        while (resultado.next()) {
            Arma armaTemporal = new Arma(resultado.getString("nombre"), resultado.getString("calidad"), resultado.getString("categoria"), resultado.getString("tipoDanho"), resultado.getDouble("peso"));
            armasTemporal.add(armaTemporal);
        }
        
        Arma[] armas = new Arma[armasTemporal.size()];
        
        for (int i = 0; i < armas.length; i++) {
            armas[i] = armasTemporal.get(i);
        }
        
        return armas;
    }

}
