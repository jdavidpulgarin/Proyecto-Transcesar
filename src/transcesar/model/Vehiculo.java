/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public abstract class Vehiculo {
    protected String placa;
    protected String ruta;
    protected int capacidadMaxima;
    protected int pasajerosActuales;
    protected boolean disponible;
    protected double tarifaBase;

    public Vehiculo(String placa, String ruta) {
        this.placa = placa;
        this.ruta = ruta;
        this.pasajerosActuales = 0;
        this.disponible = true;
    }

    public boolean tieneCupos() {
        return pasajerosActuales < capacidadMaxima;
    }

    public void imprimirDetalle() {
        System.out.println("Placa: " + placa + " | Ruta: " + ruta +
            " | Cupos: " + (capacidadMaxima - pasajerosActuales) + 
            " | Tarifa: $" + tarifaBase);
    }

    // getters y setters
    public String getPlaca() { return placa; }
    public String getRuta() { return ruta; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public int getPasajerosActuales() { return pasajerosActuales; }
    public void setPasajerosActuales(int p) { this.pasajerosActuales = p; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean d) { this.disponible = d; }
    public double getTarifaBase() { return tarifaBase; }
}