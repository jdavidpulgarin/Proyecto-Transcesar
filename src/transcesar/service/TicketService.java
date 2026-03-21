/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import transcesar.service.ReglasNegocioService;

public class TicketService {

    private TicketDAO ticketDAO;
    private List<Ticket> tickets;
    private PersonaService personaService;
    private VehiculoService vehiculoService;

    public TicketService(PersonaService personaService, VehiculoService vehiculoService) {
        this.ticketDAO = new TicketDAO();
        this.personaService = personaService;
        this.vehiculoService = vehiculoService;
        this.tickets = cargarTicketsEnMemoria();
    }

    public String venderTicket(String cedulaPasajero, String placaVehiculo,
            String origen, String destino) {

        Pasajero pasajero = personaService.buscarPasajeroPorCedula(cedulaPasajero);
        if (pasajero == null) {
            return "ERROR: Pasajero no encontrado.";
        }

        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPlaca(placaVehiculo);
        if (vehiculo == null) {
            return "ERROR: Vehículo no encontrado.";
        }

        if (!vehiculo.tieneCupos()) {
            return "ERROR: El vehículo " + placaVehiculo + " no tiene cupos disponibles.";
        }

        int nuevoId = ticketDAO.generarNuevoId();
        String fecha = LocalDate.now().toString();

        Ticket ticket = new Ticket(nuevoId, pasajero, vehiculo, fecha, origen, destino);
        ReglasNegocioService reglas = new ReglasNegocioService();
        double valorFinal = reglas.calcularValorFinal(
                vehiculo.getTarifaBase(), pasajero.calcularDescuento(), fecha);
        ticket.setValorFinal(valorFinal);

        vehiculo.agregarPasajero();
        vehiculoService.actualizarVehiculos();

        tickets.add(ticket);
        ticketDAO.guardar(ticket);

        return String.format("Ticket #%d vendido. Valor final: $%.0f", nuevoId, ticket.getValorFinal());

    }

    public void listarTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets registrados.");
            return;
        }
        for (Ticket t : tickets) {
            t.imprimirDetalle();
        }
    }

    public double calcularTotalRecaudado() {
        double total = 0;
        for (Ticket t : tickets) {
            total += t.getValorFinal();
        }
        return total;
    }

    public void mostrarPasajerosPorTipo() {
        int regular = 0, estudiante = 0, adultoMayor = 0;
        for (Ticket t : tickets) {
            switch (t.getPasajero().getTipo()) {
                case "ESTUDIANTE":
                    estudiante++;
                    break;
                case "ADULTO_MAYOR":
                    adultoMayor++;
                    break;
                default:
                    regular++;
                    break;
            }
        }
        System.out.println("======= PASAJEROS POR TIPO =======");
        System.out.println("Regular      : " + regular);
        System.out.println("Estudiante   : " + estudiante);
        System.out.println("Adulto Mayor : " + adultoMayor);
        System.out.println("Total tickets: " + tickets.size());
        System.out.println("==================================");
    }

    public void vehiculoConMasTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets registrados.");
            return;
        }
        Map<String, Integer> conteo = new HashMap<>();
        for (Ticket t : tickets) {
            String placa = t.getVehiculo().getPlaca();
            conteo.put(placa, conteo.getOrDefault(placa, 0) + 1);
        }
        String placaTop = null;
        int maxVal = 0;
        for (Map.Entry<String, Integer> e : conteo.entrySet()) {
            if (e.getValue() > maxVal) {
                maxVal = e.getValue();
                placaTop = e.getKey();
            }
        }
        System.out.println("=== VEHÍCULO CON MÁS TICKETS ===");
        System.out.println("Placa  : " + placaTop);
        System.out.println("Tickets: " + maxVal);
        System.out.println("================================");
    }

    private List<Ticket> cargarTicketsEnMemoria() {
        List<Ticket> lista = new ArrayList<>();
        List<String[]> datos = ticketDAO.cargarDatosTickets();

        for (String[] fila : datos) {
            try {
                int id = Integer.parseInt(fila[0]);
                Pasajero pasajero = personaService.buscarPasajeroPorCedula(fila[1]);
                Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPlaca(fila[2]);
                String fecha = fila[3];
                String origen = fila[4];
                String destino = fila[5];

                if (pasajero != null && vehiculo != null) {
                    Ticket t = new Ticket(id, pasajero, vehiculo, fecha, origen, destino);
                    t.setValorFinal(Double.parseDouble(fila[6]));
                    lista.add(t);
                }
            } catch (Exception e) {
                System.out.println("Advertencia: no se pudo cargar un ticket del archivo.");
            }
        }
        return lista;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
