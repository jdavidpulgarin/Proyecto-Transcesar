package transcesar.service;

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

    private Vehiculo parsearVehiculo(String linea, String archivo) {
        String[] d = linea.split(";");
        String placa = d[0];
        String ruta = d[1];
        int ocupados = 0;
        if (d.length >= 5) {
            try {
                ocupados = Integer.parseInt(d[4]);
            } catch (NumberFormatException e) {
                ocupados = 0;
            }
        }
        Vehiculo v;
        switch (archivo) {
            case "buseta.txt": v = new Buseta(placa, ruta); break;
            case "microbus.txt": v = new MicroBus(placa, ruta); break;
            default: v = new Bus(placa, ruta); break;
        }
        for (int i = 0; i < ocupados; i++) {
            v.agregarPasajero();
        }
        return v;
    }

    public void registrarVehiculo(Vehiculo v) {
        if (v.getPlaca() == null || v.getPlaca().trim().isEmpty())
            throw new IllegalArgumentException("La placa no puede estar vacia");
        if (v.getRuta() == null || v.getRuta().trim().isEmpty())
            throw new IllegalArgumentException("La ruta no puede estar vacia");
        if (vehiculoDAO.existePlaca(v.getPlaca(), "buseta.txt")
                || vehiculoDAO.existePlaca(v.getPlaca(), "microbus.txt")
                || vehiculoDAO.existePlaca(v.getPlaca(), "bus.txt"))
            throw new IllegalArgumentException("Ya existe un vehiculo con la placa: " + v.getPlaca());
        vehiculoDAO.guardar(v, obtenerArchivo(v));
    }

    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        String linea = vehiculoDAO.buscarPorPlaca(placa, "buseta.txt");
        if (linea != null) return parsearVehiculo(linea, "buseta.txt");
        linea = vehiculoDAO.buscarPorPlaca(placa, "microbus.txt");
        if (linea != null) return parsearVehiculo(linea, "microbus.txt");
        linea = vehiculoDAO.buscarPorPlaca(placa, "bus.txt");
        if (linea != null) return parsearVehiculo(linea, "bus.txt");
        return null;
    }

    public void actualizarVehiculo(Vehiculo v) {
        vehiculoDAO.actualizar(v, obtenerArchivo(v));
    }

    public List<String> listarVehiculos(String archivo) {
        return vehiculoDAO.listarTodos(archivo);
    }

    public void eliminarVehiculo(String placa, String archivo) {
        if (!vehiculoDAO.existePlaca(placa, archivo))
            throw new IllegalArgumentException("No existe vehiculo con placa: " + placa);
        vehiculoDAO.eliminar(placa, archivo);
    }

    public boolean verificarCupos(String placa, String archivo) {
        String linea = vehiculoDAO.buscarPorPlaca(placa, archivo);
        if (linea == null)
            throw new IllegalArgumentException("No existe vehiculo con placa: " + placa);
        String[] datos = linea.split(";");
        int capacidad = Integer.parseInt(datos[2]);
        int actuales = datos.length >= 5 ? Integer.parseInt(datos[4]) : 0;
        return actuales < capacidad;
    }
}
