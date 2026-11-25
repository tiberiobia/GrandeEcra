package GrandeEcra;

import java.util.Scanner;

public class MetodosBasicos {
    //---------------------------------------------
    //Ler numeros
    public static int lerInteiro() {
        Scanner sc=new Scanner(System.in);
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor introduza um número inteiro válido: ");
            }
        }
    }

    public static double lerDouble() {
        Scanner sc=new Scanner(System.in);

        while (true) {
            try {
                String input = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor introduza um valor decimal válido: ");
            }
        }
    }

}
