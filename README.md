Projeto Jdev mentoria
=====================
## Objetivo
Este projeto tem como objetivo a criação de uma aplicação web para o gerenciamento de uma loja de produtos.
## Tecnologias
* Java 17
* Spring Boot 3.0.5
* Maven 3.8.2
* Postgres 15
* Lombok
* Mapstruct
* Flyway
* JUnit 5
* Docker
* Angular 16

## Execução
Para executar o projeto, basta executar o comando abaixo na raiz do projeto:
```bash
docker build -t jdev-mentoria .
```
Após a execução do comando acima, basta executar o comando abaixo para subir o container:
```bash
docker run -p 8080:8080 jdev-mentoria
```
