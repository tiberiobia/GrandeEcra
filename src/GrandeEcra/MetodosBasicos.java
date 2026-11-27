package GrandeEcra;

import java.io.*;
import java.util.*;

public class MetodosBasicos {

    //---------------------------------------------
    //Ler numeros
    /**
     * Lê um número inteiro introduzido pelo utilizador na consola.
     * Repete o pedido até que seja inserido um valor inteiro válido.
     * @return o número inteiro válido introduzido pelo utilizador
     */
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
    /**
     * Lê um número decimal introduzido pelo utilizador na consola
     *aceitando vírgula ou ponto como separador.
     * @return o número decimal válido introduzido pelo utilizador
     */
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

    //---------------------------------------------------------------------------------
    //ler char
    /**
     * Verifica se o caráter fornecido corresponde a uma letra maiúscula válida para identificar uma fila (entre 'A' e 'Z').
     *
     * @param c o caráter da fila a validar
     * @return true se o caráter estiver entre 'A' e 'Z', false caso contrário
     */
    public static boolean filaValida(char c) {
        return c >= 'A' && c <= 'Z';
    }

    //--------------------------------------------------------------------------
    //ficheiros
    /**
     * Lê todas as linhas de um ficheiro e devolve-as numa lista.
     * Caso o ficheiro não exista ou ocorra um erro de leitura, devolve uma lista vazia.
     *
     * @param caminhoFicheiro o caminho para o ficheiro a ler
     * @return uma List com as linhas lidas do ficheiro
     */
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
    /**
     * Lê todas as linhas de um ficheiro preservando espaços e linhas em branco.
     *
     * @param caminhoFicheiro o caminho para o ficheiro a ler
     * @return uma List com todas as linhas exatas do ficheiro
     */
    public static List<String> lerFicheiroBruto(String caminhoFicheiro) {
        List<String> linhas = new ArrayList<>();
        File file = new File(caminhoFicheiro);
        if (!file.exists()) {
            System.out.println("Ficheiro não encontrado: " + caminhoFicheiro);
            return linhas;
        }
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                linhas.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler ficheiro " + caminhoFicheiro);
        }
        return linhas;
    }

    /**
     * Reescreve um ficheiro substituindo o seu conteúdo pelos dados de uma matriz.
     *
     * @param caminhoFicheiro o caminho para o ficheiro a reescrever
     * @param matriz a matriz de texto com os dados a gravar no ficheiro
     */
    public static void reescreverFicheiroMatriz(String caminhoFicheiro, String[][] matriz) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(caminhoFicheiro, false));//false limpa o ficheiro e começa do zero
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
            writer.close();

        } catch (IOException e) {
            System.out.println("Erro ao reescrever ficheiro " + caminhoFicheiro);
        }
    }

    //---------------------------------------------------------------------------------
    //matriz
    //matriz que tem cabeçalho

    /**
     * Lê um ficheiro de texto com cabeçalho e carrega os seus dados para uma matriz, ignorando o cabeçalho.
     *
     * @param caminhoFicheiro o caminho para o ficheiro a ler
     * @param separador o caráter ou expressão usada para separar as colunas em cada linha)
     * @return uma matriz com os dados do ficheiro (sem a linha de cabeçalho)
     */
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

    //matriz sem cabeçalho

    /**
     * Lê um ficheiro de texto sem cabeçalho e carrega todas as suas linhas para uma matriz.
     *
     * @param caminhoFicheiro o caminho para o ficheiro a ler
     * @param separador o caráter ou expressão usada para separar as colunas em cada linha})
     * @return uma matriz com todos os dados do ficheiro em minúsculas
     */
    public static String[][] criarMatrizSemCabecalho(String caminhoFicheiro, String separador) {
        File ficheiro = new File(caminhoFicheiro);
        int totalLinhas = 0;
        int totalColunas = 0;
        try {
            // Contar TODAS as linhas
            Scanner leitor = new Scanner(ficheiro);
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if (!linha.isEmpty()) {
                    if (totalLinhas == 0) {
                        totalColunas = linha.split(separador).length;
                    }
                    totalLinhas++;
                }
            }
            leitor.close();

            // Criar a matriz
            if (totalLinhas >= 1 && totalColunas >= 1) {
                Scanner sc = new Scanner(ficheiro);
                String[][] matriz = new String[totalLinhas][totalColunas];

                // Preencher a matriz desde a 1 linha (sem saltar nada)
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
                return new String[0][0];
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ficheiro não encontrado: " + caminhoFicheiro, e);
        }
    }

    //--------------------------------------------------
    //escolher o ficheiro

    /**
     * Solicita ao utilizador a escolha de uma sala de cinema (Mar, Terra ou Sol) e carrega os dados do respetivo ficheiro CSV.
     *
     * @return um Map<String, String[][]> em que a chave é o nome da sala escolhida e o valor é a matriz com o estado dos lugares
     */
    public static Map<String, String[][]> escolherFicheiro() {
        Scanner input = new Scanner(System.in);
        Map<String, String[][]> sala = new HashMap<>();
        System.out.println("---------------------------");
        System.out.println("Escolha uma sala:");
        System.out.println("1-Mar");
        System.out.println("2-Terra");
        System.out.println("3-Sol");
        System.out.print("Digite a opcao: ");
        int opcao = lerInteiro();


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
            return sala; // Devolve o mapa vazio
        }

        String[][] matriz = criarMatrizSemCabecalho(caminhoFicheiro, ";");
        sala.put(nomeSala, matriz);

        return sala;
    }

}

