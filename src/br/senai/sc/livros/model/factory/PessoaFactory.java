package br.senai.sc.livros.model.factory;

import br.senai.sc.livros.model.entities.*;

public class PessoaFactory {
    public Pessoa getPessoa(String nome, String sobrenome, String email, String cpf, String genero, String senha, String funcao) {
        switch (funcao) {
            case "AUTOR":
                return new Autor(nome, sobrenome, email, cpf, Genero.valueOf(genero), senha);
            case "REVISOR":
                return new Revisor(nome, sobrenome, email, cpf, Genero.valueOf(genero), senha);
            default:
                return new Diretor(nome, sobrenome, email, cpf, Genero.valueOf(genero), senha);
        }
    }
}
