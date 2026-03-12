/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class MicroBus extends Vehiculo {
    public MicroBus(String placa, String ruta) {
        super(placa, ruta);
        this.capacidadMaxima = 25;
        this.tarifaBase = 10000;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("MICROBUS | Placa: " + placa + " | Ruta: " + ruta +
            " | Cupos: " + (capacidadMaxima - pasajerosActuales) + 
            " | Tarifa: $" + tarifaBase);
    }
}