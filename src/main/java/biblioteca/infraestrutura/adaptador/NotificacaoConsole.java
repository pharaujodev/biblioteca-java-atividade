package biblioteca.infraestrutura.adaptador;

import biblioteca.dominio.Emprestimo;
import biblioteca.dominio.Usuario;
import biblioteca.porta.saida.PortaNotificacao;

public class NotificacaoConsole implements PortaNotificacao {

    @Override
    public void notificarAtraso(Usuario usuario, Emprestimo emprestimo) {
        System.out.println("Notificação: usuário " + usuario.getNome()
                + " possui empréstimo em atraso. ID do empréstimo: "
                + emprestimo.getId());
    }
}
