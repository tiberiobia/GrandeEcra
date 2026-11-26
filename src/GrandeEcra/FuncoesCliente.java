package GrandeEcra;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static GrandeEcra.MetodosBasicos.*;

public class FuncoesCliente {
    public static void registroCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Novo Registo de Cliente ===");

        System.out.print("Introduza o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Introduza o contacto telefónico: ");
        String telefone = scanner.nextLine();

        System.out.print("Introduza o email: ");
        String email = scanner.nextLine();

        System.out.println("\nCliente inserido com sucesso!");
        System.out.println("--- Dados Introduzidos ---");
        System.out.println("Nome: " + nome);
        System.out.println("Contacto Telefónico: " + telefone);
        System.out.println("Email: " + email);

        System.out.println();
    }

    public static void lerCartaz() {
        List<String> linhas = lerTodasLinhas("Files/GrandeEcra_Cartaz.txt");
        for (String linha : linhas) {
            System.out.println(linha);

        }

    }

    public static void pesqFilme() {
        String[][] matriz = criarMatriz("Files/GrandeEcra_Filmes.csv", ";");
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o género que quer pesquisar:");
        String genero = sc.nextLine().trim().toLowerCase();

        boolean encontrado = false;

        for (int i = 0; i < matriz.length; i++) {

            if (matriz[i][2].equals(genero)) {
                //apresentando título, realizador, ano, duração,
                //idade mínima e classificação
                System.out.printf("- %s, %s, %s, %s, %s, %s",
                        matriz[i][1], matriz[i][3], matriz[i][4], matriz[i][5], matriz[i][6], matriz[i][7]);

                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Não foram encontrados filmes.");
        }
    }

    public static void mapaSala(String nomeSala, String[][] matriz) {


        //exibir sala
        System.out.println("      Sala " + nomeSala);
        System.out.println("      Ecrã");
        System.out.println("----------------------");
        //numero das colunas
        System.out.println("  ");
        for (int k = 0; k < matriz[0].length; k++) {
            System.out.print((k + 1) + " ");

        }
        System.out.println();

        int disponiveis = 0;
        int ocupados = 0;
        char letraFila = 'A';
        for (int i = 0; i < matriz.length; i++) {
            //exibir a fila
            System.out.print(letraFila + " ");
            letraFila++;
            for (int j = 0; j < matriz[i].length; j++) {
                String situacao = matriz[i][j];
                if (situacao.equals("0")) {
                    System.out.print("O ");
                    disponiveis++;
                } else if (situacao.equals("1")) {
                    System.out.print("x ");
                    ocupados++;
                }

            }
            System.out.println();

        }
        int totalLugares = ocupados + disponiveis;
        double taxaOcupacao = 0;
        if (totalLugares > 0) {
            taxaOcupacao = (ocupados * 100.0) / totalLugares;
        }
        System.out.println("O - Disponivel | X - Ocupado");
        System.out.printf("Lotação: %d | Disponiveis: %d | Ocupados: %d", totalLugares, disponiveis, ocupados);
        System.out.println("Taxa de ocupacao: " + taxaOcupacao + "%");


    }

    public static void reservarBilhete() {
        Scanner input = new Scanner(System.in);
        Map<String, String[][]> sala = escolherFicheiro();

        // Sala vazia ou opção cancelada
        if (sala.isEmpty()) {
            return;
        }

        String nomeSala = sala.keySet().iterator().next();
        String[][] matriz = sala.get(nomeSala);
        mapaSala(nomeSala, matriz);

        //conta lugares disponiveis
        int disponiveis = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].equals("0")) {
                    disponiveis++;
                }
            }
        }

        // Verificar se a sala está cheia
        if (disponiveis == 0) {
            System.out.println("A sala encontra-se cheia. Não é possível efetuar reservas.");
            return;
        }

        //quant de bilhetes
        int quantidade = 0;
        do {
            System.out.print("- Quantidade de bilhetes: ");
            if (input.hasNextInt()) {
                quantidade = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine(); // Descartar entrada inválida
                quantidade = 0;
            }

            if (quantidade <= 0 || quantidade > disponiveis) {
                System.out.println("Quantidade inválida. Introduza um valor entre 1 e " + disponiveis + ".");
            }
        } while (quantidade <= 0 || quantidade > disponiveis);

        String []lugaresReservados=new String[quantidade];
        //verificar os lugares
        for (int i = 1; i <= quantidade; i++) {
            boolean lugarValido=false;
            while (!lugarValido){
                System.out.print("Digite a fileira :");
                String fileira= input.next().trim().toUpperCase();
                System.out.print("Digite o numero da cadeira");
                int cadeira=lerInteiro();

                //transformar em indices da matriz
                int linha=-1, coluna=-1;



            }




        }



    }


}
