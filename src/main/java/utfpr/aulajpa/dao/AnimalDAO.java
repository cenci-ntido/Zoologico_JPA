package utfpr.aulajpa.dao;

import java.util.List;
import javax.persistence.EntityManager;
import utfpr.aulajpa.model.Animal;

public class AnimalDAO {

    private EntityManager em;

    public AnimalDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Animal animal) {
        this.em.persist(animal);
    }

    public Animal buscaPorNome(String nome) {
        String jpql = "SELECT a FROM Animal a WHERE a.nome = :campo";
        return em.createQuery(jpql, Animal.class).setParameter("campo", nome).getResultList().get(0);
    }

    public List<Animal> buscaTodos() {
        String jpql = "SELECT a FROM Animal a";
        return em.createQuery(jpql, Animal.class).getResultList();
    }
}
