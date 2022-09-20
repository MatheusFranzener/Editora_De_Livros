package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.LivrosController;
import br.senai.sc.livros.model.entities.Livros;
import br.senai.sc.livros.model.entities.Pessoa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroLivro extends JFrame {
    private JTextField tituloInput;
    private JTextField isbnInput;
    private JTextField qtdPaginasInput;
    private JButton voltarButton;
    private JButton cadastrarButton;
    private JPanel cadastroLivro;

    public CadastroLivro(Pessoa pessoa, Livros livro) {
        criarComponentes();
        if (livro != null) {
            tituloInput.setText(livro.getTitulo());
            isbnInput.setText(String.valueOf(livro.getIsbn()));
            qtdPaginasInput.setText(String.valueOf(livro.getQtdPag()));
            cadastrarButton.setText("Salvar Alterações");
        }
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tituloInput.getText().isEmpty() || isbnInput.getText().isEmpty() || qtdPaginasInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Há campos vazios! \n" +
                            "Preencha e tente novamente!");
                } else {
                    String message = "Livro cadastrado com sucesso!";
                    LivrosController controller = new LivrosController();
                    boolean isbnValido = true;
                    if (cadastrarButton.getText().equals("Cadastrar")) {
                        isbnValido = controller.validarIsbn(isbnInput.getText());
                        if (isbnValido) {
                            controller.cadastrar(tituloInput.getText(), pessoa, isbnInput.getText(), qtdPaginasInput.getText());
                        } else {
                            JOptionPane.showMessageDialog(null, "ERRO: ISBN já cadastrado!");
                        }
                    } else {
                        livro.setTitulo(tituloInput.getText());
                        livro.setQtdPag(Integer.parseInt(qtdPaginasInput.getText()));
                        controller.editarLivro(livro.getIsbn(), livro.getStatus());
                        message = "Livro editado com sucesso!";
                    }
                    if (isbnValido) {
                        dispose();
                        Menu menu = new Menu(pessoa);
                        JOptionPane.showMessageDialog(null, message);
                        menu.setVisible(true);
                    }
                }
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu(pessoa);
                menu.setVisible(true);
            }
        });
    }

    private void criarComponentes() {
        setContentPane(cadastroLivro);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
}