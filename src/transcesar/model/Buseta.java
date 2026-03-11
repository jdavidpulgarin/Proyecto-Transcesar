/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class Buseta extends Vehiculo {
    public Buseta(String placa, String ruta) {
        super(placa, ruta);
        this.capacidadMaxima = 19;
        this.tarifaBase = 8000;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("BUSETA | Placa: " + placa + " | Ruta: " + ruta +
            " | Cupos: " + (capacidadMaxima - pasajerosActuales) + 
            " | Tarifa: $" + tarifaBase);
    }
}