package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.factory.PessoaFactory;

import javax.swing.*;
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

    private Connection conn;

    public PessoaDAO() {
        this.conn = new ConexaoFactory().conectaBD();
    }

    public void inserir(Pessoa pessoa) {
        String sql = "insert into pessoa (cpf, nome, sobrenome, email, senha, genero, funcao) values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, pessoa.getCpf());
            pstm.setString(2, pessoa.getNome());
            pstm.setString(3, pessoa.getSobrenome());
            pstm.setString(4, pessoa.getEmail());
            pstm.setString(5, pessoa.getSenha());
            pstm.setString(6, pessoa.getGenero().toString());
            pstm.setString(7, buscarFuncao(pessoa));
            try {
                pstm.execute();
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL (INSERT)");
        }
    }

    public Pessoa selecionarPorEMAIL(String email){
        String sql = "select * from pessoa where email = ? limit 1";

        try(PreparedStatement prtm = conn.prepareStatement(sql)){
            prtm.setString(1, email);
            try (ResultSet resultSet = prtm.executeQuery()){
                if(resultSet.next()){
                    return extrairObjeto(resultSet);
                }
            } catch (Exception e){
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }

        throw new RuntimeException("E-mail não encontrado!");
    }

    public Autor buscarAutorCPF(String cpf){
        String sql = "select * from pessoa where cpf = ?";

        try(PreparedStatement prtm = conn.prepareStatement(sql)){
            prtm.setString(1, cpf);
            try (ResultSet resultSet = prtm.executeQuery()){
                if(resultSet.next()){
                    return (Autor) extrairObjeto(resultSet);
                }
            } catch (Exception e){
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }

        throw new RuntimeException("Cpf não encontrado!");
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

    private Pessoa extrairObjeto(ResultSet resultSet){
        try{
            return new PessoaFactory().getPessoa(
                    resultSet.getString("nome"),
                    resultSet.getString("sobrenome"),
                    resultSet.getString("email"),
                    resultSet.getString("cpf"),
                    resultSet.getString("genero"),
                    resultSet.getString("senha"),
                    resultSet.getString("funcao")
            );
        } catch (Exception e){
            throw new RuntimeException("Erro na extração do objeto");
        }
    }
}
