package ar.edu.unju.escmi.poo.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import ar.edu.unju.escmi.tp6.servicios.VentaAhora20;

class CreditoTest {

	private Factura factura;
	private TarjetaCredito tarjeta;

	@BeforeEach
	void setUp() {
		Cliente cliente=new Cliente(12345678L,"Cliente de prueba","Direccion","3884000000");
		tarjeta=new TarjetaCredito(111111L,LocalDate.of(2030,1,1),cliente,2000000);

		Producto heladera=new Producto(1L,"Heladera",300000,"Argentina");
		Producto lavarropas=new Producto(2L,"Lavarropas",200000,"Argentina");

		factura=new Factura();
		factura.getDetalles().add(new Detalle(2,heladera));   // 600000
		factura.getDetalles().add(new Detalle(1,lavarropas)); // 200000
	}

	@Test
	void testMontoTotalCreditoNoSuperaElMaximo() {
		Credito credito=new Credito();
		credito.setFactura(factura);

		assertTrue(credito.getFactura().calcularTotal()<=VentaAhora20.MONTO_MAXIMO);
	}

	@Test
	void testSumaDetallesIgualTotalFactura() {
		double suma=0;
		for(Detalle detalle:factura.getDetalles()) {
			suma+=detalle.getImporte();
		}

		assertEquals(suma,factura.calcularTotal(),0.001);
	}

	@Test
	void testCompraNoSuperaMaximoNiDisponibleDeLaTarjeta() {
		double total=factura.calcularTotal();

		assertTrue(total<=VentaAhora20.MONTO_MAXIMO);
		assertTrue(total<=tarjeta.getLimiteCompra());
	}
}