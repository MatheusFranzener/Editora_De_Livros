import br.senai.sc.livros.controller.EditoraController;
import br.senai.sc.livros.controller.LivrosController;
import br.senai.sc.livros.controller.PessoaController;
import br.senai.sc.livros.model.entities.Autor;
import br.senai.sc.livros.model.entities.Editora;
import br.senai.sc.livros.model.entities.Genero;
import br.senai.sc.livros.model.entities.Status;

public class Teste {
    public static void main(String[] args) {
//        LivrosDAO dao = new LivrosDAO();
//        Collection<Livros> livrosDao = null;
//        try {
//            livrosDao = dao.selecionarTodos();
//        } catch (Exception exception) {
//            throw new RuntimeException(exception);
//        }
//
//        Collection<Livros> livros = new HashSet<>(livrosDao);
//        Livros livro = new Livros("String titulo", 120, 80, new Autor("String nome", "String sobrenome", "String email", "String cpf", Genero.FEMININO, "String senha"), Status.AGUARDANDO_EDICAO);
//        livros.add(livro);
//        teste(livro.getTitulo());
//        teste(livro.getIsbn());
//        teste(livro.getAutor());

        LivrosController controllerLivro = new LivrosController();
        EditoraController controllerEditora = new EditoraController();
        PessoaController controllerPessoa = new PessoaController();
    }

    public static void teste(Object o) {
        System.out.println(o);
    }
}
