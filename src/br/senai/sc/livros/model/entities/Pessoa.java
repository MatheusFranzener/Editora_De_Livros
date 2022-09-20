package br.senai.sc.livros.model.entities;

import br.senai.sc.livros.view.Menu;

import java.util.Objects;

public abstract class Pessoa {
    private String nome, sobrenome, email, senha, cpf;
    private Genero genero;

    public Pessoa(String nome, String sobrenome, String email, String cpf, Genero genero, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.genero = genero;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public Pessoa validaLogin(String senha) {
        if (this.getSenha().equals(senha))
            return this;
        throw new RuntimeException("Senha incorreta!");
    }

    public static Pessoa cadastrar(String nome, String sobrenome, String email,
                                   String cpf, Genero genero, String senha,
                                   String confimarSenha) {
        if (senha.equals(confimarSenha)) {
            if (email.contains("@")) {
                if (Menu.getUsuario() instanceof Diretor) {
                    return new Revisor(nome, sobrenome, email, cpf, genero, senha);
                } else {
                    return new Autor(nome, sobrenome, email, cpf, genero, senha);
                }
            }
            throw new RuntimeException("E-mail incorreto!");
        }
        throw new RuntimeException("As senhas não conferem!");
    }

    @Override
    public boolean equals(Object o) {
        Pessoa outraPessoa = (Pessoa) o;
        return cpf.equals(outraPessoa.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.charAt(0);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", genero=" + genero +
                '}';
    }
}
