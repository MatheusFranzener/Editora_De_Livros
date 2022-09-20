package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PessoaDAO {
//    private static Set<Pessoa> listaPessoas = new HashSet<>();
//
//    public void inserir(Pessoa pessoa) {
//        listaPessoas.add(pessoa);
//    }
//
//    public Pessoa selecionarPorEMAIL(String email) {
//        for (Pessoa pessoa: listaPessoas) {
//            if (pessoa.getEmail().equals(email))
//                return pessoa;
//        }
//        throw new RuntimeException("E-mail não encontrado!");
//    }
//
//    static {
//        listaPessoas.add(new Autor("KenzoAutor", "Sato", "ka@", "123", Genero.MASCULINO, "123"));
//        listaPessoas.add(new Revisor("KenzoRevisor", "Sato", "kr@", "123", Genero.MASCULINO, "123"));
//        listaPessoas.add(new Diretor("KenzoDiretor", "Sato", "kd@", "123", Genero.MASCULINO, "123"));
//    }

    public void inserir(Pessoa pessoa) throws SQLException {
        String sql = "insert into pessoa (cpf, nome, sobrenome, email, senha, genero, funcao) values (?, ?, ?, ?, ?, ?, ?)";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pessoa.getCpf());
        statement.setString(2, pessoa.getNome());
        statement.setString(3, pessoa.getSobrenome());
        statement.setString(4, pessoa.getEmail());
        statement.setString(5, pessoa.getSenha());
        statement.setString(6, pessoa.getGenero().toString());
        statement.setString(7, buscarFuncao(pessoa));
        statement.execute();

        connection.close();
    }

    public Pessoa selecionarPorEMAIL(String email) throws SQLException {
        String sql = "select * from pessoa where email = ? limit 1";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        Pessoa pessoa;
        if (resultSet != null) {
            if (resultSet.next()) {
                pessoa = new Pessoa(
                        resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf"),
                        Genero.valueOf(resultSet.getString("genero")),
                        resultSet.getString("senha")
                ) {
                };
                connection.close();
                return pessoa;
            }
        }
        connection.close();
        throw new RuntimeException("E-mail não encontrado!");
    }

    public String buscarFuncao(Pessoa pessoa) {
        if (pessoa instanceof Autor)
            return "AUTOR";
        else if (pessoa instanceof Revisor)
            return "REVISOR";
        else if (pessoa instanceof Diretor)
            return "DIRETOR";
        else
            throw new RuntimeException("Função não encontrada!");
    }
}
