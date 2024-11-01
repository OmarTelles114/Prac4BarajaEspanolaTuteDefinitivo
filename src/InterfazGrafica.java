import javax.swing.*;
import java.awt.*;

public class InterfazGrafica extends JFrame {
    private JLabel[] cartasJugador1, cartasJugador2;
    private JLabel cartaTriunfoLabel, barajaLabel;

    private static final int CARTA_ANCHO = 60;
    private static final int CARTA_ALTO = 90;
    private static final String BACK_IMAGE = "src/CartasBaraja/BACK.png";
    private static final String EMPTY_IMAGE = "src/CartasBaraja/EMPTY.png";

    public InterfazGrafica() {
        setTitle("Tute en Java");
        setLayout(new GridLayout(1, 3)); // Dividir la ventana en 3 columnas
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel para cartas del jugador 2 (primera columna)
        JPanel panelJugador2 = new JPanel(new GridLayout(6, 1, 5, 5));
        cartasJugador2 = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            cartasJugador2[i] = new JLabel(escalarImagen(BACK_IMAGE));
            panelJugador2.add(cartasJugador2[i]);
        }

        // Panel central para carta de triunfo y baraja (segunda columna)
        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 5, 5));
        cartaTriunfoLabel = new JLabel(escalarImagen(BACK_IMAGE));
        barajaLabel = new JLabel(escalarImagen(BACK_IMAGE));
        panelCentro.add(cartaTriunfoLabel);
        panelCentro.add(barajaLabel);

        // Panel para cartas del jugador 1 (tercera columna)
        JPanel panelJugador1 = new JPanel(new GridLayout(6, 1, 5, 5));
        cartasJugador1 = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            cartasJugador1[i] = new JLabel(escalarImagen(BACK_IMAGE));
            panelJugador1.add(cartasJugador1[i]);
        }

        add(panelJugador2);
        add(panelCentro);
        add(panelJugador1);
        setVisible(true);
    }

    private ImageIcon escalarImagen(String ruta) {
        ImageIcon icon = new ImageIcon(ruta);
        Image imagen = icon.getImage().getScaledInstance(CARTA_ANCHO, CARTA_ALTO, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }

    public void mostrarCartaSeleccionada(int jugador, int posicion, Carta carta) {
        JLabel[] cartasJugador = (jugador == 1) ? cartasJugador1 : cartasJugador2;
        cartasJugador[posicion].setIcon(escalarImagen(carta.getImagen()));
    }

    public void restaurarCartasBack(int jugador) {
        JLabel[] cartasJugador = (jugador == 1) ? cartasJugador1 : cartasJugador2;
        for (int i = 0; i < cartasJugador.length; i++) {
            cartasJugador[i].setIcon(escalarImagen(BACK_IMAGE));
        }
    }

    public void reducirCartasBack(int jugador, int cartasRestantes) {
        JLabel[] cartasJugador = (jugador == 1) ? cartasJugador1 : cartasJugador2;
        for (int i = 0; i < 6; i++) {
            if (i < 6 - cartasRestantes) {
                cartasJugador[i].setIcon(escalarImagen(EMPTY_IMAGE));
            } else {
                cartasJugador[i].setIcon(escalarImagen(BACK_IMAGE));
            }
        }
    }

    public void colocarEmpty(int jugador, int posicion) {
        JLabel[] cartasJugador = (jugador == 1) ? cartasJugador1 : cartasJugador2;
        cartasJugador[posicion].setIcon(escalarImagen(EMPTY_IMAGE));
    }

    public void mostrarCartaTriunfo(Carta carta) {
        cartaTriunfoLabel.setIcon(escalarImagen(carta.getImagen()));
    }

    public void reemplazarBarajaConEmpty() {
        barajaLabel.setIcon(escalarImagen(EMPTY_IMAGE));
    }
}
