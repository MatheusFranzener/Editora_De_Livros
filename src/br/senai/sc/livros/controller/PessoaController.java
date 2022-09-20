package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.Genero;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.service.PessoaService;

public class PessoaController {
    Pessoa model;

    public Pessoa validaLogin(String email, String senha) {
        PessoaService service = new PessoaService();
        model = service.selecionarPorEMAIL(email);

        return model.validaLogin(senha);
    }

    public void cadastrar(String nome, String sobrenome, String email,
                          String cpf, Object genero, String senha, String confimarSenha) {
        Pessoa pessoa = Pessoa.cadastrar(nome, sobrenome, email, cpf, (Genero) genero, senha, confimarSenha);
        PessoaService service = new PessoaService();
        service.inserir(pessoa);
    }
}
