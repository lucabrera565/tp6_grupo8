package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Factura;

class CuotaTest {

    @Test
    void testListaCuotasNoEsNull() {
        Credito credito = crearCredito();

        assertNotNull(credito.getCuotas(),
                "La lista de cuotas no debe ser null");
    }

    @Test
    void testListaTiene20Cuotas() {
        Credito credito = crearCredito();
        credito.generarCuotas();

        assertEquals(20, credito.getCuotas().size(),
                "La lista debe tener 20 cuotas");
    }

    @Test
    void testCantidadCuotasNoSuperaPermitidas() {
        Credito credito = crearCredito();
        credito.generarCuotas();

        assertTrue(credito.getCuotas().size() <= 20,
                "La cantidad de cuotas no debe superar 20");
    }

    private Credito crearCredito() {
        Factura factura = new Factura();

        Credito credito = new Credito();
        credito.setFactura(factura);
        credito.setCuotas(new ArrayList<Cuota>());

        return credito;
    }
}
