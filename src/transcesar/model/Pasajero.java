/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public abstract class Pasajero extends Persona {

    public Pasajero(String cedula, String nombre, String apellido,
                    String sexo, int edad, String telefono) {
        super(cedula, nombre, apellido, sexo, edad, telefono);
    }

    
    public abstract double calcularDescuento();

    
    public abstract String getTipo();
}