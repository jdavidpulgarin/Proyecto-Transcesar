
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

  private void cargarReservasEnMemoria() {
        List<String[]> datos = reservaDAO.cargarDatosReservas();
        for (int i = 0; i < datos.size(); i++) {
            String[] fila = datos.get(i);
            try {
                String codigo = fila[0];
                Pasajero pasajero = personaService.buscarPasajeroPorCedula(fila[1]);
                Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPlaca(fila[2]);
                String fechaCreacion = fila[3];
                String fechaViaje = fila[4];
                String estado = fila[5];
                if (pasajero != null && vehiculo != null) {
                    Reserva r = new Reserva(codigo, pasajero, vehiculo, fechaCreacion, fechaViaje);
                    r.setEstado(estado);
                    reservas.add(r);
                }
            } catch (Exception e) {
                System.out.println("Advertencia: no se pudo cargar una reserva.");
            }
        }
    }