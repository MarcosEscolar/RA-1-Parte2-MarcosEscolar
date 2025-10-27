package Gestion;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import Gestion.ExportacionCSV.ExportacionCliente;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String rutaArchivo = "src/datos/Cuenta.dat";
        Cuenta cuenta = null;

        // Verificar si existe el archivo de datos
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            cuenta = Cuenta.cargarCuenta(rutaArchivo);
        }

        // Si no existe, crear una nueva cuenta
        if (cuenta == null) {
            System.out.println("=== Creación de nueva cuenta ===");
            System.out.print("Nombre del cliente: ");
            String Nombre = sc.nextLine();
            System.out.print("Apellidos del cliente: ");
            String apellidos = sc.nextLine();
            System.out.print("DNI del cliente: ");
            String dni = sc.nextLine();
            System.out.print("Número de cuenta: ");
            int numeroCuenta = sc.nextInt();

            Cliente cliente = new Cliente(Nombre, apellidos, dni, numeroCuenta);
            cuenta = new Cuenta(cliente, 0); // saldo inicial 0
        }

        int opcion;
        do {
            System.out.println("\n=== MENÚ CUENTA BANCARIA ===");
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Ver movimientos");
            System.out.println("5.Exportar a CSV");
            System.out.println("6. Exportar a XML");
            System.out.println("7.Exportar JSON");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");
            // comprobamos que el usuario meta un número
            while (!sc.hasNextInt()) {
                System.out.print("Introduce un número de opción válido: ");
                sc.nextLine();
            }
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Cantidad a ingresar: ");
                    while (!sc.hasNextDouble()) {
                        System.out.print("Introduce una cantidad válida (número): ");
                        sc.nextLine();
                    }
                    double ingreso = sc.nextDouble();
                    sc.nextLine(); // limpiar buffer
                    System.out.print("Introduce la fecha (por ejemplo: 19/10/2025 15:30): ");
                    String fechaIngreso = sc.nextLine();
                    cuenta.ingresar(ingreso, fechaIngreso);
                    break;

                case 2:
                    System.out.print("Cantidad a retirar: ");
                    while (!sc.hasNextDouble()) {
                        System.out.print("Introduce una cantidad válida (número): ");
                        sc.nextLine();
                    }
                    double retirada = sc.nextDouble();
                    sc.nextLine(); // limpiar buffer
                    System.out.print("Introduce la fecha (por ejemplo: 19/10/2025 15:30): ");
                    String fechaRetirada = sc.nextLine();
                    cuenta.retirar(retirada, fechaRetirada);
                    break;

                case 3:
                    System.out.println("Saldo actual: " + cuenta.getSaldo() + " €");
                    break;

                case 4:
                    System.out.println("\n=== LISTADO DE MOVIMIENTOS ===");
                    for (Movimiento m : cuenta.getMovimientos()) {
                        System.out.println(m);
                    }
                    break;
              
               //case 5:
                	//System.out.println("Exportando a CSV....");
                	//ExportacionCSV.exportarCSV();
                	
               // case 6:
                    //System.out.println("Exportando a XML....");
                    //ExportacionXML.exportarXML();
                    // case 7:
                    //System.out.println("Exportando a JSON....");
                    //ExportacionJSON.exportarJSON();
                case 8:
                    System.out.println("Guardando datos y saliendo...");
                    cuenta.guardarCuenta(rutaArchivo);
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }
}
