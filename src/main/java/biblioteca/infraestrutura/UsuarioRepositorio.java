package biblioteca.infraestrutura;
import biblioteca.dominio.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class UsuarioRepositorio {
    private Map<Long, Usuario> usuarios = new HashMap<>();
    public void salvar(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }
    public void remover(Long id) {
        usuarios.remove(id);
    }
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

}
