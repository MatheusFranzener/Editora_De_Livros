package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.service.LivrosService;
import br.senai.sc.livros.view.Menu;

import java.util.Collection;

public class LivrosController {
    Livros model;

    public void cadastrar(String titulo, Pessoa pessoa, String isbn, String qtdPaginas) {
        Livros livro;
        livro = Livros.cadastrar(titulo, Integer.parseInt(isbn), Integer.parseInt(qtdPaginas), (Autor) pessoa);

        LivrosService service = new LivrosService();
        service.inserir(livro);
    }

    public Collection<Livros> buscarLista(int lista) {
        LivrosService service = new LivrosService();
        Pessoa usuario = Menu.getUsuario();
        if (usuario instanceof Autor) {
            if (lista == 1) {
                return service.selecionarPorAutor(usuario);
            } else {
                return service.selecionarAtividadesAutor(usuario);
            }
        } else if (usuario instanceof Revisor) {
            if (lista == 1) {
                return service.selecionarPorStatus(Status.AGUARDANDO_REVISAO);
            } else {
                return service.selecionarPorStatus(Status.EM_REVISAO);
            }
        } else {
            if (lista == 1) {
                return service.selecionarTodos();
            } else {
                return service.selecionarPorStatus(Status.APROVADO);
            }
        }
    }

    public void editarLivro(int isbn, Status status) {
        LivrosService livrosService = new LivrosService();
        Livros livroAtualizado = livrosService.selecionar((isbn));

        livroAtualizado.setStatus(status);

        livrosService.atualizar(isbn, livroAtualizado);
    }

    public void addEditora(int isbn, Editora editora) {
        LivrosService livrosService = new LivrosService();
        Livros livroAtualizado = livrosService.selecionar(isbn);

        livroAtualizado.setEditora(editora);

        livrosService.atualizar(isbn, livroAtualizado);
    }

    public boolean validarIsbn(String isbn) {
        LivrosService service = new LivrosService();
        boolean isbnValido = false;
        try {
            service.selecionar(Integer.parseInt(isbn));
        } catch (RuntimeException e) {
            isbnValido = true;
        }
        return isbnValido;
    }
}
