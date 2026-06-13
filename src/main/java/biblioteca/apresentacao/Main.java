package biblioteca.apresentacao;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Livro;
import biblioteca.dominio.SituacaoUsuario;
import biblioteca.dominio.Usuario;
import biblioteca.dominio.evento.DevolucaoRegistradaEvento;
import biblioteca.dominio.evento.EmprestimoRealizadoEvento;
import biblioteca.infraestrutura.adaptador.EmprestimoRepositorioMemoria;
import biblioteca.infraestrutura.adaptador.LivroRepositorioCsv;
import biblioteca.infraestrutura.adaptador.LivroRepositorioMemoria;
import biblioteca.infraestrutura.adaptador.NotificacaoConsole;
import biblioteca.infraestrutura.adaptador.ServicoDeLog;
import biblioteca.infraestrutura.adaptador.ServicoDeNotificacao;
import biblioteca.infraestrutura.adaptador.UsuarioRepositorioMemoria;
import biblioteca.evento.EventBus;
import biblioteca.porta.entrada.PortaEmprestimo;
import biblioteca.porta.saida.PortaEmprestimoRepositorio;
import biblioteca.porta.saida.PortaLivroRepositorio;
import biblioteca.porta.saida.PortaNotificacao;
import biblioteca.porta.saida.PortaUsuarioRepositorio;
import biblioteca.servico.EmprestimoServico;

public class Main {

    public static void main(String[] args) {
        EventBus<EmprestimoRealizadoEvento> eventosEmprestimo = new EventBus<>();
        EventBus<DevolucaoRegistradaEvento> eventosDevolucao = new EventBus<>();

        ServicoDeNotificacao servicoDeNotificacao = new ServicoDeNotificacao();
        ServicoDeLog servicoDeLog = new ServicoDeLog();

        eventosEmprestimo.assinar(servicoDeNotificacao::notificarEmprestimoRealizado);
        eventosEmprestimo.assinar(servicoDeLog::registrarEmprestimo);
        eventosDevolucao.assinar(servicoDeLog::registrarDevolucao);

        System.out.println("Demonstração com eventos e adaptador em memória");
        executarFluxoComMemoria(eventosEmprestimo, eventosDevolucao);

        System.out.println();
        System.out.println("Demonstração com eventos e adaptador CSV para livros");
        executarFluxoComCsv(eventosEmprestimo, eventosDevolucao);
    }

    private static void executarFluxoComMemoria(EventBus<EmprestimoRealizadoEvento> eventosEmprestimo,
                                                EventBus<DevolucaoRegistradaEvento> eventosDevolucao) {
        PortaLivroRepositorio livroRepositorio = new LivroRepositorioMemoria();
        PortaUsuarioRepositorio usuarioRepositorio = new UsuarioRepositorioMemoria();
        PortaEmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorioMemoria();
        PortaNotificacao notificacao = new NotificacaoConsole();

        PortaEmprestimo emprestimoServico = new EmprestimoServico(
                emprestimoRepositorio,
                livroRepositorio,
                usuarioRepositorio,
                notificacao,
                eventosEmprestimo,
                eventosDevolucao
        );

        executarFluxo(livroRepositorio, usuarioRepositorio, emprestimoServico, 1L);
    }

    private static void executarFluxoComCsv(EventBus<EmprestimoRealizadoEvento> eventosEmprestimo,
                                            EventBus<DevolucaoRegistradaEvento> eventosDevolucao) {
        PortaLivroRepositorio livroRepositorio = new LivroRepositorioCsv("livros.csv");
        PortaUsuarioRepositorio usuarioRepositorio = new UsuarioRepositorioMemoria();
        PortaEmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorioMemoria();
        PortaNotificacao notificacao = new NotificacaoConsole();

        PortaEmprestimo emprestimoServico = new EmprestimoServico(
                emprestimoRepositorio,
                livroRepositorio,
                usuarioRepositorio,
                notificacao,
                eventosEmprestimo,
                eventosDevolucao
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
        System.out.println("Empréstimos ativos: " + emprestimoServico.listarEmprestimosAtivos().size());

        emprestimoServico.registrarDevolucao(emprestimo.getId());

        System.out.println("Devolução registrada.");
    }
}
