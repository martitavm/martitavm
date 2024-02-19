package cuentas;

public class RetiroNoValidoException extends Exception {

	RetiroNoValidoException(String message){
		super(message);
	}

	@Override
	public String toString() {
		return "ERROR: No se ha podido realizar el ingreso," +getMessage();
	}
	
}
