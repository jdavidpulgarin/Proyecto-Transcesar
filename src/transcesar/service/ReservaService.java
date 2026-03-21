
package transcesar.service;

import transcesar.dao.ReservaDAO;
import transcesar.model.Reserva;
import transcesar.model.Pasajero;
import transcesar.model.Vehiculo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservaService {

    private ReservaDAO reservaDAO;
    private ArrayList<Reserva> reservas;
    private PersonaService personaService;
    private VehiculoService vehiculoService;
    private TicketService ticketService;

    public ReservaService(PersonaService personaService,
            VehiculoService vehiculoService, TicketService ticketService) {
        this.reservaDAO = new ReservaDAO();
        this.personaService = personaService;
        this.vehiculoService = vehiculoService;
        this.ticketService = ticketService;
        this.reservas = new ArrayList<>();
        cargarReservasEnMemoria();
        verificarReservasVencidas();
    }
}