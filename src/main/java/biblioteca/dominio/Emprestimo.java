package biblioteca.dominio;

import java.time.LocalDate;

public class Emprestimo {

    private Long id;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataRetirada;
    private LocalDate dataPrevistaDevolucao;
    private SituacaoEmprestimo situacao;

    public Emprestimo(Long id, Livro livro, Usuario usuario, LocalDate dataRetirada,
                      LocalDate dataPrevistaDevolucao, SituacaoEmprestimo situacao) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataRetirada = dataRetirada;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.situacao = situacao;
    }

    public void registrarDevolucao() {
        if (situacao == SituacaoEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Empréstimo já foi devolvido.");
        }

        situacao = SituacaoEmprestimo.DEVOLVIDO;
        livro.registrarDevolucao();
    }

    public boolean estaAtrasado(LocalDate dataAtual) {
        return situacao == SituacaoEmprestimo.ATIVO
                && dataAtual.isAfter(dataPrevistaDevolucao);
    }

    public void marcarComoAtrasado() {
        if (situacao == SituacaoEmprestimo.ATIVO) {
            situacao = SituacaoEmprestimo.ATRASADO;
        }
    }

    public Long getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public SituacaoEmprestimo getSituacao() {
        return situacao;
    }
}
