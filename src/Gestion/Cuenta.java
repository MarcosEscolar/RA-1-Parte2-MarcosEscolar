package Gestion;

import java.io.*;
import java.util.ArrayList;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private ArrayList<Movimiento> movimientos;
    private double saldo;

    // Constructor con cliente y saldo inicial
    public Cuenta(Cliente cliente, double saldo) {
        this.cliente = cliente;
        this.movimientos = new ArrayList<>();
        this.saldo = saldo;
    }

    // ingresar con fecha pasada desde fuera
    public void ingresar(double cantidad, String fecha) {
        if (cantidad > 0) {
            saldo += cantidad;
            movimientos.add(new Movimiento("Ingreso", cantidad, fecha));
        } else {
            System.out.println("La cantidad debe ser positiva");
        }
    }

    // retirar con fecha pasada desde fuera
    public void retirar(double cantidad, String fecha) {
        if (cantidad > 0) {
            if (saldo >= cantidad) {
                saldo -= cantidad;
                movimientos.add(new Movimiento("Retirada", cantidad, fecha));
            } else {
                System.out.println("Saldo insuficiente");
            }
        } else {
            System.out.println("La cantidad no puede ser negativa");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Guarda la cuenta en un archivo (ruta pasada como parámetro)
    public void guardarCuenta(String rutaArchivo) {
        // Asegurar que la carpeta exista
        File f = new File(rutaArchivo);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error al guardar la cuenta: " + e.getMessage());
        }
    }

    // Carga la cuenta desde un archivo (ruta pasada como parámetro)
    public static Cuenta cargarCuenta(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Cuenta) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se creará una nueva cuenta.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la cuenta: " + e.getMessage());
        }
        return null;
    }
}
