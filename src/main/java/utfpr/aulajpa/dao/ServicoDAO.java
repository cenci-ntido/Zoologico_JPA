package utfpr.aulajpa.dao;

import java.util.List;
import javax.persistence.EntityManager;
import utfpr.aulajpa.model.Servico;

public class ServicoDAO {

    private EntityManager em;

    public ServicoDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Servico servico) {
        this.em.persist(servico);
    }

    public List<Servico> listarServicosNaoRealizados() {
        String jpql = "SELECT s FROM Servico s WHERE s.realizado = false";
        return em.createQuery(jpql, Servico.class).getResultList();
    }

}
