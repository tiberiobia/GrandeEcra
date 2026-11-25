package GrandeEcra;

import static GrandeEcra.Funcoes.*;
import static GrandeEcra.MetodosBasicos.*;

public class Menus {
    //menus principais
    public static void menuAdmin() {
        //Login obrigatorio
        if (efetuarLogin()) {
            System.out.println("\n Login efetuado com sucesso!");
            int opcao;
            do {
                System.out.println("\n 1 - Consultar ficheiros");
                System.out.println("\n 2 - Estatisticas gerais de vendas");
                System.out.println("\n 3 - Receita por sala");
                System.out.println("\n 4 - Receita por filme");
                System.out.println("\n 5 - Top 3 filmes com maior receita");
                System.out.println("\n 6 - Resetar lotacao de uma sala");
                System.out.println("\n 0 - Voltar");

                System.out.print("Escolha uma opção: ");
                opcao = lerInteiro();

                switch (opcao) {
                    case 1:
                        menuFicheiros();
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

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
    public static void menuCliente(){

    }

    //---------------------------------------------------------------------------
    //sub menus
    public static void menuFicheiros(){
        //os nove ficheiros
        //ficheiro autentic validar adm
    }

}




