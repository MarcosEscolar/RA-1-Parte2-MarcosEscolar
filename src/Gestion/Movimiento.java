package Gestion;

import java.io.Serializable;

public class Movimiento implements Serializable{
	String tipo;
	double cantidad;
	String fecha;
	
	public Movimiento(String tipo, double cantidad, String fecha) {
		this.tipo= tipo;
		this.cantidad= cantidad;
		this.fecha= fecha;
	}
	
	public String gettipo() {
		return tipo;
	}
	
	public double getcantidad() {
		return cantidad;
	}
	
	public String getfecha() {
		return fecha;
	}
 
	@Override
	public String toString() {
	    return tipo + "," + cantidad + "," + fecha;
	}
}
