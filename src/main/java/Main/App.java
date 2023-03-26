//Deve ser possível cadastrar um animal.
//Deve ser possível cadastrar um profissional do zoo.
//O animal deve ter um nome, uma espécie (cão, gato, pássaro, etc.) e um treinador ou zelador.
//O zoo deve ter uma lista de serviço do dia (alimentar, vacinar, soltar no espaço aberto, recolher para transporte, etc.).
//Cada serviço deve ter uma data (dia e hora), e um animal relacionado a ele, além de um profissional responsável.
//Um animal pode ter acesso a mais de um serviço.
//Deve ser possível consultar animais e serviços ainda não realizados.
package Main;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import utfpr.aulajpa.dao.AnimalDAO;
import utfpr.aulajpa.dao.EspecieDAO;
import utfpr.aulajpa.dao.ProfissionalDAO;
import utfpr.aulajpa.dao.ServicoDAO;
import utfpr.aulajpa.model.Animal;
import utfpr.aulajpa.model.Especie;
import utfpr.aulajpa.model.Profissional;
import utfpr.aulajpa.model.Servico;
import utfpr.aulajpa.util.Factory;

public class App {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
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
                           6 - Listar animais
                           ==========Informe uma Opção=========
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
                    cadastrarAnimal();
                }
                case 4 -> {
                    cadastrarServico();
                }
                case 5 -> {
                    listarServicosNaoRealizados();
                }
                case 6 -> {
                    listarAnimais();
                }
                default -> {
                    System.out.println("Opção inválida.");
                    break;
                }
            }
        }
        System.out.println("\n********_A_D_E_U_S_********\n");
    }

    public static void cadastrarEspecie() {
        EntityManager em = Factory.getEntityManager();
        EspecieDAO especieDAO = new EspecieDAO(em);
        em.getTransaction().begin();
        System.out.println("Informe o nome da espécie: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        Especie especie = new Especie();
        especie.setNome(nome);
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

    public static void cadastrarAnimal() {
        EntityManager em = Factory.getEntityManager();
        AnimalDAO animalDAO = new AnimalDAO(em);
        em.getTransaction().begin();
        Animal animal = new Animal();
        System.out.println("Informe o nome do animal: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        animal.setNome(nome);
        scanner.nextLine();

        System.out.println("Informe o nome do profissional responsável: ");
        String nomeProfissional = scanner.nextLine();
        ProfissionalDAO profissionalDAO = new ProfissionalDAO(em);
        Profissional profissional = profissionalDAO.buscaPorNome(nomeProfissional);
        animal.setProfissionalResponsavel(profissional);

        System.out.println("Informe o nome da espécie: ");
        String nomeEspecie = scanner.nextLine();
        EspecieDAO especieDAO = new EspecieDAO(em);
        Especie especie = especieDAO.buscaPorNome(nomeEspecie);
        animal.setEspecie(especie);

        animalDAO.salvar(animal);
        em.getTransaction().commit();
        System.out.println("Animal Cadastrado!");
        em.close();
    }

    public static void cadastrarServico() throws ParseException {
        EntityManager em = Factory.getEntityManager();
        ServicoDAO servicoDAO = new ServicoDAO(em);
        em.getTransaction().begin();
        Servico servico = new Servico();

        System.out.println("Informe a descricao do serviço: ");
        scanner.nextLine();
        String descricao = scanner.nextLine();
        servico.setDescricao(descricao);
        scanner.nextLine();

        System.out.println("Informe o profissional responsável:");
        String nomeProfissional = scanner.nextLine();
        ProfissionalDAO profissionalDAO = new ProfissionalDAO(em);
        Profissional profissional = profissionalDAO.buscaPorNome(nomeProfissional);
        servico.setProfissional(profissional);

        System.out.println("Informe o nome do animal: ");
        String nomeAnimal = scanner.nextLine();
        AnimalDAO AnimalDAO = new AnimalDAO(em);
        Animal animal = AnimalDAO.buscaPorNome(nomeAnimal);
        servico.setAnimal(animal);

        boolean loop = true;
        while (loop) {
            System.out.println("O serviço já foi realizado? (Sim ou não)");
            String realizado = scanner.nextLine();

            if (realizado.equalsIgnoreCase("sim")) {
                servico.setRealizado(true);
                loop = false;
            } else if (realizado.equalsIgnoreCase("não") || realizado.equalsIgnoreCase("nao")) {
                servico.setRealizado(false);
                loop = false;
            } else {
                System.out.println("Opção inválida, digite novamente!");
            }
        }

        System.out.println("Informe a data e hora final do serviço (no formato dd/mm/yyyy hh:mm:ss): ");
        String dataHoraString = scanner.nextLine();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Timestamp dataHora = new Timestamp(dateFormat.parse(dataHoraString).getTime());
            servico.setDataHoraFim(dataHora);

        } catch (ParseException e) {
            System.out.println("Data e hora inválida!");
        }

        servico.setDataHora(Timestamp.valueOf(LocalDateTime.now()));

        servicoDAO.salvar(servico);
        em.getTransaction().commit();
        System.out.println("Animal Cadastrado!");
        em.close();
    }

    public static void listarServicosNaoRealizados() {
        EntityManager em = Factory.getEntityManager();
        ServicoDAO servicoDAO = new ServicoDAO(em);
        em.getTransaction().begin();
        List<Servico> servicosNaoRealizados = servicoDAO.listarServicosNaoRealizados();
        servicosNaoRealizados.stream().forEach(servico -> System.out.println(servico.toString()));
        em.getTransaction().commit();
        em.close();
    }

    public static void listarAnimais() {
        EntityManager em = Factory.getEntityManager();
        AnimalDAO animalDAO = new AnimalDAO(em);
        em.getTransaction().begin();
        List<Animal> animais = animalDAO.buscaTodos();
        animais.stream().forEach(animal -> System.out.println(animal.toString()));
        em.getTransaction().commit();
        em.close();
    }
}
