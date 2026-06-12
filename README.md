# Sistema de Gerenciamento de Biblioteca

Atividade prática da disciplina de Arquiteturas de Software com Java.

## Objetivo

Este projeto tem como objetivo desenvolver um sistema simples de gerenciamento de biblioteca, utilizando Java puro e organização arquitetural em etapas.

A aplicação será evoluída ao longo do desenvolvimento, passando por:

1. Arquitetura em Camadas
2. Arquitetura Hexagonal
3. Comunicação por Eventos

## Estado atual do projeto

Este commit contém a estrutura inicial do projeto e a primeira entidade do domínio: `Livro`.

Também foram adicionadas as primeiras classes de apoio para iniciar a separação entre as camadas:

- `dominio`
- `infraestrutura`
- `aplicacao`
- `apresentacao`

## Estrutura atual

```text
src/
  main/
    java/
      biblioteca/
        dominio/
          Livro.java
        infraestrutura/
          LivroRepositorio.java
        aplicacao/
          LivroServico.java
        apresentacao/
          Main.java
```

## Entidade Livro

A classe `Livro` representa um livro do acervo da biblioteca.

Ela possui os seguintes atributos:

- `id`
- `titulo`
- `autor`
- `isbn`
- `quantidadeDisponivel`

Também possui uma regra de negócio inicial no método `realizarEmprestimo()`, responsável por verificar se existe quantidade disponível antes de diminuir o estoque.

## Separação de responsabilidades

Neste momento, o projeto está organizado da seguinte forma:

- `dominio`: contém a entidade principal e suas regras de negócio.
- `infraestrutura`: contém o repositório em memória usado para armazenar livros.
- `aplicacao`: contém o serviço responsável por coordenar operações relacionadas a livros.
- `apresentacao`: contém a classe `Main`, ponto inicial de execução do sistema.

## Observação sobre o domínio

As classes da camada de domínio não devem depender das camadas de infraestrutura, aplicação ou apresentação.

Neste commit, a classe `Livro` não importa nenhuma classe dessas camadas, mantendo o domínio independente.

## Como compilar e executar pelo terminal

Na raiz do projeto, onde ficam o `README.md` e a pasta `src`, execute:

```bash
javac -d out src/main/java/biblioteca/apresentacao/Main.java
java -cp out biblioteca.apresentacao.Main
```

Neste primeiro commit, o `Main.java` ainda é simples e serve apenas para testar se o projeto está executando corretamente.

## Observação

Em etapas futuras, quando o projeto tiver mais classes sendo usadas diretamente pelo `Main`, pode ser necessário compilar todos os arquivos Java do projeto.

## Próximos passos

Os próximos commits devem adicionar:

- `Usuario`
- `Emprestimo`
- `SituacaoUsuario`
- `SituacaoEmprestimo`
- repositórios restantes
- serviços de aplicação
- fluxo completo de empréstimo e devolução
