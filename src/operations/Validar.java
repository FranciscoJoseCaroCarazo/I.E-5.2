package operations;

import java.time.LocalDate;
import Model.plataformas;

/**
 * 
 * @author Francisco José Caro Carazo
 *
 */
public class Validar {
	
	/**
	 * Metodo que valida la fecha y hace que un juego no pueda ser lanzado en una fecha que no existe
	 * @param fecha
	 * @return
	 */
	public static boolean verificar_fecha(LocalDate fecha) {
		if  (!fecha.isAfter(LocalDate.now())) {
			return true;
		}else {
			throw new IllegalArgumentException("La fecha introducida no es correcta");
		}
	}
	
	/**
	 * Método que se asegura de que ponemos una plataforma existente
	 * @param plataforma
	 * @return
	 */
	public static String validar_plataforma(String plataforma) {
		boolean temp = false;
		for (plataformas p : plataformas.values()) {
			if (p.name().equalsIgnoreCase(plataforma)) {
				temp = true;
			}
		}
		if (temp) {
			return plataforma;
		}else {
			throw new IllegalArgumentException("La plataforma no es valida");
			
		}
	}

}
