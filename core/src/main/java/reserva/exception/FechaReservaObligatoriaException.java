package reserva.exception;

public class FechaReservaObligatoriaException extends RuntimeException {

    public FechaReservaObligatoriaException() {
        super("La fecha de la reserva es obligatoria.");
    }
}
