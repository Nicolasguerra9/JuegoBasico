import java.util.Scanner;

public class PedirDatos {

    static Scanner sc = new Scanner(System.in);
    static Registro registro = new Registro();
    static Ficheros ficheros = new Ficheros();
    static  GestionarDatos gestionarDatos = new GestionarDatos();
    static GestionarJuegos gestionarJuegos = new GestionarJuegos();
    static GestionarSaldo gestionarSaldo = new GestionarSaldo();
    static final int SALDOINICIAL = 0;
    static final int FICHEROUSUARIOS = 1;
    static final int FICHEROJUEGOS = 2;
    static Usuario usuarioIniciado;
    //--------------------------------------- Menu Principal ---------------------------------------------------------
    static int menuGRegistro() {
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("MENU DE REGISTROS");
        System.out.println("1.Registre");
        System.out.println("2.Accedir");
        System.out.println("0.Soritr");
         System.out.println("---------------------------------");
        int opcion = sc.nextInt();
        System.out.println("Has elegido la opcion: " + opcion);

        return opcion;
    }

    //----------------------------------------- REGISTRO ---------------------------------------------------------------
    static Usuario rellenarRegistro() {
        String[] datos = ficheros.leerFichero(1);
        System.out.println();
        System.out.println("REGISTRE");

        System.out.print("Nombre Usuario: ");
        String nombreUsuario = sc.next();
        if (registro.validarUsuario(datos, nombreUsuario, "", "") == 0) {
            System.out.println("El usuario existe");
            return null;
        }

        System.out.print("email: ");
        String email = sc.next();
        if (!registro.comprobarEmail(email)) {
            System.out.println("ERROR: email incorrecto");
            return null;
        }
        if (registro.validarUsuario(datos, nombreUsuario, "", email) == 1) {
            System.out.println("El email existe");
            return null;
        }

        System.out.print("Contraseña: ");
        String contrasena = sc.next();
        System.out.print("Repetir contraseña: ");
        String reptetirContrasena = sc.next();
        if (!registro.comprobarConstrasenas(contrasena, reptetirContrasena)) {
            System.out.println("Las contraseñas no coinciden");
            return null;
        }
        String contrasenaEncriptada = BCrypt.crypt(contrasena);

        System.out.print("Nombre: ");
        String nombre = sc.next();
        System.out.print("Apellidos: ");
        String apellidos = sc.next();
        System.out.print("Cuenta Banco: ");
        String cuenta = sc.next();

        System.out.println("Usuari agregado!");

        return new Usuario(nombre, nombreUsuario, apellidos, email, cuenta, contrasenaEncriptada, SALDOINICIAL);
    }

    //----------------------------------------- Iniciar Sesion ---------------------------------------------------------

    static boolean iniciarSesion() {
        String[] datos = ficheros.leerFichero(1);
        System.out.print("Introduce el usuario: ");
        String nombre = sc.next();
        System.out.print("Introduce la contraseña: ");
        String contrasena = sc.next();

        if (registro.validarUsuario(datos, nombre, contrasena, "") == 2) {
            System.out.println("Has iniciado sesión");
            usuarioIniciado = registro.iniciar(datos, nombre);
            return true;
        }

        System.out.println("Error al inicar sesión");
        return false;
    }


    //------------------------------------------- Segundo Menu ---------------------------------------------------------

    static int menuJuegoOnline() {
        System.out.println();

        System.out.println("-----------------------------");
        System.out.println("JUEGOS ONLINE:");
        System.out.println("1.- Jugar");
        System.out.println("2.- Gestionar jocs");
        System.out.println("3.- Gestionar saldo");
        System.out.println("4.- Gestionar les dades de l'usuari");
        System.out.println("0.- Sortida al menú d'entrada");
        System.out.println("-----------------------------");
        int opcion = sc.nextInt();
        System.out.println("Has triat l'opció: " + opcion);
        return opcion;
    }


    //--------------------------------------- Gestionar JUEGOS ---------------------------------------------------------

    static int menuGestionarJuegos() {
        System.out.println("-----------------------------");
        System.out.println("MENU GESTIONAR JUEGOS");
        System.out.println("Elige la opción:");
        System.out.println("1.- Mostrar juegos y precios ");
        System.out.println("2.- Comprar");
        System.out.println("0.- Salir");
        System.out.println("-----------------------------");
        int opcion = sc.nextInt();
        return opcion;
    }

    static int menuComprarJuego() {
        System.out.println("-----------------------------");
        System.out.println("MENU COMPRAR JUEGOS");
        System.out.println("Elige la opción:");
        System.out.println("1.- Comprar tarfia");
        System.out.println("2.- Comprar partidas");
        System.out.println("0.- Salir");
        System.out.println("-----------------------------");
        int opcion = sc.nextInt();
        return opcion;
    }


    static int jugar() {
        String[] juegosArray = ficheros.leerFichero(FICHEROJUEGOS);
        gestionarJuegos.mostrarJuegos(juegosArray);
        System.out.println("A que juego quieres jugar: ");
        int opcion = sc.nextInt();
        if (opcion >= 0 && opcion < juegosArray.length) {
            String juego = juegosArray[opcion];
            String[] partes = new String[0];
            partes = juego.split(":");
            if (Integer.parseInt(partes[3]) > 0) {
                //restar una partida
                System.out.println("Estas Jugando");
                int fila = 0;
                Juego juegooo = GestionarJuegos.buscarEnFila(fila, juegosArray);
                ficheros.cambiarLineaJuego(juegooo.getNombre(), juegooo.getPartidasCompradas() - 1);

            } else {
                System.out.println("No tienes partidas suficientes");
            }
        }else {
            System.out.println("Ese juego no existe");
        }

        return opcion;
    }




    static void gestionarJuegos(int opcion) {
        switch (opcion) {
            case 1 -> gestionarJuegos.mostrarJuegos(ficheros.leerFichero(FICHEROJUEGOS));
            case 2 -> gestionarCompras(menuComprarJuego());
            default -> System.out.println("Error OPCION NO VALIDA");
        }
    }


    static void gestionarCompras(int opcion) {
        String[] lista = ficheros.leerFichero(2);
        int saldo;
        switch (opcion) {
            case 1 -> {
                usuarioIniciado.saldo = gestionarJuegos.comprarJuego(usuarioIniciado.getSaldo(), lista, FICHEROJUEGOS);
                ficheros.cambiarLineaUsuario(usuarioIniciado.getNombreUsuario(), nuevaLinea(usuarioIniciado));
            }
            case 2 -> {
                saldo = gestionarJuegos.comprarJuego(usuarioIniciado.getSaldo(), lista, FICHEROUSUARIOS);
                if (!gestionarJuegos.mensajeError(saldo)) {
                    usuarioIniciado.saldo = saldo;
                    ficheros.cambiarLineaUsuario(usuarioIniciado.nombreUsuario, nuevaLinea(usuarioIniciado));
                }
            }
            default -> System.out.println("Error OPCION NO VALIDA");
        }
    }



    //---------------------------------------- Gestionar Datos ---------------------------------------------------------
    static int menuGestionarDatos() {
        System.out.println("-----------------------------");
        System.out.println("Que datos quieres cambiar:");
        System.out.println("1.- Contraseña");
        System.out.println("2.- Cuenta");
        System.out.println("3.- Email");
        System.out.println("4.- Apellido");
        System.out.println("5.- Nombre");
        System.out.println("0.- Salir");
        System.out.println("-----------------------------");
        int opcion = sc.nextInt();
        return opcion;
    }

    static void gestionarDatos(int opcion) {
        switch (opcion) {
            case 1:
                String nuevaContrasena = gestionarDatos.cambiarContrasena(usuarioIniciado.nombre);
                if (nuevaContrasena != "") {
                    usuarioIniciado.contrasenaEncriptada = nuevaContrasena;
                    actualizarDatosUsuario();
                }
                break;
            case 2:
                usuarioIniciado.cuenta = gestionarDatos.cambiarCuenta(usuarioIniciado);
                actualizarDatosUsuario();
                break;
            case 3:
                String nuevoMail = gestionarDatos.cambiarMail(usuarioIniciado);
                if (registro.comprobarEmail(nuevoMail) == false) {
                    System.out.println("Error: formato del email incorrecto");
                } else {
                    usuarioIniciado.email = nuevoMail;
                    actualizarDatosUsuario();
                }
                break;
            case 4:
                usuarioIniciado.apellidos = gestionarDatos.cambiarApellido(usuarioIniciado);
                actualizarDatosUsuario();
                break;
            case 5:
                usuarioIniciado.nombre = gestionarDatos.cambiarNombre(usuarioIniciado);
                actualizarDatosUsuario();
                break;
            default:
                System.out.println("Error OPCION NO VALIDA");
                break;
        }
    }

    static void actualizarDatosUsuario() {
        ficheros.cambiarLineaUsuario(usuarioIniciado.nombreUsuario, nuevaLinea(usuarioIniciado));
    }


    //------------------------------------------------- Saldo --------------------------------------------------------------
    static int menuSaldo() {
        System.out.println("-----------------------------");
        System.out.println("Que deseas hacer:");
        System.out.println("1.- Añadir saldo");
        System.out.println("2.- Retirar saldo");
        System.out.println("3.- Saldo Actual");
        System.out.println("0.- Salir");
        System.out.println("-----------------------------");
        int opcion = sc.nextInt();
        return opcion;
    }

    static void gestionarSaldo(int opcion) {
        String[] lista = ficheros.leerFichero(2);
        try {
            switch (opcion) {
                case 1:
                    usuarioIniciado.saldo = gestionarSaldo.agregarSaldo(usuarioIniciado.saldo);
                    ficheros.cambiarLineaUsuario(usuarioIniciado.nombreUsuario, nuevaLinea(usuarioIniciado));
                    break;
                case 2:
                    usuarioIniciado.saldo = gestionarSaldo.retirarSaldo(usuarioIniciado.saldo);
                    ficheros.cambiarLineaUsuario(usuarioIniciado.nombreUsuario, nuevaLinea(usuarioIniciado));
                    break;
                case 3:
                    System.out.println("Este es tu saldo actual: " + usuarioIniciado.getSaldo() + "€");
                    break;
                default:
                    System.out.println("Error: OPCION NO VALIDA");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    static String nuevaLinea(Usuario u){
         return u.nombreUsuario + ":" + u.nombre + ":" + u.apellidos + ":" + u.email + ":" + u.cuenta  + ":" + u.contrasenaEncriptada + ":" + u.saldo;
    }
}
