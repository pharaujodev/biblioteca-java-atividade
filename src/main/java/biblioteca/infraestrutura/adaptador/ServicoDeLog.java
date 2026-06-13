package biblioteca.infraestrutura.adaptador;

import biblioteca.dominio.evento.DevolucaoRegistradaEvento;
import biblioteca.dominio.evento.EmprestimoRealizadoEvento;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class ServicoDeLog {

    private final Path caminhoArquivo = Path.of("biblioteca.log");

    public void registrarEmprestimo(EmprestimoRealizadoEvento evento) {
        String mensagem = "EMPRESTIMO_REALIZADO | emprestimoId=" + evento.emprestimoId()
                + " | usuarioId=" + evento.usuarioId()
                + " | livroId=" + evento.livroId()
                + " | dataRetirada=" + evento.dataRetirada();

        registrar(mensagem);
    }

    public void registrarDevolucao(DevolucaoRegistradaEvento evento) {
        String mensagem = "DEVOLUCAO_REGISTRADA | emprestimoId=" + evento.emprestimoId()
                + " | dataDevolucao=" + evento.dataDevolucao()
                + " | comAtraso=" + evento.comAtraso();

        registrar(mensagem);
    }

    private void registrar(String mensagem) {
        String linha = LocalDateTime.now() + " | " + mensagem + System.lineSeparator();

        try {
            Files.writeString(
                    caminhoArquivo,
                    linha,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Erro ao registrar log.", e);
        }
    }
}
