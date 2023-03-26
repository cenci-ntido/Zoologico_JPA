package utfpr.aulajpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @ManyToOne
    private Especie especie;
    @ManyToOne
    private Profissional profissionalResponsavel;

    public Animal() {
    }

    @Override
    public String toString() {
        return "Animal código:" + id + ", nome=" + nome + ", "
                + "especie=" + this.especie.getNome()
                + ", profissionalResponsavel=" + this.profissionalResponsavel.getNome();
    }

    public Profissional getProfissionalResponsavel() {
        return profissionalResponsavel;
    }

    public void setProfissionalResponsavel(Profissional profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
