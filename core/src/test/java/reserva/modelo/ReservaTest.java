package reserva.modelo;

import org.junit.jupiter.api.Test;
import reserva.exception.CantidadPersonasInvalidaException;
import reserva.exception.EmailContactoObligatorioException;
import reserva.exception.FechaReservaObligatoriaException;
import reserva.exception.IdReservaObligatorioException;
import reserva.exception.ReservaYaCanceladaException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservaTest {

    private final UUID id = UUID.randomUUID();
    private final LocalDate fecha = LocalDate.of(2026, 11, 10);
    private final String email = "contacto@ejemplo.com";

    @Test
    void creaUnaReservaActivaConLosDatosRecibidos() {
        Reserva reserva = Reserva.instance(id, email, fecha, 3);

        assertEquals(id, reserva.getId());
        assertEquals(email, reserva.getEmailContacto());
        assertEquals(fecha, reserva.getFecha());
        assertEquals(3, reserva.getCantidadPersonas());
        assertEquals(EstadoReserva.ACTIVA, reserva.getEstado());
    }

    @Test
    void requiereIdentificador() {
        assertThrows(IdReservaObligatorioException.class,
                () -> Reserva.instance(null, email, fecha, 3));
    }

    @Test
    void requiereFecha() {
        assertThrows(FechaReservaObligatoriaException.class,
                () -> Reserva.instance(id, email, null, 3));
    }

    @Test
    void requiereEmailDeContacto() {
        assertThrows(EmailContactoObligatorioException.class,
                () -> Reserva.instance(id, null, fecha, 3));
    }

    @Test
    void rechazaEmailVacio() {
        assertThrows(EmailContactoObligatorioException.class,
                () -> Reserva.instance(id, "", fecha, 3));
    }

    @Test
    void rechazaEmailEnBlanco() {
        assertThrows(EmailContactoObligatorioException.class,
                () -> Reserva.instance(id, "   ", fecha, 3));
    }

    @Test
    void requiereAlMenosUnaPersona() {
        assertThrows(CantidadPersonasInvalidaException.class,
                () -> Reserva.instance(id, email, fecha, 0));
    }

    @Test
    void rechazaCantidadNegativaDePersonas() {
        assertThrows(CantidadPersonasInvalidaException.class,
                () -> Reserva.instance(id, email, fecha, -1));
    }

    @Test
    void cancelarCambiaElEstadoA_cancelada() {
        Reserva reserva = Reserva.instance(id, email, fecha, 3);

        reserva.cancelar();

        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
    }

    @Test
    void noPermiteCancelarUnaReservaMasDeUnaVez() {
        Reserva reserva = Reserva.instance(id, email, fecha, 3);
        reserva.cancelar();

        assertThrows(ReservaYaCanceladaException.class, reserva::cancelar);
    }
}
