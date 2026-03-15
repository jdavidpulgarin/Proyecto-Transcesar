/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;
import java.util.ArrayList;

public class FestivosService {

    private ArrayList<String> festivos;

    public FestivosService() {
        festivos = new ArrayList<>();

        festivos.add("2025-01-01");
        festivos.add("2025-01-06");
        festivos.add("2025-03-24");
        festivos.add("2025-04-17");
        festivos.add("2025-04-18");
        festivos.add("2025-05-01");
        festivos.add("2025-06-02");
        festivos.add("2025-06-23");
        festivos.add("2025-06-30");
        festivos.add("2025-07-04");
        festivos.add("2025-07-20");
        festivos.add("2025-08-07");
        festivos.add("2025-08-18");
        festivos.add("2025-10-13");
        festivos.add("2025-11-03");
        festivos.add("2025-11-17");
        festivos.add("2025-12-08");
        festivos.add("2025-12-25");
        festivos.add("2026-01-01");
        festivos.add("2026-01-12");
        festivos.add("2026-03-23");
        festivos.add("2026-04-02");
        festivos.add("2026-04-03");
        festivos.add("2026-05-01");
        festivos.add("2026-05-18");
        festivos.add("2026-06-08");
        festivos.add("2026-06-15");
        festivos.add("2026-07-06");
        festivos.add("2026-07-20");
        festivos.add("2026-08-07");
        festivos.add("2026-08-17");
        festivos.add("2026-10-12");
        festivos.add("2026-11-02");
        festivos.add("2026-11-16");
        festivos.add("2026-12-08");
        festivos.add("2026-12-25");
    }

    public boolean esFestivo(String fecha) {
        for (int i = 0; i < festivos.size(); i++) {
            if (festivos.get(i).equals(fecha)) {
                return true;
            }
        }
        return false;
    }

    public double aplicarRecargo(double tarifaBase, String fecha) {
        if (esFestivo(fecha)) {
            return tarifaBase * 1.20;
        }
        return tarifaBase;
    }
}

