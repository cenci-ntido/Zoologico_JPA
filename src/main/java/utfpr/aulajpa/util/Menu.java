package utfpr.aulajpa.util;

public abstract class Menu {

    public static String geral = "Bem vindo!\n "
            + "Selecione uma opcao: \n "
            + "(0 - Sair | 1 - Especies | 2 - Profisionais | 3 - Animais | 4 - Serviços)\n";

    public static String especies = "Selecione uma opcao: \n "
            + "(0 - Sair | 1 - Cadastrar | 2 - Atualizar Nome | 3 - Excluir "
            + "| 4 - Consultar Todas)\n";

    public static String animais = "Selecione uma opcao: \n "
            + "(0 - Sair | 1 - Cadastrar | 2 - Atualizar Nome | 3 - Excluir)\n";

    public static String servicos = "Selecione uma opcao: \n "
            + "(0 - Sair | 1 - Cadastrar | 2 - Atualizar Descrição | 3 - Excluir "
            + "| 4 - Atualizar Animal "
            + "| 5 - Consultar serviços do dia)\n";

    public static void printGeral() {
        System.out.println(geral);
    }

    public static void printDisciplinas() {
        System.out.println(animais);
    }

    public static void printAlunos() {
        System.out.println(servicos);
    }

    public static void printEspecies() {
        System.out.println(especies);
    }
}
