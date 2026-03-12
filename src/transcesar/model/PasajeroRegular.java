/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class PasajeroRegular extends Pasajero {

    public PasajeroRegular(String cedula, String nombre, String apellido,
                           String sexo, int edad, String telefono) {
        super(cedula, nombre, apellido, sexo, edad, telefono);
    }

    @Override
    public double calcularDescuento() { return 0.0; }

    @Override
    public String getTipo() { return "REGULAR"; }

    @Override
    public void imprimirDetalle() {
        System.out.println("========== PASAJERO ==========");
        System.out.println("Cédula    : " + cedula);
        System.out.println("Nombre    : " + getNombreCompleto());
        System.out.println("Sexo      : " + sexo);
        System.out.println("Edad      : " + edad + " años");
        System.out.println("Teléfono  : " + telefono);
        System.out.println("Tipo      : Regular");
        System.out.println("Descuento : 0%");
        System.out.println("==============================");
    }
}