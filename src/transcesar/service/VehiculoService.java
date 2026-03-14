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
        if (v instanceof Buseta) {
            return "buseta.txt";
        }
        if (v instanceof MicroBus) {
            return "microbus.txt";
        }
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
        System.out.println(" Vehículo registrado exitosamente.");
    }

    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        String linea = vehiculoDAO.buscarPorPlaca(placa, "buseta.txt");
        if (linea != null) {
            String[] partes = linea.split(";");
            return new Buseta(partes[0], partes[1]);
        }
        linea = vehiculoDAO.buscarPorPlaca(placa, "microbus.txt");
        if (linea != null) {
            String[] partes = linea.split(";");
            return new MicroBus(partes[0], partes[1]);
        }
        linea = vehiculoDAO.buscarPorPlaca(placa, "bus.txt");
        if (linea != null) {
            String[] partes = linea.split(";");
            return new Bus(partes[0], partes[1]);
        }
        return null;
    }

    private Vehiculo parsearVehiculo(String linea, String archivo) {
        String[] d = linea.split(";");
        String placa = d[0];
        String ruta = d[1];
        int ocupados = (int) Double.parseDouble(d[3]);

        Vehiculo v;
        switch (archivo) {
            case "buseta.txt":
                v = new Buseta(placa, ruta);
                break;
            case "microbus.txt":
                v = new MicroBus(placa, ruta);
                break;
            default:
                v = new Bus(placa, ruta);
                break;
        }
        for (int i = 0; i < ocupados; i++) {
            v.agregarPasajero();
        }
        return v;
    }

    public List<String> listarVehiculos(String archivo) {
        List<String> lista = vehiculoDAO.listarTodos(archivo);
        if (lista.isEmpty()) {
            System.out.println("️ No hay vehículos registrados en " + archivo);
        }
        return lista;
    }

    public void eliminarVehiculo(String placa, String archivo) {
        if (!vehiculoDAO.existePlaca(placa, archivo)) {
            throw new IllegalArgumentException("No existe vehículo con placa: "
                    + placa);
        }
        vehiculoDAO.eliminar(placa, archivo);
        System.out.println(" Vehículo eliminado exitosamente.");
    }

    public void actualizarVehiculos() {
        for (String archivo : new String[]{"buseta.txt", "microbus.txt", "bus.txt"}) {
            List<String> lineas = vehiculoDAO.listarTodos(archivo);
            for (String linea : lineas) {
                Vehiculo v = parsearVehiculo(linea, archivo);
                vehiculoDAO.actualizar(v, archivo);
            }
        }
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
