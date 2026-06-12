package biblioteca.infraestrutura;
import biblioteca.dominio.Emprestimo;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class EmprestimoRepositorio {
    private Map<Long, Emprestimo> emprestimos = new HashMap<>();

    public void salvar(Emprestimo emprestimo) {
        emprestimos.put(emprestimo.getId(), emprestimo);
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return Optional.ofNullable(emprestimos.get(id));
    }

    public void remover(Long id) {
        emprestimos.remove(id);
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos.values());
    }
}
