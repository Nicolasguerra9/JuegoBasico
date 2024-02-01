public class Juego {

    String nombre;
    int precioPartida;
    int precioTarifa;
    int partidasCompradas;

    Juego(String nombre, int precioPartida, int precioTarifa,int partidasCompradas) {
        this.nombre = nombre;
        this.precioPartida = precioPartida;
        this.precioTarifa = precioTarifa;
        this.partidasCompradas = partidasCompradas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecioPartida() {
        return precioPartida;
    }

    public void setPrecioPartida(int precioPartida) {
        this.precioPartida = precioPartida;
    }

    public int getPrecioTarifa() {
        return precioTarifa;
    }

    public void setPrecioTarifa(int precioTarifa) {
        this.precioTarifa = precioTarifa;
    }

    public int getPartidasCompradas() {
        return partidasCompradas;
    }

    public void setPartidasCompradas(int partidasCompradas) {
        this.partidasCompradas = partidasCompradas;
    }
}
