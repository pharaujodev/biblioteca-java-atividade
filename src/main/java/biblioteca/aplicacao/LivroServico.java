package biblioteca.aplicacao;

import biblioteca.dominio.Livro;
import biblioteca.infraestrutura.LivroRepositorio;

import java.util.List;

public class LivroServico {

    private LivroRepositorio livroRepositorio;

    public LivroServico(LivroRepositorio livroRepositorio) {
        this.livroRepositorio = livroRepositorio;
    }

    public void cadastrarLivro(Livro livro) {
        livroRepositorio.salvar(livro);
    }

    public Livro buscarLivroPorId(Long id) {
        return livroRepositorio.buscarPorId(id).orElse(null);
    }

    public List<Livro> listarTodosLivros() {
        return livroRepositorio.listarTodos();
    }

    public void removerLivro(Long id) {
        livroRepositorio.remover(id);
    }
}