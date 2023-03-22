package utfpr.aulajpa.dao;

import java.util.List;
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

    public List<Especie> buscaTodasEspecies() {
        String jpql = "SELECT e FROM Especie e";
        return em.createQuery(jpql, Especie.class).getResultList();
    }

    public void atualizar(Especie especie) {
        this.em.merge(especie);
    }

    public void excluir(Especie especie) {
        atualizar(especie);
        this.em.remove(especie);
    }

}
