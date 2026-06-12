# Sistema de Gerenciamento de Biblioteca

Atividade prática da disciplina de Arquiteturas de Software com Java.

## Objetivo

Este projeto tem como objetivo desenvolver um sistema simples de gerenciamento de biblioteca, utilizando Java puro e organização arquitetural em etapas.

A aplicação será evoluída ao longo do desenvolvimento, passando por:

1. Arquitetura em Camadas
2. Arquitetura Hexagonal
3. Comunicação por Eventos

## Estado atual do projeto

Neste momento, o projeto contém a estrutura inicial da Etapa 1, com o domínio principal e os repositórios em memória.

Foram adicionadas as entidades principais do sistema:

- `Livro`
- `Usuario`
- `Emprestimo`

Também foram adicionados os enums de situação:

- `SituacaoUsuario`
- `SituacaoEmprestimo`

E os repositórios em memória:

- `LivroRepositorio`
- `UsuarioRepositorio`
- `EmprestimoRepositorio`

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

        infraestrutura/
          LivroRepositorio.java
          UsuarioRepositorio.java
          EmprestimoRepositorio.java

        aplicacao/
          LivroServico.java

        apresentacao/
          Main.java
```

## Entidades do domínio

### Livro

Representa um livro do acervo da biblioteca.

Atributos principais:

- `id`
- `titulo`
- `autor`
- `isbn`
- `quantidadeDisponivel`

A classe também possui regras relacionadas ao estoque, como realizar empréstimo e registrar devolução.

### Usuario

Representa um usuário da biblioteca.

Atributos principais:

- `id`
- `nome`
- `email`
- `situacao`

A situação do usuário pode ser:

- `ATIVO`
- `SUSPENSO`

### Emprestimo

Representa um empréstimo de livro para um usuário.

Atributos principais:

- `id`
- `livro`
- `usuario`
- `dataRetirada`
- `dataPrevistaDevolucao`
- `situacao`

A situação do empréstimo pode ser:

- `ATIVO`
- `DEVOLVIDO`
- `ATRASADO`

## Repositórios em memória

Os repositórios armazenam os dados temporariamente em memória usando `HashMap`.

Cada repositório possui os métodos principais:

- `salvar`
- `buscarPorId`
- `listarTodos`
- `remover`

Os métodos `buscarPorId` retornam `Optional`, para representar de forma mais segura a possibilidade de um registro não ser encontrado.

## Separação de responsabilidades

Neste momento, o projeto está organizado da seguinte forma:

- `dominio`: contém as entidades e regras básicas de negócio.
- `infraestrutura`: contém os repositórios em memória.
- `aplicacao`: contém serviços responsáveis por coordenar operações do sistema.
- `apresentacao`: contém a classe `Main`, ponto inicial de execução.

## Observação sobre o domínio

As classes da camada de domínio não devem depender das camadas de infraestrutura, aplicação ou apresentação.

Neste projeto, as classes dentro de `biblioteca.dominio` não importam classes de `biblioteca.infraestrutura` nem de `biblioteca.aplicacao`, mantendo o domínio independente.

## Como executar

Abra o projeto em uma IDE Java, como VS Code, IntelliJ IDEA ou Eclipse.

Execute a classe principal:

```text
src/main/java/biblioteca/apresentacao/Main.java
```

A classe `Main` é o ponto de entrada do sistema.

## Estado da implementação

Este commit ainda não finaliza toda a Etapa 1.

A parte atual cobre:

- entidades principais do domínio;
- enums de situação;
- repositórios em memória.

Ainda falta implementar nos próximos commits:

- `UsuarioServico`
- `EmprestimoServico`
- fluxo completo no `Main`
- cadastro de livro
- cadastro de usuário
- realização de empréstimo
- registro de devolução

## Próximos passos

Os próximos commits devem completar a camada de aplicação e a demonstração no console, finalizando a Etapa 1 da atividade.
