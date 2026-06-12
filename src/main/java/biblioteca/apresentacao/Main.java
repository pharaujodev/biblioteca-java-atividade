package biblioteca.apresentacao;

import biblioteca.aplicacao.EmprestimoServico;
import biblioteca.aplicacao.LivroServico;
import biblioteca.aplicacao.UsuarioServico;
import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Livro;
import biblioteca.dominio.SituacaoUsuario;
import biblioteca.dominio.Usuario;
import biblioteca.infraestrutura.EmprestimoRepositorio;
import biblioteca.infraestrutura.LivroRepositorio;
import biblioteca.infraestrutura.UsuarioRepositorio;

public class Main {

    public static void main(String[] args) {
        LivroRepositorio livroRepositorio = new LivroRepositorio();
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        EmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorio();

        LivroServico livroServico = new LivroServico(livroRepositorio);
        UsuarioServico usuarioServico = new UsuarioServico(usuarioRepositorio);
        EmprestimoServico emprestimoServico = new EmprestimoServico(
                emprestimoRepositorio,
                livroRepositorio,
                usuarioRepositorio
        );

        Livro livro = new Livro(1L, "Dom Casmurro", "Machado de Assis", "9788535910663", 2);
        Usuario usuario = new Usuario(1L, "Pedro Henrique", "pedro@email.com", SituacaoUsuario.ATIVO);

        livroServico.cadastrarLivro(livro);
        usuarioServico.cadastrarUsuario(usuario);

        System.out.println("Livro cadastrado: " + livro.getTitulo());
        System.out.println("Usuário cadastrado: " + usuario.getNome());

        Emprestimo emprestimo = emprestimoServico.realizarEmprestimo(usuario.getId(), livro.getId());

        System.out.println("Empréstimo realizado com ID: " + emprestimo.getId());
        System.out.println("Quantidade disponível após empréstimo: " + livro.getQuantidadeDisponivel());
        System.out.println("Empréstimos ativos: " + emprestimoServico.listarEmprestimosAtivos().size());

        emprestimoServico.registrarDevolucao(emprestimo.getId());

        System.out.println("Devolução registrada.");
        System.out.println("Quantidade disponível após devolução: " + livro.getQuantidadeDisponivel());
    }
}
