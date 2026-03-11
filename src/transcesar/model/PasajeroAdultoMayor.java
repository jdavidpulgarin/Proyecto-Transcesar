
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class PasajeroAdultoMayor extends Pasajero {

    public PasajeroAdultoMayor(String cedula, String nombre, String apellido,
                               String sexo, int edad, String telefono) {
        super(cedula, nombre, apellido, sexo, edad, telefono);
    }

    @Override
    public double calcularDescuento() { return 0.30; }

    @Override
    public String getTipo() { return "ADULTO_MAYOR"; }

    @Override
    public void imprimirDetalle() {
        System.out.println("========== PASAJERO ==========");
        System.out.println("Cédula    : " + cedula);
        System.out.println("Nombre    : " + getNombreCompleto());
        System.out.println("Sexo      : " + sexo);
        System.out.println("Edad      : " + edad + " años");
        System.out.println("Teléfono  : " + telefono);
        System.out.println("Tipo      : Adulto Mayor");
        System.out.println("Descuento : 30%");
        System.out.println("==============================");
    }
}