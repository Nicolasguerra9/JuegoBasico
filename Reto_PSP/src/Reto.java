public class Reto {
    static PedirDatos pedirDatos = new PedirDatos();
    static Ficheros ficheros = new Ficheros();
    public static void main(String[] args) {
        menuInicio();
    }

     private static void menuInicio() {
        int opcion;
        do {
            opcion = pedirDatos.menuGRegistro();
            menus(opcion);
        } while (opcion != 0);
    }
    private static void menuJuego() {
        int opcion;
        do {
            opcion = pedirDatos.menuJuegoOnline();
            menusJuego(opcion);
        } while (opcion != 0);
    }

    //menu principal
    private static void menus(int opcion) {
        switch (opcion){
            case 1:
                Usuario nuevoUsuario = pedirDatos.rellenarRegistro();
                if (nuevoUsuario != null) {
                    ficheros.agregarRegistro(nuevoUsuario);
                }
                break;
            case 2:
                if (pedirDatos.iniciarSesion()){
                    menuJuego();
                }
                break;
            case 0:
                System.out.println("¡El programa ha finalizado!");
                break;
            default:
                System.out.println("Error al elegir la opción");
                break;
        }
    }

    //2 menu despues del principal
    private static void menusJuego(int opcion) {
        switch (opcion){
            case 1:
                System.out.println("No estas preparado para esto ya que es de la UF2");
                pedirDatos.jugar();
                break;
            case 2:
                pedirDatos.gestionarJuegos(pedirDatos.menuGestionarJuegos());
                break;
            case 3:
                pedirDatos.gestionarSaldo(pedirDatos.menuSaldo());
                break;
            case 4:
                pedirDatos.gestionarDatos(pedirDatos.menuGestionarDatos());
                break;
            case 0:
                System.out.println("Has vuelto al menu principal");
                break;
            default:
                System.out.println("Error al elegir la opción");
                break;
        }
    }
}
