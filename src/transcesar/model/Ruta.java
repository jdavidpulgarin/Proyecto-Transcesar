/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Katherin
 */
public class Ruta {

    private String codigo;
    private String ciudadOrigen;
    private String ciudadDestino;
    private double distanciaKm;
    private int tiempoMinutos;

    public Ruta(String codigo, String ciudadOrigen, String ciudadDestino,
            double distanciaKm, int tiempoMinutos) {
        this.codigo = codigo;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.distanciaKm = distanciaKm;
        this.tiempoMinutos = tiempoMinutos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public int getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void setTiempoMinutos(int tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public void imprimirDetalle() {
        System.out.println("Codigo   : " + codigo);
        System.out.println("Origen   : " + ciudadOrigen);
        System.out.println("Destino  : " + ciudadDestino);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Tiempo   : " + tiempoMinutos + " min");
    }

    public String toString() {
        return codigo + " | " + ciudadOrigen + " -> " + ciudadDestino
                + " | " + distanciaKm + " km | " + tiempoMinutos + " min";
    }
}