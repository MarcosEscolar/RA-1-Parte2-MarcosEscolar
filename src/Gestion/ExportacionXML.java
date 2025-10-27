package Gestion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import Gestion.ExportacionCSV.ExportacionCliente;

public class ExportacionXML {
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

    private static final String INDENTACION = "  ";
    

    public static void main(String[] args) {
        List<ExportacionCliente> ExportacionClientes = Arrays.asList(
                new ExportacionCliente(1, "Juan", "García López", 20, 8.5),
                new ExportacionCliente(2, "María", "Rodríguez", 19, 9.2),
                new ExportacionCliente(3, "Pedro", "Martínez", 21, 7.8),
                new ExportacionCliente(4, "Ana", "López", 20, 8.9),
                new ExportacionCliente(5, "Carlos", "Sánchez", 22, 6.5)
        );

        try{
           
            exportarXML(ExportacionClientes);
            
            System.out.println("Archivo creado");
        } catch (IOException e) {
            System.out.println("Error al cargar clientes");
        }
    }
	
	public static boolean exportarXML(List<ExportacionCliente> estudiantes) throws IOException {
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
        BufferedWriter writer = null;
        String estudiantesXML = "src/datos/Clientes.xml";
        try {
            writer = new BufferedWriter(new FileWriter("src/datos/Clientes.xml"));

            //DECLARACIÓN XML
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.newLine();

            // COMENTARIO (opcional pero buena práctica)
            writer.write("<!-- Datos de estudiantes -->");
            writer.newLine();
            writer.write("<!-- Fecha: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                    " -->");
            writer.newLine();
            writer.newLine();

            // ELEMENTO RAÍZ (todo el contenido va dentro)
            writer.write("<clase>");
            writer.newLine();

            // METADATA (información sobre la clase)
            escribirMetadataXML(writer, estudiantes);

            // LISTA DE PRODUCTOS
            escribirEstudiantesXML(writer, estudiantes);

            // RESUMEN CON ESTADÍSTICAS
            escribirResumenXML(writer, estudiantes);

            // CERRAR ELEMENTO RAÍZ
            writer.write("</clase>");
            writer.newLine();

            System.out.println("Exportación XML completada exitosamente.");
            System.out.println("   Total de productos exportados: " + estudiantes.size());
            return true;

        } catch (IOException e) {
            System.out.println("ERROR al escribir el archivo XML.");
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

    private static void escribirMetadataXML(BufferedWriter writer, List<ExportacionCliente> estudiantes) throws IOException {
        writer.write(INDENTACION + "<metadata>");
        writer.newLine();

        writer.write(INDENTACION + INDENTACION + "<version>1.0</version>");
        writer.newLine();

        writer.write(INDENTACION + INDENTACION + "<fechaGeneracion>");
        writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        writer.write("</fechaGeneracion>");
        writer.newLine();

        writer.write(INDENTACION + INDENTACION + "<totalEstudiantes>");
        writer.write(String.valueOf(estudiantes.size()));
        writer.write("</totalEstudiantes>");
        writer.newLine();

        writer.write(INDENTACION + "</metadata>");
        writer.newLine();
        writer.newLine();
    }

    // Escribe todos los estudiantes dentro del XML
    private static void escribirEstudiantesXML(BufferedWriter writer, List<ExportacionCliente> estudiantes) throws IOException {
        writer.write("  <estudiantes>");
        writer.newLine();

        for (ExportacionCliente e : estudiantes) {
            writer.write("    <estudiante id=\"" + e.getId() + "\">");
            writer.newLine();
            writer.write("      <nombre>" + escaparXML(e.getNombre()) + "</nombre>");
            writer.newLine();
            writer.write("      <apellidos>" + escaparXML(e.getApellidos()) + "</apellidos>");
            writer.newLine();
            writer.write("      <edad>" + e.getEdad() + "</edad>");
            writer.newLine();
            writer.write("      <nota>" + e.getNotas() + "</nota>");
            writer.newLine();
            writer.write("    </estudiante>");
            writer.newLine();
        }

        writer.write("  </estudiantes>");
        writer.newLine();
        writer.newLine();
    }

    // Escapa caracteres especiales del XML (&, <, >, ", ')
    private static String escaparXML(String texto) {
        if (texto == null) return "";
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private static void escribirResumenXML(BufferedWriter writer, List<ExportacionCliente> estudiantes) throws IOException {

    }

}
