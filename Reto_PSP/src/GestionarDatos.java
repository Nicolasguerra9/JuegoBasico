import java.util.Scanner;

public class GestionarDatos {

    static Scanner sc = new Scanner(System.in);
    static Ficheros ficheros = new Ficheros();
    static Registro registro = new Registro();
    static String cambiarNombre(Usuario usuario) {
        System.out.println("Tu nombre es: " + usuario.nombre);
        System.out.println("Introduce el nuevo nombre: ");
        String nuevoNombre = sc.next();
        if (usuario.nombre.equals(nuevoNombre)){
            System.out.println("No puedes poner el mismo nombre");
            nuevoNombre = usuario.nombre;
        }
        return nuevoNombre;
    }
    static String cambiarApellido(Usuario apellido) {
        System.out.println("Tu apellido es: " + apellido.apellidos);
        System.out.println("Introduce el nuevo apellido: ");
        String nuevoApellido = sc.next();
        if (apellido.apellidos.equals(nuevoApellido)){
            System.out.println("No puedes poner el mismo apellido");
            nuevoApellido = apellido.apellidos;
        }
        return nuevoApellido;
    }

    static String cambiarMail(Usuario email) {
        System.out.println("Tu email es: " + email.email);
        System.out.println("Introduce el nuevo email: ");
        String nuevoEmail = sc.next();
        if (email.email.equals(nuevoEmail)){
            System.out.println("No puedes poner el mismo email");
            nuevoEmail = email.email;
        }
        return nuevoEmail;
    }

    static String cambiarCuenta(Usuario cuenta) {
        System.out.println("Tu cuenta es: " + cuenta.cuenta);
        System.out.println("Introduce la nueva cuenta: ");
        String nuevaCuenta = sc.next();
        if (cuenta.cuenta.equals(nuevaCuenta)){
            System.out.println("No puedes poner el mismo numero de cuenta");
            nuevaCuenta = cuenta.cuenta;
        }
        return nuevaCuenta;
    }

    static String cambiarContrasena(String nombre) {
        String[] datos = ficheros.leerFichero(1);
        System.out.println("Introduce la contraseña:");
        String contrasena = sc.next();

        if (registro.validarUsuario(datos,nombre,contrasena,"") == 2) {
            System.out.println("Introduce la nueva contraseña: ");
            String nuevaContrasena = sc.next();
            String contrasenaEncriptada = BCrypt.crypt(nuevaContrasena);
            return contrasenaEncriptada;
        }
        return "";
    }
}
