import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Juego {
    private Baraja baraja;
    private Jugador jugador1;
    private Jugador jugador2;
    private Mesa mesa;
    private String figuraTriunfo;
    private Carta cartaTriunfo;
    private InterfazGrafica interfaz;
    private Scanner scanner;
    private HashSet<String> cantesRealizados = new HashSet<>();

    public Juego() {
        scanner = new Scanner(System.in);
        baraja = new Baraja();
        mesa = new Mesa();
        interfaz = new InterfazGrafica();

        iniciarJugadores();
        repartirCartas();
        prepararUltimaCartaTriunfo();

        System.out.println("Figura de triunfo: " + figuraTriunfo + ", Carta de triunfo: " + cartaTriunfo);
        interfaz.mostrarCartaTriunfo(cartaTriunfo);
        interfaz.restaurarCartasBack(0);
        interfaz.restaurarCartasBack(1);
    }

    private void iniciarJugadores() {
        System.out.println("Introduce el nombre del Jugador 1:");
        String nombre1 = scanner.nextLine();
        System.out.println("Introduce el nombre del Jugador 2:");
        String nombre2 = scanner.nextLine();
        jugador1 = new Jugador(nombre1);
        jugador2 = new Jugador(nombre2);
    }

    private void repartirCartas() {
        for (int i = 0; i < 6; i++) {
            jugador1.recibirCarta(baraja.robar());
            jugador2.recibirCarta(baraja.robar());
        }
    }

    private void prepararUltimaCartaTriunfo() {
        cartaTriunfo = baraja.robar(); // Retirar carta de triunfo de la baraja solo al final
        figuraTriunfo = cartaTriunfo.getFigura();
    }

    public void jugar() {
        int turno = 1;

        while (jugador1.tieneCartas() && jugador2.tieneCartas()) {
            Jugador jugadorActual = (turno % 2 == 1) ? jugador1 : jugador2;
            Jugador jugadorOponente = (turno % 2 == 1) ? jugador2 : jugador1;

            System.out.println(jugadorActual.getNombre() + ", elige una carta para jugar:");
            mostrarCartas(jugadorActual);
            int index = scanner.nextInt() - 1;
            Carta cartaJugada = jugadorActual.jugarCarta(index);
            mesa.ponerCarta(cartaJugada);
            interfaz.mostrarCartaSeleccionada(turno % 2, index, cartaJugada);

            System.out.println(jugadorActual.getNombre() + " jugó: " + cartaJugada);

            // Turno del oponente
            System.out.println(jugadorOponente.getNombre() + ", elige una carta para jugar:");
            mostrarCartas(jugadorOponente);
            index = scanner.nextInt() - 1;
            Carta cartaOponente = jugadorOponente.jugarCarta(index);
            mesa.ponerCarta(cartaOponente);
            interfaz.mostrarCartaSeleccionada((turno + 1) % 2, index, cartaOponente);

            System.out.println(jugadorOponente.getNombre() + " jugó: " + cartaOponente);

            // Determinar ganador de la baza
            Jugador ganador = determinarGanador(cartaJugada, cartaOponente);
            System.out.println("¡" + ganador.getNombre() + " ganó la baza!");

            ganador.recogerBaza(mesa.getCartasJugadas());
            ganador.sumarPuntos(calcularPuntos(cartaJugada, cartaOponente));
            comprobarCante(ganador);

            // Limpiar mesa y robar cartas si quedan en la baraja
            mesa.limpiarMesa();
            if (baraja.tieneCartas()) {
                if (baraja.size() == 1) {
                    jugadorActual.recibirCarta(baraja.robar());
                    jugadorOponente.recibirCarta(cartaTriunfo); // La última carta será la carta de triunfo
                    interfaz.reemplazarBarajaConEmpty();
                } else {
                    jugadorActual.recibirCarta(baraja.robar());
                    jugadorOponente.recibirCarta(baraja.robar());
                    interfaz.restaurarCartasBack(turno % 2);
                    interfaz.restaurarCartasBack((turno + 1) % 2);
                }
            } else {
                // Si no hay cartas para robar, reducir cartas BACK en pantalla
                interfaz.reducirCartasBack(turno % 2, jugadorActual.getMazo().size());
                interfaz.reducirCartasBack((turno + 1) % 2, jugadorOponente.getMazo().size());
            }

            turno++;
        }
        mostrarResultadoFinal();
    }

    private void comprobarCante(Jugador jugador) {
        boolean tieneRey = false, tieneCaballo = false;
        String figuraCante = null;

        for (Carta carta : jugador.getMazo()) {
            if (carta.getValor() == 11) {
                tieneCaballo = true;
                figuraCante = carta.getFigura();
            } else if (carta.getValor() == 12 && figuraCante != null && figuraCante.equals(carta.getFigura())) {
                tieneRey = true;
            }
        }

        if (tieneRey && tieneCaballo && !cantesRealizados.contains(figuraCante)) {
            jugador.sumarPuntos(20);
            cantesRealizados.add(figuraCante);
            System.out.println("¡" + jugador.getNombre() + " ha hecho un cante con " + figuraCante + "!");
        }
    }

    private void mostrarCartas(Jugador jugador) {
        for (int i = 0; i < jugador.getMazo().size(); i++) {
            System.out.println((i + 1) + ". " + jugador.getMazo().get(i));
        }
    }

    private Jugador determinarGanador(Carta carta1, Carta carta2) {
        if (carta1.getFigura().equals(carta2.getFigura())) {
            return (carta1.getValor() > carta2.getValor()) ? jugador1 : jugador2;
        } else if (carta2.getFigura().equals(figuraTriunfo)) {
            return jugador2;
        } else {
            return jugador1;
        }
    }

    private int calcularPuntos(Carta carta1, Carta carta2) {
        int puntos = 0;
        puntos += obtenerPuntosCarta(carta1);
        puntos += obtenerPuntosCarta(carta2);
        return puntos;
    }

    private int obtenerPuntosCarta(Carta carta) {
        switch (carta.getValor()) {
            case 1: return 11;
            case 3: return 10;
            case 11: return 2;
            case 12: return 4;
            default: return 0;
        }
    }

    private void mostrarResultadoFinal() {
        System.out.println("Resultado final:");
        System.out.println(jugador1.getNombre() + ": " + jugador1.getPuntaje() + " puntos");
        System.out.println(jugador2.getNombre() + ": " + jugador2.getPuntaje() + " puntos");

        if (jugador1.getPuntaje() > jugador2.getPuntaje()) {
            System.out.println("¡" + jugador1.getNombre() + " ha ganado el juego!");
        } else if (jugador2.getPuntaje() > jugador1.getPuntaje()) {
            System.out.println("¡" + jugador2.getNombre() + " ha ganado el juego!");
        } else {
            System.out.println("¡El juego terminó en empate!");
        }
    }
}
