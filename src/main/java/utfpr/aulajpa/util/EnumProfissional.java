package utfpr.aulajpa.util;

public enum EnumProfissional {
    treinador("Treinador"),
    zelador("Zelador");

    private String tipo;

    private EnumProfissional(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
