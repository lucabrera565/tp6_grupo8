package ar.edu.unju.escmi.poo.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;

class CuotaTest {

	private Credito credito;

	@BeforeEach
	void setUp() {
		Producto producto=new Producto(1L,"Lavarropas",500000,"Argentina");

		Factura factura=new Factura();
		factura.getDetalles().add(new Detalle(1,producto));

		credito=new Credito();
		credito.setFactura(factura);
		credito.setCuotas(new ArrayList<Cuota>());
	}

	@Test
	void testListaCuotasNoEsNull() {
		credito.generarCuotas();
		assertNotNull(credito.getCuotas());
	}

	@Test
	void testListaTiene20Cuotas() {
		credito.generarCuotas();
		assertEquals(20,credito.getCuotas().size());
	}

	@Test
	void testCantidadCuotasNoSuperaPermitidas() {
		credito.generarCuotas();
		credito.generarCuotas(); // llamarlo dos veces no debe duplicar las cuotas
		assertTrue(credito.getCuotas().size()<=20);
	}
}