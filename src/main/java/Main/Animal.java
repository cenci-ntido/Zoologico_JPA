package Main;

import org.bson.types.ObjectId;

public class Animal {

    private ObjectId _id;
    private String nome;
    private ObjectId id_especie;
    private ObjectId id_profissional;

    public Animal() {

    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ObjectId getId_especie() {
        return id_especie;
    }

    public void setId_especie(ObjectId id_especie) {
        this.id_especie = id_especie;
    }

    public ObjectId getId_profissional() {
        return id_profissional;
    }

    public void setId_profissional(ObjectId id_profissional) {
        this.id_profissional = id_profissional;
    }

}
