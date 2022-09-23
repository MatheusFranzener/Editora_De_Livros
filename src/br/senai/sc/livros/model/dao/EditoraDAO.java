package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.Editora;
import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.factory.EditoraFactory;

import java.sql.*;
import java.util.ArrayList;

public class EditoraDAO {
//    private static ArrayList<Editora> listaEditoras = new ArrayList<>();
//
//    static {
//        listaEditoras.add(new Editora("MthEditora"));
//        listaEditoras.add(new Editora("MthEditora2"));
//        listaEditoras.add(new Editora("MthEditora3"));
//        listaEditoras.add(new Editora("MthEditora4"));
//        listaEditoras.add(new Editora("MthEditora5"));
//    }
//
//    public ArrayList<Editora> getListaEditora() {
//        return listaEditoras;
//    }

    private Connection conn;

    public EditoraDAO() {
        this.conn = new ConexaoFactory().conectaBD();
    }

    public ArrayList<Editora> getListaEditora() throws SQLException {
        ArrayList<Editora> contatoCollection = new ArrayList<>();
        String sql = "select * from editora";

        try (PreparedStatement prtm = conn.prepareStatement(sql)) {
            try (ResultSet resultSet = prtm.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    contatoCollection.add(extrairObjeto(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL (SELECIONAR TODOS)");
        }

        return contatoCollection;
    }

    private Editora extrairObjeto(ResultSet resultSet) {
        try {
            return new EditoraFactory().getEditora(
                    resultSet.getString("nome")
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair objeto");
        }
    }
}
