# Sistema de Gerenciamento de Biblioteca

Atividade prática da disciplina de Arquiteturas de Software com Java.

## Objetivo

Este projeto tem como objetivo desenvolver um sistema simples de gerenciamento de biblioteca, utilizando Java puro e organização arquitetural em etapas.

A aplicação será evoluída ao longo do desenvolvimento, passando por:

1. Arquitetura em Camadas
2. Arquitetura Hexagonal
3. Comunicação por Eventos

## Estado atual do projeto

Neste momento, o projeto conclui a primeira versão da Etapa 1, usando Arquitetura em Camadas.

A aplicação já possui:

- entidades principais do domínio;
- enums de situação;
- repositórios em memória;
- serviços da camada de aplicação;
- classe `Main` demonstrando um fluxo básico no console.

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
          UsuarioServico.java
          EmprestimoServico.java

        apresentacao/
          Main.java
```

## Funcionalidades demonstradas

A classe `Main` executa um fluxo simples com:

1. cadastro de livro;
2. cadastro de usuário;
3. realização de empréstimo;
4. listagem de empréstimos ativos;
5. registro de devolução.

## Separação de responsabilidades

O projeto está organizado da seguinte forma:

- `dominio`: contém as entidades e regras básicas de negócio.
- `infraestrutura`: contém os repositórios em memória.
- `aplicacao`: contém os serviços responsáveis pelos casos de uso.
- `apresentacao`: contém a classe `Main`, usada para demonstrar o funcionamento no console.

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

## Próximos passos

Os próximos commits devem evoluir o projeto para a Arquitetura Hexagonal, criando portas e adaptadores para desacoplar os serviços da infraestrutura.
