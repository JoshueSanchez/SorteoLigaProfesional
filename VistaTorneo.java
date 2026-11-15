
package vista;

import java.util.Scanner;
import java.util.List;
import modelo.Equipo;
import modelo.Partido;

public class VistaTorneo {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String leerNombreEquipo(int numero) {
        System.out.print("Ingrese el nombre del equipo " + numero + ": ");
        return scanner.nextLine().trim();
    }

    public void mostrarPartidos(String etapa, List<Partido> partidos) {
        System.out.println("\n========== " + etapa.toUpperCase() + " ==========");

        for (int i = 0; i < partidos.size(); i++) {
            System.out.println((i + 1) + ". " + partidos.get(i));
        }
    }

    public Equipo seleccionarGanador(Partido partido, int numero) {
        while (true) {
            System.out.println("\nPartido " + numero + ":");
            System.out.println("1. " + partido.getEquipo1());
            System.out.println("2. " + partido.getEquipo2());
            System.out.print("Seleccione el ganador (1 o 2): ");

            String opcion = scanner.nextLine().trim();

            if (opcion.equals("1")) {
                return partido.getEquipo1();
            }

            if (opcion.equals("2")) {
                return partido.getEquipo2();
            }

            System.out.println("Opcion invalida. Intente nuevamente.");
        }
    }

    public void mostrarCampeon(Equipo campeon) {
        System.out.println("\n********************************");
        System.out.println("CAMPEON DEL TORNEO: " + campeon);
        System.out.println("********************************");
    }
}

