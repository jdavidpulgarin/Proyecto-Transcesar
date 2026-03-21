
package transcesar.service;

import transcesar.dao.RutaDAO;
import transcesar.model.Ruta;
import java.util.ArrayList;
import java.util.List;
 
public class RutaService {
 
    private RutaDAO rutaDAO;
    private ArrayList<Ruta> rutas;
 
    public RutaService() {
        this.rutaDAO = new RutaDAO();
        this.rutas = new ArrayList<>();
        cargarRutas();
    }
 
    private void cargarRutas() {
        List<Ruta> lista = rutaDAO.cargarRutas();
        for (int i = 0; i < lista.size(); i++) {
            rutas.add(lista.get(i));
        }
    }
 
    public String registrarRuta(String codigo, String ciudadOrigen,
            String ciudadDestino, double distanciaKm, int tiempoMinutos) {
 
        if (codigo == null || codigo.trim().isEmpty()) {
            return "ERROR: El codigo no puede estar vacio";
        }
        if (ciudadOrigen == null || ciudadOrigen.trim().isEmpty()) {
            return "ERROR: La ciudad de origen no puede estar vacia";
        }
        if (ciudadDestino == null || ciudadDestino.trim().isEmpty()) {
            return "ERROR: La ciudad de destino no puede estar vacia";
        }
        if (distanciaKm <= 0) {
            return "ERROR: La distancia debe ser mayor a 0";
        }
        if (tiempoMinutos <= 0) {
            return "ERROR: El tiempo debe ser mayor a 0";
        }
 
        for (int i = 0; i < rutas.size(); i++) {
            if (rutas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return "ERROR: Ya existe una ruta con el codigo " + codigo;
            }
        }
 
        Ruta nueva = new Ruta(codigo, ciudadOrigen, ciudadDestino, distanciaKm, tiempoMinutos);
        rutas.add(nueva);
        rutaDAO.guardar(nueva);
        return "Ruta registrada exitosamente.";
    }
 
    public Ruta buscarPorCodigo(String codigo) {
        for (int i = 0; i < rutas.size(); i++) {
            if (rutas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return rutas.get(i);
            }
        }
        return null;
    }
 
    public ArrayList<Ruta> getRutas() {
        return rutas;
    }
 
    public boolean hayRutas() {
        return !rutas.isEmpty();
    }
}