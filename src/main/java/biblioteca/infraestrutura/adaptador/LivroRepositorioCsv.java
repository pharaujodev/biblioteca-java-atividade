package biblioteca.infraestrutura.adaptador;

import biblioteca.dominio.Livro;
import biblioteca.porta.saida.PortaLivroRepositorio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroRepositorioCsv implements PortaLivroRepositorio {

    private Path caminhoArquivo;

    public LivroRepositorioCsv(String caminhoArquivo) {
        this.caminhoArquivo = Path.of(caminhoArquivo);
        criarArquivoSeNecessario();
    }

    @Override
    public void salvar(Livro livro) {
        List<Livro> livros = listarTodos();
        List<Livro> atualizados = new ArrayList<>();
        boolean substituiu = false;

        for (Livro livroAtual : livros) {
            if (livroAtual.getId().equals(livro.getId())) {
                atualizados.add(livro);
                substituiu = true;
            } else {
                atualizados.add(livroAtual);
            }
        }

        if (!substituiu) {
            atualizados.add(livro);
        }

        salvarTodos(atualizados);
    }

    @Override
    public Optional<Livro> buscarPorId(Long id) {
        for (Livro livro : listarTodos()) {
            if (livro.getId().equals(id)) {
                return Optional.of(livro);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Livro> listarTodos() {
        criarArquivoSeNecessario();

        try {
            List<String> linhas = Files.readAllLines(caminhoArquivo);
            List<Livro> livros = new ArrayList<>();

            for (String linha : linhas) {
                if (!linha.isBlank()) {
                    livros.add(converterLinhaParaLivro(linha));
                }
            }

            return livros;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo CSV de livros.", e);
        }
    }

    @Override
    public void remover(Long id) {
        List<Livro> livros = listarTodos();
        List<Livro> atualizados = new ArrayList<>();

        for (Livro livro : livros) {
            if (!livro.getId().equals(id)) {
                atualizados.add(livro);
            }
        }

        salvarTodos(atualizados);
    }

    private void salvarTodos(List<Livro> livros) {
        List<String> linhas = new ArrayList<>();

        for (Livro livro : livros) {
            linhas.add(converterLivroParaLinha(livro));
        }

        try {
            Files.write(caminhoArquivo, linhas, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo CSV de livros.", e);
        }
    }

    private String converterLivroParaLinha(Livro livro) {
        return livro.getId() + ";"
                + livro.getTitulo() + ";"
                + livro.getAutor() + ";"
                + livro.getIsbn() + ";"
                + livro.getQuantidadeDisponivel();
    }

    private Livro converterLinhaParaLivro(String linha) {
        String[] partes = linha.split(";");

        Long id = Long.parseLong(partes[0]);
        String titulo = partes[1];
        String autor = partes[2];
        String isbn = partes[3];
        int quantidadeDisponivel = Integer.parseInt(partes[4]);

        return new Livro(id, titulo, autor, isbn, quantidadeDisponivel);
    }

    private void criarArquivoSeNecessario() {
        try {
            if (!Files.exists(caminhoArquivo)) {
                Files.createFile(caminhoArquivo);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar arquivo CSV de livros.", e);
        }
    }
}
