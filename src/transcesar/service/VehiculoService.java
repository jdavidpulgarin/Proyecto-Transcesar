/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;

/**
 *
 * @author Pulgarin
 */
import transcesar.dao.VehiculoDAO;
import transcesar.model.Vehiculo;
import transcesar.model.Buseta;
import transcesar.model.MicroBus;
import transcesar.model.Bus;
import java.util.List;
public class VehiculoService {

    private VehiculoDAO vehiculoDAO;

    public VehiculoService() {
        this.vehiculoDAO = new VehiculoDAO();
    }

    
    private String obtenerArchivo(Vehiculo v) {
        if (v instanceof Buseta) return "buseta.txt";
        if (v instanceof MicroBus) return "microbus.txt";
        return "bus.txt";
    }

   
    public void registrarVehiculo(Vehiculo v) {
        if (v.getPlaca() == null || v.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía");
        }
        if (v.getRuta() == null || v.getRuta().trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía");
        }
        if (vehiculoDAO.existePlaca(v.getPlaca(), obtenerArchivo(v))) {
            throw new IllegalArgumentException("Ya existe un vehículo con la placa: "
                    + v.getPlaca());
        }
        vehiculoDAO.guardar(v, obtenerArchivo(v));
        System.out.println("✅ Vehículo registrado exitosamente.");
    }

  
    public String buscarVehiculoPorPlaca(String placa, String archivo) {
        String resultado = vehiculoDAO.buscarPorPlaca(placa, archivo);
        if (resultado == null) {
            throw new IllegalArgumentException("No se encontró vehículo con placa: "
                    + placa);
        }
        return resultado;
    }

   
    public List<String> listarVehiculos(String archivo) {
        List<String> lista = vehiculoDAO.listarTodos(archivo);
        if (lista.isEmpty()) {
            System.out.println("⚠️ No hay vehículos registrados en " + archivo);
        }
        return lista;
    }

  
    public void eliminarVehiculo(String placa, String archivo) {
        if (!vehiculoDAO.existePlaca(placa, archivo)) {
            throw new IllegalArgumentException("No existe vehículo con placa: "
                    + placa);
        }
        vehiculoDAO.eliminar(placa, archivo);
        System.out.println("✅ Vehículo eliminado exitosamente.");
    }

    
    public void actualizarVehiculos(Vehiculo v, String placaAnterior) {
        String archivo = obtenerArchivo(v);
        if (!vehiculoDAO.existePlaca(placaAnterior, archivo)) {
            throw new IllegalArgumentException("No existe vehículo con placa: "
                    + placaAnterior);
        }
        vehiculoDAO.eliminar(placaAnterior, archivo);
        vehiculoDAO.guardar(v, archivo);
        System.out.println("✅ Vehículo actualizado exitosamente.");
    }

    
    public boolean verificarCupos(String placa, String archivo) {
        String linea = vehiculoDAO.buscarPorPlaca(placa, archivo);
        if (linea == null) {
            throw new IllegalArgumentException("No existe vehículo con placa: "
                    + placa);
        }
        String[] datos = linea.split(";");
        int capacidad = Integer.parseInt(datos[2]);
        int actuales = Integer.parseInt(datos[3]);
        return actuales < capacidad;
    }
}