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

    // Cria um objeto para se conectar ao banco de dados MongoDB
    private static final MongoClient client = new MongoClient();
    // Obtém uma instância do banco de dados Zoologico
    private static final MongoDatabase db = client.getDatabase("Zoologico");
    // Obtém uma instância da coleção especie
    private static final MongoCollection<Document> especies = db.getCollection("especie");
    // Obtém uma instância da coleção profissional
    private static final MongoCollection<Document> profissionais = db.getCollection("profissional");
    // Obtém uma instância da coleção animal
    private static final MongoCollection<Document> animais = db.getCollection("animal");
    // Obtém uma instância da coleção servico
    private static final MongoCollection<Document> servicos = db.getCollection("servico");
    // Cria um objeto Scanner para ler entradas do usuário
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
        client.close();//fecha conexão do banco
    }

    public static void cadastrarEspecie() {
        // Pede ao usuário as informações da espécie
        System.out.println("Informe o nome da espécie: ");
        String nome = scanner.next();

        System.out.println("Informe o nome científico: ");
        String nomeCient = scanner.next();

        System.out.println("Informe o nome da família: ");
        String familia = scanner.next();

        System.out.println("Informe os comportamentos: ");
        String comportamentos = scanner.next();
        
        //insere especie
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

        Document profissional = null;//documento de profissional
                                     //que será usando para inserir animal
        boolean sair = false;
        while (!sair) {//loop para se errar o nome do profissional
            System.out.println("Informe o nome do profissional responsável: ");
            String nomeProfissional = scanner.next();
            MongoCursor<Document> profissionaisRes = profissionais.find(
                    Filters.eq("nome", nomeProfissional))
                    .limit(1)
                    .iterator();//filtra e pega o primeiro de acordo com o nome
            if (profissionaisRes.hasNext()) {//se achou documento
                // Se houver um resultado o método next() para obter o documento 
                profissional = profissionaisRes.next();//alimenta a variável
                sair = true;//sai do loop
            } else {//senão tenta de novo
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }
        
        //msemsa coisa daqui em diante, só que com espécie
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

        //insere o animal
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

        
        //procura profissional
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

        
        //procura animal
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

        //se o serviço foi realizado ou não
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
        
        //data e hora do serviçi
        Timestamp dataHoraFim = null;
        loop = true;
        while (loop) {
            System.out.println("Informe a data e hora final do serviço (no formato dd/mm/yyyy hh:mm:ss): ");
            String dataHoraString = scanner.nextLine();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");//formatar
                Timestamp dataHora = new Timestamp(dateFormat.parse(dataHoraString).getTime());
                dataHoraFim = dataHora;//alimenta variável
                loop = false;
            } catch (ParseException e) {
                System.out.println("Data e hora inválida!");
                loop = true;
            }
        }

        //insere o serviço
        Document servico = new Document("descricao", descricao)
                .append("id_profissional", profissional.getObjectId("_id"))
                .append("id_animal", animal.getObjectId("_id"))
                .append("dataHoraCadastro", Timestamp.valueOf(LocalDateTime.now()))//data e hora atual
                .append("dataHoraFim", dataHoraFim)
                .append("realizado", servicoRealizado);

        servicos.insertOne(servico);
        System.out.println("Serviço Cadastrado!");
    }

    public static void listarServicosNaoRealizados() {
        MongoCursor<Document> resultados = servicos.find(Filters.
                eq("realizado", false)).iterator();//filtra os não realizados
        int i = 1;
        while (resultados.hasNext()) {//printa os que encontrou
            System.out.println("Serviço " + i + ": \n" + resultados.next());
            i++;
        }
    }

    public static void listarAnimais() {
        MongoCursor<Document> resultados = animais.find().iterator();//busca todos
        int i = 1;
        while (resultados.hasNext()) {//printa todos
            System.out.println("Animal " + i + ": \n" + resultados.next());
            i++;
        }
    }

    private static void atualizarProfServico() {
        //pede qual serviço
        System.out.println("Informe a descrição do serviço:");
        String descricao = scanner.next();
        
        //busca o profissional
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
        
        //atualiza o id_profissional com o ObjectId do profissional encontrado
        servicos.updateOne(Filters.eq("descricao", descricao),
                new Document("$set", new Document("descricao", descricao)
                        .append("id_profissional", profissional.getObjectId("_id"))));

        System.out.println("Serviço atualizado!");
    }

    private static void cadastrarAnimal2() {
        //agora será feito usando a classe Servico transformando o documento em classe
        //pede o animal
        System.out.println("Informe o nome do animal: ");
        String nome = scanner.next();
        
        //pede o profisisonal
        Profissional profissional = new Profissional();//cria instancia da classe
        Document aux1 = null;//documento que servirá para alimentar o objeto
        boolean sair = false;
        while (!sair) {
            System.out.println("Informe o nome do profissional responsável: ");
            String nomeProfissional = scanner.next();
            
            //encontra o profissional
            MongoCursor<Document> profissionaisRes = profissionais.find(
                    Filters.eq("nome", nomeProfissional))
                    .limit(1)
                    .iterator();
            if (profissionaisRes.hasNext()) {
                // Se houver um resultado o método next() para obter o documento 
                aux1 = profissionaisRes.next();//alimenta o documento
                
                //transforma o documento em classe 
                Gson gson = new Gson();
                profissional = gson.fromJson(aux1.toJson(), Profissional.class);
                //alimentando o objeto da classe
                sair = true;
            } else {
                System.out.println("Profissional não encontrado, tente novamente!");
            }
        }

        
        //mesma coisa aqui para especie
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
        
        //insere o serviço só que agora buscando o ObjectId não do documento e sim do objeto
        //para mim essa forma é mais complexa que a outra, achei mais fácil fazer da primeira
        //não sei se tem alguma vantagem fazer isso aqui hehe
        //mas funciona tbm
        Document animal = new Document("nome", nome)
                .append("id_profissional", especie.getId())
                .append("id_especie", profissional.getId());

        animais.insertOne(animal);
        System.out.println("Animal Cadastrado!");
    }

    private static void excluirServico() {
        
        //pede qual serviço excluir
        System.out.println("Informe a descrição do serviço:");
        String descricao = scanner.next();

        //deleta o documento do serviço pela descrição informada
        servicos.deleteOne(Filters.eq("descricao", descricao));

        System.out.println("Serviço excluido!");
    }
}
