package reserva.exception;

public class CantidadPersonasInvalidaException extends RuntimeException {

    public CantidadPersonasInvalidaException() {
        super("La cantidad de personas debe ser mayor que cero.");
    }
}
