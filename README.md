# Sistema de Gerenciamento de Biblioteca

Atividade prática da disciplina de Arquiteturas de Software com Java.

## Objetivo

Este projeto tem como objetivo desenvolver um sistema simples de gerenciamento de biblioteca, utilizando Java puro e organização arquitetural em etapas.

A aplicação está sendo desenvolvida de forma incremental, passando por:

1. Arquitetura em Camadas
2. Arquitetura Hexagonal
3. Comunicação por Eventos

## Estado atual do projeto

Nesta etapa, o projeto foi estendido com comunicação por eventos usando o padrão Publisher/Subscriber.

Agora o `EmprestimoServico` publica eventos quando um empréstimo é realizado e quando uma devolução é registrada. Os consumidores desses eventos são registrados no `Main`, sem chamada direta dentro do serviço.

## Estrutura atual

```text
src/
  main/
    java/
      biblioteca/
        dominio/
          Livro.java
          Usuario.java
          Emprestimo.java
          SituacaoUsuario.java
          SituacaoEmprestimo.java
          evento/
            EmprestimoRealizadoEvento.java
            DevolucaoRegistradaEvento.java

        porta/
          entrada/
            PortaEmprestimo.java
          saida/
            PortaLivroRepositorio.java
            PortaUsuarioRepositorio.java
            PortaEmprestimoRepositorio.java
            PortaNotificacao.java

        servico/
          EmprestimoServico.java

        evento/
          EventBus.java

        infraestrutura/
          adaptador/
            LivroRepositorioMemoria.java
            LivroRepositorioCsv.java
            UsuarioRepositorioMemoria.java
            EmprestimoRepositorioMemoria.java
            NotificacaoConsole.java
            ServicoDeNotificacao.java
            ServicoDeLog.java


        apresentacao/
          Main.java
```

## Eventos

Foram criados dois eventos no pacote `dominio/evento`:

- `EmprestimoRealizadoEvento`
- `DevolucaoRegistradaEvento`

Esses eventos carregam os dados básicos da operação realizada.

## EventBus

O `EventBus<T>` fica no pacote `biblioteca.evento` e é genérico e permite:

- assinar consumidores;
- publicar eventos;
- acionar todos os consumidores cadastrados.

## Consumidores

Foram criados dois consumidores principais:

- `ServicoDeNotificacao`: consome `EmprestimoRealizadoEvento` e exibe uma notificação no console.
- `ServicoDeLog`: consome `EmprestimoRealizadoEvento` e `DevolucaoRegistradaEvento`, registrando as operações em `biblioteca.log` com timestamp.

## Desacoplamento

O `EmprestimoServico` não importa `ServicoDeNotificacao` nem `ServicoDeLog`.

A relação entre publicador e consumidores acontece no `Main`, por meio do `EventBus`.

## Como executar

Abra o projeto em uma IDE Java, como VS Code, IntelliJ IDEA ou Eclipse.

Execute a classe principal:

```text
src/main/java/biblioteca/apresentacao/Main.java
```

Ao executar, o sistema demonstra:

1. cadastro de livro;
2. cadastro de usuário;
3. realização de empréstimo;
4. publicação de evento de empréstimo;
5. notificação automática;
6. registro de log;
7. devolução;
8. publicação de evento de devolução;
9. registro de log da devolução.

## Dificuldades encontradas

Uma dificuldade foi entender como separar as responsabilidades na Arquitetura Hexagonal, principalmente para que o serviço dependesse apenas de interfaces e não de classes concretas da infraestrutura.

Outra dificuldade foi integrar os eventos sem criar dependência direta entre o serviço de empréstimos e os consumidores. Para resolver isso, foi criado um EventBus genérico, e os consumidores passaram a ser registrados na classe Main.
