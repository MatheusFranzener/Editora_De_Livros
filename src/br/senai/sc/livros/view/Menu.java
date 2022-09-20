package br.senai.sc.livros.view;

import br.senai.sc.livros.model.entities.Autor;
import br.senai.sc.livros.model.entities.Diretor;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.entities.Revisor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    private JPanel menu;
    private JButton sairButton;
    private JButton cadastrarLivroButton;
    private JButton listarLivrosButton;
    private JButton listarAtividadesButton;
    private JButton cadastrarRevisorButton;
    private static Pessoa usuario;

    public static Pessoa getUsuario() {
        return usuario;
    }

    public Menu(Pessoa pessoa) {
        usuario = pessoa;
        criarComponentes();
    }

    private void criarComponentes() {
        setContentPane(menu); //define o menu como o painel
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //quando o usuário fechar o painel o sistema vai fechar apenas essa tela
        pack();

        cadastrarLivroButton.addActionListener(this);
        cadastrarLivroButton.setActionCommand("cadastrarLivro");

        listarLivrosButton.addActionListener(this);
        listarLivrosButton.setActionCommand("listarLivros");

        listarAtividadesButton.addActionListener(this);
        listarAtividadesButton.setActionCommand("listarAtividades");

        cadastrarRevisorButton.addActionListener(this);
        cadastrarRevisorButton.setActionCommand("cadastrarRevisor");

        sairButton.addActionListener(this);
        sairButton.setActionCommand("sair");

        if (usuario instanceof Autor || usuario instanceof Revisor) {
            cadastrarRevisorButton.setVisible(false);
        }

        if (usuario instanceof Revisor || usuario instanceof Diretor) {
            cadastrarLivroButton.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("cadastrarLivro")) {
            CadastroLivro cadastroLivro = new CadastroLivro(usuario, null);
            dispose();
            cadastroLivro.setVisible(true);
        } else if (e.getActionCommand().equals("listarLivros")) {
            Estante estante = new Estante(1);
            dispose();
            estante.setVisible(true);
        } else if (e.getActionCommand().equals("listarAtividades")) {
            Estante estante = new Estante(2);
            dispose();
            estante.setVisible(true);
        } else if (e.getActionCommand().equals("cadastrarRevisor")) {
            CadastroPessoa cadastroPessoa = new CadastroPessoa();
            dispose();
            cadastroPessoa.setVisible(true);
        } else if (e.getActionCommand().equals("sair")) {
            usuario = null;
            dispose();
            Login login = new Login();
            login.run();
        }
    }
}
