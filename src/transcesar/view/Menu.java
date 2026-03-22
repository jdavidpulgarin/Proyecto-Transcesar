package transcesar.view;
 
import java.util.ArrayList;
import javax.swing.JOptionPane;
import transcesar.model.Bus;
import transcesar.model.Buseta;
import transcesar.model.Conductor;
import transcesar.model.MicroBus;
import transcesar.model.Pasajero;
import transcesar.model.Reserva;
import transcesar.model.Ruta;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;
import transcesar.service.PersonaService;
import transcesar.service.ReglasNegocioService;
import transcesar.service.ReporteService;
import transcesar.service.ReservaService;
import transcesar.service.RutaService;
import transcesar.service.TicketService;
import transcesar.service.VehiculoService;
 
public class Menu {
 
    private PersonaService personaService;
    private VehiculoService vehiculoService;
    private TicketService ticketService;
    private ReporteService reporteService;
    private ReglasNegocioService reglasNegocioService;
    private ReservaService reservaService;
    private RutaService rutaService;
 
    public Menu() {
        personaService = new PersonaService();
        vehiculoService = new VehiculoService();
        ticketService = new TicketService(personaService, vehiculoService);
        reporteService = new ReporteService(ticketService);
        reglasNegocioService = new ReglasNegocioService();
        reservaService = new ReservaService(personaService, vehiculoService, ticketService);
        rutaService = new RutaService();
    }
 
    public void iniciar() {
        int opcion = -1;
        do {
            String menu = "SISTEMA TRANSCESAR\n"
                    + "-- Rutas --\n"
                    + "1. Registrar Ruta\n"
                    + "2. Listar Rutas\n"
                    + "-- Vehiculos --\n"
                    + "3. Registrar Buseta\n"
                    + "4. Registrar Bus\n"
                    + "5. Registrar MicroBus\n"
                    + "6. Listar todos los vehiculos\n"
                    + "-- Personas --\n"
                    + "7. Registrar Conductor\n"
                    + "8. Registrar Pasajero\n"
                    + "9. Listar Personas\n"
                    + "-- Tickets --\n"
                    + "10. Vender Tickets\n"
                    + "11. Listar Tickets Vendidos\n"
                    + "-- Estadisticas --\n"
                    + "12. Total Recaudado\n"
                    + "13. Pasajero Por Tipo\n"
                    + "14. Vehiculo Con Mas Tickets\n"
                    + "-- Reportes --\n"
                    + "15. Reportes\n"
                    + "-- Reservas --\n"
                    + "16. Reservas\n"
                    + "0. Salir";
 
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break;
 
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido");
                continue;
            }
 
            switch (opcion) {
                case 1: registrarRuta(); break;
                case 2: listarRutas(); break;
                case 3: registrarBuseta(); break;
                case 4: registrarBus(); break;
                case 5: registrarmicroBus(); break;
                case 6: listarVehiculos(); break;
                case 7: registrarConductor(); break;
                case 8: registrarPasajero(); break;
                case 9: listarPersonas(); break;
                case 10: venderTickets(); break;
                case 11: listarTickets(); break;
                case 12: totalRecaudado(); break;
                case 13: pasajeroTipo(); break;
                case 14: vehiculomTickets(); break;
                case 15: menuReportes(); break;
                case 16: menuReservas(); break;
                case 0: JOptionPane.showMessageDialog(null, "Saliendo...."); break;
                default: JOptionPane.showMessageDialog(null, "Opcion invalida"); break;
            }
        } while (opcion != 0);
    }
 
    private void registrarRuta() {
        String codigo = "";
        while (true) {
            codigo = JOptionPane.showInputDialog("Codigo de la ruta (ej: R001):");
            if (codigo == null) return;
            if (!codigo.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "El codigo no puede estar vacio. Intentelo de nuevo.");
        }
        String origen = "";
        while (true) {
            origen = JOptionPane.showInputDialog("Ciudad de origen:");
            if (origen == null) return;
            if (origen.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) break;
            JOptionPane.showMessageDialog(null, "La ciudad solo puede contener letras. Intentelo de nuevo.");
        }
        String destino = "";
        while (true) {
            destino = JOptionPane.showInputDialog("Ciudad de destino:");
            if (destino == null) return;
            if (destino.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) break;
            JOptionPane.showMessageDialog(null, "La ciudad solo puede contener letras. Intentelo de nuevo.");
        }
        double distancia = 0;
        while (true) {
            String distStr = JOptionPane.showInputDialog("Distancia en km:");
            if (distStr == null) return;
            try {
                distancia = Double.parseDouble(distStr.trim());
                if (distancia > 0) break;
                JOptionPane.showMessageDialog(null, "La distancia debe ser mayor a 0. Intentelo de nuevo.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido. Intentelo de nuevo.");
            }
        }
        int tiempo = 0;
        while (true) {
            String tiempoStr = JOptionPane.showInputDialog("Tiempo estimado en minutos:");
            if (tiempoStr == null) return;
            try {
                tiempo = Integer.parseInt(tiempoStr.trim());
                if (tiempo > 0) break;
                JOptionPane.showMessageDialog(null, "El tiempo debe ser mayor a 0. Intentelo de nuevo.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido. Intentelo de nuevo.");
            }
        }
        String resultado = rutaService.registrarRuta(codigo.trim(), origen.trim(), destino.trim(), distancia, tiempo);
        JOptionPane.showMessageDialog(null, resultado);
    }
 
    private void listarRutas() {
        ArrayList<Ruta> lista = rutaService.getRutas();
        String texto = "=== RUTAS REGISTRADAS ===\n\n";
        if (lista.isEmpty()) {
            texto = texto + "(Sin rutas registradas)";
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Ruta r = lista.get(i);
                texto = texto + "Codigo: " + r.getCodigo()
                        + " | " + r.getCiudadOrigen() + " -> " + r.getCiudadDestino()
                        + " | " + r.getDistanciaKm() + " km"
                        + " | " + r.getTiempoMinutos() + " min\n";
            }
        }
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Rutas", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private Ruta seleccionarRuta() {
        ArrayList<Ruta> lista = rutaService.getRutas();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay rutas registradas. Registre una ruta primero.");
            return null;
        }
        String[] opciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Ruta r = lista.get(i);
            opciones[i] = r.getCodigo() + " | " + r.getCiudadOrigen()
                    + " -> " + r.getCiudadDestino()
                    + " | " + r.getDistanciaKm() + " km"
                    + " | " + r.getTiempoMinutos() + " min";
        }
        String elegida = (String) JOptionPane.showInputDialog(null,
                "Seleccione la ruta:", "Rutas disponibles",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (elegida == null) return null;
        String codigoRuta = elegida.split("\\|")[0].trim();
        return rutaService.buscarPorCodigo(codigoRuta);
    }
 
    private Vehiculo seleccionarVehiculo() {
        ArrayList<String> busetas = (ArrayList<String>) vehiculoService.listarVehiculos("buseta.txt");
        ArrayList<String> buses = (ArrayList<String>) vehiculoService.listarVehiculos("bus.txt");
        ArrayList<String> micros = (ArrayList<String>) vehiculoService.listarVehiculos("microbus.txt");
 
        ArrayList<String> opciones = new ArrayList<>();
        for (String s : busetas) opciones.add("BUSETA | " + s);
        for (String s : buses) opciones.add("BUS | " + s);
        for (String s : micros) opciones.add("MICROBUS | " + s);
 
        if (opciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehiculos registrados.");
            return null;
        }
 
        String[] arreglo = opciones.toArray(new String[0]);
        String elegido = (String) JOptionPane.showInputDialog(null,
                "Seleccione el vehiculo:", "Vehiculos disponibles",
                JOptionPane.QUESTION_MESSAGE, null, arreglo, arreglo[0]);
        if (elegido == null) return null;
 
        String placa = elegido.split("\\|")[1].trim().split(";")[0].trim();
        return vehiculoService.buscarVehiculoPorPlaca(placa);
    }
 
    private void registrarBuseta() {
        String placa = "";
        while (true) {
            placa = JOptionPane.showInputDialog("Placa de la Buseta:");
            if (placa == null) return;
            if (!placa.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "La placa no puede estar vacia. Intentelo de nuevo.");
        }
        Ruta ruta = seleccionarRuta();
        if (ruta == null) return;
        try {
            Buseta buseta = new Buseta(placa.trim(), ruta.getCiudadOrigen() + " - " + ruta.getCiudadDestino());
            vehiculoService.registrarVehiculo(buseta);
            JOptionPane.showMessageDialog(null,
                    "Buseta registrada: " + placa + "\n"
                    + "Ruta: " + ruta.getCiudadOrigen() + " -> " + ruta.getCiudadDestino() + "\n"
                    + "Distancia: " + ruta.getDistanciaKm() + " km | Tiempo: " + ruta.getTiempoMinutos() + " min\n"
                    + "Capacidad maxima: " + buseta.getCapacidadMaxima() + " pasajeros\n"
                    + "Cupos disponibles: " + (buseta.getCapacidadMaxima() - buseta.getPasajerosActuales()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
 
    private void registrarBus() {
        String placa = "";
        while (true) {
            placa = JOptionPane.showInputDialog("Placa del Bus:");
            if (placa == null) return;
            if (!placa.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "La placa no puede estar vacia. Intentelo de nuevo.");
        }
        Ruta ruta = seleccionarRuta();
        if (ruta == null) return;
        try {
            Bus bus = new Bus(placa.trim(), ruta.getCiudadOrigen() + " - " + ruta.getCiudadDestino());
            vehiculoService.registrarVehiculo(bus);
            JOptionPane.showMessageDialog(null,
                    "Bus registrado: " + placa + "\n"
                    + "Ruta: " + ruta.getCiudadOrigen() + " -> " + ruta.getCiudadDestino() + "\n"
                    + "Distancia: " + ruta.getDistanciaKm() + " km | Tiempo: " + ruta.getTiempoMinutos() + " min\n"
                    + "Capacidad maxima: " + bus.getCapacidadMaxima() + " pasajeros\n"
                    + "Cupos disponibles: " + (bus.getCapacidadMaxima() - bus.getPasajerosActuales()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
 
    private void registrarmicroBus() {
        String placa = "";
        while (true) {
            placa = JOptionPane.showInputDialog("Placa del MicroBus:");
            if (placa == null) return;
            if (!placa.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "La placa no puede estar vacia. Intentelo de nuevo.");
        }
        Ruta ruta = seleccionarRuta();
        if (ruta == null) return;
        try {
            MicroBus micro = new MicroBus(placa.trim(), ruta.getCiudadOrigen() + " - " + ruta.getCiudadDestino());
            vehiculoService.registrarVehiculo(micro);
            JOptionPane.showMessageDialog(null,
                    "MicroBus registrado: " + placa + "\n"
                    + "Ruta: " + ruta.getCiudadOrigen() + " -> " + ruta.getCiudadDestino() + "\n"
                    + "Distancia: " + ruta.getDistanciaKm() + " km | Tiempo: " + ruta.getTiempoMinutos() + " min\n"
                    + "Capacidad maxima: " + micro.getCapacidadMaxima() + " pasajeros\n"
                    + "Cupos disponibles: " + (micro.getCapacidadMaxima() - micro.getPasajerosActuales()));
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
            for (int i = 0; i < busetas.size(); i++) texto = texto + "  " + busetas.get(i) + "\n";
            hay = true;
        }
        if (!buses.isEmpty()) {
            texto = texto + "-- Buses --\n";
            for (int i = 0; i < buses.size(); i++) texto = texto + "  " + buses.get(i) + "\n";
            hay = true;
        }
        if (!micros.isEmpty()) {
            texto = texto + "-- MicroBuses --\n";
            for (int i = 0; i < micros.size(); i++) texto = texto + "  " + micros.get(i) + "\n";
            hay = true;
        }
        if (!hay) texto = texto + "(Sin vehiculos registrados)";
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Vehiculos", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void registrarConductor() {
        String cedula = "";
        while (true) {
            cedula = JOptionPane.showInputDialog("Cedula (solo numeros):");
            if (cedula == null) return;
            if (cedula.trim().matches("[0-9]+")) break;
            JOptionPane.showMessageDialog(null, "La cedula solo puede contener numeros. Intentelo de nuevo.");
        }
        String nombre = "";
        while (true) {
            nombre = JOptionPane.showInputDialog("Nombre:");
            if (nombre == null) return;
            if (nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) break;
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras. Intentelo de nuevo.");
        }
        String apellido = "";
        while (true) {
            apellido = JOptionPane.showInputDialog("Apellido:");
            if (apellido == null) return;
            if (apellido.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) break;
            JOptionPane.showMessageDialog(null, "El apellido solo puede contener letras. Intentelo de nuevo.");
        }
        String[] sexoOpciones = {"M", "F"};
        String sexo = (String) JOptionPane.showInputDialog(null, "Sexo:", "Sexo",
                JOptionPane.QUESTION_MESSAGE, null, sexoOpciones, sexoOpciones[0]);
        if (sexo == null) return;
        int edad = 0;
        while (true) {
            String edadStr = JOptionPane.showInputDialog("Edad:");
            if (edadStr == null) return;
            try {
                edad = Integer.parseInt(edadStr.trim());
                if (edad >= 18 && edad <= 70) break;
                JOptionPane.showMessageDialog(null, "La edad del conductor debe estar entre 18 y 70. Intentelo de nuevo.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un numero. Intentelo de nuevo.");
            }
        }
        String telefono = "";
        while (true) {
            telefono = JOptionPane.showInputDialog("Telefono:");
            if (telefono == null) return;
            if (telefono.trim().matches("[0-9]+")) break;
            JOptionPane.showMessageDialog(null, "El telefono solo puede contener numeros. Intentelo de nuevo.");
        }
        String licencia = "";
        while (true) {
            licencia = JOptionPane.showInputDialog("Numero de licencia:");
            if (licencia == null) return;
            if (!licencia.trim().isEmpty()) break;
            JOptionPane.showMessageDialog(null, "El conductor debe tener licencia registrada. Intentelo de nuevo.");
        }
        String[] cats = {"B1", "B2", "C1", "C2"};
        String categoria = (String) JOptionPane.showInputDialog(null, "Categoria:", "Licencia",
                JOptionPane.QUESTION_MESSAGE, null, cats, cats[0]);
        if (categoria == null) return;
        String resultado = personaService.registrarConductor(cedula.trim(), nombre.trim(),
                apellido.trim(), sexo, edad, telefono.trim(), licencia.trim(), categoria);
        JOptionPane.showMessageDialog(null, resultado);
    }
 
    private void registrarPasajero() {
        String cedula = "";
        while (true) {
            cedula = JOptionPane.showInputDialog("Cedula (solo numeros):");
            if (cedula == null) return;
            if (cedula.trim().matches("[0-9]+")) break;
            JOptionPane.showMessageDialog(null, "La cedula solo puede contener numeros. Intentelo de nuevo.");
        }
        String nombre = "";
        while (true) {
            nombre = JOptionPane.showInputDialog("Nombre:");
            if (nombre == null) return;
            if (nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) break;
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras. Intentelo de nuevo.");
        }
        String apellido = "";
        while (true) {
            apellido = JOptionPane.showInputDialog("Apellido:");
            if (apellido == null) return;
            if (apellido.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) break;
            JOptionPane.showMessageDialog(null, "El apellido solo puede contener letras. Intentelo de nuevo.");
        }
        String[] sexoOpciones = {"M", "F"};
        String sexo = (String) JOptionPane.showInputDialog(null, "Sexo:", "Sexo",
                JOptionPane.QUESTION_MESSAGE, null, sexoOpciones, sexoOpciones[0]);
        if (sexo == null) return;
        int edad = 0;
        while (true) {
            String edadStr = JOptionPane.showInputDialog("Edad:");
            if (edadStr == null) return;
            try {
                edad = Integer.parseInt(edadStr.trim());
                if (edad >= 1 && edad <= 120) break;
                JOptionPane.showMessageDialog(null, "La edad debe estar entre 1 y 120. Intentelo de nuevo.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un numero. Intentelo de nuevo.");
            }
        }
        String telefono = "";
        while (true) {
            telefono = JOptionPane.showInputDialog("Telefono:");
            if (telefono == null) return;
            if (telefono.trim().matches("[0-9]+")) break;
            JOptionPane.showMessageDialog(null, "El telefono solo puede contener numeros. Intentelo de nuevo.");
        }
        String tipoFinal;
        if (edad >= 60) {
            tipoFinal = "ADULTO_MAYOR";
            JOptionPane.showMessageDialog(null,
                    "Pasajero mayor de 60 anos.\nSe asigna automaticamente como Adulto Mayor con 30% de descuento.");
        } else {
            String[] tiposDisplay = {"Regular (0%)", "Estudiante (15%)"};
            String tipoElegido = (String) JOptionPane.showInputDialog(null, "Tipo de pasajero:", "Tipo",
                    JOptionPane.QUESTION_MESSAGE, null, tiposDisplay, tiposDisplay[0]);
            if (tipoElegido == null) return;
            tipoFinal = tipoElegido.equals("Estudiante (15%)") ? "ESTUDIANTE" : "REGULAR";
        }
        String resultado = personaService.registrarPasajero(cedula.trim(), nombre.trim(),
                apellido.trim(), sexo, edad, telefono.trim(), tipoFinal);
        JOptionPane.showMessageDialog(null, resultado);
    }
 
    private void listarPersonas() {
        String texto = "=== CONDUCTORES REGISTRADOS ===\n\n";
        ArrayList<Conductor> conductores = (ArrayList<Conductor>) personaService.getConductores();
        if (conductores.isEmpty()) {
            texto = texto + "(Sin conductores registrados)\n";
        } else {
            for (int i = 0; i < conductores.size(); i++) {
                Conductor c = conductores.get(i);
                texto = texto + "Cedula: " + c.getCedula()
                        + " | " + c.getNombreCompleto()
                        + " | Licencia: " + c.getNumeroLicencia()
                        + " | Categoria: " + c.getCategoriaLicencia() + "\n";
            }
        }
        texto = texto + "\n=== PASAJEROS REGISTRADOS ===\n\n";
        ArrayList<Pasajero> pasajeros = (ArrayList<Pasajero>) personaService.getPasajeros();
        if (pasajeros.isEmpty()) {
            texto = texto + "(Sin pasajeros registrados)";
        } else {
            for (int i = 0; i < pasajeros.size(); i++) {
                Pasajero p = pasajeros.get(i);
                texto = texto + "Cedula: " + p.getCedula()
                        + " | " + p.getNombreCompleto()
                        + " | Tipo: " + p.getTipo() + "\n";
            }
        }
        javax.swing.JTextArea area = new javax.swing.JTextArea(20, 45);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Personas registradas", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void venderTickets() {
        String cedula = JOptionPane.showInputDialog("Cedula del pasajero:");
        if (cedula == null) return;
 
        Vehiculo vehiculo = seleccionarVehiculo();
        if (vehiculo == null) return;
        String placa = vehiculo.getPlaca();
 
        int cuposDisponibles = vehiculo.getCapacidadMaxima() - vehiculo.getPasajerosActuales();
        if (cuposDisponibles <= 0) {
            JOptionPane.showMessageDialog(null,
                    "ERROR: El vehiculo " + placa + " no tiene cupos disponibles.\n"
                    + "Capacidad maxima: " + vehiculo.getCapacidadMaxima() + "\n"
                    + "Pasajeros actuales: " + vehiculo.getPasajerosActuales());
            return;
        }
 
        Ruta ruta = seleccionarRuta();
        if (ruta == null) return;
 
        String origen = ruta.getCiudadOrigen();
        String destino = ruta.getCiudadDestino();
        String fecha = java.time.LocalDate.now().toString();
 
        String errorLimite = reglasNegocioService.validarLimiteTickets(
                (ArrayList<Ticket>) ticketService.getTickets(), cedula, fecha);
        if (errorLimite != null) {
            JOptionPane.showMessageDialog(null, errorLimite);
            return;
        }
 
        if (reglasNegocioService.esFestivo(fecha)) {
            JOptionPane.showMessageDialog(null,
                    "ATENCION: Hoy " + fecha + " es dia festivo.\n"
                    + "Se aplicara un recargo del 20% sobre la tarifa base.");
        }
 
        String resultado = ticketService.venderTicket(cedula, placa, origen, destino);
 
        if (!resultado.startsWith("ERROR")) {
            Vehiculo actualizado = vehiculoService.buscarVehiculoPorPlaca(placa);
            int cuposRestantes = actualizado.getCapacidadMaxima() - actualizado.getPasajerosActuales();
            JOptionPane.showMessageDialog(null,
                    resultado + "\n\nCupos restantes en el vehiculo: "
                    + cuposRestantes + " de " + actualizado.getCapacidadMaxima());
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
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
        int regulares = 0, estudiantes = 0, adultos = 0;
        for (int i = 0; i < lista.size(); i++) {
            String tipo = lista.get(i).getPasajero().getTipo();
            if (tipo.equals("ESTUDIANTE")) estudiantes++;
            else if (tipo.equals("ADULTO_MAYOR")) adultos++;
            else regulares++;
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
                if (lista.get(j).getVehiculo().getPlaca().equals(placa)) contador++;
            }
            if (contador > maxTickets) {
                maxTickets = contador;
                placaTop = placa;
            }
        }
        JOptionPane.showMessageDialog(null,
                "VEHICULO CON MAS TICKETS\nPlaca: " + placaTop + " - " + maxTickets + " ticket(s)");
    }
 
    private void menuReportes() {
        int opcion = -1;
        do {
            String menu = "REPORTES\n"
                    + "1. Tickets por fecha\n"
                    + "2. Tickets por tipo de vehiculo\n"
                    + "3. Tickets por tipo de pasajero\n"
                    + "4. Resumen del dia de hoy\n"
                    + "0. Volver";
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break;
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido");
                continue;
            }
            switch (opcion) {
                case 1: reportePorFecha(); break;
                case 2: reportePorTipoVehiculo(); break;
                case 3: reportePorTipoPasajero(); break;
                case 4: resumenHoy(); break;
                case 0: break;
                default: JOptionPane.showMessageDialog(null, "Opcion invalida"); break;
            }
        } while (opcion != 0);
    }
 
    private void reportePorFecha() {
        String fecha = JOptionPane.showInputDialog("Ingrese la fecha (YYYY-MM-DD):");
        if (fecha == null) return;
        String texto = reporteService.reportePorFecha(fecha);
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Reporte por fecha", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void reportePorTipoVehiculo() {
        String[] tipos = {"Buseta", "Bus", "MicroBus"};
        String tipoElegido = (String) JOptionPane.showInputDialog(null,
                "Tipo de vehiculo:", "Filtro",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipoElegido == null) return;
        String texto = reporteService.reportePorTipoVehiculo(tipoElegido);
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Reporte por vehiculo", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void reportePorTipoPasajero() {
        String[] tiposDisplay = {"Regular", "Estudiante", "Adulto Mayor"};
        String tipoElegido = (String) JOptionPane.showInputDialog(null,
                "Tipo de pasajero:", "Filtro",
                JOptionPane.QUESTION_MESSAGE, null, tiposDisplay, tiposDisplay[0]);
        if (tipoElegido == null) return;
        String tipoValor = "REGULAR";
        if (tipoElegido.equals("Estudiante")) tipoValor = "ESTUDIANTE";
        else if (tipoElegido.equals("Adulto Mayor")) tipoValor = "ADULTO_MAYOR";
        String texto = reporteService.reportePorTipoPasajero(tipoValor);
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Reporte por pasajero", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void resumenHoy() {
        String texto = reporteService.resumenHoy();
        JOptionPane.showMessageDialog(null, texto, "Resumen del dia", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void menuReservas() {
        int opcion = -1;
        do {
            String menu = "RESERVAS\n"
                    + "1. Crear reserva\n"
                    + "2. Cancelar reserva\n"
                    + "3. Listar reservas activas\n"
                    + "4. Historial de reservas de un pasajero\n"
                    + "5. Convertir reserva en ticket\n"
                    + "6. Verificar reservas vencidas\n"
                    + "0. Volver";
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break;
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido");
                continue;
            }
            switch (opcion) {
                case 1: crearReserva(); break;
                case 2: cancelarReserva(); break;
                case 3: listarReservasActivas(); break;
                case 4: historialReservasPasajero(); break;
                case 5: convertirReservaEnTicket(); break;
                case 6: verificarReservasVencidas(); break;
                case 0: break;
                default: JOptionPane.showMessageDialog(null, "Opcion invalida"); break;
            }
        } while (opcion != 0);
    }
 
    private void crearReserva() {
        String cedula = JOptionPane.showInputDialog("Cedula del pasajero:");
        if (cedula == null) return;
        Vehiculo vehiculo = seleccionarVehiculo();
        if (vehiculo == null) return;
        String fechaViaje = JOptionPane.showInputDialog("Fecha del viaje (YYYY-MM-DD):");
        if (fechaViaje == null) return;
        String resultado = reservaService.crearReserva(cedula, vehiculo.getPlaca(), fechaViaje);
        JOptionPane.showMessageDialog(null, resultado);
    }
 
    private void cancelarReserva() {
        String codigo = JOptionPane.showInputDialog("Codigo de la reserva:");
        if (codigo == null) return;
        String resultado = reservaService.cancelarReserva(codigo);
        JOptionPane.showMessageDialog(null, resultado);
    }
 
    private void listarReservasActivas() {
        ArrayList<Reserva> lista = reservaService.listarReservasActivas();
        String texto = "=== RESERVAS ACTIVAS ===\n\n";
        if (lista.isEmpty()) {
            texto = texto + "(Sin reservas activas)";
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Reserva r = lista.get(i);
                texto = texto + "Codigo: " + r.getCodigo()
                        + " | " + r.getPasajero().getNombreCompleto()
                        + " | Vehiculo: " + r.getVehiculo().getPlaca()
                        + " | Viaje: " + r.getFechaViaje() + "\n";
            }
        }
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Reservas activas", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void historialReservasPasajero() {
        String cedula = JOptionPane.showInputDialog("Cedula del pasajero:");
        if (cedula == null) return;
        ArrayList<Reserva> lista = reservaService.historialPasajero(cedula);
        String texto = "=== HISTORIAL DE RESERVAS ===\n\n";
        if (lista.isEmpty()) {
            texto = texto + "(Sin reservas para ese pasajero)";
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Reserva r = lista.get(i);
                texto = texto + "Codigo: " + r.getCodigo()
                        + " | Vehiculo: " + r.getVehiculo().getPlaca()
                        + " | Viaje: " + r.getFechaViaje()
                        + " | Estado: " + r.getEstado() + "\n";
            }
        }
        javax.swing.JTextArea area = new javax.swing.JTextArea(15, 40);
        area.setText(texto);
        JOptionPane.showMessageDialog(null, area, "Historial reservas", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void convertirReservaEnTicket() {
        String codigo = JOptionPane.showInputDialog("Codigo de la reserva:");
        if (codigo == null) return;
        Ruta ruta = seleccionarRuta();
        if (ruta == null) return;
        String resultado = reservaService.convertirEnTicket(codigo,
                ruta.getCiudadOrigen(), ruta.getCiudadDestino());
        JOptionPane.showMessageDialog(null, resultado);
    }
 
    private void verificarReservasVencidas() {
        int canceladas = reservaService.verificarReservasVencidas();
        JOptionPane.showMessageDialog(null,
                "Verificacion completada.\nReservas canceladas por vencimiento: " + canceladas);
    }
}