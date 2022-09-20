package br.senai.sc.livros.model.entities;

public class Livros {
    private Autor autor;
    private Editora editora;
    private String titulo;
    private Integer isbn, qtdPag;
    private Status status;

    public Livros(String titulo, int isbn, int qtdPaginas, Autor autor, Status status) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.qtdPag = qtdPaginas;
        this.autor = autor;
        this.status = status;
    }

    public Livros() {

    }

    public static Livros cadastrar(String titulo, int isbn, int qtdPaginas, Autor autor) {
        return new Livros(titulo, isbn, qtdPaginas, autor, Status.AGUARDANDO_REVISAO);
    }

    public Autor getAutor() {
        return autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getIsbn() {
        return isbn;
    }

    public int getQtdPag() {
        return qtdPag;
    }

    public void setQtdPag(int qtdPag) {
        this.qtdPag = qtdPag;
    }

    @Override
    public String toString() {
        return "Livros{" +
                "autor=" + autor +
                ", editora=" + editora +
                ", titulo='" + titulo + '\'' +
                ", isbn=" + isbn +
                ", qtdPag=" + qtdPag +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        Livros livros = (Livros) o;
        return this.isbn == livros.isbn;
    }

    @Override
    public int hashCode() {
        return isbn;
    }
}
