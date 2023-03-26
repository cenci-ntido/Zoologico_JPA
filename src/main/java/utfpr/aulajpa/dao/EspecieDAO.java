package utfpr.aulajpa.dao;

import javax.persistence.EntityManager;
import utfpr.aulajpa.model.Especie;

public class EspecieDAO {

    private EntityManager em;

    public EspecieDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Especie especie) {
        this.em.persist(especie);
    }

    public Especie buscaPorNome(String nome) {
        String jpql = "SELECT e FROM Especie e WHERE e.nome = :campo";
        return em.createQuery(jpql, Especie.class).setParameter("campo", nome).getResultList().get(0);
    }
}
