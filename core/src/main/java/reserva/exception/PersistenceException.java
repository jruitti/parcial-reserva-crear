package reserva.exception;

public class PersistenceException extends RuntimeException {

    public PersistenceException() {
        super("No se pudo guardar la reserva.");
    }
}
