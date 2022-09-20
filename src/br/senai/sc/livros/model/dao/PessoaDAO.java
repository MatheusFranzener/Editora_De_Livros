package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
//        listaPessoas.add(new Autor("mthAutor", "mth", "mth@", "123", Genero.MASCULINO, "123"));
//        listaPessoas.add(new Revisor("mthAutor", "mth", "mt@", "123", Genero.MASCULINO, "123"));
//        listaPessoas.add(new Diretor("mthAutor", "mth", "mh@", "123", Genero.MASCULINO, "123"));
//    }

    public void inserir(Pessoa pessoa) throws SQLException {
        String sql = "insert into pessoa (cpf, nome, sobrenome, email, senha, genero, funcao) values (?, ?, ?, ?, ?, ?, ?)";

        ConexaoFactory conexao = new ConexaoFactory();

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

        ConexaoFactory conexao = new ConexaoFactory();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        Pessoa pessoa;
        if (resultSet != null && resultSet.next()) {
            if (resultSet.getString("funcao").equals("AUTOR")) {
                pessoa = new Autor(
                        resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf"),
                        Genero.valueOf(resultSet.getString("genero")),
                        resultSet.getString("senha")
                );
                connection.close();
                return pessoa;
            } else if(resultSet.getString("funcao").equals("REVISOR")) {
                pessoa = new Revisor(
                        resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf"),
                        Genero.valueOf(resultSet.getString("genero")),
                        resultSet.getString("senha")
                );
                connection.close();
                return pessoa;
            } else {
                pessoa = new Diretor(
                        resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf"),
                        Genero.valueOf(resultSet.getString("genero")),
                        resultSet.getString("senha")
                );
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
