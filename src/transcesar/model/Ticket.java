/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class Ticket {

    private int      id;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private String   fechaCompra;
    private String   origen;
    private String   destino;
    private double   valorFinal;

    public Ticket(int id, Pasajero pasajero, Vehiculo vehiculo, String fechaCompra, String origen, String destino) {
        this.id = id;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCompra = fechaCompra;
        this.origen = origen;
        this.destino = destino;
    }
 
    public double calcularTotal() {
        double tarifa    = vehiculo.getTarifaBase();
        double descuento = pasajero.calcularDescuento();
        return tarifa - (tarifa * descuento);
    }
    public void imprimirDetalle() {
        System.out.println("========== TICKET #" + id + " ==========");
        System.out.println("Pasajero   : " + pasajero.getNombreCompleto()
                           + " (C.C. " + pasajero.getCedula() + ")");
        System.out.println("Tipo       : " + pasajero.getTipo());
        System.out.println("Vehículo   : " + vehiculo.getPlaca()
                           + " [" + vehiculo.getTipo() + "]");
        System.out.println("Ruta       : " + vehiculo.getRuta());
        System.out.println("Origen     : " + origen);
        System.out.println("Destino    : " + destino);
        System.out.println("Fecha      : " + fechaCompra);
        System.out.printf ("Tarifa base: $%.0f%n", vehiculo.getTarifaBase());
        System.out.printf ("Descuento  : %.0f%%%n", pasajero.calcularDescuento() * 100);
        System.out.printf ("VALOR FINAL: $%.0f%n", valorFinal);
        System.out.println("==========================================");
    }

    public int getId() {
        return id;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getValorFinal() {
        return valorFinal;
    }
public void setValorFinal(double valorFinal) { this.valorFinal = valorFinal; }
}