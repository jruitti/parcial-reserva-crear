package reserva.exception;

public class IdReservaObligatorioException extends RuntimeException {

    public IdReservaObligatorioException() {
        super("El identificador de la reserva es obligatorio.");
    }
}
