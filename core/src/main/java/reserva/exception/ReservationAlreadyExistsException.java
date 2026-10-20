package reserva.exception;

public class ReservationAlreadyExistsException extends RuntimeException {

    public ReservationAlreadyExistsException() {
        super("Ya existe una reserva activa para ese correo y fecha.");
    }
}
