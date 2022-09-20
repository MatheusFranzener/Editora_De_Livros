package br.senai.sc.livros.model.entities;

public class Autor extends Pessoa {
    public Autor(String nome, String sobrenome, String email, String cpf, Genero genero, String senha) {
        super(nome, sobrenome, email, cpf, genero, senha);
    }

    @Override
    public String toString() {
        return this.getNome() + " " + this.getSobrenome();
    }


}
