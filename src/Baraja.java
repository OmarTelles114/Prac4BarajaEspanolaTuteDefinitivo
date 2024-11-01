import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

public class Baraja {
    private ArrayList<Carta> cartas;

    public Baraja() {
        cartas = new ArrayList<>();
        String[] figuras = {"BASTO", "COPA", "ESPADA", "ORO"};
        int[] valores = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};

        // Uso de lambdas y streams para añadir cartas
        for (String figura : figuras) {
            IntStream.of(valores)
                    .mapToObj(valor -> new Carta(figura, valor))
                    .forEach(cartas::add);
        }
        Collections.shuffle(cartas);
    }

    public Carta robar() {
        return cartas.isEmpty() ? null : cartas.remove(0);
    }

    public boolean tieneCartas() {
        return !cartas.isEmpty();
    }

    public int size() {
        return cartas.size();
    }
}
