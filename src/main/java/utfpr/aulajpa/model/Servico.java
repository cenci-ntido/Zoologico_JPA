package utfpr.aulajpa.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private Timestamp dataHoraCadastro;
    private Timestamp dataHoraFim;
    private boolean realizado;
    @ManyToOne
    private Animal animal;
    @ManyToOne
    private Profissional profissional;

    public Servico() {
    }

    public Timestamp getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Timestamp dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Serviço código: " + id + ", descricao=" + descricao + ", "
                + "dataHoraCadastro=" + dataHoraCadastro + ", "
                + "dataHoraFim=" + dataHoraFim + ", "
                + "realizado=" + realizado + ", animal=" + this.animal.getNome()
                + ", profissional=" + this.profissional.getNome() + '}';
    }

    public Timestamp getDataHora() {
        return dataHoraCadastro;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHoraCadastro = dataHora;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

}
