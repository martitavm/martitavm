package cuentas;

public class FondoInversion extends Cuenta {
	private static double InteresAnual;

	public FondoInversion(int numeroCuenta, Cliente cliente) {
		super(numeroCuenta, 5000, cliente);
		this.InteresAnual = 0;
	}

	@Override
	public String toString() {
		return "Fondo Inversión" + super.toString() + "Interes anual del " + InteresAnual + " %";
	}

	@Override
	public void ingreso(double cantidad) throws IngresoNoValidoException {
		if (cantidad < 500) {
			throw new IngresoNoValidoException("Ingreso minimo 500 euros");
		}

		// se ingresa y se registra
		confirmarIngreso(cantidad);
	}

	@Override
	public void retiro(double cantidad) throws RetiroNoValidoException {
		if (saldo < 3000) {
			throw new RetiroNoValidoException("El saldo debe ser mayor de 3000 euros, el saldo actual es: " + saldo);
		}
		if (cantidad > saldo) {
			throw new RetiroNoValidoException("Saldo insuficiente, el saldo actual es: " + saldo);
		}
		if (cantidad < 500) {
			throw new RetiroNoValidoException("Retiro minimo de 500 euros");
		}
		confirmarRetiro(cantidad);
	}

	@Override
	public void verDatos() {
		String s = "";
		s += "Nºcuenta: " + numeroCuenta + " Fondo Inversión\n";
		s += "Titular: " + titular.nombreCompleto() + ", domicilio en " + titular.direccionCompleta() + "\n";
		s += "Saldo actual: " + saldo + "€\n";
		s += "------------------------  M O V I M I E N T O S  ------------------------\n";
		s += obtenerMovimientos();

		System.out.println(s);

	}
}
