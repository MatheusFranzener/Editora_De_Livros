package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.EditoraController;
import br.senai.sc.livros.controller.LivrosController;
import br.senai.sc.livros.model.entities.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Estante extends JFrame {
    private JPanel estante;
    private JTable tabelaLivros;
    private JButton voltarButton;
    private JButton editarButton;
    private static int lista;

    public Estante(int botao) {
        lista = botao;
        criarComponentes();
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu(Menu.getUsuario());
                menu.setVisible(true);
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivrosController livrosController = new LivrosController();
                Pessoa usuario = Menu.getUsuario();

                int isbn = (int) tabelaLivros.getModel()
                        .getValueAt(tabelaLivros.getSelectedRow(), 0);

                int index = tabelaLivros.getSelectedRow();
                ArrayList<Livros> listaLivros = new ArrayList<>(livrosController.buscarLista(lista));
                Livros livro = listaLivros.get(index);

                if (usuario instanceof Autor) {
                    CadastroLivro cadastroLivro = new CadastroLivro(Menu.getUsuario(), livro);
                    dispose();
                    cadastroLivro.setVisible(true);

                    livrosController.editarLivro(isbn, Status.AGUARDANDO_REVISAO);

                } else if (usuario instanceof Revisor) {
                    if (lista == 1) {
                        livrosController.editarLivro(isbn, Status.EM_REVISAO);
                    } else {
                        Object[] options = {"Aprovar", "Reprovar", "Pedir para editar"};
                        int opcao = JOptionPane.showOptionDialog(null, "Selecione a ação desejada", "Editar livro",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);

                        System.out.println("opcao: " + opcao);
                        switch (opcao) {
                            case 0:
                                livrosController.editarLivro(isbn, Status.APROVADO);
                                break;
                            case 1:
                                livrosController.editarLivro(isbn, Status.REPROVADO);
                                break;
                            case 2:
                                livrosController.editarLivro(isbn, Status.AGUARDANDO_EDICAO);
                                break;
                        }
                    }

                } else {
                    Object[] options = {"Publicar", "Reprovar", "Pedir para editar"};
                    int opcao = JOptionPane.showOptionDialog(null, "Selecione a ação desejada", "Editar livro",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);

                    switch (opcao) {
                        case 0:
                            EditoraController controller = new EditoraController();
                            ArrayList<Editora> listaEditoras = controller.buscarLista();
                            Object[] itens = new Object[listaEditoras.size()];

                            for (int i = 0; i < itens.length; i++) {
                                itens[i] = listaEditoras.get(i);
                            }

                            Object editora = JOptionPane.showInputDialog(null,
                                    "Escolha uma editora", "Editora",
                                    JOptionPane.INFORMATION_MESSAGE, null,
                                    itens, itens[0].toString());
                            livrosController.addEditora(isbn, (Editora) editora);
                            livrosController.editarLivro(isbn, Status.PUBLICADO);
                            break;
                        case 1:
                            livrosController.editarLivro(isbn, Status.REPROVADO);
                            break;
                        case 2:
                            livrosController.editarLivro(isbn, Status.AGUARDANDO_EDICAO);
                            break;
                    }
                }
            }
        });
    }

    public void criarComponentes() {
        LivrosController livrosController = new LivrosController();
        tabelaLivros.setModel(new DefaultTableModelCollection(livrosController.buscarLista(lista)));
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (lista == 1) {
            editarButton.setVisible(false);
        }

        if (Menu.getUsuario() instanceof Revisor && lista == 1) {
            editarButton.setVisible(true);
            editarButton.setText("Começar a Editar");
        }

        setContentPane(estante);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
}
