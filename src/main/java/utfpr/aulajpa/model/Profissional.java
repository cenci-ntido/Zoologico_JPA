package utfpr.aulajpa.model;

import org.bson.types.ObjectId;

public class Profissional {

    private ObjectId _id;
    private String nome;
    private String tipo;

    public Profissional() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void setId(ObjectId _id) {
        this._id = _id;
    }
}
