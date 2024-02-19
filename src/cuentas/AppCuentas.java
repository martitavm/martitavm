package cuentas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AppCuentas {
	private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private static ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	private static Cuenta cuentaActiva = null;
	private static Scanner teclado = new Scanner(System.in);
	private static int contadorCuenta = 0;

	static void menuPrincipal() {
		System.out.println("------------------Menu Principal------------------");
		System.out.println("¿Qué acción quieres realizar?");
		System.out.println("--------------------------------------------------");
		System.out.println("1.- Crear Cliente");
		System.out.println("2.- Crear Cuenta");
		System.out.println("3.- Mantenimiento cuentas- selección de la cuenta");
		System.out.println("4.- Fin del programa");

	}

	static void menuCuenta() {
		System.out.println("------------------Menu Mantenimiento Cuenta------------------");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1.- Ingresar");
		System.out.println("2.- Retirar");
		System.out.println("3.- Ver datos de cuenta");
		System.out.println("4.- Volver a menu principal");
	}

	static void menuTipoCuenta() {
		System.out.println("Has elegido Crear una cuenta");
		System.out.println("--------------------------------------------------");
		System.out.println("Indica que tipo de cuenta quieres crear");
		System.out.println("--------------------------------------------------");
		System.out.println("1.- Cuenta Corriente");
		System.out.println("2.- Cuenta Vivienda");
		System.out.println("3.- Fondo Inversion");

	}

	static void crearCliente() {
		String nombre, apellidos, direccion, ciudad;
		LocalDate fechaNacimiento;
		System.out.println("Dime el nombre del Cliente");
		nombre = validarNombre();
		System.out.println("Dime los apellidos del Cliente");
		apellidos = validarString();
		System.out.println("Dime la direccion del Cliente");
		direccion = teclado.nextLine();
		System.out.println("Dime la ciudad del Cliente");
		ciudad = teclado.nextLine();
		System.out.println("Dime la fecha de nacimiento del Cliente");
		fechaNacimiento = validarFechaDeNacimiento();

		clientes.add(new Cliente(nombre, apellidos, direccion, ciudad, fechaNacimiento));
		System.out.println(
				"El cliente de nombre " + nombre + " y apellidos " + apellidos + " se ha creado correctamente");
	}

	static LocalDate validarFechaDeNacimiento() {
		while (true) {
			try {
				return LocalDate.parse(teclado.nextLine());
			} catch (Exception e) {
				System.out.println("ERROR: El formato de la fecha no es correcta: introduce una fecha con el siguiente "
						+ "formato AAAA-MM-DD");
			}
		}
	}

	static String validarString() {
		while (true) {
			String s = teclado.nextLine();
			if (s.matches("[a-zA-ZáéíóúÁÉÍÓÚ\\s]+")) {
				return s;
			}
			System.out.println("ERROR: Unicamente puedes introducir letras y espacios");

		}
	}

	/**
	 * Comprueba si un cliente existe en mi lista de clientes. Dos clientes son
	 * iguales si poseen el mismo nombre
	 * 
	 * @return
	 */
	static String validarNombre() {
		String nombre;
		while (true) {
			try {
				String s = validarString();
				for (Cliente c : clientes) {
					if (c.getNombre().toUpperCase().equals(s)) {
						throw new ClienteExisteException(c);
					}
				}
				return s;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	static Cliente validarCliente() {
		String nombre;
		while (true) {
			System.out.println("Dime el nombre del cliente que quiere crear la cuenta");
			for (Cliente c : clientes) {
				System.out.println(c.getNombre());
			}
			nombre = teclado.nextLine();
			for (Cliente c : clientes) {
				if (c.getNombre().equals(nombre)) {
					return c;
				}
			}
		}
	}

	static Cuenta validarCuenta(Cliente c) {
		menuTipoCuenta();
		int op;
		while (true) {
			op = Integer.parseInt(teclado.nextLine());
			switch (op) {
			case 1:
				return new CuentaCorriente(contadorCuenta++, c);
			case 2:
				return new CuentaVivienda(contadorCuenta++, c);
			case 3:
				return new FondoInversion(contadorCuenta++, c);
			default:
				System.out.println("Tipo de cuenta no valida, selecciona una opcion entre 1 y 3");
			}

		}
	}

	static void crearCuenta() {
		if (clientes.isEmpty()) {
			System.out.println("No hay clientes en la sucursal, pimero debes crear un cliente");
			return;
		}

		// obtener Cliente
		Cliente cliente = validarCliente();

		// obtener Cuenta
		Cuenta cuenta = validarCuenta(cliente);
		// añadimos la cuenta al array cuentas
		cuentas.add(cuenta);
		System.out.println("La cuenta a nombre de " + cliente.nombreCompleto() + "se ha añadido correctamente");
	}

	static void seleccionarCuenta() {
		// si no hay cuenta
		if (cuentas.isEmpty()) {
			System.out.println("No hay cuentas en la sucursal, pimero debes crear una cuenta");
			return;
		}

		// seleccionamos una cuenta
		int numCuenta = 0;
		do {
			try {
				System.out.println("Dime el numero de la cuenta");
				for (Cuenta c : cuentas) {
					System.out.println(c.toString());
				}
				numCuenta = Integer.parseInt(teclado.nextLine());
			} catch (Exception e) {
				System.out.println("Debes seleccionar un numero de cuenta valido, entre 1 y " + (contadorCuenta - 1));
			}

		} while (!validarCuenta(numCuenta));
		// mostramos el menu de las operaciones de la cuenta
		seleccionarOpcionesCuenta();

	}

	static boolean validarCuenta(int numCuenta) {
		for (Cuenta c : cuentas) {
			if (c.getNumeroCuenta() == numCuenta)
				cuentaActiva = c;
			return true;
		}
		return false;
	}

	static void seleccionarOpcionesCuenta() {
		int opCuenta = 0;
		while (opCuenta != 4) {
			try {
				menuCuenta();
				opCuenta = Integer.parseInt(teclado.nextLine());
				switch (opCuenta) {
				case 1:
					ingresar();
					break;
				case 2:
					retirar();
					break;
				case 3:
					cuentaActiva.verDatos();
					break;
				case 4:
					// Volver a menu principal
					break;
				default:
					System.out.println("Opción no válida, selecciona una opción entre 1 y 4");
				}

			} catch (Exception e) {
				System.out.println("Opción no válida, selecciona una opción entre 1 y 4");
			}
		}
	}

	static void ingresar() {
		try {
			System.out.println("Indica la cantidad a ingresar");
			double cantidad = Integer.parseInt(teclado.nextLine());
			cuentaActiva.ingreso(cantidad);

		} catch (NumberFormatException e) {
			System.out.println("Debe introducir una cantidad numerica valida");

		} catch (IngresoNoValidoException e) {
			System.out.println(e.getMessage());
			System.out.println(e);
		}
	}

	static void retirar() {
		try {
			System.out.println("Indica la cantidad a retirar");
			double cantidad = Integer.parseInt(teclado.nextLine());
			cuentaActiva.retiro(cantidad);

		} catch (NumberFormatException e) {
			System.out.println("Debe introducir una cantidad numerica valida");

		} catch (RetiroNoValidoException e) {
			System.out.println(e.getMessage());
			System.out.println(e);
		}
	}

	public static void main(String[] args) {

		int op = 0;
		while (op != 4) {
			try {
				menuPrincipal();
				op = Integer.parseInt(teclado.nextLine());

				switch (op) {
				case 1:
					System.out.println("Has seleccionado Crear Cliente");
					crearCliente();
					break;

				case 2:

					System.out.println("Has seleccionado Crear Cuenta");
					crearCuenta();
					break;

				case 3:
					System.out.println("Mantenimiento cuentas- selección de la cuenta");
					seleccionarCuenta();
					break;

				default:
					System.out.println("Opción no válida, seleccione una opción entre 1 y 4");

				}
			} catch (Exception e) {
				System.out.println("Opción no válida, seleccione una opción entre 1 y 4");
			}

		}

	}
}
