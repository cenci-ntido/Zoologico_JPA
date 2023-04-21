package utfpr.aulajpa.model;

import java.sql.Timestamp;
import org.bson.types.ObjectId;

public class Servico {

    private ObjectId _id;
    private String descricao;
    private Timestamp dataHoraCadastro;
    private Timestamp dataHoraFim;
    private boolean realizado;
    private ObjectId id_animal;
    private ObjectId id_profissional;

    public Servico() {
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Timestamp dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Timestamp getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Timestamp dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public ObjectId getId_animal() {
        return id_animal;
    }

    public void setId_animal(ObjectId id_animal) {
        this.id_animal = id_animal;
    }

    public ObjectId getId_profissional() {
        return id_profissional;
    }

    public void setId_profissional(ObjectId id_profissional) {
        this.id_profissional = id_profissional;
    }

}
