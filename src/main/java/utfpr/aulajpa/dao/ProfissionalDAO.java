package utfpr.aulajpa.dao;

import java.util.List;
import javax.persistence.EntityManager;
import utfpr.aulajpa.model.Profissional;

public class ProfissionalDAO {

    private EntityManager em;

    public ProfissionalDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Profissional profissional) {
        this.em.persist(profissional);
    }

    public List<Profissional> buscaTodosProfissionais() {
        String jpql = "SELECT p FROM Profissional p";
        return em.createQuery(jpql, Profissional.class).getResultList();
    }

    public void atualizar(Profissional profissional) {
        this.em.merge(profissional);
    }

    public void excluir(Profissional profissional) {
        atualizar(profissional);
        this.em.remove(profissional);
    }

}
