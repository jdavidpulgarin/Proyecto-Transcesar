/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;

/**
 *
 * @author Pulgarin
 */
import transcesar.model.Vehiculo;
import transcesar.model.Buseta;
import transcesar.model.MicroBus;
import transcesar.model.Bus;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public void guardar(Vehiculo v, String archivo) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(archivo, true))) {
           bw.write(v.getPlaca() + ";" +
         v.getRuta() + ";" +
         v.getCapacidadMaxima() + ";" +
         v.getTarifaBase() + ";" +
         v.getPasajerosActuales());          
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar vehículo: " + e.getMessage());
        }
    }

    public String buscarPorPlaca(String placa, String archivo) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(";");
                if (datos[0].equals(placa)) {
                    return linea;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar vehículo: " + e.getMessage());
        }
        return null;
    }

    public List<String> listarTodos(String archivo) {
        List<String> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                lista.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al listar vehículos: " + e.getMessage());
        }
        return lista;
    }

    public void eliminar(String placa, String archivo) {
        List<String> lista = listarTodos(archivo);
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(archivo, false))) {
            for (String linea : lista) {
                String[] datos = linea.split(";");
                if (!datos[0].equals(placa)) {
                    bw.write(linea);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
        }
    }

    public void actualizar(Vehiculo v, String archivo) {
        eliminar(v.getPlaca(), archivo);
        guardar(v, archivo);
    }

    public boolean existePlaca(String placa, String archivo) {
        return buscarPorPlaca(placa, archivo) != null;
    }
}
