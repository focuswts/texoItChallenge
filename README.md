# Worst Movie Awards API

## Descrição

A API Worst Movie Awards gerencia filmes, produtores e estúdios, e calcula intervalos entre prêmios consecutivos para produtores.

## Como utilizar o seu proprio CSV
- **Colocar o CSV desejado na pasta resources/data do projeto.**
- **Alterar o valor do file.input dentro do application.yml seguindo o padrao ex: data/nomedoarquivo.csv**
- **Utilizar o endpoint /movies/interval para pegar os valores do objetivo do desafio.**
  
- **Caso desejar utilizar as validacoes dos testes de integracao em conjunto com seu proprio CSV basta alterar o valor do file.input dentro do application-test.yml seguindo o padrao ex: data/nomedoarquivo.csv**
- **O teste responsavel por fazer a validacao do endpoint /interval fica no MovieControllerTest.ShouldGetIntervals

## Endpoints

### Movies

- **Listar todos os filmes**
  - **GET** `/movies`

- **Listar todos os filmes vencedores**
  - **GET** `/movies/winner`

- **Obter intervalo entre prêmios**
  - **GET** `/movies/interval`

- **Obter filme por ID**
  - **GET** `/movies/{id}`
  - **Parâmetros**: `id` (ID do filme)

- **Criar novo filme**
  - **POST** `/movies`
  - **Parâmetros**: 
    ```json
    {
      "title": "string",
      "year": "int",
      "producers": ["array"],
      "studios": ["array"],
      "winner": "boolean"
    }
    ```

- **Atualizar filme**
  - **PUT** `/movies/{id}`
  - **Parâmetros**: `id` (ID do filme), Corpo da requisição igual ao método de criação

- **Deletar filme**
  - **DELETE** `/movies/{id}`
  - **Parâmetros**: `id` (ID do filme)

### Producers

- **Listar todos os produtores**
  - **GET** `/producers`

- **Obter produtor por ID**
  - **GET** `/producers/{id}`
  - **Parâmetros**: `id` (ID do produtor)

- **Criar novo produtor**
  - **POST** `/producers`
  - **Parâmetros**: 
    ```json
    {
      "name": "string"
    }
    ```

- **Atualizar produtor**
  - **PUT** `/producers/{id}`
  - **Parâmetros**: `id` (ID do produtor), Corpo da requisição igual ao método de criação

- **Deletar produtor**
  - **DELETE** `/producers/{id}`
  - **Parâmetros**: `id` (ID do produtor)

### Studios

- **Listar todos os estúdios**
  - **GET** `/studios`

- **Obter estúdio por ID**
  - **GET** `/studios/{id}`
  - **Parâmetros**: `id` (ID do estúdio)

- **Criar novo estúdio**
  - **POST** `/studios`
  - **Parâmetros**: 
    ```json
    {
      "name": "string"
    }
    ```

- **Atualizar estúdio**
  - **PUT** `/studios/{id}`
  - **Parâmetros**: `id` (ID do estúdio), Corpo da requisição igual ao método de criação

- **Deletar estúdio**
  - **DELETE** `/studios/{id}`
  - **Parâmetros**: `id` (ID do estúdio)

## Executando o Projeto

Para rodar o projeto, você precisa ter o Java e o Maven instalados. Use os seguintes comandos para construir e iniciar a aplicação:

```bash
mvn clean install
mvn spring-boot:run
