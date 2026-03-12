/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;

import transcesar.dao.PersonaDAO;
import transcesar.model.*;

import java.util.List;


public class PersonaService {
    private PersonaDAO     personaDAO;
    private List<Conductor> conductores;
    private List<Pasajero>  pasajeros;

    public PersonaService() {
        this.personaDAO  = new PersonaDAO();
        this.conductores = personaDAO.cargarConductores();
        this.pasajeros   = personaDAO.cargarPasajeros();
    }

 
    public String registrarConductor(String cedula, String nombre, String apellido,
                                     String sexo, int edad, String telefono,
                                     String numeroLicencia, String categoriaLicencia) {
        for (Conductor c : conductores) {
            if (c.getCedula().equalsIgnoreCase(cedula))
                return "ERROR: Ya existe un conductor con la cédula " + cedula;
        }
        Conductor nuevo = new Conductor(cedula, nombre, apellido, sexo, edad, telefono,
                                        numeroLicencia, categoriaLicencia);
        conductores.add(nuevo);
        personaDAO.guardarConductor(nuevo);
        return "Conductor registrado exitosamente.";
    }

    
    public Conductor buscarConductorPorCedula(String cedula) {
        for (Conductor c : conductores)
            if (c.getCedula().equalsIgnoreCase(cedula)) return c;
        return null;
    }

 
    public boolean conductorTieneLicencia(String cedula) {
        Conductor c = buscarConductorPorCedula(cedula);
        return c != null && c.tieneLicencia();
    }

    
    public void listarConductores() {
        if (conductores.isEmpty()) {
            System.out.println("No hay conductores registrados.");
            return;
        }
        for (Conductor c : conductores) c.imprimirDetalle();
    }

   
    public String registrarPasajero(String cedula, String nombre, String apellido,
                                    String sexo, int edad, String telefono, String tipo) {
        for (Pasajero p : pasajeros)
            if (p.getCedula().equalsIgnoreCase(cedula))
                return "ERROR: Ya existe un pasajero con la cédula " + cedula;

        Pasajero nuevo;
        switch (tipo.toUpperCase()) {
            case "ESTUDIANTE":
                nuevo = new PasajeroEstudiante(cedula, nombre, apellido, sexo, edad, telefono);
                break;
            case "ADULTO_MAYOR":
                nuevo = new PasajeroAdultoMayor(cedula, nombre, apellido, sexo, edad, telefono);
                break;
            default:
                nuevo = new PasajeroRegular(cedula, nombre, apellido, sexo, edad, telefono);
                break;
        }
        pasajeros.add(nuevo);
        personaDAO.guardarPasajero(nuevo);
        return "Pasajero registrado exitosamente.";
    }

   
    public Pasajero buscarPasajeroPorCedula(String cedula) {
        for (Pasajero p : pasajeros)
            if (p.getCedula().equalsIgnoreCase(cedula)) return p;
        return null;
    }


    public void listarPasajeros() {
        if (pasajeros.isEmpty()) {
            System.out.println("No hay pasajeros registrados.");
            return;
        }
        for (Pasajero p : pasajeros) p.imprimirDetalle();
    }

    public List<Conductor> getConductores() { return conductores; }
    public List<Pasajero>  getPasajeros()   { return pasajeros;   }
}





