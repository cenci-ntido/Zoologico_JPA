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

    public List<Animal> buscaTodosProfissionais() {
        String jpql = "SELECT a FROM Animal a";
        return em.createQuery(jpql, Animal.class).getResultList();
    }

    public void atualizar(Animal animal) {
        this.em.merge(animal);
    }

    public void excluir(Animal animal) {
        atualizar(animal);
        this.em.remove(animal);
    }

}
