
package transcesar.model;

/**
 *
 * @author Pulgarin
 */
public class Conductor extends Persona {

    private String numeroLicencia;
    private String categoriaLicencia; // B1, B2, C1, C2

    public Conductor(String cedula, String nombre, String apellido, String sexo,
                     int edad, String telefono,
                     String numeroLicencia, String categoriaLicencia) {
        super(cedula, nombre, apellido, sexo, edad, telefono);
        this.numeroLicencia    = numeroLicencia;
        this.categoriaLicencia = categoriaLicencia;
        
        
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getCategoriaLicencia() {
        return categoriaLicencia;
    }

    public void setCategoriaLicencia(String categoriaLicencia) {
        this.categoriaLicencia = categoriaLicencia;
    }
    
    public boolean tieneLicencia() {
        return numeroLicencia !=null && !numeroLicencia.trim().isEmpty();
    }
    
    @Override
    public void imprimirDetalle() {
        System.out.println("========== CONDUCTOR ==========");
        System.out.println("Cédula      : " + cedula);
        System.out.println("Nombre      : " + getNombreCompleto());
        System.out.println("Sexo        : " + sexo);
        System.out.println("Edad        : " + edad + " años");
        System.out.println("Teléfono    : " + telefono);
        System.out.println("N° Licencia : " + numeroLicencia);
        System.out.println("Categoría   : " + categoriaLicencia);
        System.out.println("================================");
    }
}