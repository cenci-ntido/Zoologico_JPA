
import java.util.Scanner;
import javax.persistence.EntityManager;
import utfpr.aulajpa.dao.EspecieDAO;
import utfpr.aulajpa.model.Especie;
import utfpr.aulajpa.util.Factory;

public class App {

    private static Scanner scanner = new Scanner(System.in);
    private static EntityManager em = Factory.getEntityManager();

    public static void main(String[] args) {

        boolean sair = false;
        em.getTransaction().begin();

        while (!sair) {
            System.out.println("================MENU================");
            System.out.println(
                    "0 - Sair\n"
                    + "1 - Cadastrar Espécie\n"
                    + "2 - Cadastrar Profissional\n"
                    + "3 - Cadastrar Animal\n"
                    + "4 - Cadastrar serviço\n"
                    + "5 - Listar Serviços não realizados\n");
            System.out.println("==========Informe uma Opção=========\n");
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 0:
                    sair = true;
                    break;
                case 1:
                    System.out.println("Informe o nome da espécie:\n");
                    scanner.nextLine();
                    String nome = scanner.nextLine();
                    scanner.nextLine();
                    EspecieDAO especieDAO = new EspecieDAO(em);
                    Especie especie = new Especie(nome);
                    especieDAO.salvar(especie);
                    System.out.println("Espécie Cadastrada!");                   
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        System.out.println("Até mais!");
        em.close();
        scanner.close();

    }
}
