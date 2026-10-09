package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {

public static final int MONTO_1 = 500000;
public static final int MONTO_2 = 1999000;
public static final int MONTO_3 = 1000;
public static final double MONTO_MAXIMO = 2500000;

@Test
void testMontoCreditoValido() {
    Factura factura = crearFactura();
    Credito credito = new Credito();
    credito.setFactura(factura);

    assertTrue(credito.getFactura().calcularTotal() <= MONTO_MAXIMO,
            "El monto del crédito no debe superar los $2.500.000");
}

@Test
void testSumaDetallesIgualTotalFactura() {
    Factura factura = crearFactura();

    double sumaDetalles = 0;

    for (Detalle detalle : factura.getDetalles()) {
        sumaDetalles += detalle.getImporte();
    }

    assertEquals(sumaDetalles, factura.calcularTotal(), 0.001,
            "La suma de los importes debe coincidir con el total de la factura");
}

@Test
void testCompraNoSuperaLimiteCredito() {
    Factura factura = crearFactura();

    Cliente cliente = new Cliente(
            12345678L, "Cliente de prueba", "Direccion de prueba", "3884000000");

    TarjetaCredito tarjeta = new TarjetaCredito();
    tarjeta.setCliente(cliente);
    tarjeta.setLimiteCompra(2500000);

    Credito credito = new Credito();
    credito.setFactura(factura);
    credito.setTarjetaCredito(tarjeta);

    double totalCompra = factura.calcularTotal();

    assertTrue(totalCompra <= MONTO_MAXIMO,
            "La compra no debe superar los $2.500.000");

    assertTrue(totalCompra <= tarjeta.getLimiteCompra(),
            "La compra no debe superar el límite disponible de la tarjeta");
}

private Factura crearFactura() {
    Factura factura = new Factura();
    factura.setDetalles(crearListaDetalles());
    return factura;
}

private List<Detalle> crearListaDetalles() {
    List<Detalle> listaDetalles = new ArrayList<>();

    Detalle detalle1 = new Detalle();
    detalle1.setImporte(MONTO_1);

    Detalle detalle2 = new Detalle();
    detalle2.setImporte(MONTO_2);

    Detalle detalle3 = new Detalle();
    detalle3.setImporte(MONTO_3);

    listaDetalles.add(detalle1);
    listaDetalles.add(detalle2);
    listaDetalles.add(detalle3);

    return listaDetalles;
}
```

}
