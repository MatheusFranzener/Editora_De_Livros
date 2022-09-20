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
        try {
            acesso.inserir(livro);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Collection<Livros> selecionarTodos() {
        try {
            return acesso.selecionarTodos();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Livros selecionar(int isbn) {
        try {
            return acesso.selecionar(isbn);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void atualizar(int isbn, Livros livroAtualizado) {
        try {
            acesso.atualizar(isbn, livroAtualizado);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Collection<Livros> selecionarPorAutor(Pessoa pessoa) {
        try {
            return acesso.selecionarPorAutor(pessoa);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Collection<Livros> selecionarAtividadesAutor(Pessoa pessoa) {
        try {
            return acesso.selecionarAtividadesAutor(pessoa);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Collection<Livros> selecionarPorStatus(Status status) {
        try {
            return acesso.selecionarPorStatus(status);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
