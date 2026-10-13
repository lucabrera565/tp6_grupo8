package ar.edu.unju.escmi.tp6.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import ar.edu.unju.escmi.tp6.servicios.VentaAhora20;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static VentaAhora20 venta = new VentaAhora20();

    public static void main(String[] args) {

        try {
            // el orden importa: las tarjetas necesitan a los clientes y el stock a los productos
            CollectionCliente.precargarClientes();
            CollectionTarjetaCredito.precargarTarjetas();
            CollectionProducto.precargarProductos();
            CollectionStock.precargarStocks();

            int opcion = 0;

            do {
                mostrarMenu();
                long leido = leerNumero("Ingrese su opcion: ");
                opcion = (leido>=1 && leido<=6) ? (int) leido : 0;

                try {
                    switch (opcion) {
                    case 1:
                        realizarVenta();
                        break;
                    case 2:
                        verComprasCliente();
                        break;
                    case 3:
                        System.out.println("\nElectrodomesticos del programa Ahora 20:");
                        CollectionProducto.mostrarProductosAhora20();
                        break;
                    case 4:
                        System.out.println("\nStock de los electrodomesticos del programa:");
                        CollectionStock.mostrarStockAhora20();
                        break;
                    case 5:
                        revisarCreditosCliente();
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opcion incorrecta. Ingrese un numero del 1 al 6.");
                    }

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("\nNo se pudo completar la operacion: " + e.getMessage());

                } catch (Exception e) {
                    System.out.println("\nOcurrio un error inesperado: " + e.getMessage());

                } finally {
                    if (opcion != 6) {
                        System.out.println("\n--- Volviendo al menu principal ---");
                    }
                }

            } while (opcion != 6);

        } catch (Exception e) {
            System.out.println("Ocurrio un error al iniciar el programa.");

        } finally {
            scanner.close();
            System.out.println("Programa finalizado.");
        }
    }

    static void mostrarMenu() {
        System.out.println("\n====== Menu Principal ======");
        System.out.println("1- Realizar una venta con Ahora 20");
        System.out.println("2- Ver compras realizadas por un cliente (DNI)");
        System.out.println("3- Lista de electrodomesticos del programa");
        System.out.println("4- Consultar stock de los electrodomesticos del programa");
        System.out.println("5- Revisar creditos de un cliente (DNI)");
        System.out.println("6- Salir");
    }

    static long leerNumero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero entero.");
            }
        }
    }

    static Cliente pedirCliente() {
        long dni = leerNumero("Ingrese el DNI del cliente: ");
        Cliente cliente = CollectionCliente.buscarCliente(dni);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con el DNI " + dni + ".");
        }
        return cliente;
    }

    static void realizarVenta() {
        Cliente cliente = pedirCliente();

        List<TarjetaCredito> tarjetas = new ArrayList<TarjetaCredito>();
        for (TarjetaCredito t : CollectionTarjetaCredito.tarjetas) {
            if (t.getCliente() != null && t.getCliente().getDni() == cliente.getDni()) {
                tarjetas.add(t);
            }
        }
        if (tarjetas.isEmpty()) {
            throw new IllegalArgumentException("El cliente no tiene tarjetas de credito registradas.");
        }
        System.out.println("\nTarjetas del cliente:");
        for (TarjetaCredito t : tarjetas) {
            System.out.println(t);
        }
        long numero = leerNumero("\nIngrese el numero de la tarjeta a usar: ");
        TarjetaCredito tarjeta = CollectionTarjetaCredito.buscarTarjetaCredito(numero);

        System.out.println("\nProductos disponibles en Ahora 20:");
        CollectionProducto.mostrarProductosAhora20();

        List<Detalle> detalles = new ArrayList<Detalle>();
        long codigo;
        do {
            codigo = leerNumero("\nCodigo del producto (0 para terminar la compra): ");
            if (codigo != 0) {
                Producto producto = CollectionProducto.buscarProducto(codigo);
                if (!CollectionProducto.esProductoAhora20(producto)) {
                    System.out.println("Ese producto no existe o no pertenece al programa.");
                } else {
                    long cantidad = leerNumero("Cantidad: ");
                    if (cantidad <= 0 || cantidad > 1000) {
                        System.out.println("Cantidad invalida.");
                    } else {
                        detalles.add(new Detalle((int) cantidad, producto));
                        System.out.println("Producto agregado.");
                    }
                }
            }
        } while (codigo != 0);

        Credito credito = venta.registrarVenta(cliente, tarjeta, detalles);
        System.out.println("\nVenta realizada con exito.");
        credito.mostrarCredito();
    }

    static void verComprasCliente() {
        Cliente cliente = pedirCliente();
        List<Factura> compras = cliente.consultarCompras();
        if (compras == null || compras.isEmpty()) {
            System.out.println("El cliente no tiene compras registradas.");
            return;
        }
        for (Factura fac : compras) {
            System.out.println(fac);
            System.out.println("TOTAL: $" + fac.calcularTotal());
        }
    }

    static void revisarCreditosCliente() {
        Cliente cliente = pedirCliente();
        List<Credito> creditos = CollectionCredito.buscarCreditosPorDni(cliente.getDni());
        if (creditos.isEmpty()) {
            System.out.println("El cliente no tiene creditos registrados.");
            return;
        }
        for (Credito cre : creditos) {
            cre.mostrarCredito();
        }
    }
}