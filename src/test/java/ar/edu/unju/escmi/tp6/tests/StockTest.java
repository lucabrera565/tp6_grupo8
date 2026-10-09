
package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class StockTest {

    @BeforeEach
    public void prepararStock() {
        CollectionStock.stocks.clear();
    }

    @Test
    public void testReducirStock() {
        Producto producto = new Producto(
                1L, "Heladera", 500000, "Nacional");

        Stock stock = new Stock(15, producto);

        CollectionStock.agregarStock(stock);
        CollectionStock.reducirStock(stock, 4);

        assertEquals(11, stock.getCantidad());
    }
}