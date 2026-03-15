/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;

import transcesar.model.Ticket;
import java.util.ArrayList;
import java.time.LocalDate;

public class ReporteService {

    private TicketService ticketService;

    public ReporteService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public String reportePorFecha(String fecha) {
        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();
        String texto = "=== TICKETS DEL " + fecha + " ===\n\n";
        int contador = 0;
        double total = 0;

        for (int i = 0; i < lista.size(); i++) {
            Ticket t = lista.get(i);
            if (t.getFechaCompra().equals(fecha)) {
                texto = texto + "Ticket #" + t.getId()
                        + " | " + t.getPasajero().getNombreCompleto()
                        + " | " + t.getVehiculo().getPlaca()
                        + " | $" + (int) t.getValorFinal() + "\n";
                contador++;
                total = total + t.getValorFinal();
            }
        }

        if (contador == 0) {
            texto = texto + "(Sin tickets para esa fecha)";
        } else {
            texto = texto + "\nTotal tickets : " + contador;
            texto = texto + "\nTotal recaudado: $" + (int) total;
        }
        return texto;
    }

    public String reportePorTipoVehiculo(String tipo) {
        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();
        String texto = "=== TICKETS - " + tipo.toUpperCase() + " ===\n\n";
        int contador = 0;

        for (int i = 0; i < lista.size(); i++) {
            Ticket t = lista.get(i);
            if (t.getVehiculo().getTipo().equalsIgnoreCase(tipo)) {
                texto = texto + "Ticket #" + t.getId()
                        + " | " + t.getPasajero().getNombreCompleto()
                        + " | Placa: " + t.getVehiculo().getPlaca()
                        + " | " + t.getFechaCompra()
                        + " | $" + (int) t.getValorFinal() + "\n";
                contador++;
            }
        }

        if (contador == 0) {
            texto = texto + "(Sin tickets para ese tipo de vehiculo)";
        } else {
            texto = texto + "\nTotal: " + contador + " ticket(s)";
        }
        return texto;
    }

    public String reportePorTipoPasajero(String tipo) {
        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();
        String texto = "=== TICKETS - " + tipo.toUpperCase() + " ===\n\n";
        int contador = 0;

        for (int i = 0; i < lista.size(); i++) {
            Ticket t = lista.get(i);
            if (t.getPasajero().getTipo().equalsIgnoreCase(tipo)) {
                texto = texto + "Ticket #" + t.getId()
                        + " | " + t.getPasajero().getNombreCompleto()
                        + " | " + t.getVehiculo().getPlaca()
                        + " | " + t.getFechaCompra()
                        + " | $" + (int) t.getValorFinal() + "\n";
                contador++;
            }
        }

        if (contador == 0) {
            texto = texto + "(Sin tickets para ese tipo de pasajero)";
        } else {
            texto = texto + "\nTotal: " + contador + " ticket(s)";
        }
        return texto;
    }

    public String resumenHoy() {
        String fechaHoy = LocalDate.now().toString();
        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();
        int contador = 0;
        double total = 0;

        for (int i = 0; i < lista.size(); i++) {
            Ticket t = lista.get(i);
            if (t.getFechaCompra().equals(fechaHoy)) {
                contador++;
                total = total + t.getValorFinal();
            }
        }

        String texto = "=== RESUMEN DE HOY (" + fechaHoy + ") ===\n\n"
                + "Tickets vendidos : " + contador + "\n"
                + "Total recaudado  : $" + (int) total;
        return texto;
    }
}
