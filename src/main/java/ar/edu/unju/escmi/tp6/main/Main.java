package ar.edu.unju.escmi.tp6.main;

import java.util.Scanner;
import java.util.InputMismatchException;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            CollectionTarjetaCredito.precargarTarjetas();
            CollectionCliente.precargarClientes();
            CollectionProducto.precargarProductos();
            CollectionStock.precargarStocks();

            int opcion = 0;

            do {
                System.out.println("\n====== Menu Principal =====");
                System.out.println("1- Realizar una venta");
                System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
                System.out.println("3- Mostrar lista de los electrodomésticos");
                System.out.println("4- Consultar stock");
                System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
                System.out.println("6- Salir");
                System.out.println("Ingrese su opcion: ");

                try {
                    opcion = scanner.nextInt();

                    if (opcion < 1 || opcion > 6) {
                        System.out.println("Opcion incorrecta. Ingrese un numero del 1 al 6.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Error: debe ingresar un numero.");
                    scanner.nextLine();
                    opcion = 0;

                } finally {
                    System.out.println("Fin de la lectura de la opcion.");
                }

            } while (opcion != 6);

        } catch (Exception e) {
            System.out.println("Ocurrio un error al iniciar el programa.");

        } finally {
            scanner.close();
            System.out.println("Programa finalizado.");
        }
    }
}