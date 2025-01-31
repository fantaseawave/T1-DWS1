package br.ufscar.dc.dsw.domain;

public class Locadora extends Usuario {
    private String cidade;

    public Locadora(int id, String documento, String email, String password, String nome, 
                    boolean admin, boolean locadora, String cidade) {
        super(id, documento, email, password, nome, admin, locadora);
        this.cidade = cidade;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
