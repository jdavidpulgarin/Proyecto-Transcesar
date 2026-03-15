/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;

/**
 *
 * @author Katherin
 */
import transcesar.model.Ruta;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    private static final String ARCHIVO_RUTAS = "rutas.txt";

    public void guardar(Ruta r) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_RUTAS, true))) {
            bw.write(r.getCodigo() + ";"
                    + r.getCiudadOrigen() + ";"
                    + r.getCiudadDestino() + ";"
                    + r.getDistanciaKm() + ";"
                    + r.getTiempoMinutos());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("ERROR al guardar ruta: " + e.getMessage());
        }
    }

    public List<Ruta> cargarRutas() {
        List<Ruta> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_RUTAS);
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
                String[] p = linea.split(";");
                if (p.length == 5) {
                    String codigo = p[0];
                    String origen = p[1];
                    String destino = p[2];
                    double distancia = Double.parseDouble(p[3]);
                    int tiempo = Integer.parseInt(p[4]);
                    lista.add(new Ruta(codigo, origen, destino, distancia, tiempo));
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR al cargar rutas: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeCodigo(String codigo) {
        List<Ruta> lista = cargarRutas();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public Ruta buscarPorCodigo(String codigo) {
        List<Ruta> lista = cargarRutas();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return lista.get(i);
            }
        }
        return null;
    }
}
