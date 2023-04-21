package utfpr.aulajpa.model;

import org.bson.types.ObjectId;

public class Especie {

    private ObjectId _id;
    private String nome;
    private String nomeCientifico;
    private String familia;
    private String comportamentos;

    public Especie() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getComportamentos() {
        return comportamentos;
    }

    public void setComportamentos(String comportamentos) {
        this.comportamentos = comportamentos;
    }

}
