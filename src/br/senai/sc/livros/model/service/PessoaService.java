package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.PessoaDAO;
import br.senai.sc.livros.model.entities.Pessoa;

public class PessoaService {
    PessoaDAO dao = new PessoaDAO();

    public void inserir(Pessoa pessoa) {
        new PessoaDAO().inserir(pessoa);
    }

    public Pessoa selecionarPorEMAIL(String email) {
       return new PessoaDAO().selecionarPorEMAIL(email);
    }
}
