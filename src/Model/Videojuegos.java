package Model;

import java.time.LocalDate;


	/**
	 * 
	 * @author Francisco José Caro Carazo
	 *
	 */
	public class Videojuegos implements java.io.Serializable{
	
	//Atrivutos
	private static final long serialVersionUID = -8758544352436550112L;
	private String nombre;
	private LocalDate fecha_lanzamiento;
	private String plataforma;
	
	//Constructores
	public Videojuegos(){
		
	}

	public Videojuegos(String nombre, LocalDate fecha_lanzamiento, String plataforma) {
		this.nombre = nombre;
		this.fecha_lanzamiento = fecha_lanzamiento;
		this.plataforma = plataforma;
		
	}
	
	//Getters y Setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha_lanzamiento() {
		return fecha_lanzamiento;
	}

	public void setFecha_lanzamiento(LocalDate fecha_lanzamiento) {
		this.fecha_lanzamiento = fecha_lanzamiento;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	@Override
	public String toString() {
		return "\nNombre del Videojuego:" + nombre + "\nPlataforma: " + plataforma + "\nFecha de lanzamiento:" + fecha_lanzamiento;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Videojuegos other = (Videojuegos) obj;
		if (fecha_lanzamiento == null) {
			if (other.fecha_lanzamiento != null)
				return false;
		} else if (!fecha_lanzamiento.equals(other.fecha_lanzamiento))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (plataforma == null) {
			if (other.plataforma != null)
				return false;
		} else if (!plataforma.equals(other.plataforma))
			return false;
		return true;
	}

	
	
}