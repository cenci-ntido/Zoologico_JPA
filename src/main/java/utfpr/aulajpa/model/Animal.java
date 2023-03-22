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

    public Animal(String nome, Especie especie, Profissional profissionalResponsavel) {
        this.nome = nome;
        this.especie = especie;
        this.profissionalResponsavel = profissionalResponsavel;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
