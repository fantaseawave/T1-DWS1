package br.ufscar.dc.dsw.domain;

public class Cliente extends Usuario{
    private String telefone;
    private String sexo;
    private String dataNascimento;

    public Cliente(String documento, String email, String password, String nome, 
                    boolean admin, boolean locadora, String telefone, String sexo, String dataNascimento) {
        super(documento, email, password, nome, admin, locadora);
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getSexo() {
        return this.sexo;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
