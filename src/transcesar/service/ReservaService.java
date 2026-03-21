
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
  private String generarCodigo() {
        int numero = reservaDAO.contarReservas() + 1;
        return "R" + String.format("%03d", numero);
    }
  public String crearReserva(String cedulaPasajero, String placaVehiculo, String fechaViaje) {
        Pasajero pasajero = personaService.buscarPasajeroPorCedula(cedulaPasajero);
        if (pasajero == null) {
            return "ERROR: Pasajero no encontrado.";
        }

        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPlaca(placaVehiculo);
        if (vehiculo == null) {
            return "ERROR: Vehiculo no encontrado.";
        }

       
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getEstado().equals(Reserva.ACTIVA)
                    && r.getPasajero().getCedula().equals(cedulaPasajero)
                    && r.getVehiculo().getPlaca().equals(placaVehiculo)
                    && r.getFechaViaje().equals(fechaViaje)) {
                return "ERROR: El pasajero ya tiene una reserva activa para ese vehiculo y fecha.";
            }
        }

        int cuposOcupados = vehiculo.getPasajerosActuales();
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getEstado().equals(Reserva.ACTIVA)
                    && r.getVehiculo().getPlaca().equals(placaVehiculo)) {
                cuposOcupados++;
            }
        }
        if (cuposOcupados >= vehiculo.getCapacidadMaxima()) {
            return "ERROR: El vehiculo no tiene cupos disponibles para reservar.";
        }

        String fechaCreacion = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String codigo = generarCodigo();

        Reserva nueva = new Reserva(codigo, pasajero, vehiculo, fechaCreacion, fechaViaje);
        reservas.add(nueva);
        reservaDAO.guardar(nueva);

        return "Reserva creada exitosamente. Codigo: " + codigo;
    }