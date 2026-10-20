package reserva.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reserva.exception.PersistenceException;
import reserva.exception.ReservationAlreadyExistsException;
import reserva.modelo.EstadoReserva;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateReservationUseCaseTest {

    private ReservationRepository repository;
    private CreateReservationUseCase useCase;
    private final UUID id = UUID.randomUUID();
    private final String email = "contacto@ejemplo.com";
    private final LocalDate fecha = LocalDate.of(2026, 11, 10);

    @BeforeEach
    void setUp() {
        repository = mock(ReservationRepository.class);
        useCase = new CreateReservationUseCase(repository);
    }

    @Test
    void creaYGuardaUnaReservaNueva() {
        when(repository.existsActiveByEmailAndDate(email, fecha)).thenReturn(false);
        when(repository.save(org.mockito.ArgumentMatchers.any(Reserva.class))).thenReturn(true);

        Reserva reserva = useCase.execute(id, email, fecha, 3);

        assertEquals(id, reserva.getId());
        assertEquals(email, reserva.getEmailContacto());
        assertEquals(fecha, reserva.getFecha());
        assertEquals(3, reserva.getCantidadPersonas());
        assertEquals(EstadoReserva.ACTIVA, reserva.getEstado());
        verify(repository).save(reserva);
    }

    @Test
    void rechazaUnaReservaActivaDuplicadaParaElMismoCorreoYFecha() {
        when(repository.existsActiveByEmailAndDate(email, fecha)).thenReturn(true);

        assertThrows(ReservationAlreadyExistsException.class,
                () -> useCase.execute(id, email, fecha, 3));
    }

    @Test
    void informaCuandoNoSePuedeGuardarLaReserva() {
        when(repository.existsActiveByEmailAndDate(email, fecha)).thenReturn(false);
        when(repository.save(org.mockito.ArgumentMatchers.any(Reserva.class))).thenReturn(false);

        assertThrows(PersistenceException.class,
                () -> useCase.execute(id, email, fecha, 3));
    }
}
