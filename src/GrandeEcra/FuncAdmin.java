package GrandeEcra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static GrandeEcra.MetodosBasicos.*;

public class FuncAdmin {
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
    //estatisticas
    public static void estatisticasVendas(){

        String[][] dados= criarMatriz("Files/GrandeEcra_Bilhetes.csv",";");

        //num total de bilhetes
        int totalBilhetes= dados.length;

        //receita total
        //somar a ultima coluna
        double receitaTotal=0;
        for (int i = 0; i < dados.length; i++) {
            int colunaVendas=dados[i].length-1;//indice da ultima coluna
            double valorBilhete=Double.parseDouble(dados[i][colunaVendas].trim());
            receitaTotal+=valorBilhete;

        }
        //valor medio
        double valorMedio=(totalBilhetes>0)?(receitaTotal/totalBilhetes):0;

        System.out.println("\n-----------------------------------");
        System.out.println("Estatiscas de vendas de bilhetes");
        System.out.println("-----------------------------------\n");
        System.out.println("Total de bilhetes vendidos: "+totalBilhetes);
        System.out.println("Receita Total: "+receitaTotal);
        System.out.printf("Valor medio por bilhete: %.2f",valorMedio);

    }
    //-----------------------------------------------
    //receitas

    public static void receitaSala(){
        String[][] dados= criarMatriz("Files/GrandeEcra_Bilhetes.csv",";");
        int bilhetesMar = 0, bilhetesSol = 0, bilhetesTerra = 0;
        double receitaMar = 0, receitaSol = 0, receitaTerra = 0;
        int colunaSala=6;
        for (int i = 0; i < dados.length; i++) {
            int colunaVendas = dados[i].length - 1;
            String sala = dados[i][colunaSala];
            double valorBilhete = Double.parseDouble(dados[i][colunaVendas]);

            if (sala.equalsIgnoreCase("Mar") ) {
                bilhetesMar++;
                receitaMar += valorBilhete;
            } else if (sala.equalsIgnoreCase("Sol")) {
                bilhetesSol++;
                receitaSol += valorBilhete;
            } else if (sala.equalsIgnoreCase("Terra") ) {
                bilhetesTerra++;
                receitaTerra += valorBilhete;
            }
        }

        System.out.println("\n-----------------------------------------");
        System.out.println("Receita por sala");
        System.out.println("-----------------------------------------\n");
        System.out.printf("Sala Mar   | %d bilhetes | %.2f EUR\n", bilhetesMar, receitaMar);
        System.out.printf("Sala Sol   | %d bilhetes | %.2f EUR\n", bilhetesSol, receitaSol);
        System.out.printf("Sala Terra | %d bilhetes | %.2f EUR\n", bilhetesTerra, receitaTerra);
    }

    public static void receitaFilme(){
        String[][] dados= criarMatriz("Files/GrandeEcra_Bilhetes.csv",";");
        ArrayList<String> filmes=new ArrayList<>();
        ArrayList<Integer> bilhetesPorFilme=new ArrayList<>();
        ArrayList<Double>receitaPorFilme=new ArrayList<>();

        int colunaFilme=5;

        for (int i = 0; i < dados.length; i++) {
            int colunaVendas=dados[i].length-1;
            //guardar filme e o valor do bilhete
            String nomeFilme=dados[i][colunaFilme];
            double valorBilhete= Double.parseDouble(dados[i][colunaVendas]);

            //procurar o filme na lista de filmes
            int indice=filmes.indexOf(nomeFilme);//se nao existe devolve -1

            if(indice!= -1){
                //filme existe
                bilhetesPorFilme.set(indice, bilhetesPorFilme.get(indice)+1);
                receitaPorFilme.set(indice, receitaPorFilme.get(indice)+valorBilhete);
            } else {
                //filme ainda nao existe
                filmes.add(nomeFilme);
                bilhetesPorFilme.add(1);
                receitaPorFilme.add(valorBilhete);

            }

        }
       //encontrar o top 3
        System.out.println("\n-----------------------------------------");
        System.out.println("-- Top 3 Filmes --");
        System.out.println("-----------------------------------------\n");
        for (int podio = 1; podio <=3 ; podio++) {
            int indiceMelhor=0;
            for (int i = 0; i < receitaPorFilme.size(); i++) { //ja começa no indice 1 pq o 0 ta no indice melhor
                double atual=receitaPorFilme.get(i);
                double melhor=receitaPorFilme.get(indiceMelhor);

                //comparar
                if (atual>melhor || (atual==melhor && filmes.get(i).compareTo(filmes.get(indiceMelhor))<0)){
                    //Número negativo (< 0): o primeiro elemento  vem antes no alfabeto.
                    indiceMelhor=i;
                }

            }
            System.out.printf("%d. %s, total de %d bilhetes vendidos gerando %.2f EUR\n",
                    podio,filmes.get(indiceMelhor),bilhetesPorFilme.get(indiceMelhor),receitaPorFilme.get(indiceMelhor));

            //remover o que ja foi mostrado
            filmes.remove(indiceMelhor);
            bilhetesPorFilme.remove(indiceMelhor);
            receitaPorFilme.remove(indiceMelhor);

        }

    }


    public static void consultarFilme() throws  FileNotFoundException{
        String[][] dados= criarMatriz("Files/GrandeEcra_Bilhetes.csv",";");
        Scanner input=new Scanner(System.in);

        System.out.print("Introduza o nome do filme: ");
        String filmeProcurado=input.nextLine().trim().toLowerCase();

        int totalBilhetes=0;
        double receitaTotal=0;
        int colunaFilme=5;
        for (int i = 0; i < dados.length; i++) {
            String novoFilme=dados[i][colunaFilme];

            //comparar o filme com a busca
            if (novoFilme.equals(filmeProcurado)){
                int colunaVendas= dados[i].length-1;
                Double valorBilhete=Double.parseDouble(dados[i][colunaVendas]);
                totalBilhetes++;
                receitaTotal=+valorBilhete;
            }

        }
        System.out.println("\n-----------------------------------------");
        System.out.println("--Resultado da pesquisa--");
        System.out.println("-----------------------------------------\n");
        if (totalBilhetes>0){
            System.out.printf("%s: %d bilhetes, Total: %.2f EUR \n",
                    filmeProcurado,totalBilhetes,receitaTotal);

        }

    }
    //------------------------------------------------------------------
    //resetar sala

    public static void resetarSala(){
        Scanner input = new Scanner(System.in);
        Map<String, String[][]> sala = escolherFicheiro();
        //sala vazia
        if (sala.isEmpty()){
            return;
        }
        String nomeSala=sala.keySet().iterator().next();
        String[][] matrizSala= sala.get(nomeSala);


        System.out.println("sala a resetar: "+nomeSala);
        System.out.print("Confirma a reposicao? (S/N):");
        String confirmacao=input.nextLine().trim().toUpperCase();
        if(!confirmacao.equals("S")){
            System.out.println("Operação cancelada. O ficheiro não foi alterado");
            return;
        }


        int totalOcupado=0;

        for (int i = 0; i < matrizSala.length; i++) {
            for (int j = 0; j < matrizSala[i].length; j++) {

                //valor 1 é ocupado
                int posicao=Integer.parseInt(matrizSala[i][j].trim());
                if (posicao==1){
                    totalOcupado++;
                    matrizSala[i][j]="0";

                }


            }

        }
        String caminhoFicheiro="Files/Sala_"+nomeSala+".csv";
        //guardar matriz
        reescreverFicheiroMatriz(caminhoFicheiro,matrizSala);
        System.out.println(totalOcupado+ "lugares libertados.");


    }


}
