package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.PessoaDAO;
import br.senai.sc.livros.model.entities.Pessoa;

public class PessoaService {
    PessoaDAO dao = new PessoaDAO();

    public void inserir(Pessoa pessoa) {
        try {
            dao.inserir(pessoa);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Pessoa selecionarPorEMAIL(String email) {
        try {
            return dao.selecionarPorEMAIL(email);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
