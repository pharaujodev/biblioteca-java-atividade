package biblioteca.dominio;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private SituacaoUsuario situacao;

    public Usuario(Long id, String nome, String email, SituacaoUsuario situacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.situacao = situacao;
    }

    public boolean estaAtivo() {
        return situacao == SituacaoUsuario.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public SituacaoUsuario getSituacao() {
        return situacao;
    }
}
