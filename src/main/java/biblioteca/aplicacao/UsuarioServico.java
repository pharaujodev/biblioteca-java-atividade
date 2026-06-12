package biblioteca.aplicacao;

import biblioteca.dominio.Usuario;
import biblioteca.infraestrutura.UsuarioRepositorio;

import java.util.List;

public class UsuarioServico {

    private UsuarioRepositorio usuarioRepositorio;

    public UsuarioServico(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarioRepositorio.salvar(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepositorio.listarTodos();
    }

    public void removerUsuario(Long id) {
        usuarioRepositorio.remover(id);
    }
}
