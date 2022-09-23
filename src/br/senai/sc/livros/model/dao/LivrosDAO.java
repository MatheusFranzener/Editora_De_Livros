package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.factory.LivroFactory;
import br.senai.sc.livros.model.factory.PessoaFactory;

import java.sql.*;
import java.util.*;

public class LivrosDAO {
//    private static Collection<Livros> listaLivros = new HashSet<>();
//
//    static {
//        listaLivros.add(new Livros("mthLivro 1", 1, 300, new Autor("mthAutor 1", "Sato", "autor1@", "123", Genero.MASCULINO, "123"), Status.APROVADO));
//        listaLivros.add(new Livros("mthLivro 2", 2, 350, new Autor("mthAutor 2", "Sato", "autor2@", "123", Genero.MASCULINO, "123"), Status.REPROVADO));
//        listaLivros.add(new Livros("mthLivro 3", 3, 400, new Autor("mthAutor 3", "Sato", "autor3@", "123", Genero.MASCULINO, "123"), Status.AGUARDANDO_REVISAO));
//        listaLivros.add(new Livros("mthLivro 4", 4, 450, new Autor("mthAutor 4", "Sato", "autor4@", "123", Genero.MASCULINO, "123"), Status.AGUARDADNDO_EDICAO));
//        listaLivros.add(new Livros("mthLivro 5", 5, 500, new Autor("mthAutor 5", "Sato", "autor5@", "123", Genero.MASCULINO, "123"), Status.EM_REVISAO));
//        listaLivros.add(new Livros("mthLivro 6", 6, 550, new Autor("mthAutor 6", "Sato", "autor6@", "123", Genero.MASCULINO, "123"), Status.PUBLICADO));
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

    private Connection conn;
    static PessoaDAO dao = new PessoaDAO();

    public LivrosDAO() {
        this.conn = new ConexaoFactory().conectaBD();
    }

    public void inserir(Livros livro) {
        String sql = "insert into livro (isbn, titulo, qtdPaginas, status, pessoa_cpf) values (?, ?, ?, ?, ?)";
        String cpf = livro.getAutor().getCpf();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, livro.getIsbn());
            pstm.setString(2, livro.getTitulo());
            pstm.setInt(3, livro.getQtdPag());
            pstm.setString(4, livro.getStatus().toString());
            pstm.setString(5, cpf);
            try {
                pstm.execute();
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    public void atualizar(int isbn, Livros livroAtualizado){
        String sql = "";

        if (livroAtualizado.getEditora() == null) {
            sql = "update livro set isbn = ?, titulo = ?, qtdPaginas = ?, status = ?, pessoa_cpf = ? where isbn = ?";
        } else {
            sql = "update livro set isbn = ?, titulo = ?, qtdPaginas = ?, status = ?, pessoa_cpf = ?, editora_id = ? where isbn = ?";
        }

        try (PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, livroAtualizado.getIsbn());
            pstm.setString(2, livroAtualizado.getTitulo());
            pstm.setInt(3, livroAtualizado.getQtdPag());
            pstm.setString(4, livroAtualizado.getStatus().toString());
            pstm.setString(5, livroAtualizado.getAutor().getCpf());
            if (livroAtualizado.getEditora() != null) {
                pstm.setInt(6, getEditoraID(livroAtualizado.getEditora().getNome()));
                pstm.setInt(7, isbn);
            } else {
                pstm.setInt(6, isbn);
            }
            try{
                pstm.execute();
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    public Collection<Livros> selecionarTodos() {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro";

        try (PreparedStatement prtm = conn.prepareStatement(sql)) {
            try (ResultSet resultSet = prtm.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    livrosCollection.add(extrairObjetoLivro(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL (SELECIONAR TODOS)");
        }

        return livrosCollection;
    }

    public Collection<Livros> selecionarPorAutor(String cpf) {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro where pessoa_cpf = ?";

        System.out.println("Passou aqui 1");

        try (PreparedStatement prtm = conn.prepareStatement(sql)) {
            System.out.println("Passou aqui 2");
            prtm.setString(1, cpf);
            try (ResultSet resultSet = prtm.executeQuery()) {
                System.out.println("Passou aqui 3");
                while (resultSet.next()) {
                    livrosCollection.add(extrairObjetoLivro(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL (SELECIONAR POR AUTOR)");
        }

        return livrosCollection;
    }

    public Collection<Livros> selecionarAtividadesAutor(Pessoa pessoa) {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro where status = ? and pessoa_cpf = ?";

        try (PreparedStatement prtm = conn.prepareStatement(sql)) {
            prtm.setString(1, Status.AGUARDANDO_EDICAO.toString());
            prtm.setString(2, pessoa.getCpf());
            try (ResultSet resultSet = prtm.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    livrosCollection.add(extrairObjetoLivro(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL (SELECIONAR ATIVIDADES AUTOR)");
        }

        return livrosCollection;
    }

    public Collection<Livros> selecionarPorStatus(Status status) {
        Collection<Livros> livrosCollection = new ArrayList<>();
        String sql = "select * from livro where status = ?";

        try (PreparedStatement prtm = conn.prepareStatement(sql)) {
            prtm.setString(1, status.toString());
            try (ResultSet resultSet = prtm.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    livrosCollection.add(extrairObjetoLivro(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL (SELECIONAR STATUS)");
        }

        return livrosCollection;
    }

    public Livros selecionar(int isbn) {
        String sql = "select * from livro where isbn = ?";

        try (PreparedStatement prtm = conn.prepareStatement(sql)) {
            prtm.setInt(1, isbn);
            try (ResultSet resultSet = prtm.executeQuery()) {
                if (resultSet.next()) {
                    return extrairObjetoLivro(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            System.out.println("Erro na preparação do comando SQL (SELECIONAR POR ISBN)");
        }

        throw new RuntimeException("Algo deu ruim!");
    }

    public Integer getEditoraID(String editoraNome) throws SQLException {
        String sql = "select * from editora where nome = ? limit 1";

        ConexaoFactory conexao = new ConexaoFactory();

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

    private Livros extrairObjetoLivro(ResultSet resultSet) {
        try {
            return new LivroFactory().getLivro(
                    resultSet.getString("titulo"),
                    resultSet.getInt("isbn"),
                    resultSet.getInt("qtdPaginas"),
                    dao.buscarAutorCPF(resultSet.getString("pessoa_cpf")),
                    resultSet.getString("status")
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair objeto");
        }
    }
}
