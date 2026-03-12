
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class PasajeroEstudiante extends Pasajero {

    public PasajeroEstudiante(String cedula, String nombre, String apellido,
                              String sexo, int edad, String telefono) {
        super(cedula, nombre, apellido, sexo, edad, telefono);
    }

    @Override
    public double calcularDescuento() { return 0.15; }

    @Override
    public String getTipo() { return "ESTUDIANTE"; }

    @Override
    public void imprimirDetalle() {
        System.out.println("========== PASAJERO ==========");
        System.out.println("Cédula    : " + cedula);
        System.out.println("Nombre    : " + getNombreCompleto());
        System.out.println("Sexo      : " + sexo);
        System.out.println("Edad      : " + edad + " años");
        System.out.println("Teléfono  : " + telefono);
        System.out.println("Tipo      : Estudiante");
        System.out.println("Descuento : 15%");
        System.out.println("==============================");
    }
}