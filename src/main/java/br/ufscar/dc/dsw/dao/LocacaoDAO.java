package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Locacao;

public class LocacaoDAO extends GeralDAO{

     public List<Locacao> getAll() {
        List<Locacao> listaLocacoes = new ArrayList<>();
        String sqlQuery = "SELECT * FROM locacao;";

        try {
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String CPF = resultSet.getString("CPF");
                String CNPJ = resultSet.getString("CNPJ");
                Date dia = resultSet.getDate("dia");
                Time horario = resultSet.getTime("horario");
                Locacao locacao = new Locacao(new ClienteDAO().getClienteByCPF(CPF), new LocadoraDAO().getLocadoraByCNPJ(CNPJ), dia, horario);
                listaLocacoes.add(locacao); 
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocacoes;
    }

    public Locacao getLocacao(String CPF, String CNPJ, Date dia, Time horario) {
        Locacao locacao = null;   
        String sql = "SELECT * FROM locacao WHERE CPF = ? AND CNPJ = ? AND dia = ? AND horario = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setString(1, CPF);
            statement.setString(2, CNPJ);
            statement.setDate(3, dia);
            statement.setTime(4, horario);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                locacao = new Locacao(new ClienteDAO().getClienteByCPF(CPF), new LocadoraDAO().getLocadoraByCNPJ(CNPJ), dia, horario);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
     return locacao;
    }

     public boolean existeLocacao(String cidade, Date dia, Time horario) {  
        String sql = "SELECT l.* FROM Locacao l JOIN Locadora lo ON l.CNPJ = lo.CNPJ WHERE lo.cidade = ? AND l.dia = ? AND l.horario = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setString(1, cidade);
            statement.setDate(2, dia);
            statement.setTime(3, horario);

            ResultSet resultSet = statement.executeQuery();
            boolean existe = false;
            if (resultSet.next()) {
                existe = true;
            }

            resultSet.close();
            statement.close();
            conn.close();
            return existe;
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertLocacao(Locacao locacao) {

        String sql = "INSERT INTO Locacao (CPF, CNPJ, dia, horario) VALUES (?, ?, ?, ?);";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, locacao.getCliente().getDocumento());
            statement.setString(2, locacao.getLocadora().getDocumento());
            statement.setDate(3, locacao.getDia());
            statement.setTime(4, locacao.getHorario());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLocacao(Locacao locacao) {
        
        String sql = "DELETE FROM locacao WHERE CPF = ?, CNPJ = ?, dia = ?, horario = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, locacao.getCliente().getDocumento());
            statement.setString(2, locacao.getLocadora().getDocumento());
            statement.setDate(3, locacao.getDia());
            statement.setTime(4, locacao.getHorario());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
   
}
