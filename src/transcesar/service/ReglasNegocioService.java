/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;
import transcesar.model.Ticket;
import java.util.ArrayList;

public class ReglasNegocioService {

    private FestivosService festivosService;

    public ReglasNegocioService() {
        festivosService = new FestivosService();
    }

    public int contarTicketsPasajeroEnFecha(ArrayList<Ticket> tickets,
            String cedula, String fecha) {
        int contador = 0;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            if (t.getPasajero().getCedula().equals(cedula)
                    && t.getFechaCompra().equals(fecha)) {
                contador++;
            }
        }
        return contador;
    }

    public String validarLimiteTickets(ArrayList<Ticket> tickets,
            String cedula, String fecha) {
        int cantidad = contarTicketsPasajeroEnFecha(tickets, cedula, fecha);
        if (cantidad >= 3) {
            return "ERROR: El pasajero ya tiene " + cantidad
                    + " ticket(s) hoy. No puede comprar mas de 3 por dia.";
        }
        return null;
    }

    public double calcularValorFinal(double tarifaBase,
            double descuentoPasajero, String fecha) {
        double tarifaConRecargo = festivosService.aplicarRecargo(tarifaBase, fecha);
        return tarifaConRecargo - (tarifaConRecargo * descuentoPasajero);
    }

    public boolean esFestivo(String fecha) {
        return festivosService.esFestivo(fecha);
    }
}