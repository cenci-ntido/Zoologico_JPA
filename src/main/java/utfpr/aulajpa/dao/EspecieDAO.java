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

}
