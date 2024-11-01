public class Carta {
    private String figura;
    private int valor;
    private String imagen; // Nueva variable para la imagen de la carta

    public Carta(String figura, int valor) {
        this.figura = figura;
        this.valor = valor;
        this.imagen = generarRutaImagen(figura, valor); // Asignar la ruta de la imagen
    }

    // Método para generar la ruta de la imagen basada en la figura y el valor
    private String generarRutaImagen(String figura, int valor) {
        return "src/CartasBaraja/" + valor + " de " + figura.toLowerCase() + ".png"; // Ejemplo de ruta
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getImagen() {
        return imagen; // Devolver la ruta de la imagen
    }

    @Override
    public String toString() {
        return valor + " de " + figura;
    }
}
