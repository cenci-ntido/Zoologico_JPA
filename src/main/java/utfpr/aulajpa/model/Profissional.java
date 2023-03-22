package utfpr.aulajpa.model;

import utfpr.aulajpa.util.EnumProfissional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private EnumProfissional tipo;

    public Profissional() {
    }

    public Profissional(String nome, EnumProfissional tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public EnumProfissional getTipo() {
        return tipo;
    }

    public void setTipo(EnumProfissional tipo) {
        this.tipo = tipo;
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
