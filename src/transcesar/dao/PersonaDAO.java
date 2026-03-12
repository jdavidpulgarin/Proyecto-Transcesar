/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;

/**
 *
 * @author Pulgarin
 */
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.model.PasajeroAdultoMayor;
import transcesar.model.PasajeroEstudiante;
import transcesar.model.PasajeroRegular;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PersonaDAO {

    private static final String ARCHIVO_CONDUCTORES = "conductores.txt";
    private static final String ARCHIVO_PASAJEROS   = "pasajeros.txt";

   

    public void guardarConductor(Conductor c) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CONDUCTORES, true))) {
            bw.write(c.getCedula()   + ";" + c.getNombre()   + ";" +
                     c.getApellido() + ";" + c.getSexo()     + ";" +
                     c.getEdad()     + ";" + c.getTelefono() + ";" +
                     c.getNumeroLicencia() + ";" + c.getCategoriaLicencia());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("ERROR al guardar conductor: " + e.getMessage());
        }
    }

    public List<Conductor> cargarConductores() {
        List<Conductor> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_CONDUCTORES);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] p = linea.split(";");
                if (p.length == 8) {
                    lista.add(new Conductor(
                        p[0], p[1], p[2], p[3],
                        Integer.parseInt(p[4]), p[5], p[6], p[7]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR al cargar conductores: " + e.getMessage());
        }
        return lista;
    }

    public void guardarPasajero(Pasajero p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PASAJEROS, true))) {
            bw.write(p.getCedula()   + ";" + p.getNombre()   + ";" +
                     p.getApellido() + ";" + p.getSexo()     + ";" +
                     p.getEdad()     + ";" + p.getTelefono() + ";" +
                     p.getTipo());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("ERROR al guardar pasajero: " + e.getMessage());
        }
    }

    public List<Pasajero> cargarPasajeros() {
        List<Pasajero> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_PASAJEROS);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] p = linea.split(";");
                if (p.length == 7) {
                    String cedula   = p[0], nombre  = p[1], apellido = p[2];
                    String sexo     = p[3];
                    int    edad     = Integer.parseInt(p[4]);
                    String telefono = p[5];
                    String tipo     = p[6].toUpperCase();

                    Pasajero pasajero;
                    switch (tipo) {
                        case "ESTUDIANTE":
                            pasajero = new PasajeroEstudiante(cedula, nombre, apellido, sexo, edad, telefono);
                            break;
                        case "ADULTO_MAYOR":
                            pasajero = new PasajeroAdultoMayor(cedula, nombre, apellido, sexo, edad, telefono);
                            break;
                        default:
                            pasajero = new PasajeroRegular(cedula, nombre, apellido, sexo, edad, telefono);
                            break;
                    }
                    lista.add(pasajero);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR al cargar pasajeros: " + e.getMessage());
        }
        return lista;
    }
}