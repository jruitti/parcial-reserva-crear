package reserva.output;

import reserva.modelo.Reserva;

import java.time.LocalDate;

/** Puerto de salida que el Core usa para consultar y guardar reservas. */
public interface ReservationRepository {

    boolean existsActiveByEmailAndDate(String emailContacto, LocalDate fecha);

    /** @return {@code true} si la reserva se guardó; {@code false} si no pudo guardarse. */
    boolean save(Reserva reserva);
}
