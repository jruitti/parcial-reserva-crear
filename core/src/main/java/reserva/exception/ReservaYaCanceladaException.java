package reserva.exception;

public class ReservaYaCanceladaException extends RuntimeException {

    public ReservaYaCanceladaException() {
        super("La reserva ya está cancelada.");
    }
}
