package Gestion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExportacionCSV{
    static class ExportacionCliente{
        int id;
        String nombre;
        String apellidos;
        int edad;
        double notas;

        public ExportacionCliente(int id, String nombre,String apellidos, int edad,double notas){
            this.id = id;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.edad = edad;
            this.notas = notas;
        }
        public int getId() {return id;}

        public String getNombre() {return nombre;}

        public String getApellidos() {return apellidos;}

        public int getEdad() {return edad;}

        public double getNotas() {return notas;}
    }

    private static final String SEPARADOR = ";";

    public static void main(String[] args) {
        List<ExportacionCliente> ExportacionClientes = Arrays.asList(
                new ExportacionCliente(1, "Juan", "García López", 20, 8.5),
                new ExportacionCliente(2, "María", "Rodríguez", 19, 9.2),
                new ExportacionCliente(3, "Pedro", "Martínez", 21, 7.8),
                new ExportacionCliente(4, "Ana", "López", 20, 8.9),
                new ExportacionCliente(5, "Carlos", "Sánchez", 22, 6.5)
        );

        try{
            exportarCSV(ExportacionClientes);
            System.out.println("Archivos creados");
        } catch (IOException e) {
            System.out.println("Error al cargar clientes");
        }
    }

    //ExportarCSV
    public static boolean exportarCSV(List<ExportacionCliente> estudiantes) throws IOException {
        //Validar
        if (estudiantes == null || estudiantes.isEmpty()) {
            System.out.println("ERROR: No hay estudiantes para exportar.");
            return false;
        }


        //Crear Directorio
        File datos = new File("src/datos");
        if (!datos.exists()) {
            datos.mkdirs();
            System.out.println("Directorio 'src/datos' creado");
        } else {
            System.out.println("Ya existe el directorio 'src/datos'.");
        }

        //exportar
        BufferedWriter writer= null;
        String estudiantesCSV= "src/Datos/Clientes.csv";

        try {
            writer = new BufferedWriter(new FileWriter("src/Datos/Clientes.csv"));
            System.out.println("Exportando estudiantes a CSV:" + estudiantesCSV);

            //Escribir encabezado
            escribirEncabezadoCSV(writer);

            //ESCRIBIR CADA PRODUCTO
            for(ExportacionCliente est: estudiantes){
                escribirEstudianteCSV(writer, est);
            }


            //ESCRIBIR RESUMEN
            escribirResumenCSV(writer, estudiantes);

            System.out.println("Exportación completada exitosamente.");
            System.out.println("   Total de productos exportados: " + estudiantes.size());
        }catch(IOException e){
            System.out.println("Error al escribir el archivo CSV");
            return false;
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Advertencia: Error al cerrar el archivo.");
            }
        }

        return true;
    }

    //Escribe el encabezado del CSV
    private static void escribirEncabezadoCSV(BufferedWriter writer) throws IOException {
        writer.write("# CATÁLOGO DE ESTUDIANTES - FORMATO CSV");
        writer.newLine();
        writer.write("# Generado el: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        writer.newLine();
        writer.newLine(); // Línea en blanco para separar

        // Encabezado con nombres de columnas
        writer.write("ID" + SEPARADOR);
        writer.write("Nombre" + SEPARADOR);
        writer.write("Apellidos" + SEPARADOR);
        writer.write("Edad" + SEPARADOR);
        writer.write("Notas");
        writer.newLine();
    }

    //Escribe un producto como una línea del CSV
    private static void escribirEstudianteCSV(BufferedWriter writer, ExportacionCliente est) throws IOException {
        writer.write(est.getId() + SEPARADOR);
        writer.write(escaparCSV(est.getNombre()) + SEPARADOR);
        writer.write(escaparCSV(est.getApellidos()) + SEPARADOR);
        writer.write(est.getEdad() + SEPARADOR);
        writer.write(String.format("%.2f", est.getNotas()));
        writer.newLine();
    }

    private static String escaparCSV(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }

        // Si contiene el separador, comillas o saltos de línea, debemos escapar
        if (texto.contains(SEPARADOR) || texto.contains("\"") || texto.contains("\n")) {
            // Duplicamos las comillas y encerramos todo entre comillas
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }

        return texto;
    }

    private static void escribirResumenCSV(BufferedWriter writer, List<ExportacionCliente> estudiantes) throws IOException {
        // Método vacío para mantener funcionalidad
    }

   
}