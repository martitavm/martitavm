package cuentas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public abstract class Cuenta {

	protected int numeroCuenta;
	protected double saldo;
	protected Cliente titular;
	protected ArrayList<Movimiento> movimientos;

	//////////////////////////////////////////////////////
	// CONSTRUCTORES
	//////////////////////////////////////////////////////

	public Cuenta() {
	}

	public Cuenta(int numeroCuenta, double saldo, Cliente titular) {

		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.titular = titular;
		this.movimientos = new ArrayList<Movimiento>();

	}

	//////////////////////////////////////////////////////
	// GETTERS & SETTERS
	//////////////////////////////////////////////////////

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getTitular() {
		return titular;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	//////////////////////////////////////////////////////
	// MÉTODOS
	//////////////////////////////////////////////////////

	public abstract void ingreso(double cantidad) throws IngresoNoValidoException;

	public abstract void retiro(double cantidad) throws RetiroNoValidoException;

	public abstract void verDatos();

	public void confirmarIngreso(double cantidad) {
		// se ingresa y se registra
		saldo += cantidad;
		movimientos.add(new Movimiento(LocalDateTime.now(), cantidad, Movimiento.INGRESO));
		System.out.println("Se han ingresado correctamente " + cantidad + "€ en la cuenta " + numeroCuenta);
	}

	public void confirmarRetiro(double cantidad) {
		// se retira y se registra
		saldo -= cantidad;
		movimientos.add(new Movimiento(LocalDateTime.now(), cantidad, Movimiento.RETIRO));
		System.out.println("Se han retirado correctamente " + cantidad + "€ de la cuenta " + numeroCuenta);
	}

	/*
	 * public void retiro(double cantidad) {
	 * 
	 * // el retiro debe ser positivo if (cantidad < 0) {
	 * System.out.println("ERROR: No se puede retirar una cantidad negativa");
	 * return; }
	 * 
	 * // si no hay suficiente saldo if (cantidad > saldo) {
	 * System.out.println("ERROR: No hay suficiente saldo"); return; }
	 * 
	 * // se retira y se registra saldo -= cantidad; movimientos.add(new
	 * Movimiento(LocalDateTime.now(), cantidad, Movimiento.RETIRO));
	 * System.out.println("Se han retirado correctamente " + cantidad +
	 * "€ de la cuenta " + numeroCuenta); }
	 */
	protected String obtenerMovimientos() {
		String s = "";
		for (Movimiento m : movimientos) {
			s += m.toString() + "\n";
		}
		return s;
	}

	/*
	 * public void verDatos() {
	 * 
	 * String s = ""; s += "Nºcuenta: " + numeroCuenta + "\n"; s += "Titular: " +
	 * titular.nombreCompleto() + ", domicilio en " + titular.direccionCompleta() +
	 * "\n"; s += "Saldo actual: " + saldo + "€\n"; s +=
	 * "------------------------  M O V I M I E N T O S  ------------------------\n"
	 * ; s += obtenerMovimientos();
	 * 
	 * System.out.println(s);
	 * 
	 * }
	 */

	@Override
	public String toString() {
		return "Cuenta [numeroCuenta=" + numeroCuenta + ", titular=" + titular.nombreCompleto() + "]";
	}

	//////////////////////////////////////////////////////
	// CLASE MOVIMIENTO
	//////////////////////////////////////////////////////
	private class Movimiento {

		private static final byte INGRESO = 0;
		private static final byte RETIRO = 1;

		private LocalDateTime fechaHora;
		private double importe;
		private double saldoFinal;
		private byte tipo;

		public Movimiento(LocalDateTime fechaHora, double importe, byte tipo) {
			this.fechaHora = fechaHora;
			this.importe = importe;
			this.saldoFinal = saldo;
			this.tipo = tipo;
		}

		@Override
		public String toString() {
			return (tipo == INGRESO ? "INGRESO" : " RETIRO") + " [Fecha = "
					+ fechaHora.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) + ", hora = "
					+ fechaHora.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) + ", importe = " + importe
					+ "€" + ", saldo = " + saldoFinal + "€" + "]";
		}
	}
}