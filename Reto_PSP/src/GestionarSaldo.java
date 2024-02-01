import java.util.Scanner;

public class GestionarSaldo {

    static Usuario usuarioIniciado;
    //para agregar tu saldo(max 20)
    static int agregarSaldo(int saldoActual) {
        final int MAXIMO_INGRESO = 20;
        Scanner sc = new Scanner(System.in);
        System.out.println("Cuánto dinero quieres ingresar: ");
        int ingreso = sc.nextInt();

        if (ingreso > MAXIMO_INGRESO) {
            System.out.println("Has sobrepasado el límite de ingreso");
        } else {
            saldoActual += ingreso;
            System.out.println("Tu saldo actual es: " + saldoActual + "€");
        }

        return saldoActual;
    }



    //para retirar tu saldo
    static int retirarSaldo(int saldoActual) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Cuanto dinero quieres retirar: ");
        int retirada = sc.nextInt();

        if (retirada >= saldoActual) {
            System.out.println("No puedes retirar esa cantidad, no tienes saldo suficiente");
            return saldoActual;
        }

        saldoActual -= retirada;
        System.out.println("Tu saldo actual es: " + saldoActual + "€");
        return retirada;
    }

}
