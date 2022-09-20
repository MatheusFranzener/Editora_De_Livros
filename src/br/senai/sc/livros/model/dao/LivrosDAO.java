package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;

import java.sql.*;
import java.util.*;

public class LivrosDAO {
//    private static Collection<Livros> listaLivros = new HashSet<>();
//
//    static {
//        listaLivros.add(new Livros("KenzoLivro 1", 1, 300, new Autor("KenzoAutor 1", "Sato", "autor1@", "123", Genero.MASCULINO, "123"), Status.APROVADO));
//        listaLivros.add(new Livros("KenzoLivro 2", 2, 350, new Autor("KenzoAutor 2", "Sato", "autor2@", "123", Genero.MASCULINO, "123"), Status.REPROVADO));
//        listaLivros.add(new Livros("KenzoLivro 3", 3, 400, new Autor("KenzoAutor 3", "Sato", "autor3@", "123", Genero.MASCULINO, "123"), Status.AGUARDANDO_REVISAO));
//        listaLivros.add(new Livros("KenzoLivro 4", 4, 450, new Autor("KenzoAutor 4", "Sato", "autor4@", "123", Genero.MASCULINO, "123"), Status.AGUARDADNDO_EDICAO));
//        listaLivros.add(new Livros("KenzoLivro 5", 5, 500, new Autor("KenzoAutor 5", "Sato", "autor5@", "123", Genero.MASCULINO, "123"), Status.EM_REVISAO));
//        listaLivros.add(new Livros("KenzoLivro 6", 6, 550, new Autor("KenzoAutor 6", "Sato", "autor6@", "123", Genero.MASCULINO, "123"), Status.PUBLICADO));
//    }
//
//    public void inserir(Livros livro) {
//        listaLivros.add(livro);
//    }
//
//    public Collection<Livros> selecionarTodos() {
//        return Collections.unmodifiableCollection(listaLivros);
//    }
//
//    public Collection<Livros> selecionarPorAutor(Pessoa pessoa) {
//        List<Livros> lista = new ArrayList<>(listaLivros);
//        ArrayList<Livros> livrosAutor = new ArrayList<>();
//        for (int i = 0; i < listaLivros.size(); i++) {
//            if (lista.get(i).getAutor().equals(pessoa))
//                livrosAutor.add(lista.get(i));
//        }
//        return livrosAutor;
//    }
//
//    public Collection<Livros> selecionarAtividadesAutor(Pessoa pessoa) {
//        ArrayList<Livros> livrosAutor = new ArrayList<>();
//        for (Livros livro : listaLivros) {
//            if (livro.getAutor().equals(pessoa) && livro.getStatus().equals(Status.AGUARDADNDO_EDICAO))
//                livrosAutor.add(livro);
//        }
//        return livrosAutor;
//    }
//
//    public Collection<Livros> selecionarPorStatus(Status status) {
//        List<Livros> lista = new ArrayList<>(listaLivros);
//        ArrayList<Livros> livrosStatus = new ArrayList<>();
//        for (int i = 0; i < listaLivros.size(); i++) {
//            if (lista.get(i).getStatus().equals(status))
//                livrosStatus.add(lista.get(i));
//        }
//        return livrosStatus;
//    }
//
//    public Livros selecionar(int isbn) {
//        for (Livros livro: listaLivros) {
//            if (livro.getIsbn() == isbn)
//                return livro;
//        }
//        throw new RuntimeException();
//    }
//
//    public void atualizar(int isbn, Livros livroAtualizado) {
//        for (Livros livro : listaLivros) {
//            if (livro.getIsbn() == isbn) {
//                listaLivros.remove(livro);
//                listaLivros.add(livroAtualizado);
//            }
//        }
//    }

    public void inserir(Livros livro) throws SQLException {
        String sql = "insert into livro (isbn, titulo, qtdPaginas, status, pessoa_cpf) values (?, ?, ?, ?, ?)";
        String cpf = livro.getAutor().getCpf();
        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, livro.getIsbn());
        statement.setString(2, livro.getTitulo());
        statement.setInt(3, livro.getQtdPag());
        statement.setString(4, livro.getStatus().toString());
        statement.setString(5, cpf);
        statement.execute();

        connection.close();
    }

    public Collection<Livros> selecionarTodos() throws SQLException {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet != null && resultSet.next()) {
            Livros livro = new Livros(
                    resultSet.getString("titulo"),
                    resultSet.getInt("isbn"),
                    resultSet.getInt("qtdPaginas"),
                    buscarAutorCPF(resultSet.getString("pessoa_cpf")),
                    Status.valueOf(resultSet.getString("status"))
            );

            livrosCollection.add(livro);
        }
        connection.close();

        return livrosCollection;
    }

    public Collection<Livros> selecionarPorAutor(Pessoa pessoa) throws SQLException {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro where pessoa_cpf = ?";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pessoa.getCpf());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet != null && resultSet.next()) {
            Livros livro = new Livros(
                    resultSet.getString("titulo"),
                    resultSet.getInt("isbn"),
                    resultSet.getInt("qtdPaginas"),
                    buscarAutorCPF(resultSet.getString("pessoa_cpf")),
                    Status.valueOf(resultSet.getString("status"))
            );

            livrosCollection.add(livro);
        }
        connection.close();

        return livrosCollection;
    }

    public Collection<Livros> selecionarAtividadesAutor(Pessoa pessoa) throws SQLException {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro where status = ? and pessoa_cpf = ?";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Status.AGUARDANDO_EDICAO.toString());
        statement.setString(2, pessoa.getCpf());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet != null && resultSet.next()) {
            Livros livro = new Livros(
                    resultSet.getString("titulo"),
                    resultSet.getInt("isbn"),
                    resultSet.getInt("qtdPaginas"),
                    buscarAutorCPF(resultSet.getString("pessoa_cpf")),
                    Status.valueOf(resultSet.getString("status"))
            );

            livrosCollection.add(livro);
        }
        connection.close();

        return livrosCollection;
    }

    public Collection<Livros> selecionarPorStatus(Status status) throws SQLException {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro where status = ?";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, status.toString());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet != null && resultSet.next()) {
            Livros livro = new Livros(
                    resultSet.getString("titulo"),
                    resultSet.getInt("isbn"),
                    resultSet.getInt("qtdPaginas"),
                    buscarAutorCPF(resultSet.getString("pessoa_cpf")),
                    Status.valueOf(resultSet.getString("status"))
            );

            livrosCollection.add(livro);
        }
        connection.close();

        return livrosCollection;
    }

    public Livros selecionar(int isbn) throws SQLException {
        String sql = "select * from livro where isbn = ?";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, isbn);
        ResultSet resultSet = statement.executeQuery();

        Livros livro;
        if (resultSet != null) {
            if (resultSet.next()) {
                livro = new Livros(
                        resultSet.getString("titulo"),
                        resultSet.getInt("isbn"),
                        resultSet.getInt("qtdPaginas"),
                        buscarAutorCPF(resultSet.getString("pessoa_cpf")),
                        Status.valueOf(resultSet.getString("status"))
                );
                return livro;
            }
        }
        connection.close();
        throw new RuntimeException("Deu ruim!");
    }

    public void atualizar(int isbn, Livros livroAtualizado) throws SQLException {
        String sql = "";
        if (livroAtualizado.getEditora() == null) {
            sql = "update livro set isbn = ?, titulo = ?, qtdPaginas = ?, status = ?, pessoa_cpf = ? where isbn = ?";
        } else {
            sql = "update livro set isbn = ?, titulo = ?, qtdPaginas = ?, status = ?, pessoa_cpf = ?, editora_id = ? where isbn = ?";
        }

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, livroAtualizado.getIsbn());
        statement.setString(2, livroAtualizado.getTitulo());
        statement.setInt(3, livroAtualizado.getQtdPag());
        statement.setString(4, livroAtualizado.getStatus().toString());
        statement.setString(5, livroAtualizado.getAutor().getCpf());
        if (livroAtualizado.getEditora() != null) {
            statement.setInt(6, getEditoraID(livroAtualizado.getEditora().getNome()));
            statement.setInt(7, isbn);
        } else {
            statement.setInt(6, isbn);
        }
        statement.execute();

        connection.close();
    }

    public Autor buscarAutorCPF(String cpf) throws SQLException {
        String sql = "select * from pessoa where cpf = ?";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        ResultSet resultSet = statement.executeQuery();

        Autor autor;
        if (resultSet != null) {
            if (resultSet.next()) {
                autor = new Autor(
                        resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),
                        resultSet.getString("email"),
                        resultSet.getString("cpf"),
                        Genero.valueOf(resultSet.getString("genero")),
                        resultSet.getString("senha")
                );
                return autor;
            }
        }
        connection.close();
        throw new RuntimeException("Deu ruim!");
    }

    public Integer getEditoraID(String editoraNome) throws SQLException {
        String sql = "select * from editora where nome = ? limit 1";

        Conexao conexao = new Conexao();

        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, editoraNome);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null) {
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new RuntimeException("Editora não encontrada!");
    }
}
