/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class Bus extends Vehiculo {
    public Bus(String placa, String ruta) {
        super(placa, ruta);
        this.capacidadMaxima = 45;
        this.tarifaBase = 15000;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("BUS | Placa: " + placa + " | Ruta: " + ruta +
            " | Cupos: " + (capacidadMaxima - pasajerosActuales) + 
            " | Tarifa: $" + tarifaBase);
    }
}