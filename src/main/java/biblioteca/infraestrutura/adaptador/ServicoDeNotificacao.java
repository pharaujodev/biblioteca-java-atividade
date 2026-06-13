package biblioteca.infraestrutura.adaptador;

import biblioteca.dominio.evento.EmprestimoRealizadoEvento;

public class ServicoDeNotificacao {

    public void notificarEmprestimoRealizado(EmprestimoRealizadoEvento evento) {
        System.out.println("Notificação de empréstimo: usuário " + evento.usuarioId()
                + " realizou o empréstimo " + evento.emprestimoId()
                + " do livro " + evento.livroId()
                + ". Data prevista de devolução: "
                + evento.dataRetirada().plusDays(7) + ".");
    }
}
