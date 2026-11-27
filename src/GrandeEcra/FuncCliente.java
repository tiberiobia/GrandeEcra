package GrandeEcra;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static GrandeEcra.MetodosBasicos.*;

/**
 * Classe que reúne as funcionalidades disponíveis para o cliente.
 */
public class FuncCliente {

    /**
     * Solicita ao utilizador os dados de registo de um novo cliente (nome, contacto telefónico e email)
     * e apresenta um resumo dos dados introduzidos.
     */
    public static void registroCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Novo Registo de Cliente ---");

        System.out.print("Introduza o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Introduza o contacto telefónico: ");
        int telefone = lerInteiro();

        System.out.print("Introduza o email: ");
        String email = scanner.nextLine();

        System.out.println("\nCliente inserido com sucesso!");
        System.out.println("--- Dados Introduzidos ---");
        System.out.println("Nome: " + nome);
        System.out.println("Contacto Telefónico: " + telefone);
        System.out.println("Email: " + email);

        System.out.println();
        System.out.println("voltando para o menu principal.....");
    }

    /**
     * Lê o ficheiro de cartaz do cinema e exibe o seu conteúdo linha a linha na consola.
     */
    public static void lerCartaz() {
        List<String> linhas = lerFicheiroBruto("Files/GrandeEcra_Cartaz.txt");
        for (String linha : linhas) {
            System.out.println(linha);

        }

    }

    /**
     * Pesquisa filmes por género e exibe os resultados encontrados.
     * Apresenta o título, realizador, ano, duração, idade mínima e classificação de cada filme correspondente.
     */
    public static void pesqFilme() {
        String[][] matriz = criarMatriz("Files/GrandeEcra_Filmes.csv", ";");
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o género que quer pesquisar: ");
        String genero = sc.nextLine().trim().toLowerCase();
        System.out.println("---------------------------");
        boolean encontrado = false;

        for (int i = 0; i < matriz.length; i++) {

            if (matriz[i][2].equals(genero)) {
                //apresentando título, realizador, ano, duração,
                //idade mínima e classificação
                System.out.printf("- %s, %s, %s, %s, %s, %s",
                        matriz[i][1], matriz[i][3], matriz[i][4], matriz[i][5], matriz[i][6], matriz[i][7]);
                System.out.println();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Não foram encontrados filmes.");
        }
    }

    /**
     * Exibe o mapa visual dos lugares de uma sala de cinema.
     *
     * @param nomeSala o nome da sala.
     * @param matriz a matriz bidimensional que contém a disposição e o estado dos lugares da sala
     */
    public static void mapaSala(String nomeSala, String[][] matriz) {


        //exibir sala
        System.out.println("---------------------");
        System.out.println("     Sala " + nomeSala.toUpperCase());
        System.out.println();
        System.out.println("        Ecrã");
        System.out.println("----------------------");
        //numero das colunas
        System.out.println();
        System.out.print("   ");
        for (int k = 0; k < matriz[0].length; k++) {
            System.out.print((k + 1) + " ");

        }
        System.out.println();

        int disponiveis = 0;
        int ocupados = 0;
        char letraFila = 'A';
        for (int i = 0; i < matriz.length; i++) {
            //exibir a fila
            System.out.print(letraFila + "  ");
            letraFila++;
            for (int j = 0; j < matriz[i].length; j++) {
                String situacao = matriz[i][j];
                if (situacao.equals("0")) {
                    System.out.print("O ");
                    disponiveis++;
                } else if (situacao.equals("1")) {
                    System.out.print("X ");
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
        System.out.printf("Lotação: %d | Disponiveis: %d | Ocupados: %d\n", totalLugares, disponiveis, ocupados);
        System.out.printf("Taxa de ocupacao: %.2f", taxaOcupacao);


    }

    /**
     * Gere o processo de reserva de bilhetes numa sala de cinema escolhida pelo utilizador.
     * Exibe o mapa da sala, verifica se existem lugares livres  e solicita a quantidade de bilhetes para concluir a reserva.
     */
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
        System.out.println("\n---------------------------");
        // Conta lugares disponíveis
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

        System.out.println("> Sala: " + nomeSala);

        // Quantidade de bilhetes
        int quantidade = 0;
        do {
            System.out.print("> Quantidade de bilhetes: ");
            quantidade = lerInteiro();

            if (quantidade <= 0 || quantidade > disponiveis) {
                System.out.println("Quantidade inválida. Introduza um valor entre 1 e " + disponiveis + ".");
            }
        } while (quantidade <= 0 || quantidade > disponiveis);

        char ultimaFileira = (char) ('A' + matriz.length - 1);
        String[] lugaresReservados = new String[quantidade];

        // Verificar e selecionar os lugares
        for (int i = 0; i < quantidade; i++) {
            boolean lugarValido = false;
            while (!lugarValido) {

                // validar a fila
                String fileira = "";
                int linha = -1;
                while (linha < 0 || linha >= matriz.length) {
                    System.out.print("> Fila (A-" + ultimaFileira + "): ");
                    fileira = input.nextLine().trim().toUpperCase();

                    if (fileira.length() == 1 && fileira.charAt(0) >= 'A' && fileira.charAt(0) <= ultimaFileira) {
                        linha = fileira.charAt(0) - 'A';
                    } else {
                        System.out.println("Fila inválida! Introduza uma letra entre A e " + ultimaFileira + ".");
                    }
                }

                // validar o lugar
                System.out.print("> Lugar (1-" + matriz[linha].length + "): ");
                int cadeira = lerInteiro();
                int coluna = cadeira - 1;

                // Validar limites da cadeira e disponibilidade
                if (coluna < 0 || coluna >= matriz[linha].length) {
                    System.out.println("Lugar inválido! Introduza um número entre 1 e " + matriz[linha].length + ".");
                } else if (!matriz[linha][coluna].equals("0")) {
                    System.out.println("Lugar indisponível. Escolha outro lugar.");
                } else {
                    matriz[linha][coluna] = "1";
                    lugaresReservados[i] = fileira + cadeira;
                    lugarValido = true;
                    System.out.println("Lugar selecionado.");
                }
            }
        }

        // matriz atualizada
        String caminhoFicheiro = "Files/Sala_" + nomeSala + ".csv";
        reescreverFicheiroMatriz(caminhoFicheiro, matriz);

        // Apresentar resumo final
        System.out.println("----- Reserva Concluida -----");
        System.out.print("Sala " + nomeSala + " | Lugares: ");
        for (int i = 0; i < lugaresReservados.length; i++) {
            System.out.print(lugaresReservados[i]);
            if (i < lugaresReservados.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" | Total: " + quantidade);
    }

    /**
     * Solicita ao utilizador as suas preferências e recomenda filmes que cumpram os critérios indicados.
     *
     */
    public static void recomendarFilmes() {
        String[][] filmes = criarMatriz("Files/GrandeEcra_Filmes.csv", ";");
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Recomendação de Filmes ---");
        System.out.print("Introduza a sua idade: ");
        int idadeCliente = lerInteiro();

        System.out.print("Género preferido: ");
        String generoPreferido = sc.nextLine().trim().toLowerCase();

        System.out.print("Tempo disponível (em minutos): ");
        int tempoDisponivel = lerInteiro();

        System.out.print("Ano mínimo de lançamento pretendido: ");
        int anoMinimo = lerInteiro();

        // Listas para guardar os filmes elegíveis
        java.util.ArrayList<String> titulos = new java.util.ArrayList<>();
        java.util.ArrayList<String> generos = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> duracoes = new java.util.ArrayList<>();
        java.util.ArrayList<Double> classificacoes = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> pontuacoes = new java.util.ArrayList<>();


        // [0]idFilme [1]titulo  [2]genero  [3]realizador  [4]ano  [5]duracao  [6]idadeMinima  [7]classificacao
        for (int i = 0; i < filmes.length; i++) {
            int idadeMinimaFilme = Integer.parseInt(filmes[i][6].trim());

            // idade inferior à idade mínima do filme
            if (idadeCliente < idadeMinimaFilme) {
                continue;
            }

            String titulo = filmes[i][1].trim();
            String genero = filmes[i][2].trim();
            int ano = Integer.parseInt(filmes[i][4].trim());
            int duracao = Integer.parseInt(filmes[i][5].trim());
            double classificacao = Double.parseDouble(filmes[i][7].trim().replace(",", "."));

            int pontos = 0;

            //  género preferido +30
            if (genero.equalsIgnoreCase(generoPreferido)) {
                pontos += 30;
            }

            // Duração inferior ou igual ao tempo disponível +20
            if (duracao <= tempoDisponivel) {
                pontos += 20;
            }

            // Ano igual ou superior ao ano mínimo +10
            if (ano >= anoMinimo) {
                pontos += 10;
            }

            // Classificação >= 8 +20
            // >= 9 +10
            if (classificacao >= 8.0) {
                pontos += 20;
            }
            if (classificacao >= 9.0) {
                pontos += 10;
            }

            titulos.add(titulo);
            generos.add(genero);
            duracoes.add(duracao);
            classificacoes.add(classificacao);
            pontuacoes.add(pontos);
        }

        // não existam filmes
        if (titulos.isEmpty()) {
            System.out.println("\nNão existem filmes elegíveis para a sua idade no catálogo.");
            return;
        }

        System.out.println("\n--- Top Recomendações ---");
        // Mostrar até 3 filmes
        int limite = 3;
        if (titulos.size()<3){
            limite= titulos.size();
        }

        for (int podio = 1; podio <= limite; podio++) {
            int indiceMelhor = 0;

            for (int i = 1; i < pontuacoes.size(); i++) {
                int pontosAtual = pontuacoes.get(i);
                int pontosMelhor = pontuacoes.get(indiceMelhor);

                double classifAtual = classificacoes.get(i);
                double classifMelhor = classificacoes.get(indiceMelhor);

                String tituloAtual = titulos.get(i);
                String tituloMelhor = titulos.get(indiceMelhor);

                // Critérios de  desempate:
                // 1 mais pontos
                if (pontosAtual > pontosMelhor) {
                    indiceMelhor = i;
                }
                // 2 maior classificação
                else if (pontosAtual == pontosMelhor && classifAtual > classifMelhor) {
                    indiceMelhor = i;
                }
                // 3ordem alfabética
                else if (pontosAtual == pontosMelhor && classifAtual == classifMelhor
                        && tituloAtual.compareToIgnoreCase(tituloMelhor) < 0) {
                    indiceMelhor = i;
                }

            }

            System.out.printf("%d. %s | Género: %s | Duração: %d min | Classificação: %.1f | Pontos: %d\n",
                    podio,
                    titulos.get(indiceMelhor),
                    generos.get(indiceMelhor),
                    duracoes.get(indiceMelhor),
                    classificacoes.get(indiceMelhor),
                    pontuacoes.get(indiceMelhor));

            // Remover o filme apresentado
            titulos.remove(indiceMelhor);
            generos.remove(indiceMelhor);
            duracoes.remove(indiceMelhor);
            classificacoes.remove(indiceMelhor);
            pontuacoes.remove(indiceMelhor);
        }
    }

    /**
     * Inicia o quiz cinematográfico a partir das perguntas carregadas.
     * Permite ao utilizador escolher uma modalidade, apresenta as perguntas de forma aleatória e calcula a pontuação final.
     */
    public static void quizCinematografico() {
        String[][] perguntas = criarMatriz("Files/GrandeEcra_Quiz.csv", ";");

        // Ficheiro vazio
        if (perguntas.length == 0) {
            System.out.println("Não existem perguntas disponíveis no ficheiro do quiz.");
            return;
        }

        System.out.println("\n=== Quiz Cinematográfico ===");
        System.out.println("1 - Curta-Metragem (5 questões)");
        System.out.println("2 - Longa-Metragem (10 questões)");
        System.out.println("3 - Saga Completa (20 questões)");

        int numQuestoes = 0;
        String nomeModalidade = "";

        while (numQuestoes == 0) {
            System.out.print("Escolha uma modalidade (1-3): ");
            int modalidade = lerInteiro();

            if (modalidade == 1) {
                numQuestoes = 5;
            } else if (modalidade == 2) {
                numQuestoes = 10;
            } else if (modalidade == 3) {
                numQuestoes = 20;
            } else {
                System.out.println("Opção inválida! Escolha 1, 2 ou 3.");
            }
        }

        // caso o ficheiro tenha menos perguntas que a modalidade escolhida
        if (numQuestoes > perguntas.length) {
            numQuestoes = perguntas.length;
        }

        System.out.println("\nQUIZ CINEMATOGRAFICO - " + nomeModalidade);

        //randon
        java.util.Random aleatorio = new java.util.Random();

        // Lista com os números de das perguntas disponíveis
        java.util.ArrayList<Integer> disponiveis = new java.util.ArrayList<>();
        for (int i = 0; i < perguntas.length; i++) {
            disponiveis.add(i);
        }

        int corretas = 0;
        int incorretas = 0;

        // Sortear e remover da lista a cada ronda
        for (int q = 1; q <= numQuestoes; q++) {
            int posicaoSorteada = aleatorio.nextInt(disponiveis.size());
            int indicePergunta = disponiveis.remove(posicaoSorteada);


            // [0] pergunta [1] op1
            // [2] op2  [3] op3  [4] op4
            // [5] respostaCorreta (1 a 4)
            String enunciado = perguntas[indicePergunta][0];
            String op1 = perguntas[indicePergunta][1];
            String op2 = perguntas[indicePergunta][2];
            String op3 = perguntas[indicePergunta][3];
            String op4 = perguntas[indicePergunta][4];
            int respostaCerta = Integer.parseInt(perguntas[indicePergunta][5].trim());

            System.out.println("\nQuestão " + q + " de " + numQuestoes + ":");
            System.out.println(enunciado);
            System.out.println("1 - " + op1);
            System.out.println("2 - " + op2);
            System.out.println("3 - " + op3);
            System.out.println("4 - " + op4);

            // Validar se esta entre 1 e 4
            int respostaCliente = 0;
            while (respostaCliente < 1 || respostaCliente > 4) {
                System.out.print("A sua resposta (1-4): ");
                respostaCliente = lerInteiro();
                if (respostaCliente < 1 || respostaCliente > 4) {
                    System.out.println("Resposta inválida! Introduza um número entre 1 e 4.");
                }
            }

            //  acertou ou nao
            if (respostaCliente == respostaCerta) {
                System.out.println("Resposta correta!");
                corretas++;
            } else {
                String textoCorreto = perguntas[indicePergunta][respostaCerta];
                System.out.println("Resposta incorreta! A resposta correta era: " + respostaCerta + " - " + textoCorreto);
                incorretas++;
            }
        }

        // porcentagem e classificação final
        double percentagem = (corretas * 100.0) / numQuestoes;
        String classificacao;

        if (percentagem < 25) {
            classificacao = "Só veio pelas pipocas";
        } else if (percentagem < 50) {
            classificacao = "Só vê ao domingo à tarde";
        } else if (percentagem < 75) {
            classificacao = "Cinéfilo de bancada";
        } else if (percentagem < 100) {
            classificacao = "Spielberg da freguesia";
        } else {
            classificacao = "Óscar de Ouro";
        }

        System.out.println("\n----Resultado do quiz ----");
        System.out.println("Respostas corretas: " + corretas);
        System.out.println("Respostas incorretas: " + incorretas);
        System.out.printf("Percentagem de sucesso: %.1f%%\n", percentagem);
        System.out.println("Classificação: " + classificacao);
        System.out.println("----------------------------------");
    }



}
