package GrandeEcra;

import java.io.*;
import java.util.*;

public class MetodosBasicos {

    //---------------------------------------------
    //Ler numeros
    public static int lerInteiro() {
        Scanner sc = new Scanner(System.in);
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
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                String input = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor introduza um valor decimal válido: ");
            }
        }
    }

    //-------------------------------------------------------------------------------
    //numero aleatorio

    //--------------------------------------------------------------------------
    //ficheiros
    public static List<String> lerTodasLinhas(String caminhoFicheiro) {
        List<String> linhas = new ArrayList<>();
        File file = new File(caminhoFicheiro);
        if (!file.exists()) return linhas;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String l = sc.nextLine().trim();
                if (!l.isEmpty()) {
                    linhas.add(l);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler ficheiro " + caminhoFicheiro);
        }
        return linhas;
    }

    public static void reescreverFicheiroMatriz(String caminhoFicheiro, String[][] matriz) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(caminhoFicheiro, false));
            for (int i = 0; i < matriz.length; i++) {
                String linha = "";
                for (int j = 0; j < matriz[i].length; j++) {
                    linha += matriz[i][j];
                    //separa por ;
                    if (j < matriz[i].length - 1) {
                        linha += ";";
                    }

                }
                writer.println(linha);
            }

        } catch (IOException e) {
            System.out.println("Erro ao reescrever ficheiro " + caminhoFicheiro);
        }
    }

    //---------------------------------------------------------------------------------
    //matriz
    public static String[][] criarMatriz(String caminhoFicheiro, String separador) {
        File ficheiro = new File(caminhoFicheiro);
        int totalLinhas = 0;
        int totalColunas = 0;
        try {
            // contar linhas e descobrir o número de colunas
            Scanner leitor = new Scanner(ficheiro);
            if (leitor.hasNextLine()) {
                String cabecalho = leitor.nextLine().trim();
                totalColunas = cabecalho.split(separador).length;
                while (leitor.hasNextLine()) {
                    String linha = leitor.nextLine();
                    if (!linha.isEmpty()) {
                        totalLinhas++;
                    }
                }
            }
            leitor.close();

            // Criar a matriz com as dimensões exatas
            if (totalLinhas >= 1 && totalColunas >= 1) {
                Scanner sc = new Scanner(ficheiro);
                String[][] matriz = new String[totalLinhas][totalColunas];
                // preencher a matriz

                if (sc.hasNextLine()) {
                    sc.nextLine(); // Ignorar cabeçalho
                }
                int linhaMatriz = 0;
                while (sc.hasNextLine()) {
                    String linha = sc.nextLine().trim();
                    if (!linha.isEmpty()) {
                        String[] info = linha.split(separador);
                        for (int i = 0; i < totalColunas; i++) {
                            matriz[linhaMatriz][i] = info[i].trim().toLowerCase();
                        }
                        linhaMatriz++;
                    }
                }
                sc.close();
                return matriz;
            } else {
                String[][] matriz = new String[0][0]; // String vazia
                return matriz;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ficheiro não encontrado: " + caminhoFicheiro, e);
        }

    }

    //--------------------------------------------------
    //escolher o ficheiro
    public static Map<String, String[][]> escolherFicheiro() {
        Scanner input = new Scanner(System.in);
        Map<String, String[][]> sala = new HashMap<>();

        System.out.println("Escolha uma sala:");
        System.out.println("1-Mar");
        System.out.println("2-Terra");
        System.out.println("3-Sol");
        System.out.print("Digite a opcao: ");
        int opcao = lerInteiro();
        input.nextLine();

        String caminhoFicheiro = "";
        String nomeSala = "";

        if (opcao == 1) {
            caminhoFicheiro = "Files/Sala_Mar.csv";
            nomeSala = "Mar";
        } else if (opcao == 2) {
            caminhoFicheiro = "Files/Sala_Terra.csv";
            nomeSala = "Terra";
        } else if (opcao == 3) {
            caminhoFicheiro = "Files/Sala_Sol.csv";
            nomeSala = "Sol";
        } else {
            System.out.println("Sala Invalida!");
            return sala; // Devolve o mapa vazio s
        }

        String[][] matriz = criarMatriz(caminhoFicheiro, ";");
        sala.put(nomeSala, matriz);

        return sala;
    }

}

