package cuentas;

public class CuentaVivienda extends Cuenta {

	public CuentaVivienda(int numeroCuenta, Cliente cliente) {
		super(numeroCuenta, 1000, cliente);

	}

	@Override
	public String toString() {
		return "Cuenta Vivienda" + super.toString();
	}

	@Override
	public void ingreso(double cantidad) throws IngresoNoValidoException {
		if (cantidad < 10) {
			throw new IngresoNoValidoException("Ingreso minimo 10 euros");
		}

		// se ingresa y se registra
		confirmarIngreso(cantidad);
	}

	@Override
	public void retiro(double cantidad) throws RetiroNoValidoException {
		if (cantidad > 500) {
			throw new RetiroNoValidoException("Retiro maximo 500 euros");
		}
		if (cantidad > saldo) {
			throw new RetiroNoValidoException("Saldo insuficiente, el saldo actual es: " + saldo);
		}
		if (cantidad < 10) {
			throw new RetiroNoValidoException("Retiro minimo de 10 euros");
		}
		confirmarRetiro(cantidad);
	}

	@Override
	public void verDatos() {
		String s = "";
		s += "Nºcuenta: " + numeroCuenta + " Cuenta Vivienda\n";
		s += "Titular: " + titular.nombreCompleto() + ", domicilio en " + titular.direccionCompleta() + "\n";
		s += "Saldo actual: " + saldo + "€\n";
		s += "------------------------  M O V I M I E N T O S  ------------------------\n";
		s += obtenerMovimientos();

		System.out.println(s);

	}
}
