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

    
}
