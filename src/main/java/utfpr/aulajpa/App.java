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
                switch (opcaoD) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Informe o nome da espécie:\n");
                        scanner.nextLine();
                        String nomeEsp = scanner.nextLine();
                        scanner.nextLine();                 
                        Especie especie = new Especie(nomeEsp);
                        especieDao.salvar(especie);
                        System.out.println("Espécie Cadastrada!");
                        break;
                    case 2:
                        System.out.println("Informe o código da disciplina para atualizar o nome:\n");
                        long codDis = scanner.nextLong();
                        scanner.nextLine();
                        Disciplina dis = disDAO.buscaDisciplina(codDis);
                        System.out.println("Informe o novo nome da disciplina:\n");
                        String novoNomeDis = scanner.nextLine();
                        scanner.nextLine();
                        dis.setNome(novoNomeDis);
                        disDAO.atualizar(dis);
                        System.out.println("Nome de disciplina atualizado!");
                        break;
                    case 3:
                        System.out.println("Informe o código da disciplina para excluir:\n");
                        long codDisEx = scanner.nextLong();
                        scanner.nextLine();
                        Disciplina dis2 = disDAO.buscaDisciplina(codDisEx);
                        disDAO.excluir(dis2);
                        System.out.println("Disciplina excluida!");
                        break;
                }
                break;
            case 2:
                Menu.printAlunos();
                int opcaoA = scanner.nextInt();
                AlunoDAO alunoDAO = new AlunoDAO(em);
                switch (opcaoA) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Informe o nome do aluno:\n");
                        scanner.nextLine();
                        String nomeAluno = scanner.nextLine();
                        scanner.nextLine();
                        System.out.println("Informe o RA do aluno:\n");
                        int raAluno = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Informe a média do aluno:\n");
                        double mediaAluno = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Informe o id da disciplina dele:\n");
                        long idDisciplina = scanner.nextLong();
                        DisciplinaDAO disDao2 = new DisciplinaDAO(em);
                        Disciplina disciplina = disDao2.buscaDisciplina(idDisciplina);
                        Aluno aluno = new Aluno(nomeAluno, raAluno, mediaAluno, disciplina);
                        alunoDAO.salvar(aluno);
                        System.out.println("Aluno Cadastrado!");
                        break;
                    case 2:
                        System.out.println("Informe o código do aluno para atualizar a media:\n");
                        long codDis = scanner.nextLong();
                        scanner.nextLine();
                        Aluno al = alunoDAO.buscaAluno(codDis);
                        System.out.println("Informe a nova média:\n");
                        Double novaMedia = scanner.nextDouble();
                        scanner.nextLine();
                        al.setMedia(novaMedia);
                        alunoDAO.atualizar(al);
                        System.out.println("Média do aluno atualizada!");
                        break;
                    case 3:
                        System.out.println("Informe o código do aluno para excluir:\n");
                        long codAlEx = scanner.nextLong();
                        scanner.nextLine();
                        Aluno al2 = alunoDAO.buscaAluno(codAlEx);
                        alunoDAO.excluir(al2);
                        System.out.println("Aluno excluido!");
                        break;
                    case 4:
                        System.out.println("Informe o nome da disciplina para ver os alunos presentes nela:\n");
                        scanner.nextLine();
                        String disBuscaAl = scanner.nextLine();
                        scanner.nextLine();
                        List<Aluno> rs = alunoDAO.buscaDisciplina(disBuscaAl);
                        rs.forEach(a -> System.out.println(a.getNome() + " com o RA: " + a.getRA()));
                        break;
                    case 5:
                        System.out.println("Alunos reprovados:");
                        List<Aluno> rs2 = alunoDAO.buscaTodosReprovados();
                        rs2.forEach(a -> System.out.println(a.getNome() + " com o RA: " + a.getRA()));
                        break;
                }
                break;

        }
        System.out.println("\nAté mais!\n");
        em.getTransaction().commit();
        em.close();
    }

}
