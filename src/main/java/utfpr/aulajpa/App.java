package utfpr.aulajpa;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import utfpr.aulajpa.dao.EspecieDAO;
import utfpr.aulajpa.model.Especie;
import utfpr.aulajpa.util.Factory;
import utfpr.aulajpa.util.Menu;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EntityManager em = Factory.getEntityManager();
        em.getTransaction().begin();
        Menu.printGeral();
        int opcao = scanner.nextInt();
        switch (opcao) {
            case 0:
                break;
            case 1:
                Menu.printEspecies();
                int opcaoD = scanner.nextInt();
                EspecieDAO especieDao = new EspecieDAO(em);
                Especie especie = new Especie();
                switch (opcaoD) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Informe o nome da espécie:\n");
                        scanner.nextLine();
                        String nomeEsp = scanner.nextLine();
                        scanner.nextLine();                 
                        especie = new Especie(nomeEsp);
                        especieDao.salvar(especie);
                        System.out.println("Espécie Cadastrada!");
                        break;
                    case 2:
                        System.out.println("Informe o código da espécie para atualizar o nome:\n");
                        long cod = scanner.nextLong();
                        scanner.nextLine();
                        especie = especieDao.buscaEspecie(cod);
                        System.out.println("Informe o novo nome da espécie:\n");
                        String novoNome = scanner.nextLine();
                        scanner.nextLine();
                        especie.setNome(novoNome);
                        especieDao.atualizar(especie);
                        System.out.println("Nome de espécie atualizado!");
                        break;
                    case 3:
                        System.out.println("Informe o código da espécie para excluir:\n");
                        long codEsp = scanner.nextLong();
                        scanner.nextLine();
                        especie = especieDao.buscaEspecie(codEsp);
                        especieDao.excluir(especie);
                        System.out.println("Espécie excluida!");
                        break;
                }
                break;
        }
        System.out.println("\nAté mais!\n");
        em.getTransaction().commit();
        em.close();
    }

}
