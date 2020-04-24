package operations;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import Model.Videojuegos;
/**
 * 
 * @author Francisco Jos� Caro Carazo
 *
 */
public class helper {

	static HashMap<Integer, Videojuegos> lista = new HashMap<Integer, Videojuegos>(10);
	static Excepciones ex = new Excepciones();
	static int contador = 1;

	/**
	 * M�todo que se encarga de lanzar los datos guardados si es que los hay y de
	 * lanzar el menu principla del programa
	 * 
	 * @throws IOException IOException
	 */
	public static void Menuprincipal() throws IOException {

		cargarArchivo();
		Scanner sc = new Scanner(System.in);
		int opcion = 0;

		do {
			System.out.println("==========================================");
			System.out.println("========= Gesti�n de Videojuegos =========");
			System.out.println("==========================================");
			System.out.println();
			System.out.println("1.-A�adir un videojuego.");
			System.out.println("2.-Listar videojuegos.");
			System.out.println("3.-Borrar un videojuego.");
			System.out.println("4.-Guardar datos en fichero.");
			System.out.println("5.-Recuperar datos desde fichero.");
			System.out.println();
			System.out.println("0.-Salir de la aplicacion.");
			System.out.println("==========================================");
			System.out.println("Introduzca la opcion elegida");

			opcion = ex.controlInt();
			switch (opcion) {
			case 1:
				aniadirVideojuego();
				break;
			case 2:
				leerVideojuego();
				break;
			case 3:
				borrarVideojuego();
				break;
			case 4:
				guardarArchivo();
				break;
			case 5:
				recuperarDatos();
				break;
			case 0:
				comprobarGuardado();
				break;
			default:
				System.out.println("Por favor seleccione una opcion del 0-5");
				break;
			}

		} while (opcion != 0);
	}

	/**
	 * Metodo que sirve para poder a�adir un videojuego a la lista
	 * 
	 * @throws IOException IOException
	 */
	public static void aniadirVideojuego() throws IOException {
		Scanner sc = new Scanner(System.in);
		if (lista.size() > 10) {
			System.out.println("La lista de juegos esta llena.\nPor favor borra un juego para poder crear otro.");
		} else {
			Videojuegos videojuego = new Videojuegos();
			System.out.println("Introduzca los datos del videojuego");
			String nombre;
			do {
				System.out.println("Nombre: ");
				nombre = sc.nextLine();
				videojuego.setNombre(nombre);
			} while (nombre.isEmpty());
			System.out.println("");
			String plataforma = null;
			System.out.println("Plataforma: ");
			do {
				try {
					plataforma = sc.nextLine();
					Validar.validar_plataforma(plataforma);
					videojuego.setPlataforma(plataforma);
				} catch (IllegalArgumentException e) {
					System.err.println("Plataforma no valida");
				}
			} while (videojuego.getPlataforma() == null);
			System.out.println("Fecha de lanzamiento del juego:");
			LocalDate fecha = null;
			do {
				try {
					System.out.println("Introduce el dia de lanzamiento");
					int dia = ex.controlInt();
					System.out.println("Introduce el mes de lanzamiento");
					int mes = ex.controlInt();
					System.out.println("Introduce el anio de lanzamiento");
					int anio = ex.controlInt();
					Validar.verificar_fecha(LocalDate.of(anio, mes, dia));
					fecha = LocalDate.of(anio, mes, dia);
					videojuego.setFecha_lanzamiento(fecha);
				} catch (IllegalArgumentException e) {
					System.err.println("Fecha de lanzamiento no v�lida");
				} catch (java.time.DateTimeException t) {
					System.err.println("Fecha de lanzamiento no v�lida");
				}
			} while (fecha == null);
			System.out.println("Se ha creado el videojuego");
			lista.put(saber_id() + 1, videojuego);

		}

	}

	/**
	 * Metodo que nos permite imprimir por pantalla todos los juegos que se han
	 * creado.
	 * 
	 * @throws IOException IOException
	 */
	public static void leerVideojuego() throws IOException {
		if (lista.isEmpty()) {
			System.out.println("No existe ningun videojuego");
		} else {
			lista.entrySet().stream()
					.forEach(e -> System.out.println("\n<--------Videojuego-------->\nCodigo de Juego: " + e));
		}
		System.out.println("");
		System.out.println("Se han mostrado " + lista.size() + " juegos");

	}

	/**
	 * M�todo que nos permite Borrar un videojuego que este en la lista.
	 * 
	 * @throws IOException IOException
	 */
	public static void borrarVideojuego() throws IOException {
		Scanner sc = new Scanner(System.in);
		Videojuegos temp = new Videojuegos();
		if (lista.isEmpty()) {
			System.out.println("No existe ningun videojuego");
		} else {
			System.out.println("Introduzca el id del juego a borrar");
			System.out.println("");
			Integer buscar = ex.controlInt();
			if (lista.containsKey(buscar)) {
				System.out.println("\n<--------Videojuego-------->\nCodigo de Juego: " + lista.get(buscar));
				System.out.println("\n\n�Deseea continuar con el borrado? S/N");
				String confirmar = ex.textosinnumeros(sc.nextLine());
				if (confirmar.equalsIgnoreCase("S")) {
					lista.remove(buscar);
				} else if (confirmar.equalsIgnoreCase("N")) {
					System.out.println("Cancelando Operacion");
				} else {
					System.out.println("Introduzca la opcion correcta");
				}

			} else {
				System.out.println("El ID introducido no es correcto por favor introduzca un ID valido");
			}
		}

	}

	/**
	 * Metodo que nos permite guardar los cambios que hayamos realizado en el
	 * archivo.
	 */
	public static void guardarArchivo() {
		try {

			FileOutputStream fs = new FileOutputStream("videojue.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fs);

			oos.writeObject(lista);

			if (oos != null) {
				oos.close();
				fs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Se han Guardado los datos correctamente");
	}

	/**
	 * Meotodo que se encarga de Comprobar el guardado a la hora de salir del
	 * programa.
	 * 
	 * @throws IOException IOException
	 */
	@SuppressWarnings("unchecked")
	public static void comprobarGuardado() throws IOException {
		HashMap<Integer, Videojuegos> temporal = new HashMap<Integer, Videojuegos>();
		Scanner sc = new Scanner(System.in);
		try {
			File f = null;
			ObjectInputStream Obj = null;
			FileInputStream fe = null;
			try {
				f = new File("videojue.dat");
				if (f.exists()) {
					fe = new FileInputStream(f);
					Obj = new ObjectInputStream(fe);
					while (true) {
						temporal = (HashMap<Integer, Videojuegos>) Obj.readObject();
					}
				}
			} catch (EOFException eof) {
				System.out.println("");
			} catch (FileNotFoundException fnf) {
				System.err.println("Fichero no encontrado " + fnf);
			} catch (IOException e) {
				System.err.println("Se ha producido una IOException");
				e.printStackTrace();
			} catch (Throwable e) {
				System.err.println("Error de programa: " + e);
				e.printStackTrace();
			} finally {
				if (Obj != null) {
					Obj.close();
					fe.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!lista.equals(temporal)) {
			System.out.println("Ha realizado cambios que no ha guardado en disco.\n"
					+ "Si contin�a se perder�n los cambios no guardados.\n"
					+ "�Desea continuar y guardar los datos en disco? (S/N)");

			String confirmar_accion = ex.textosinnumeros(sc.nextLine());
			if (confirmar_accion.equalsIgnoreCase("S")) {
				guardarArchivo();
				System.out.println("Se han guardado los datos. Saliendo Del Programa ...");
			} else if (confirmar_accion.equalsIgnoreCase("N")) {
				System.out.println("No se han guardado los datos. Saliendo del Programa ...");
			} else {
				System.out.println("Introduzca la opcion correcta");
			}
		}

	}

	/**
	 * Metodo que permite cargar los datos guardados al iniciar el programa
	 */
	@SuppressWarnings("unchecked")
	public static void cargarArchivo() {
		try {
			File f = null;
			ObjectInputStream Obj = null;
			FileInputStream fe = null;
			try {
				f = new File("videojue.dat");
				if (f.exists()) {
					fe = new FileInputStream(f);
					Obj = new ObjectInputStream(fe);
					while (true) {
						lista = (HashMap<Integer, Videojuegos>) Obj.readObject();
					}
				}
			} catch (EOFException eof) {
				System.out.println("");
			} catch (FileNotFoundException fnf) {
				System.err.println("Fichero no encontrado " + fnf);
			} catch (IOException e) {
				System.err.println("Se ha producido una IOException");
				e.printStackTrace();
			} catch (Throwable e) {
				System.err.println("Error de programa: " + e);
				e.printStackTrace();
			} finally {
				if (Obj != null) {
					Obj.close();
					fe.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		if (!lista.isEmpty()) {
			System.out.println("Se han cargado Datos anteriores.");
		}

	}

	/**
	 * Metodo que nos permite restaurar los datos del archivo de guardado
	 * 
	 * @throws IOException IOException
	 */
	@SuppressWarnings("unchecked")
	public static void recuperarDatos() throws IOException {
		HashMap<Integer, Videojuegos> temporal = new HashMap<Integer, Videojuegos>();
		Scanner sc = new Scanner(System.in);
		try {
			File f = null;
			ObjectInputStream Obj = null;
			FileInputStream fe = null;
			try {
				f = new File("videojue.dat");
				if (f.exists()) {
					fe = new FileInputStream(f);
					Obj = new ObjectInputStream(fe);
					while (true) {
						temporal = (HashMap<Integer, Videojuegos>) Obj.readObject();
					}
				}
			} catch (EOFException eof) {
				System.out.println("");
			} catch (FileNotFoundException fnf) {
				System.err.println("Fichero no encontrado " + fnf);
			} catch (IOException e) {
				System.err.println("Se ha producido una IOException");
				e.printStackTrace();
			} catch (Throwable e) {
				System.err.println("Error de programa: " + e);
				e.printStackTrace();
			} finally {
				if (Obj != null) {
					Obj.close();
					fe.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!lista.equals(temporal)) {
			System.out.println("Ha realizado cambios que no ha guardado en disco.\n"
					+ "Si contin�a la carga del archivo se restaurar�n los datos\n"
					+ "de disco y se perder�n los cambios no guardados.\n"
					+ "�Desea continuar con la carga y restaurar los datos del archivo? (S/N)");

			String confirmar_accion = ex.textosinnumeros(sc.nextLine());
			if (confirmar_accion.equalsIgnoreCase("S")) {
				cargarArchivo();
				System.out.println("Se han restaurado los datos.");
			} else if (confirmar_accion.equalsIgnoreCase("N")) {
				System.out.println("No se han restaurado los datos.");
			} else {
				System.out.println("Introduzca la opcion correcta");
			}

		}

	}

	/**
	 * Metodo que nos permite tener una id correlativa y de un unico uso.
	 * @return
	 */
	public static Integer saber_id() {
		Set<Integer> keys = lista.keySet();
		Integer mayor = 0;
		for (Integer key : keys) {
			if (key >= mayor) {
				mayor = key;
			}

		}

		return mayor;
	}
}