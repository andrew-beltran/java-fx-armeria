package com.mycompany.armeriafx;

import com.mycompany.armeriafx.clases.Arma;
import com.mycompany.armeriafx.clases.Armero;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

public class ArmeroTest {
    
    Armero armero;
    String nombreArma;
    
    @Before
    public void ArmeroTest() {
        armero = new Armero(10);
        nombreArma = "Espada Dragón";
        armero.armaNueva(nombreArma, "Espada larga", "Legendaria", "Corte", 3.23);
        
    }

    @Test
    public void testGetNumMaximoArmas() {
        assertEquals(10, armero.getNumMaximoArmas());
    }

    @Test
    public void testGetArmasTotales() {
        assertEquals(1, armero.getArmasTotales());
    }

    @Test
    public void testArmaNueva() {
        armero.armaNueva("test", "Espada larga", "Legendaria", "Corte", 3.23);
        assertEquals(2, armero.getArmasTotales());
        armero.eliminarArma(1);
    }
    

    @Test
    public void testGetArmaPorNombre() {
        Arma arma = armero.getArmaPorNombre(nombreArma);
        boolean obtenido = false;
        if (arma != null){
            obtenido = true;
        }
        assertEquals(true, obtenido);
    }


}
