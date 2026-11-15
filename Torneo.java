
package modelo;

import java.io.*;
import java.util.*;

public class Torneo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Equipo> equipos;
    private List<Partido> partidos;
    private String etapaActual;

    public Torneo() {
        equipos = new ArrayList<>();
        partidos = new ArrayList<>();
        etapaActual = "Octavos de final";
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public String getEtapaActual() {
        return etapaActual;
    }

    public List<Partido> sorteoRecursivo(List<Equipo> lista)
            throws NumeroEquiposInvalidoException,
                   SorteoInvalidoException {

        if (lista == null || lista.size() < 2
                || (lista.size() & (lista.size() - 1)) != 0) {
            throw new NumeroEquiposInvalidoException(
                    "La cantidad de equipos debe ser una potencia de 2.");
        }

        List<Equipo> disponibles = new ArrayList<>(lista);
        Collections.shuffle(disponibles);

        List<Partido> resultado = new ArrayList<>();
        emparejarRecursivo(disponibles, resultado);

        return resultado;
    }

    private void emparejarRecursivo(List<Equipo> equipos,
                                    List<Partido> resultado)
            throws SorteoInvalidoException {

        if (equipos.isEmpty()) {
            return;
        }

        if (equipos.size() < 2) {
            throw new SorteoInvalidoException(
                    "No se pudo completar el emparejamiento.");
        }

        Equipo primero = equipos.remove(0);
        Equipo segundo = equipos.remove(0);

        resultado.add(new Partido(primero, segundo));

        emparejarRecursivo(equipos, resultado);
    }

    public void guardarTorneo(String archivo) throws IOException {
        try (ObjectOutputStream salida =
                new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(this);
        }
    }

    public static Torneo cargarTorneo(String archivo)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream entrada =
                new ObjectInputStream(new FileInputStream(archivo))) {
            return (Torneo) entrada.readObject();
        }
    }
}

