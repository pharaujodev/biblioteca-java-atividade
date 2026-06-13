package biblioteca.apresentacao;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Livro;
import biblioteca.dominio.SituacaoUsuario;
import biblioteca.dominio.Usuario;
import biblioteca.infraestrutura.adaptador.EmprestimoRepositorioMemoria;
import biblioteca.infraestrutura.adaptador.LivroRepositorioCsv;
import biblioteca.infraestrutura.adaptador.LivroRepositorioMemoria;
import biblioteca.infraestrutura.adaptador.NotificacaoConsole;
import biblioteca.infraestrutura.adaptador.UsuarioRepositorioMemoria;
import biblioteca.porta.entrada.PortaEmprestimo;
import biblioteca.porta.saida.PortaEmprestimoRepositorio;
import biblioteca.porta.saida.PortaLivroRepositorio;
import biblioteca.porta.saida.PortaNotificacao;
import biblioteca.porta.saida.PortaUsuarioRepositorio;
import biblioteca.servico.EmprestimoServico;

public class Main {

    public static void main(String[] args) {
        System.out.println("Demonstração com adaptador em memória");
        executarFluxoComMemoria();

        System.out.println();
        System.out.println("Demonstração com adaptador CSV para livros");
        executarFluxoComCsv();
    }

    private static void executarFluxoComMemoria() {
        PortaLivroRepositorio livroRepositorio = new LivroRepositorioMemoria();
        PortaUsuarioRepositorio usuarioRepositorio = new UsuarioRepositorioMemoria();
        PortaEmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorioMemoria();
        PortaNotificacao notificacao = new NotificacaoConsole();

        PortaEmprestimo emprestimoServico = new EmprestimoServico(
                emprestimoRepositorio,
                livroRepositorio,
                usuarioRepositorio,
                notificacao
        );

        executarFluxo(livroRepositorio, usuarioRepositorio, emprestimoServico, 1L);
    }

    private static void executarFluxoComCsv() {
        PortaLivroRepositorio livroRepositorio = new LivroRepositorioCsv("livros.csv");
        PortaUsuarioRepositorio usuarioRepositorio = new UsuarioRepositorioMemoria();
        PortaEmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorioMemoria();
        PortaNotificacao notificacao = new NotificacaoConsole();

        PortaEmprestimo emprestimoServico = new EmprestimoServico(
                emprestimoRepositorio,
                livroRepositorio,
                usuarioRepositorio,
                notificacao
        );

        executarFluxo(livroRepositorio, usuarioRepositorio, emprestimoServico, 2L);
    }

    private static void executarFluxo(PortaLivroRepositorio livroRepositorio,
                                      PortaUsuarioRepositorio usuarioRepositorio,
                                      PortaEmprestimo emprestimoServico,
                                      Long idBase) {
        Livro livro = new Livro(idBase, "Dom Casmurro", "Machado de Assis", "9788535910663", 2);
        Usuario usuario = new Usuario(idBase, "Pedro Henrique", "pedro@email.com", SituacaoUsuario.ATIVO);

        livroRepositorio.salvar(livro);
        usuarioRepositorio.salvar(usuario);

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
