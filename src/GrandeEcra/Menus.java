package GrandeEcra;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import static GrandeEcra.FuncAdmin.*;
import static GrandeEcra.FuncCliente.*;

import static GrandeEcra.MetodosBasicos.*;

public class Menus {
    //menus principais

    /**
     * Apresenta o menu administrativo após validar o login e executa a opção escolhida.
     * @throws FileNotFoundException
     */
    public static void menuAdmin() throws FileNotFoundException {
        //Login obrigatorio
        if (efetuarLogin()) {
            System.out.println("\n Login efetuado com sucesso!");
            int opcao;
            do {
                System.out.println("\n1- Consultar ficheiros");
                System.out.println("2- Estatisticas gerais de vendas");
                System.out.println("3- Receita por sala");
                System.out.println("4- Receita por filme");
                System.out.println("5- Top 3 filmes com maior receita");
                System.out.println("6- Resetar lotacao de uma sala");
                System.out.println("0- Voltar");

                System.out.print("Escolha uma opção: ");
                opcao = lerInteiro();

                switch (opcao) {
                    case 1:
                        menuFicheiros();
                        break;
                    case 2:
                        estatisticasVendas();
                        break;
                    case 3:
                        receitaSala();
                        break;
                    case 4:
                        receitaFilme();
                        break;
                    case 5:
                        consultarFilme();
                        break;
                    case 6:
                        resetarSala();
                        break;
                    case 0:
                        System.out.println("\nA voltar para o menu inicial!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } while (opcao != 0);
        } else {
            System.out.println("Acesso encerrado pelo utilizador.");
        }


    }

    /**
     *  Apresenta o menu principal do cliente e executa a opção escolhida
     */
    public static void menuCliente(){
        int opcao;
        do {
            System.out.println("\n1- Novo registo");
            System.out.println("2- Consultar cartaz cinematografico");
            System.out.println("3- Pesquisar filmes");
            System.out.println("4- Visualizar mapa de uma sala");
            System.out.println("5- Reservar bilhetes");
            System.out.println("6- Recomendar filmes");
            System.out.println("7- Quiz cinematografico");
            System.out.println("0- Voltar");

            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    registroCliente();
                    break;
                case 2:
                    lerCartaz();

                    break;
                case 3:
                    pesqFilme();

                    break;
                case 4:
                    Map<String, String[][]> sala = escolherFicheiro();
                    if (sala.isEmpty()) {
                        break; //volta ao menuCliente
                    }
                    String nomeSala = sala.keySet().iterator().next();
                    String[][] matriz = sala.get(nomeSala);
                    mapaSala(nomeSala, matriz);
                    break;

                case 5:
                    reservarBilhete();
                    break;
                case 6:
                    recomendarFilmes();
                    break;
                case 7:
                    quizCinematografico();
                    break;
                case 0:
                    System.out.println("\nA voltar para o menu inicial!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);


}

    //---------------------------------------------------------------------------
    //sub menu

    /**
     * Apresenta o menu de consulta dos nove ficheiros do sistema (CSV e TXT) e exibe o conteúdo do ficheiro escolhido.
     */
    public static void menuFicheiros(){
        //os nove ficheiros
//       O ficheiro de autenticação é exclusivamente de consulta
//        ADMIN,
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\nqual ficheiro queres ver:");
            System.out.println("1-AdminLogin.csv");
            System.out.println("2-Bilhetes.csv");
            System.out.println("3-Filmes.csv ");
            System.out.println("4-Quiz.csv");
            System.out.println("5-Sala_Mar.csv");
            System.out.println("6-Sala_Terra.csv");
            System.out.println("7-Sala_Sol.csv");
            System.out.println("8-Cartaz.txt");
            System.out.println("9-Copyright.txt");
            System.out.println("0-voltar para o menu anterior");
            System.out.print("Escolha uma opção:");
            opcao=lerInteiro();
            switch (opcao){
                case 1:
                    //tem que validar o login
                   if (efetuarLogin()){
                       System.out.println(lerTodasLinhas("Files/GrandeEcra_AdminLogin.csv"));
                   }else{
                       System.out.println("Login necessario nao efetuado pelo utilizador");
                   }
                    break;
                case 2:
                    System.out.println(lerTodasLinhas("Files/GrandeEcra_Bilhetes.csv"));
                    break;
                case 3:
                    System.out.println(lerTodasLinhas("Files/GrandeEcra_Filmes.csv"));
                    break;
                case 4:
                    System.out.println(lerTodasLinhas("Files/GrandeEcra_Quiz.csv"));
                    break;
                case 5:
                    System.out.println(lerTodasLinhas("Files/Sala_Mar.csv"));
                    break;
                case 6:
                    System.out.println(lerTodasLinhas("Files/Sala_Terra.csv"));
                    break;
                case 7:
                    System.out.println(lerTodasLinhas("Files/Sala_Sol.csv"));
                    break;
                case 8:
                    System.out.println(lerTodasLinhas("Files/GrandeEcra_Cartaz.txt"));
                    break;
                case 9:
                    System.out.println(lerTodasLinhas("Files/GrandeEcra_Copyright.txt"));
                    break;
                case 0:
                    System.out.println("\nA voltar para o menu inicial!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        }while (opcao!=0);



    }

}




