package biblioteca.infraestrutura.adaptador;

import biblioteca.dominio.Emprestimo;
import biblioteca.porta.saida.PortaEmprestimoRepositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmprestimoRepositorioMemoria implements PortaEmprestimoRepositorio {

    private Map<Long, Emprestimo> emprestimos = new HashMap<>();

    @Override
    public void salvar(Emprestimo emprestimo) {
        emprestimos.put(emprestimo.getId(), emprestimo);
    }

    @Override
    public Optional<Emprestimo> buscarPorId(Long id) {
        return Optional.ofNullable(emprestimos.get(id));
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos.values());
    }

    @Override
    public void remover(Long id) {
        emprestimos.remove(id);
    }
}
