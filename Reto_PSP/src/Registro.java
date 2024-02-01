import java.util.Arrays;
import java.util.Optional;

public class Registro {
    static boolean comprobarConstrasenas(String contrasena, String reptetirContrasena) {
        if (contrasena.equals(reptetirContrasena)) return true;

        return false;
    }

    static boolean comprobarEmail(String email) {
        if (email.contains("@") && (email.contains(".")) == true)  return true;

        return false;
    }
    static int validarUsuario(String[] lineas, String usuario, String contrasena, String email) {
        if (lineas == null || lineas.length == 0) {
            return -1;
        }

        for (String linea : lineas) {
            String[] partes = linea.split(":");
            boolean coinciden = BCrypt.checkpw(contrasena, partes[5]);

            if (usuario.equals(partes[0]) && coinciden) {
                return 2; // Existe usuario y contraseña
            }

            if (usuario.equals(partes[0])) {
                return 0; // Existe usuario
            }

            if (email.equals(partes[3])) {
                return 1; // Existe email
            }
        }

        return -1;
    }


    static Usuario iniciar(String[] lineas, String nombre) {
        Optional<String> usuario = Arrays.stream(lineas)
                .filter(linea -> linea.startsWith(nombre + ":"))
                .findFirst();

        if (usuario.isPresent()) {
            String[] partes = usuario.get().split(":");
            int saldo = Integer.parseInt(partes[6]);
            return new Usuario(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], saldo);
        }

        return null;
    }

}
