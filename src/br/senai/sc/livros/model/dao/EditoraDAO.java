package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.Editora;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class EditoraDAO {
//    private static ArrayList<Editora> listaEditoras = new ArrayList<>();
//
//    static {
//        listaEditoras.add(new Editora("KenzoEditora1"));
//        listaEditoras.add(new Editora("KenzoEditora2"));
//        listaEditoras.add(new Editora("KenzoEditora3"));
//        listaEditoras.add(new Editora("KenzoEditora4"));
//        listaEditoras.add(new Editora("KenzoEditora5"));
//    }
//
//    public ArrayList<Editora> getListaEditora() {
//        return listaEditoras;
//    }

    public ArrayList<Editora> getListaEditora() throws SQLException {
        ArrayList<Editora> contatoCollection = new ArrayList<>();
        String sql = "select * from editora";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet != null && resultSet.next()) {
            Editora contato = new Editora(
                    resultSet.getString("nome")
            );

            contatoCollection.add(contato);
        }
        connection.close();

        return contatoCollection;
    }
}
