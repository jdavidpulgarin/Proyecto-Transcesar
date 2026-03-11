/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.view;

import javax.swing.JOptionPane;

/**
 *
 * @author Pulgarin
 */
public class Menu {

    public void iniciar() {
        int opcion;
        do {
            String menu = """
                          SISTEMA TRANSCESAR
                          1. Registrar Buseta
                          2. Registrar  Bus
                          3. Registrar MicroBus
                          4. Listar todos los vehiculos
                          -- Personas --
                          5. Registrar Conductor
                          6. Registrar Pasajeto
                          -- Tickets --
                          7. Vender Tickets
                          8. Listar Tickets Vendidos
                          -- Estadisticas --
                          9. Total Recaudado
                          10. Pasajero Por Tipo
                          11. Vehiculo Con Mas Tickets
                          0. Salir 
                          
                          """;
            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));
            switch (opcion) {
                case 1 ->
                    registrarBuseta();
                case 2 ->
                    registrarBus();
                case 3 ->
                    registrarmicroBus();
                case 4 ->
                    listarVehiculos();
                case 5 ->
                    registrarConductor();
                case 6 ->
                    registrarPasajero();
                case 7 ->
                    venderTickets();
                case 8 ->
                    listarTickets();
                case 9 ->
                    totalRecaudado();
                case 10 ->
                    pasajeroTipo();
                case 11 ->
                    vehiculomTickets();
                case 0 ->
                    JOptionPane.showMessageDialog(null, "Saliendo....");
                default ->
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } while (opcion != 0);
    }

    // cree metodos aplicando encapsulamiento para los VEHICULOS
    private void registrarBuseta() {
        String placa = JOptionPane.showInputDialog("Placa de la Buseta:");
        if (placa == null) {
            return;
        }
        String ruta = JOptionPane.showInputDialog("Ruta:");
        if (ruta == null) {
            return;
        }

        JOptionPane.showMessageDialog(null, " Buseta registrada: " + placa);
    }

    private void registrarBus() {
        String placa = JOptionPane.showInputDialog("Placa del Bus:");
        if (placa == null) {
            return;
        }
        String ruta = JOptionPane.showInputDialog("Ruta:");
        if (ruta == null) {
            return;
        }

        JOptionPane.showMessageDialog(null, " Bus registrado: " + placa);
    }

    private void registrarmicroBus() {
        String placa = JOptionPane.showInputDialog("Placa del micro bus:");
        if (placa == null) {
            return;
        }
        String ruta = JOptionPane.showInputDialog("Ruta:");
        if (ruta == null) {
            return;
        }

        JOptionPane.showMessageDialog(null, "MicroBus Rgistrado: " + placa);
    }

    private void listarVehiculos() {
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText("=== VEHÍCULOS REGISTRADOS ===\n\n");

        area.append("(Sin vehículos registrado)");
        JOptionPane.showMessageDialog(null, area, "Vehículos",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // cree metodos para registrar las personas Personas
    private void registrarPasajero() {
        String cedula = JOptionPane.showInputDialog("Cédula:");
        if (cedula == null) {
            return;
        }
        String nombre = JOptionPane.showInputDialog("Nombre:");
        if (nombre == null) {
            return;
        }
        String[] tipos = {"Regular (0%)", "Estudiante (15%)", "Adulto Mayor (30%)"};
        String tipo = (String) JOptionPane.showInputDialog(null,
                "Tipo de pasajero:", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipo == null) {
            return;
        }

        JOptionPane.showMessageDialog(null, " Pasajero registrado: " + nombre);
    }

    private void registrarConductor() {
        String cedula = JOptionPane.showInputDialog("Cédula:");
        if (cedula == null) {
            return;
        }
        String nombre = JOptionPane.showInputDialog("Nombre:");
        if (nombre == null) {
            return;
        }
        String licencia = JOptionPane.showInputDialog("Número de licencia:");
        if (licencia == null) {
            return;
        }
        String[] cats = {"B1", "B2", "C1", "C2"};
        String categoria = (String) JOptionPane.showInputDialog(null,
                "Categoría:", "Licencia",
                JOptionPane.QUESTION_MESSAGE, null, cats, cats[0]);
        if (categoria == null) {
            return;
        }

        JOptionPane.showMessageDialog(null, " Conductor registrado exitosqamente: " + nombre);
    }
    // Tickets

    private void venderTickets() {
        String cedula = JOptionPane.showInputDialog("Cédula del pasajero:");
        if (cedula == null) {
            return;
        }
        String placa = JOptionPane.showInputDialog("Placa del vehículo:");
        if (placa == null) {
            return;
        }
        String origen = JOptionPane.showInputDialog("Origen:");
        if (origen == null) {
            return;
        }
        String destino = JOptionPane.showInputDialog("Destino:");
        if (destino == null) {
            return;
        }

        JOptionPane.showMessageDialog(null,
                " TICKET GENERADO\n"
                + "Pasajero: " + cedula + "\n"
                + "Vehículo: " + placa + "\n"
                + "Ruta: " + origen + " -> " + destino);
    }

    private void listarTickets() {
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText("=== TICKETS VENDIDOS ===\n\n");

        area.append("(Sin tickets )");
        JOptionPane.showMessageDialog(null, area, "Tickets",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // estadisticas
    private void totalRecaudado() {
        
        JOptionPane.showMessageDialog(null,
                " TOTAL RECAUDADO\n"
                + "Total: $0");
    }

    private void pasajeroTipo() {
        javax.swing.JTextArea area = new javax.swing.JTextArea(6, 30);
        area.setText("=== PASAJEROS POR TIPO ===\n\n");
        area.append("Regular:      0\n");
        area.append("Estudiante:   0\n");
        area.append("Adulto Mayor: 0\n");
        JOptionPane.showMessageDialog(null, area, "Pasajeros por Tipo ",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void vehiculomTickets() {

        JOptionPane.showMessageDialog(null,
                " VEHÍCULO CON MÁS TICKETS\n"
                + "(Sin datos aun generado)");
    }

}
