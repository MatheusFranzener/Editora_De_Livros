package br.senai.sc.livros.model.factory;

import br.senai.sc.livros.model.entities.Autor;
import br.senai.sc.livros.model.entities.Livros;
import br.senai.sc.livros.model.entities.Status;

public class LivroFactory {
    public Livros getLivro(String titulo, int isbn, int qtdPaginas, Autor autor, String status){
       return new Livros(titulo, isbn, qtdPaginas, autor, Status.valueOf(status));
    }
}
