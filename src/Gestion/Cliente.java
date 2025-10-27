package Gestion;

public class Cliente {
	String Nombre;
	String Apellidos;
	String DNI;
	int NumeroCuenta;
	
	public Cliente(String Nombre, String Apellidos, String DNI, int NumeroCuenta ) {
		this.Nombre= Nombre;
		this.Apellidos= Apellidos;
		this.DNI= DNI;
		this.NumeroCuenta= NumeroCuenta;
	}
	
    public String getDNI() { return DNI; }
    public String getNombre() { return Nombre; }
    public int getNumeroCuenta() { return NumeroCuenta; }
    public String getApellidos() { return Apellidos; }
    
    public void setDNI(String DNI) { this.DNI = DNI; }
    public void setNombre(String Nombre) { this.Nombre = Nombre; }
    public void setApellidos(String Apellidos) { this.Apellidos = Apellidos; }
    
    @Override
    public String toString() {
    	return Nombre + ", " + Apellidos + ", " + DNI + ", " + NumeroCuenta;
    }

}
