package br.senai.sc.livros.view;

import br.senai.sc.livros.model.entities.Livros;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultTableModelCollection extends AbstractTableModel {
    List<Livros> dados;
    String[] colunas;

    public DefaultTableModelCollection(Collection<Livros> lista) {
        this.dados = new ArrayList<>(lista);
        colunas = new String[]{ "ISBN", "Título", "Qtd Páginas", "Autor", "Editora", "Status" };
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Livros livro = dados.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return livro.getIsbn();
            }
            case 1 -> {
                return livro.getTitulo();
            }
            case 2 -> {
                return livro.getQtdPag();
            }
            case 3 -> {
                return livro.getAutor();
            }
            case 4 -> {
                return livro.getEditora();
            }
            default -> {
                return livro.getStatus();
            }
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }
}
