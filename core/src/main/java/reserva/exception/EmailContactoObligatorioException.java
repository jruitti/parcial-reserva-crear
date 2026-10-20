package reserva.exception;

public class EmailContactoObligatorioException extends RuntimeException {

    public EmailContactoObligatorioException() {
        super("El correo de contacto es obligatorio.");
    }
}
