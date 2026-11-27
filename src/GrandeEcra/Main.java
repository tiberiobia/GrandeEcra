package GrandeEcra;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static GrandeEcra.Menus.*;
import static GrandeEcra.MetodosBasicos.*;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("---------------------------\n");
            System.out.println("=========== GRANDEECRA ===========\n");
            System.out.println("Grandes histórias merecem um grande ecrã.");
            System.out.println();
            System.out.println("---------------------------");
            System.out.println("1- Admin");
            System.out.println("2- Cliente");
            System.out.println("0- Sair");
            System.out.println("---------------------------");
            System.out.print("Escolha uma opção:");
            opcao= input.nextInt();
            switch (opcao){
                case 1:
                    menuAdmin();
                    break;
                case 2:
                    menuCliente();
                    break;
                case 0:
                    System.out.println();
                    List<String> copyright = lerFicheiroBruto("Files/GrandeEcra_Copyright.txt");
                    for (String linha : copyright) {
                        System.out.println(linha);
                    }
                    break;
                    
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        }while (opcao!=0);


    }
}