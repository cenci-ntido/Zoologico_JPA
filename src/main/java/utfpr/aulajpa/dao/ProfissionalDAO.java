package utfpr.aulajpa.dao;

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

}
