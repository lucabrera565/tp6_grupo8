package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class CollectionStock {

    public static List<Stock> stocks = new ArrayList<Stock>();

    public static void precargarStocks() {
        if (stocks.isEmpty()) {
            stocks = new ArrayList<Stock>();
            stocks.add(new Stock(12, CollectionProducto.productos.get(0)));
            stocks.add(new Stock(22, CollectionProducto.productos.get(1)));
            stocks.add(new Stock(13, CollectionProducto.productos.get(2)));
            stocks.add(new Stock(101, CollectionProducto.productos.get(3)));
            stocks.add(new Stock(87, CollectionProducto.productos.get(4)));
            stocks.add(new Stock(45, CollectionProducto.productos.get(5)));
            stocks.add(new Stock(16, CollectionProducto.productos.get(6)));
            stocks.add(new Stock(8, CollectionProducto.productos.get(7)));
            stocks.add(new Stock(5, CollectionProducto.productos.get(8)));
            stocks.add(new Stock(21, CollectionProducto.productos.get(9)));
            stocks.add(new Stock(17, CollectionProducto.productos.get(10)));
            stocks.add(new Stock(11, CollectionProducto.productos.get(11)));
            stocks.add(new Stock(8, CollectionProducto.productos.get(12)));
            stocks.add(new Stock(14, CollectionProducto.productos.get(13)));
            stocks.add(new Stock(4, CollectionProducto.productos.get(14)));
            stocks.add(new Stock(15, CollectionProducto.productos.get(15)));
            stocks.add(new Stock(28, CollectionProducto.productos.get(16)));
            stocks.add(new Stock(47, CollectionProducto.productos.get(17)));
            stocks.add(new Stock(33, CollectionProducto.productos.get(18)));
            stocks.add(new Stock(13, CollectionProducto.productos.get(19)));
        }
    }

    public static void agregarStock(Stock stock) {
        try {
            if (stock == null) {
                System.out.println("ERROR: el stock no puede ser nulo.");
                return;
            }

            if (stocks.isEmpty()) {
                stocks.add(stock);
            } else {
                Producto controlProducto = stock.getProducto();
                boolean band = true;
                int i = 0;

                for (Stock sto : stocks) {
                    if (band) {
                        if (controlProducto == sto.getProducto()) {
                            stocks.set(i, stock);
                            band = false;
                        }
                    }
                    i++;
                }

                if (band) {
                    stocks.add(stock);
                }
            }
        } catch (Exception e) {
            System.out.println("NO SE PUEDE GUARDAR EL STOCK.");
        }
    }

    public static void reducirStock(Stock stock, int cantidad) {
        if (stock == null) {
            System.out.println("ERROR: el stock no existe.");
            return;
        }

        if (cantidad <= 0) {
            System.out.println("ERROR: la cantidad debe ser mayor a cero.");
            return;
        }

        int i = stocks.indexOf(stock);

        if (i >= 0) {
            if (stock.getCantidad() >= cantidad) {
                stock.setCantidad(stock.getCantidad() - cantidad);
                stocks.set(i, stock);
            } else {
                System.out.println("ERROR: no hay suficiente stock.");
            }
        } else {
            System.out.println("ERROR: el stock no se encuentra registrado.");
        }
    }

    public static Stock buscarStock(Producto producto) {
        if (producto == null || stocks == null) {
            return null;
        }

        for (Stock sto : stocks) {
            if (sto.getProducto() == producto) {
                return sto;
            }
        }

        return null;
    }
}