/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;

/**
 *
 * @author Pulgarin
 */
import transcesar.model.Ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


 
public class TicketDAO {

    private static final String ARCHIVO_TICKETS = "tickets.txt";

    public void guardar(Ticket t) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_TICKETS, true))) {
            bw.write(t.getId()                        + ";" +
                     t.getPasajero().getCedula()       + ";" +
                     t.getVehiculo().getPlaca()        + ";" +
                     t.getFechaCompra()                + ";" +
                     t.getOrigen()                     + ";" +
                     t.getDestino()                    + ";" +
                     t.getValorFinal());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("ERROR al guardar ticket: " + e.getMessage());
        }
    }

    
    public List<String[]> cargarDatosTickets() {
        List<String[]> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_TICKETS);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(";");
                if (partes.length == 7) lista.add(partes);
            }
        } catch (IOException e) {
            System.out.println("ERROR al cargar tickets: " + e.getMessage());
        }
        return lista;
    }

   
    public int generarNuevoId() {
        return cargarDatosTickets().size() + 1;
    }
}