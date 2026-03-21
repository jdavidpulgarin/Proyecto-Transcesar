/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Katherin
 */
public class Reserva {

    private String codigo;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private String fechaCreacion;
    private String fechaViaje;
    private String estado;

    public static final String ACTIVA = "ACTIVA";
    public static final String CONVERTIDA = "CONVERTIDA";
    public static final String CANCELADA = "CANCELADA";

    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo,
            String fechaCreacion, String fechaViaje) {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.estado = ACTIVA;
}
     public String getCodigo() {
        return codigo;
    }
    public Pasajero getPasajero() {
        return pasajero;
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public String getFechaViaje() {
        return fechaViaje;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
