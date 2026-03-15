package transcesar.view;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import transcesar.model.Bus;
import transcesar.model.Buseta;
import transcesar.model.MicroBus;
import transcesar.model.Ticket;
import transcesar.service.PersonaService;
import transcesar.service.TicketService;
import transcesar.service.VehiculoService;

public class Menu {

    private PersonaService personaService;
    private VehiculoService vehiculoService;
    private TicketService ticketService;

    public Menu() {
        personaService = new PersonaService();
        vehiculoService = new VehiculoService();
        ticketService = new TicketService(personaService, vehiculoService);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            String menu = "SISTEMA TRANSCESAR\n"
                    + "1. Registrar Buseta\n"
                    + "2. Registrar Bus\n"
                    + "3. Registrar MicroBus\n"
                    + "4. Listar todos los vehiculos\n"
                    + "-- Personas --\n"
                    + "5. Registrar Conductor\n"
                    + "6. Registrar Pasajero\n"
                    + "-- Tickets --\n"
                    + "7. Vender Tickets\n"
                    + "8. Listar Tickets Vendidos\n"
                    + "-- Estadisticas --\n"
                    + "9. Total Recaudado\n"
                    + "10. Pasajero Por Tipo\n"
                    + "11. Vehiculo Con Mas Tickets\n"
                    + "-- Reportes --\n"
                    + "12. Reportes\n"
                    + "0. Salir";

            String input = JOptionPane.showInputDialog(menu);
            if (input == null) {
                break;
            }

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarBuseta();
                    break;
                case 2:
                    registrarBus();
                    break;
                case 3:
                    registrarmicroBus();
                    break;
                case 4:
                    listarVehiculos();
                    break;
                case 5:
                    registrarConductor();
                    break;
                case 6:
                    registrarPasajero();
                    break;
                case 7:
                    venderTickets();
                    break;
                case 8:
                    listarTickets();
                    break;
                case 9:
                    totalRecaudado();
                    break;
                case 10:
                    pasajeroTipo();
                    break;
                case 11:
                    vehiculomTickets();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo....");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
                    break;
            }
        } while (opcion != 0);
    }

    private void registrarBuseta() {
        String placa = JOptionPane.showInputDialog("Placa de la Buseta:");
        if (placa == null) {
            return;
        }
        String ruta = JOptionPane.showInputDialog("Ruta:");
        if (ruta == null) {
            return;
        }

        try {
            vehiculoService.registrarVehiculo(new Buseta(placa, ruta));
            JOptionPane.showMessageDialog(null, "Buseta registrada: " + placa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
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

        try {
            vehiculoService.registrarVehiculo(new Bus(placa, ruta));
            JOptionPane.showMessageDialog(null, "Bus registrado: " + placa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
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

        try {
            vehiculoService.registrarVehiculo(new MicroBus(placa, ruta));
            JOptionPane.showMessageDialog(null, "MicroBus registrado: " + placa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void listarVehiculos() {
        String texto = "=== VEHICULOS REGISTRADOS ===\n\n";

        ArrayList<String> busetas = (ArrayList<String>) vehiculoService.listarVehiculos("buseta.txt");
        ArrayList<String> buses = (ArrayList<String>) vehiculoService.listarVehiculos("bus.txt");
        ArrayList<String> micros = (ArrayList<String>) vehiculoService.listarVehiculos("microbus.txt");

        boolean hay = false;

        if (!busetas.isEmpty()) {
            texto = texto + "-- Busetas --\n";
            for (int i = 0; i < busetas.size(); i++) {
                texto = texto + "  " + busetas.get(i) + "\n";
            }
            hay = true;
        }
        if (!buses.isEmpty()) {
            texto = texto + "-- Buses --\n";
            for (int i = 0; i < buses.size(); i++) {
                texto = texto + "  " + buses.get(i) + "\n";
            }
            hay = true;
        }
        if (!micros.isEmpty()) {
            texto = texto + "-- MicroBuses --\n";
            for (int i = 0; i < micros.size(); i++) {
                texto = texto + "  " + micros.get(i) + "\n";
            }
            hay = true;
        }

        if (!hay) {
            texto = texto + "(Sin vehiculos registrados)";
        }

        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Vehiculos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void registrarConductor() {
        String cedula = JOptionPane.showInputDialog("Cedula:");
        if (cedula == null) {
            return;
        }
        String nombre = JOptionPane.showInputDialog("Nombre:");
        if (nombre == null) {
            return;
        }
        String apellido = JOptionPane.showInputDialog("Apellido:");
        if (apellido == null) {
            return;
        }
        String sexo = JOptionPane.showInputDialog("Sexo (M/F):");
        if (sexo == null) {
            return;
        }
        String edadStr = JOptionPane.showInputDialog("Edad:");
        if (edadStr == null) {
            return;
        }
        String telefono = JOptionPane.showInputDialog("Telefono:");
        if (telefono == null) {
            return;
        }
        String licencia = JOptionPane.showInputDialog("Numero de licencia:");
        if (licencia == null) {
            return;
        }
        String[] cats = {"B1", "B2", "C1", "C2"};
        String categoria = (String) JOptionPane.showInputDialog(null,
                "Categoria:", "Licencia",
                JOptionPane.QUESTION_MESSAGE, null, cats, cats[0]);
        if (categoria == null) {
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            String resultado = personaService.registrarConductor(cedula, nombre,
                    apellido, sexo, edad, telefono, licencia, categoria);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La edad debe ser un numero");
        }
    }

    private void registrarPasajero() {
        String cedula = JOptionPane.showInputDialog("Cedula:");
        if (cedula == null) {
            return;
        }
        String nombre = JOptionPane.showInputDialog("Nombre:");
        if (nombre == null) {
            return;
        }
        String apellido = JOptionPane.showInputDialog("Apellido:");
        if (apellido == null) {
            return;
        }
        String sexo = JOptionPane.showInputDialog("Sexo (M/F):");
        if (sexo == null) {
            return;
        }
        String edadStr = JOptionPane.showInputDialog("Edad:");
        if (edadStr == null) {
            return;
        }
        String telefono = JOptionPane.showInputDialog("Telefono:");
        if (telefono == null) {
            return;
        }

        String[] tiposDisplay = {"Regular (0%)", "Estudiante (15%)", "Adulto Mayor (30%)"};
        String[] tiposValor = {"REGULAR", "ESTUDIANTE", "ADULTO_MAYOR"};

        String tipoElegido = (String) JOptionPane.showInputDialog(null,
                "Tipo de pasajero:", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, tiposDisplay, tiposDisplay[0]);
        if (tipoElegido == null) {
            return;
        }

        String tipoFinal = "REGULAR";
        if (tipoElegido.equals("Estudiante (15%)")) {
            tipoFinal = "ESTUDIANTE";
        } else if (tipoElegido.equals("Adulto Mayor (30%)")) {
            tipoFinal = "ADULTO_MAYOR";
        }

        try {
            int edad = Integer.parseInt(edadStr);
            String resultado = personaService.registrarPasajero(cedula, nombre,
                    apellido, sexo, edad, telefono, tipoFinal);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La edad debe ser un numero");
        }
    }

    private void venderTickets() {
        String cedula = JOptionPane.showInputDialog("Cedula del pasajero:");
        if (cedula == null) {
            return;
        }
        String placa = JOptionPane.showInputDialog("Placa del vehiculo:");
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

        String resultado = ticketService.venderTicket(cedula, placa, origen, destino);
        JOptionPane.showMessageDialog(null, resultado);
    }

    private void listarTickets() {
        String texto = "=== TICKETS VENDIDOS ===\n\n";

        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();

        if (lista.isEmpty()) {
            texto = texto + "(Sin tickets registrados)";
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Ticket t = lista.get(i);
                texto = texto + "Ticket #" + t.getId()
                        + " | " + t.getPasajero().getNombreCompleto()
                        + " | Vehiculo: " + t.getVehiculo().getPlaca()
                        + " | " + t.getOrigen() + " -> " + t.getDestino()
                        + " | $" + (int) t.getValorFinal() + "\n";
            }
        }

        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Tickets", JOptionPane.INFORMATION_MESSAGE);
    }

    private void totalRecaudado() {
        double total = ticketService.calcularTotalRecaudado();
        JOptionPane.showMessageDialog(null, "TOTAL RECAUDADO\nTotal: $" + (int) total);
    }

    private void pasajeroTipo() {
        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();
        int regulares = 0;
        int estudiantes = 0;
        int adultos = 0;

        for (int i = 0; i < lista.size(); i++) {
            Ticket t = lista.get(i);
            String tipo = t.getPasajero().getTipo();
            if (tipo.equals("ESTUDIANTE")) {
                estudiantes++;
            } else if (tipo.equals("ADULTO_MAYOR")) {
                adultos++;
            } else {
                regulares++;
            }
        }

        String texto = "=== PASAJEROS POR TIPO ===\n\n"
                + "Regular:      " + regulares + "\n"
                + "Estudiante:   " + estudiantes + "\n"
                + "Adulto Mayor: " + adultos + "\n";

        javax.swing.JTextArea area = new javax.swing.JTextArea(6, 30);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Pasajeros por Tipo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void vehiculomTickets() {
        ArrayList<Ticket> lista = (ArrayList<Ticket>) ticketService.getTickets();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "VEHICULO CON MAS TICKETS\n(Sin datos aun)");
            return;
        }

        String placaTop = "";
        int maxTickets = 0;

        for (int i = 0; i < lista.size(); i++) {
            String placa = lista.get(i).getVehiculo().getPlaca();
            int contador = 0;
            for (int j = 0; j < lista.size(); j++) {
                if (lista.get(j).getVehiculo().getPlaca().equals(placa)) {
                    contador++;
                }
            }
            if (contador > maxTickets) {
                maxTickets = contador;
                placaTop = placa;
            }
        }

        JOptionPane.showMessageDialog(null,
                "VEHICULO CON MAS TICKETS\nPlaca: " + placaTop + " - " + maxTickets + " ticket(s)");
    }
}
