package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.LivrosDAO;
import br.senai.sc.livros.model.entities.Livros;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.entities.Status;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class LivrosService {
    LivrosDAO acesso = new LivrosDAO();

    public void inserir(Livros livro) {
        new LivrosDAO().inserir(livro);
    }

    public Collection<Livros> selecionarTodos() {
        try {
            return acesso.selecionarTodos();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Livros selecionar(int isbn) {
        return new LivrosDAO().selecionar(isbn);
    }

    public void atualizar(int isbn, Livros livroAtualizado) {
        try {
            acesso.atualizar(isbn, livroAtualizado);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Collection<Livros> selecionarPorAutor(Pessoa pessoa) {
        String cpf = pessoa.getCpf();
        return new LivrosDAO().selecionarPorAutor(cpf);
    }

    public Collection<Livros> selecionarAtividadesAutor(Pessoa pessoa) {
        return new LivrosDAO().selecionarAtividadesAutor(pessoa);
    }

    public Collection<Livros> selecionarPorStatus(Status status) {
        return new LivrosDAO().selecionarPorStatus(status);
    }
}
