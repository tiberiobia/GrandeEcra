package GrandeEcra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Funcoes {
    //login
    public static boolean efetuarLogin() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("\n--- LOGIN ---");
            System.out.print("Username (ou '0' para sair): ");
            String username = input.nextLine().trim();

            if (username.equals("0")) {
                return false;
            }

            System.out.print("Password: ");
            String password = input.nextLine().trim();

            if (validarCredenciais(username, password)) {
                return true;
            } else {
                System.out.println("Credenciais incorretas! Tente novamente.");
            }
        }
    }
    public static boolean validarCredenciais(String username, String password) {
        File file = new File("Files/GrandeEcra_AdminLogin.csv");

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (linha.isEmpty()) continue;
                String[] partes = linha.split(";");
                if (partes.length >= 2) {
                    if (partes[0].trim().equals(username) && partes[1].trim().equals(password)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler ficheiro Autenticacao.csv");
        }
        return false;
    }
    //------------------------------------+
    //
}
