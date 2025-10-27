package Gestion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;



public class ExportacionJSON {
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

    
    private static final String INDENT = "  ";

    public static void main(String[] args) {
        List<ExportacionCliente> ExportacionClientes = Arrays.asList(
                new ExportacionCliente(1, "Juan", "García López", 20, 8.5),
                new ExportacionCliente(2, "María", "Rodríguez", 19, 9.2),
                new ExportacionCliente(3, "Pedro", "Martínez", 21, 7.8),
                new ExportacionCliente(4, "Ana", "López", 20, 8.9),
                new ExportacionCliente(5, "Carlos", "Sánchez", 22, 6.5)
        );

        try{
            exportarJSON(ExportacionClientes);
            System.out.println("Archivo creado");
        } catch (IOException e) {
            System.out.println("Error al cargar clientes");
        }
    }

	 public static boolean exportarJSON(List<ExportacionCliente> estudiantes) throws IOException {
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


	        //Exportar
	        String estudiantesJSON = "src/Clientes.json";
	        BufferedWriter writer = null;

	        try {
	            writer = new BufferedWriter(new FileWriter("src/datos/Clientes.json"));

	            System.out.println("\n Exportando productos a JSON...");
	            System.out.println("   Archivo: " + estudiantesJSON);


	            writer.write("{");
	            writer.newLine();

	            // OBJETO CATÁLOGO
	            writer.write(INDENT + "\"catalogo\": {");
	            writer.newLine();

	            // METADATA
	            escribirMetadataJSON(writer, estudiantes);
	            writer.write(","); // Coma porque vienen más elementos
	            writer.newLine();

	            // ARRAY DE PRODUCTOS
	            escribirEstudiantesJSON(writer, estudiantes);
	            writer.write(","); // Coma porque viene el resumen
	            writer.newLine();

	            // RESUMEN
	            escribirResumenJSON(writer, estudiantes);
	            // NO ponemos coma aquí porque es el último elemento
	            writer.newLine();

	            // CERRAR OBJETO CATÁLOGO
	            writer.write(INDENT + "}");
	            writer.newLine();

	            // CERRAR OBJETO RAÍZ
	            writer.write("}");
	            writer.newLine();

	            System.out.println("Total de productos exportados: " + estudiantes.size());
	            return true;

	        } catch (IOException e) {
	            System.out.println("ERROR al escribir el archivo JSON.");
	            System.out.println("   Detalles: " + e.getMessage());
	            return false;

	        } finally {
	            // SIEMPRE cerrar el archivo
	            try {
	                if (writer != null) {
	                    writer.close();
	                }
	            } catch (IOException e) {
	                System.out.println("Advertencia: Error al cerrar el archivo.");
	            }
	        }
	    }

	    private static void escribirMetadataJSON(BufferedWriter writer, List<ExportacionCliente> estudiantes) throws IOException {
	        String indent2 = INDENT + INDENT;

	        writer.write(indent2 + "\"metadata\": {");
	        writer.newLine();

	        writer.write(indent2 + INDENT + "\"version\": \"1.0\",");
	        writer.newLine();

	        writer.write(indent2 + INDENT + "\"fecha\": \"" +
	                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\",");
	        writer.newLine();

	        writer.write(indent2 + INDENT + "\"totalEstudiantes\": " + estudiantes.size());
	        writer.newLine();

	        writer.write(indent2 + "}");
	    }

	    private static void escribirEstudiantesJSON(BufferedWriter writer, List<ExportacionCliente> estudiantes)
	            throws IOException {

	        String indent2 = INDENT + INDENT;
	        String indent3 = indent2 + INDENT;

	        // Abrimos el array de productos
	        writer.write(indent2 + "\"estudiantes\": [");
	        writer.newLine();

	        // Escribimos cada producto
	        int totalEstudiantes = estudiantes.size();
	        for (int i = 0; i < totalEstudiantes; i++) {
	            ExportacionCliente est = estudiantes.get(i);

	            // Abrimos el objeto producto
	            writer.write(indent3 + "{");
	            writer.newLine();

	            escribirEstudianteJSON(writer, est);

	            // Cerramos el objeto producto
	            writer.write(indent3 + "}");

	            // Añadimos coma si NO es el último elemento del array
	            if (i < totalEstudiantes - 1) {
	                writer.write(",");
	            }
	            writer.newLine();
	        }

	        // Cerramos el array
	        writer.write(indent2 + "]");
	    }

	    private static void escribirEstudianteJSON(BufferedWriter writer, ExportacionCliente est) throws IOException {
	        String indent4 = INDENT + INDENT + INDENT + INDENT;

	        writer.write(indent4 + "\"id\": " + est.getId() + ",");
	        writer.newLine();

	        writer.write(indent4 + "\"nombre\": \"" + escaparJSON(est.getNombre()) + "\",");
	        writer.newLine();

	        writer.write(indent4 + "\"apellidos\": \"" + escaparJSON(est.getApellidos()) + "\",");
	        writer.newLine();

	        writer.write(indent4 + "\"edad\": " + est.getEdad() + ",");
	        writer.newLine();

	        writer.write(indent4 + "\"notas\": " + String.format(Locale.US,"%.2f", est.getNotas()));
	        writer.newLine();
	    }

	    private static String escaparJSON(String texto) {
	        if (texto == null || texto.isEmpty()) {
	            return "";
	        }

	        // IMPORTANTE: El orden importa - escapar \ primero
	        return texto.replace("\\", "\\\\")   // Barra invertida primero
	                .replace("\"", "\\\"")    // Comillas dobles
	                .replace("\n", "\\n")     // Nueva línea
	                .replace("\r", "\\r")     // Retorno de carro
	                .replace("\t", "\\t");    // Tabulador
	    }

	    private static void escribirResumenJSON(BufferedWriter writer, List<ExportacionCliente> estudiantes) throws IOException {
	        String indent2 = INDENT + INDENT;
	        writer.write(indent2 + "\"resumen\": {}");
	    }

}
