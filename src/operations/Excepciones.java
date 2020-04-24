package operations;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * 
 * @author Francisco José Caro Carazo
 *
 */
public class Excepciones {
	Scanner entrada = new Scanner(System.in);
	boolean error = false;

	/**
	 * Metodo que controla la entrada de numeros enteros
	 * 
	 * @return int
	 */
	public int controlInt() {
		int i = 0;
		do {
			try {
				i = entrada.nextInt();
				entrada.nextLine();
				error = false;
			} catch (Exception ex) {
				System.out.println("Valor no valido. Vuelva a introducir valor:");
				entrada.nextLine();
				error = true;
			}
		} while (error || i < 0);
		return i;
	}

	/**
	 * Metodo que controla la entrada de numero double
	 * 
	 * @return double
	 */
	public double controlDouble() {
		double d = 0;
		do {
			try {
				d = entrada.nextDouble();
				entrada.nextLine();
				error = false;
			} catch (Exception ex) {
				System.out.println("Valor no valido. Vuelva a introducir valor:");
				entrada.nextLine();
				error = true;
			}
		} while (error);
		return d;
	}

	/**
	 * Metodo que controla la entrada de numero float
	 * 
	 * @return float
	 */
	public float controlFloat() {
		float f = 0;
		do {
			try {
				f = entrada.nextFloat();
				entrada.nextLine();
				error = false;
			} catch (Exception ex) {
				System.out.println("Valor no valido. Vuelva a introducir valor:");
				entrada.nextLine();
				error = true;
			}
		} while (error);
		return f;
	}
	
	/**
	 * Metodo que solo acepta caracteres que sean letras y no admite numeros.
	 * @param a
	 * @return a
	 * @throws IOException IOException
	 */
	public String textosinnumeros(String a) throws IOException {
		int cont=0;
		do {
			if (a.toLowerCase().matches("^([a-z]+[ ]?){1,2}$")==false) {
				System.out.println("Introduzca solo letras, por favor.");
				a=entrada.nextLine();
			}else {
				cont=1;
			}
		} while (cont==0);
		return a;
	}
	
}