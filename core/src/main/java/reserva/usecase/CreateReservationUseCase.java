package reserva.usecase;

import reserva.exception.PersistenceException;
import reserva.exception.ReservationAlreadyExistsException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.time.LocalDate;
import java.util.UUID;

public final class CreateReservationUseCase {

    private final ReservationRepository reservationRepository;

    public CreateReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reserva execute(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        Reserva reserva = Reserva.instance(id, emailContacto, fecha, cantidadPersonas);

        if (reservationRepository.existsActiveByEmailAndDate(emailContacto, fecha)) {
            throw new ReservationAlreadyExistsException();
        }

        if (!reservationRepository.save(reserva)) {
            throw new PersistenceException();
        }

        return reserva;
    }
}
