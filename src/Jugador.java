import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> mazo;
    private int puntaje;
    private ArrayList<Carta> bazasGanadas; // Mazo de cartas ganadas

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mazo = new ArrayList<>();
        this.puntaje = 0;
        this.bazasGanadas = new ArrayList<>();
    }

    public void recibirCarta(Carta carta) {
        mazo.add(carta);
    }

    public Carta jugarCarta(int index) {
        return mazo.remove(index);
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void sumarPuntos(int puntos) {
        puntaje += puntos;
    }

    public boolean tieneCartas() {
        return !mazo.isEmpty();
    }

    // Nuevo método para recoger cartas ganadas en las bazas
    public void recogerBaza(ArrayList<Carta> cartasGanadas) {
        bazasGanadas.addAll(cartasGanadas);
    }

    @Override
    public String toString() {
        return nombre + " tiene " + mazo.size() + " cartas y " + puntaje + " puntos.";
    }
}
