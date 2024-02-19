package cuentas;

public class IngresoNoValidoException extends Exception {

	IngresoNoValidoException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "ERROR: No se ha podido realizar el retiro," + getMessage();
	}

}
