import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Ficheros {
    static final String RUTAUSERS = "admin/users.txt";
    static final String RUTAJUEGOS = "admin/juegos.txt";



    static void agregarRegistro(Usuario usuario) {
        final String nuevoRegistro = usuario.nombreUsuario + ":" + usuario.nombre + ":" + usuario.apellidos + ":" + usuario.email + ":" + usuario.cuenta  + ":" + usuario.contrasenaEncriptada + ":" + usuario.saldo;
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(RUTAUSERS, true)))) {
            pw.println(nuevoRegistro);
        } catch (IOException e) {
            System.out.println("Error no se puede escribir en el fichero: " + e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    static String[] leerFichero(int fichero) {
        String ruta = fichero == 1 ? RUTAUSERS : RUTAJUEGOS;
        try (Stream<String> stream = Files.lines(Paths.get(ruta))) {
            return stream.toArray(String[]::new);
        } catch (IOException e) {
            System.out.println(e);
        }
        return new String[0];
    }

    //------------------------------------------------------------------------------------------------------------------
    static void cambiarLineaUsuario(String nombreUsuario, String lineaRemplazar) {
        String[] lineas = leerFichero(1);

        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i].startsWith(nombreUsuario + ":")) {
                lineas[i] = nombreUsuario + ":" + lineaRemplazar;
                guardarArray(1, lineas);
                break; // no necesitamos seguir iterando
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    static void cambiarLineaJuego(String nombreJuego, int numeroComprado) {
        String[] lineas = leerFichero(2);
        String stringNumeroComprado = Integer.toString(numeroComprado);

        for (int i = 0; i < lineas.length; i++) {
            String[] linea = lineas[i].split(":");
            if (linea[0].equals(nombreJuego)) {
                String lineaNueva =  linea[0] + ":" + linea[1]  + ":" +  linea[2]  + ":" + stringNumeroComprado;
                if (!lineaNueva.equals(lineas[i])) {
                    lineas[i] = lineaNueva;
                    guardarArray(2, lineas);
                }
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    static void guardarArray(int fichero, String[] array) {
        try {
            Path ruta = null;
            switch (fichero) {
                case 1:
                    ruta = Paths.get(RUTAUSERS);
                    break;
                case 2:
                    ruta = Paths.get(RUTAJUEGOS);
                    break;
                default:
                    throw new IllegalArgumentException("Fichero desconocido");
            }
            Files.write(ruta, Arrays.asList(array), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error no se puede escribir en el fichero: " + e);
        }
    }
}