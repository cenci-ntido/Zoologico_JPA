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
import utfpr.aulajpa.model.Animal;
import utfpr.aulajpa.model.Especie;
import utfpr.aulajpa.model.Profissional;
import utfpr.aulajpa.model.Servico;
import org.bson.Document;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class App {

    private static final MongoClient client = new MongoClient();
    private static final MongoDatabase db = client.getDatabase("Zoologico");
    private static final MongoCollection<Document> especies = db.getCollection("especie");
    private static final MongoCollection<Document> profissionais = db.getCollection("profissional");
    private static final MongoCollection<Document> animais = db.getCollection("animal");
    private static final MongoCollection<Document> servicos = db.getCollection("servico");
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
                           7 - Atualizar responsável do 
                           8 - Cadastrar Animal 2
                           9 - Excluir Serviço
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
                case 7 -> {
                    atualizarProfServico();
                }
                case 8 -> {
                    cadastrarAnimal2();
                }
                case 9 -> {
                    excluirServico();
                }
                default -> {
                    System.out.println("Opção inválida.");
                    break;
                }
            }
        }
        System.out.println("\n********_A_D_E_U_S_********\n");
        client.close();
    }

    public static void cadastrarEspecie() {
        System.out.println("Informe o nome da espécie: ");
        String nome = scanner.next();

        System.out.println("Informe o nome científico: ");
        String nomeCient = scanner.next();

        System.out.println("Informe o nome da família: ");
        String familia = scanner.next();

        System.out.println("Informe os comportamentos: ");
        String comportamentos = scanner.next();

        Document especie = new Document("nome", nome)
                .append("nome-cient", nomeCient)
                .append("familia", familia)
                .append("comportamentos", comportamentos);

        especies.insertOne(especie);

        System.out.println("Espécie Cadastrada!");
    }

    public static void cadastrarProfissional() {
        System.out.println("Informe o nome do profissional: ");
        String nome = scanner.next();

        System.out.println("Informe o tipo do profissional: ");
        String tipo = scanner.next();

        Document profissional = new Document("nome", nome)
                .append("tipo", tipo);

        profissionais.insertOne(profissional);

        System.out.println("Profissional Cadastrado!");
    }

    public static void cadastrarAnimal() {
        System.out.println("Informe o nome do animal: ");
        String nome = scanner.next();

        Document profissional = null;
        boolean sair = false;
        while (!sair) {
            System.out.println("Informe o nome do profissional responsável: ");
            String nomeProfissional = scanner.next();
            MongoCursor<Document> profissionaisRes = profissionais.find(
                    Filters.eq("nome", nomeProfissional))
                    .limit(1)
                    .iterator();
            if (profissionaisRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                profissional = profissionaisRes.next();
                sair = true;
            } else {
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }

        Document especie = null;
        sair = false;
        while (!sair) {
            System.out.println("Informe o nome da espécie: ");
            String nomeEspecie = scanner.next();
            MongoCursor<Document> especiesRes = especies.find(
                    Filters.eq("nome", nomeEspecie))
                    .limit(1)
                    .iterator();
            if (especiesRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                especie = especiesRes.next();
                sair = true;
            } else {
                System.out.println("Espécie não encontrada, tente novamente!");
            }
        }

        Document animal = new Document("nome", nome)
                .append("id_profissional", profissional.getObjectId("_id"))
                .append("id_especie", especie.getObjectId("_id"));

        animais.insertOne(animal);
        System.out.println("Animal Cadastrado!");
    }

    public static void cadastrarServico() throws ParseException {
        boolean servicoRealizado = false;
        System.out.println("Informe a descricao do serviço: ");
        String descricao = scanner.next();

        Document profissional = null;
        boolean sair = false;
        while (!sair) {
            System.out.println("Informe o nome do profissional responsável: ");
            String nomeProfissional = scanner.next();
            MongoCursor<Document> profissionaisRes = profissionais.find(
                    Filters.eq("nome", nomeProfissional))
                    .limit(1)
                    .iterator();
            if (profissionaisRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                profissional = profissionaisRes.next();
                sair = true;
            } else {
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }

        Document animal = null;
        sair = false;
        while (!sair) {
            System.out.println("Informe o nome do animal: ");
            String nomeAnimal = scanner.next();
            MongoCursor<Document> animalRes = animais.find(
                    Filters.eq("nome", nomeAnimal))
                    .limit(1)
                    .iterator();
            if (animalRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                animal = animalRes.next();
                sair = true;
            } else {
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }

        boolean loop = true;
        while (loop) {
            System.out.println("O serviço já foi realizado? (Sim ou não)");
            String realizado = scanner.next();
            if (realizado.equalsIgnoreCase("sim")) {
                servicoRealizado = true;
                loop = false;
            } else if (realizado.equalsIgnoreCase("não") || realizado.equalsIgnoreCase("nao")) {
                servicoRealizado = false;
                loop = false;
            } else {
                System.out.println("Opção inválida, digite novamente!");
            }
        }

        Timestamp dataHoraFim = null;
        loop = true;
        while (loop) {
            System.out.println("Informe a data e hora final do serviço (no formato dd/mm/yyyy hh:mm:ss): ");
            String dataHoraString = scanner.nextLine();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                Timestamp dataHora = new Timestamp(dateFormat.parse(dataHoraString).getTime());
                dataHoraFim = dataHora;
                loop = false;
            } catch (ParseException e) {
                System.out.println("Data e hora inválida!");
                loop = true;
            }
        }

        Document servico = new Document("descricao", descricao)
                .append("id_profissional", profissional.getObjectId("_id"))
                .append("id_animal", animal.getObjectId("_id"))
                .append("dataHoraCadastro", Timestamp.valueOf(LocalDateTime.now()))
                .append("dataHoraFim", dataHoraFim)
                .append("realizado", servicoRealizado);

        servicos.insertOne(servico);
        System.out.println("Serviço Cadastrado!");
    }

    public static void listarServicosNaoRealizados() {
        MongoCursor<Document> resultados = servicos.find(Filters.eq("realizado", false)).iterator();
        int i = 1;
        while (resultados.hasNext()) {
            System.out.println("Serviço " + i + ": \n" + resultados.next());
            i++;
        }
    }

    public static void listarAnimais() {
        MongoCursor<Document> resultados = animais.find().iterator();
        int i = 1;
        while (resultados.hasNext()) {
            System.out.println("Animal " + i + ": \n" + resultados.next());
            i++;
        }
    }

    private static void atualizarProfServico() {
        System.out.println("Informe a descrição do serviço:");
        String descricao = scanner.next();

        Document profissional = null;
        boolean sair = false;
        while (!sair) {
            System.out.println("Informe o nome do novo profissional responsável: ");
            String nomeProfissional = scanner.next();
            MongoCursor<Document> profissionaisRes = profissionais.find(
                    Filters.eq("nome", nomeProfissional))
                    .limit(1)
                    .iterator();
            if (profissionaisRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                profissional = profissionaisRes.next();
                sair = true;
            } else {
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }

        servicos.updateOne(Filters.eq("descricao", descricao),
                new Document("$set", new Document("descricao", descricao)
                        .append("id_profissional", profissional.getObjectId("_id"))));

        System.out.println("Serviço atualizado!");
    }

    private static void cadastrarAnimal2() {
        System.out.println("Informe o nome do animal: ");
        String nome = scanner.next();

        Profissional profissional = new Profissional();
        Document aux1 = null;
        boolean sair = false;
        while (!sair) {
            System.out.println("Informe o nome do profissional responsável: ");
            String nomeProfissional = scanner.next();
            MongoCursor<Document> profissionaisRes = profissionais.find(
                    Filters.eq("nome", nomeProfissional))
                    .limit(1)
                    .iterator();
            if (profissionaisRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                aux1 = profissionaisRes.next();
                Gson gson = new Gson();
                profissional = gson.fromJson(aux1.toJson(), Profissional.class);
                sair = true;
            } else {
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }

        Especie especie = new Especie();
        Document aux2 = null;
        sair = false;
        while (!sair) {
            System.out.println("Informe o nome da espécie: ");
            String nomeEspecie = scanner.next();
            MongoCursor<Document> especiesRes = especies.find(
                    Filters.eq("nome", nomeEspecie))
                    .limit(1)
                    .iterator();
            if (especiesRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                aux2 = especiesRes.next();
                Gson gson = new Gson();
                especie = gson.fromJson(aux2.toJson(), Especie.class);
                sair = true;
            } else {
                System.out.println("Espécie não encontrada, tente novamente!");
            }
        }

        Document animal = new Document("nome", nome)
                .append("id_profissional", especie.getId())
                .append("id_especie", profissional.getId());

        animais.insertOne(animal);
        System.out.println("Animal Cadastrado!");
    }

    private static void excluirServico() {
        System.out.println("Informe a descrição do serviço:");
        String descricao = scanner.next();
        
        servicos.deleteOne(Filters.eq("descricao", descricao));
        
        System.out.println("Serviço excluido!");
    }
}
