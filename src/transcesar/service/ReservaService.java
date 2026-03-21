
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

        // Regla: un pasajero no puede tener mas de una reserva activa
        // para el mismo vehiculo en la misma fecha
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

    public String cancelarReserva(String codigo) {
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getCodigo().equals(codigo)) {
                if (!r.getEstado().equals(Reserva.ACTIVA)) {
                    return "ERROR: Solo se pueden cancelar reservas activas.";
                }
                r.setEstado(Reserva.CANCELADA);
                reservaDAO.actualizarEstado(codigo, Reserva.CANCELADA);
                return "Reserva " + codigo + " cancelada exitosamente.";
            }
        }
        return "ERROR: No se encontro una reserva con el codigo " + codigo;
    }

    public String convertirEnTicket(String codigo, String origen, String destino) {
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getCodigo().equals(codigo)) {
                if (!r.getEstado().equals(Reserva.ACTIVA)) {
                    return "ERROR: Solo se pueden convertir reservas activas.";
                }
               
                String resultado = ticketService.venderTicket(
                        r.getPasajero().getCedula(),
                        r.getVehiculo().getPlaca(),
                        origen, destino);

                if (resultado.startsWith("ERROR")) {
                    return resultado;
                }

                r.setEstado(Reserva.CONVERTIDA);
                reservaDAO.actualizarEstado(codigo, Reserva.CONVERTIDA);
                return "Reserva convertida en ticket.\n" + resultado;
            }
        }
        return "ERROR: No se encontro una reserva con el codigo " + codigo;
    }

    public ArrayList<Reserva> listarReservasActivas() {
        ArrayList<Reserva> activas = new ArrayList<>();
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getEstado().equals(Reserva.ACTIVA)) {
                activas.add(reservas.get(i));
            }
        }
        return activas;
    }

    public ArrayList<Reserva> historialPasajero(String cedula) {
        ArrayList<Reserva> historial = new ArrayList<>();
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getPasajero().getCedula().equals(cedula)) {
                historial.add(reservas.get(i));
            }
        }
        return historial;
    }

    public int verificarReservasVencidas() {
        int canceladas = 0;
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getEstado().equals(Reserva.ACTIVA)) {
                try {
                    LocalDateTime fechaCreacion = LocalDateTime.parse(r.getFechaCreacion(), formato);
                    long horas = java.time.Duration.between(fechaCreacion, ahora).toHours();
                    if (horas > 24) {
                        r.setEstado(Reserva.CANCELADA);
                        reservaDAO.actualizarEstado(r.getCodigo(), Reserva.CANCELADA);
                        canceladas++;
                    }
                } catch (Exception e) {
                    System.out.println("Advertencia: no se pudo verificar la reserva " + r.getCodigo());
                }
            }
        }
        return canceladas;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
}