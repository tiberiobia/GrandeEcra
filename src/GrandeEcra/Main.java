package GrandeEcra;

import java.util.Scanner;

import static GrandeEcra.Menus.*;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=========== GRANDEECRA ===========\n");
            System.out.println("Grandes histórias merecem um grande ecrã.");
            System.out.println(" ");
            System.out.println("1 - Admin");
            System.out.println("2 - Cliente");
            System.out.println("0 - Sair");
            System.out.println("Escolha uma opção:");
            opcao= input.nextInt();
            switch (opcao){
                case 1:
                    menuAdmin();
                    break;
                case 2:
                    menuCliente();
                    break;
                case 0:
                    System.out.println("\nA encerrar o sistema. Até breve!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        }while (opcao!=0);


    }
}