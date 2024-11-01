import java.util.ArrayList;

public class Mesa {
    private ArrayList<Carta> cartasJugadas;

    public Mesa() {
        cartasJugadas = new ArrayList<>();
    }

    public void ponerCarta(Carta carta) {
        cartasJugadas.add(carta);
    }

    public Carta obtenerCarta(int index) {
        return cartasJugadas.get(index);
    }

    public void limpiarMesa() {
        cartasJugadas.clear();
    }

    public ArrayList<Carta> getCartasJugadas() {
        return cartasJugadas;
    }
}
