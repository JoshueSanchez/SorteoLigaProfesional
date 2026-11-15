
package controlador;

import modelo.*;
import vista.VistaTorneo;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ControladorTorneo {

    private final Torneo torneo;
    private final VistaTorneo vista;

    public ControladorTorneo() {
        torneo = new Torneo();
        vista = new VistaTorneo();
    }

    public void iniciar() {
        try {
            vista.mostrarMensaje("===== SORTEO LIGA PROFESIONAL =====");

            for (int i = 1; i <= 16; i++) {
                String nombre = vista.leerNombreEquipo(i);

                while (nombre.isEmpty()) {
                    vista.mostrarMensaje("El nombre no puede estar vacio.");
                    nombre = vista.leerNombreEquipo(i);
                }

                torneo.agregarEquipo(new Equipo(nombre));
            }

            List<Equipo> participantes = new ArrayList<>(torneo.getEquipos());

            String[] etapas = {
                "Octavos de final",
                "Cuartos de final",
                "Semifinales",
                "Final"
            };

            for (String etapa : etapas) {

                List<Partido> partidos =
                        torneo.sorteoRecursivo(participantes);

                vista.mostrarPartidos(etapa, partidos);

                List<Equipo> ganadores = new ArrayList<>();

                for (int i = 0; i < partidos.size(); i++) {
                    Equipo ganador =
                            vista.seleccionarGanador(partidos.get(i), i + 1);

                    partidos.get(i).setGanador(ganador);
                    ganadores.add(ganador);
                }

                torneo.getPartidos().addAll(partidos);

                participantes = ganadores;
            }

            vista.mostrarCampeon(participantes.get(0));

            torneo.guardarTorneo("torneo.ser");
            Torneo recuperado = Torneo.cargarTorneo("torneo.ser");

            vista.mostrarMensaje("Torneo recuperado correctamente.");
            vista.mostrarMensaje("Partidos recuperados: "
        + recuperado.getPartidos().size());
            vista.mostrarMensaje("Torneo guardado correctamente.");

        } catch (NumeroEquiposInvalidoException |
                 SorteoInvalidoException e) {

            vista.mostrarMensaje("Error en el sorteo: " + e.getMessage());

       } catch (IOException e) {
    vista.mostrarMensaje("Error de archivo: " + e.getMessage());

} catch (ClassNotFoundException e) {
    vista.mostrarMensaje("Error al recuperar el torneo: " + e.getMessage());
}
    }
}

