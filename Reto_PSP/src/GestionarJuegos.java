import java.util.Arrays;
import java.util.Scanner;

public class GestionarJuegos {
    static final int MAXIMAPARTIDAS = 10;
    static  Ficheros ficheros = new Ficheros();
    static void mostrarJuegos(String[] listaDeJuegos) {
        System.out.println("LISTA DE JUEGOS:");
        for (int i = 0; i < listaDeJuegos.length; i++) {
            String[] partes = listaDeJuegos[i].split(":");
            System.out.printf("%d.- Nombre del Juego: %s | Precio por partida: %s€ | Precio tarifa: %s€ | Partidas compradas: %s\n",
                    i, partes[0], partes[1], partes[2], partes[3]);
        }
    }



    //------------------------------------------------------------------------------------------------------------------
    static int comprarJuego(int saldo, String[] listaDeJuegos, int opcion) {
        Scanner sc = new Scanner(System.in);
        mostrarJuegos(listaDeJuegos);
        System.out.println("¿Qué juego quieres comprar?");
        int fila = sc.nextInt();

        Juego juego = buscarEnFila(fila, listaDeJuegos);

        int precioJuego = 0;
        if (opcion == 1) {
            precioJuego = juego.precioPartida;
        } else if (opcion == 2) {
            precioJuego = juego.precioTarifa;
        }

        if (juego.partidasCompradas > MAXIMAPARTIDAS) {
            return 0;
        } else if (precioJuego > saldo) {
            System.out.println("No tienes suficiente saldo para comprar este juego");
            return saldo;
        } else {
            saldo -= precioJuego;
            ficheros.cambiarLineaJuego(juego.nombre, juego.partidasCompradas + 1);
            System.out.println("La compra ha sido realizada");
            return saldo;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    static boolean mensajeError(int saldo) {
        if (saldo == -1) {
            System.out.println("No tienes suficiente saldo para comprarlo");
        } else if (saldo == 0) {
            System.out.println("No puedes comprar más partidas, el límite son " + MAXIMAPARTIDAS + " partidas");
        } else {
            System.out.println("La compra ha sido realizada");
            return false;
        }
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------

    static Juego buscarEnFila(int fila, String[] listaDeJuegos) {
        String[] partes = listaDeJuegos[fila].split(":");
        int[] valores = Arrays.stream(partes).skip(1).mapToInt(Integer::parseInt).toArray();
        return new Juego(partes[0], valores[0], valores[1], valores[2]);
    }
}
