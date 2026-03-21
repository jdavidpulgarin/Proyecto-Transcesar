/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;


import transcesar.model.Reserva;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private static final String ARCHIVO_RESERVAS = "reservas.txt";
     public void guardar(Reserva r) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_RESERVAS, true))) {
            bw.write(r.getCodigo() + ";"
                    + r.getPasajero().getCedula() + ";"
                    + r.getVehiculo().getPlaca() + ";"
                    + r.getFechaCreacion() + ";"
                    + r.getFechaViaje() + ";"
                    + r.getEstado());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("ERROR al guardar reserva: " + e.getMessage());
        }
}
}
     