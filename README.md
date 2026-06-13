# Sistema de Gerenciamento de Biblioteca

Atividade prática da disciplina de Arquiteturas de Software com Java.

## Objetivo

Este projeto tem como objetivo desenvolver um sistema simples de gerenciamento de biblioteca, utilizando Java puro e organização arquitetural em etapas.

A aplicação está sendo desenvolvida de forma incremental, passando por:

1. Arquitetura em Camadas
2. Arquitetura Hexagonal
3. Comunicação por Eventos

## Estado atual do projeto

Nesta etapa, o projeto foi refatorado para utilizar **Arquitetura Hexagonal**, também conhecida como **Ports and Adapters**.

A principal mudança foi fazer o serviço de empréstimos depender de interfaces, e não diretamente de classes concretas da infraestrutura. Com isso, é possível trocar a forma de armazenamento sem alterar a lógica principal do sistema.

Atualmente o projeto possui:

- entidades principais do domínio;
- portas de entrada e saída;
- serviço de empréstimos usando interfaces;
- adaptadores em memória;
- adaptador CSV para livros;
- adaptador de notificação por console;
- demonstração de troca de adaptador na classe `Main`.

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

        infraestrutura/
          adaptador/
            LivroRepositorioMemoria.java
            LivroRepositorioCsv.java
            UsuarioRepositorioMemoria.java
            EmprestimoRepositorioMemoria.java
            NotificacaoConsole.java

        apresentacao/
          Main.java
```

## Domínio

O pacote `dominio` contém as entidades principais do sistema:

- `Livro`
- `Usuario`
- `Emprestimo`

Também contém os enums:

- `SituacaoUsuario`
- `SituacaoEmprestimo`

As regras básicas continuam nas entidades. Por exemplo, a classe `Livro` possui o método `realizarEmprestimo()`, que verifica a disponibilidade antes de diminuir a quantidade em estoque.

## Portas

As portas representam contratos usados pelo sistema.

### Porta de entrada

A porta de entrada define os casos de uso de empréstimo:

- `realizarEmprestimo`
- `registrarDevolucao`
- `listarEmprestimosAtivos`
- `verificarAtrasos`

Arquivo:

```text
porta/entrada/PortaEmprestimo.java
```

### Portas de saída

As portas de saída representam dependências externas usadas pelo serviço:

- `PortaLivroRepositorio`
- `PortaUsuarioRepositorio`
- `PortaEmprestimoRepositorio`
- `PortaNotificacao`

Essas interfaces permitem que o serviço use repositórios e notificação sem conhecer diretamente suas implementações.

## Serviço

O `EmprestimoServico` implementa a interface `PortaEmprestimo`.

Ele depende das portas de saída, e não dos adaptadores concretos. Isso deixa a lógica de empréstimo desacoplada da infraestrutura.

Exemplo da ideia usada:

```java
PortaLivroRepositorio livroRepositorio;
PortaUsuarioRepositorio usuarioRepositorio;
PortaEmprestimoRepositorio emprestimoRepositorio;
PortaNotificacao notificacao;
```

## Adaptadores

Os adaptadores ficam no pacote `infraestrutura/adaptador`.

Foram implementados:

- `LivroRepositorioMemoria`
- `UsuarioRepositorioMemoria`
- `EmprestimoRepositorioMemoria`
- `LivroRepositorioCsv`
- `NotificacaoConsole`

Os repositórios em memória usam `HashMap`.

O `LivroRepositorioCsv` persiste os livros em um arquivo `livros.csv`, usando recursos da biblioteca padrão do Java.

## Troca de adaptador

A classe `Main` demonstra a troca de adaptador.

Primeiro, o fluxo é executado com o repositório de livros em memória:

```java
PortaLivroRepositorio livroRepositorio = new LivroRepositorioMemoria();
```

Depois, o mesmo fluxo é executado usando o repositório CSV:

```java
PortaLivroRepositorio livroRepositorio = new LivroRepositorioCsv("livros.csv");
```

A lógica do `EmprestimoServico` não precisa ser alterada para essa troca funcionar, pois ele trabalha com a interface `PortaLivroRepositorio`.

## Separação de responsabilidades

O projeto está organizado da seguinte forma:

- `dominio`: entidades e regras básicas de negócio;
- `porta`: interfaces de entrada e saída;
- `servico`: implementação dos casos de uso;
- `infraestrutura`: adaptadores concretos;
- `apresentacao`: classe `Main`, responsável por montar e executar a demonstração.

## Observação sobre o domínio

As classes do pacote `biblioteca.dominio` não devem importar classes de infraestrutura, aplicação ou apresentação.

Nesta versão, o domínio não importa classes de:

- `biblioteca.infraestrutura`
- `biblioteca.apresentacao`
- `biblioteca.servico`

Isso mantém o domínio independente dos detalhes externos.

## Como executar

Abra o projeto em uma IDE Java, como VS Code, IntelliJ IDEA ou Eclipse.

Execute a classe principal:

```text
src/main/java/biblioteca/apresentacao/Main.java
```

A classe `Main` executa uma demonstração com:

1. cadastro de livro;
2. cadastro de usuário;
3. realização de empréstimo;
4. devolução;
5. troca de adaptador entre memória e CSV.

## Decisões de design

A principal decisão desta etapa foi usar interfaces para representar as dependências do serviço.

Com isso, o `EmprestimoServico` não precisa saber se os dados vêm de um `HashMap`, de um arquivo CSV ou de outra fonte. Ele apenas chama os métodos definidos pelas portas.

Essa organização facilita a troca de adaptadores e deixa o código mais próximo da Arquitetura Hexagonal.

## Próximos passos

A próxima etapa será adicionar comunicação por eventos, usando um `EventBus` genérico e consumidores independentes para notificação e log.
