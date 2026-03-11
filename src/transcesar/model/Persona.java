
package transcesar.model;

/**
 *
 * @author Pulgarin
 */

    public abstract class Persona {

    protected String cedula;
    protected String nombre;
    protected String apellido;
    protected String sexo;      
    protected int    edad;
    protected String telefono;

    public Persona(String cedula, String nombre, String apellido, String sexo, int edad, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.edad = edad;
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido; 
}
    public abstract void imprimirDetalle();
}
    