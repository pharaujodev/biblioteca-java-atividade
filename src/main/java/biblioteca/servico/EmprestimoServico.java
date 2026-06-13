package biblioteca.servico;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Livro;
import biblioteca.dominio.SituacaoEmprestimo;
import biblioteca.dominio.Usuario;
import biblioteca.porta.entrada.PortaEmprestimo;
import biblioteca.porta.saida.PortaEmprestimoRepositorio;
import biblioteca.porta.saida.PortaLivroRepositorio;
import biblioteca.porta.saida.PortaNotificacao;
import biblioteca.porta.saida.PortaUsuarioRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoServico implements PortaEmprestimo {

    private PortaEmprestimoRepositorio emprestimoRepositorio;
    private PortaLivroRepositorio livroRepositorio;
    private PortaUsuarioRepositorio usuarioRepositorio;
    private PortaNotificacao notificacao;

    public EmprestimoServico(PortaEmprestimoRepositorio emprestimoRepositorio,
                             PortaLivroRepositorio livroRepositorio,
                             PortaUsuarioRepositorio usuarioRepositorio,
                             PortaNotificacao notificacao) {
        this.emprestimoRepositorio = emprestimoRepositorio;
        this.livroRepositorio = livroRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.notificacao = notificacao;
    }

    @Override
    public Emprestimo realizarEmprestimo(Long usuarioId, Long livroId) {
        Usuario usuario = usuarioRepositorio.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        Livro livro = livroRepositorio.buscarPorId(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        if (!usuario.estaAtivo()) {
            throw new IllegalStateException("Usuário não está ativo para realizar empréstimos.");
        }

        livro.realizarEmprestimo();

        LocalDate dataRetirada = LocalDate.now();
        LocalDate dataPrevistaDevolucao = dataRetirada.plusDays(7);

        Emprestimo emprestimo = new Emprestimo(
                gerarIdEmprestimo(),
                livro,
                usuario,
                dataRetirada,
                dataPrevistaDevolucao,
                SituacaoEmprestimo.ATIVO
        );

        livroRepositorio.salvar(livro);
        emprestimoRepositorio.salvar(emprestimo);

        return emprestimo;
    }

    @Override
    public void registrarDevolucao(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepositorio.buscarPorId(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado."));

        emprestimo.registrarDevolucao();

        livroRepositorio.salvar(emprestimo.getLivro());
        emprestimoRepositorio.salvar(emprestimo);
    }

    @Override
    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();

        for (Emprestimo emprestimo : emprestimoRepositorio.listarTodos()) {
            if (emprestimo.getSituacao() == SituacaoEmprestimo.ATIVO) {
                ativos.add(emprestimo);
            }
        }

        return ativos;
    }

    @Override
    public List<Emprestimo> verificarAtrasos() {
        List<Emprestimo> atrasados = new ArrayList<>();
        LocalDate dataAtual = LocalDate.now();

        for (Emprestimo emprestimo : emprestimoRepositorio.listarTodos()) {
            if (emprestimo.estaAtrasado(dataAtual)) {
                emprestimo.marcarComoAtrasado();
                emprestimoRepositorio.salvar(emprestimo);
                notificacao.notificarAtraso(emprestimo.getUsuario(), emprestimo);
                atrasados.add(emprestimo);
            }
        }

        return atrasados;
    }

    private Long gerarIdEmprestimo() {
        return (long) emprestimoRepositorio.listarTodos().size() + 1;
    }
}
