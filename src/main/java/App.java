
import java.util.Scanner;
import javax.persistence.EntityManager;
import utfpr.aulajpa.dao.EspecieDAO;
import utfpr.aulajpa.dao.ProfissionalDAO;
import utfpr.aulajpa.model.Especie;
import utfpr.aulajpa.model.Profissional;
import utfpr.aulajpa.util.Factory;

public class App {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean sair = false;
        while (!sair) {
            System.out.println("""
                           ================MENU================
                           0 - Sair
                           1 - Cadastrar Espécie
                           2 - Cadastrar Profissional
                           3 - Cadastrar Animal
                           4 - Cadastrar serviço
                           5 - Listar Serviços não realizados
                           ==========Informe uma Opção=========\n
                               """);
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 0 -> {
                    sair = true;
                    break;
                }
                case 1 -> {
                    cadastrarEspecie();
                }
                case 2 -> {
                    cadastrarProfissional();
                }
                case 3 -> {
                }
                default -> {
                    System.out.println("Opção inválida.");
                    break;
                }
            }
        }
        System.out.println("A D E U S!");
    }

    public static void cadastrarEspecie() {
        EntityManager em = Factory.getEntityManager();
        EspecieDAO especieDAO = new EspecieDAO(em);
        em.getTransaction().begin();
        System.out.println("Informe o nome da espécie: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        Especie especie = new Especie(nome);
        especieDAO.salvar(especie);
        em.getTransaction().commit();
        System.out.println("Espécie Cadastrada!");
        em.close();
    }

    public static void cadastrarProfissional() {
        EntityManager em = Factory.getEntityManager();
        ProfissionalDAO profissionalDAO = new ProfissionalDAO(em);
        em.getTransaction().begin();
        Profissional profissional = new Profissional();
        System.out.println("Informe o nome do profissional: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        profissional.setNome(nome);
        scanner.nextLine();
        System.out.println("Informe o tipo do profissional: ");
        String tipo = scanner.nextLine();
        profissional.setTipo(tipo);
        profissionalDAO.salvar(profissional);
        em.getTransaction().commit();
        System.out.println("Profissional Cadastrado!");
        em.close();
    }
}
