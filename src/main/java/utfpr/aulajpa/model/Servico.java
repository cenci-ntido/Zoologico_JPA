package utfpr.aulajpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private DateTime dataHora;
    @ManyToOne
    private Animal animal;
    @ManyToOne
    private Profissional profissional;

    public Servico() {
    }

    public Servico(String descricao, DateTime dataHora, Animal animal, Profissional profissional) {
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.animal = animal;
        this.profissional = profissional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(DateTime dataHora) {
        this.dataHora = dataHora;
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

}
