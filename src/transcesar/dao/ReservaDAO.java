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
     public void actualizarEstado(String codigo, String nuevoEstado) {
        List<String> lineas = cargarLineas();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_RESERVAS, false))) {
            for (int i = 0; i < lineas.size(); i++) {
                String linea = lineas.get(i);
                String[] partes = linea.split(";");
                if (partes.length == 6 && partes[0].equals(codigo)) {
                    bw.write(partes[0] + ";" + partes[1] + ";" + partes[2] + ";"
                            + partes[3] + ";" + partes[4] + ";" + nuevoEstado);
                } else {
                    bw.write(linea);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("ERROR al actualizar reserva: " + e.getMessage());
        }
    }
     public List<String[]> cargarDatosReservas() {
        List<String[]> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_RESERVAS);
        if (!archivo.exists()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    lista.add(partes);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR al cargar reservas: " + e.getMessage());
        }
        return lista;
    }
       private List<String> cargarLineas() {
        List<String> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_RESERVAS);
        if (!archivo.exists()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(linea.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR al leer reservas: " + e.getMessage());
        }
        return lista;
    }

    public int contarReservas() {
        return cargarDatosReservas().size();
    }
}
     