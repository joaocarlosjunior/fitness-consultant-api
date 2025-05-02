![bd](https://github.com/joaocarlosjunior/fitness-consultant-api/assets/83256465/0b511b8c-c3a1-4b66-98af-de9845ba8a9e)

<h1 align="center" style="font-weight: bold;">API - Sistema de Gerenciamento de Treinos para Personal Trainer</h1>
<p align="center">
  <a href="#contexto">Contexto</a> • 
  <a href="#funcionalidades">Funcionalidades</a> • 
  <a href="#tecnologias">Tecnologias</a> • 
  <a href="#instalação">Instalação</a> •
  <a href="#endpoints-api">API Endpoints</a> •
  <a href="#licença">Licença</a>

</p>
<p align="center">
    <b>Este repositório contém a API desenvolvida para um sistema de gerenciamento de treinos de personal trainer. 
O objetivo principal é facilitar o cadastro, organização e acompanhamento dos treinos dos clientes de forma estruturada e automatizada, 
substituindo o uso de planilha manual.</b>
</p>

## Contexto

A ideia surgiu a partir da experiência de um personal trainer que utilizava planilhas para montar os treinos
personalizados de seus clientes e, em seguida, enviava esses arquivos manualmente.
Apesar de funcional, esse método exigia muito tempo e esforço: a cada novo treino, seja para um cliente novo ou para
atualização de um treino já existente, era necessário editar toda a planilha novamente,
ajustar exercícios, cargas e formatações, o que tornava o processo lento, repetitivo e sujeito a erros.
Diante disso, surgiu a proposta de desenvolver um sistema que digitalizasse e otimizasse essa rotina,
tornando o gerenciamento dos treinos mais ágil, prático e escalável.

![Image](https://github.com/user-attachments/assets/b9001756-d7a5-4b15-bc15-c455e2133d0c)

## Funcionalidades

- Cadastro e autenticação de usuários (administrador e usuários)
- Registro e organização de treinos por divisão(ABCDE) e grupo muscular
- Cadastro de exercícios com:
    - Nome exercício
    - Método de treino
    - Séries
    - Repetições
    - Carga inicial e carga final
- Criação e edição de periodizações (mesociclos)
- Classificação de exercícios por grupos musculares
- Regras e recomendações pré e pós-treino

### Modelagem Banco

![bd](https://github.com/joaocarlosjunior/fitness-consultant-api/assets/83256465/0b511b8c-c3a1-4b66-98af-de9845ba8a9e)

## Tecnologias

- [Spring Boot 3.4.4](https://spring.io/projects/spring-boot)
- [PostgreSQL 15.6](https://www.postgresql.org/docs/release/15.6/)
- [Redis](https://redis.io/pt/)
- [Docker](https://www.docker.com/)

## Instalação

#### Pré Requisitos

- Java 17
- Docker e Docker Compose
- GIT

#### Executar

Clone o repositorio:

```
git clone git@github.com:joaocarlosjunior/fitness-consultant-api.git
```

Navegue até a pasta de destino:

```
cd fitness-consultant-api
```

Contrua o container docker:

```
docker compose up --build
```

Execute o comando para iniciar a aplicação(LINUX):

```
./mvnw spring-boot:run
```

Execute o comando para iniciar a aplicação(WINDOWS):

```
mvnw.cmd spring-boot:run
```

#### SWAGGER

Link do Swagger:

```
http://localhost:8080/fitness-consultant-documentation
```

## Endpoints API

### Rotas sem necessidade de autenticação e autorização:

**Endpoint para recursos relacionado a Autenticação e Autorização**

| rota                               | descrição                                                |
|------------------------------------|----------------------------------------------------------|
| <kbd>POST /api/v1/auth/login</kbd> | Autentica usuário [request e response](#post-user-login) |

### Rotas role USER e ADMIN:

**Endpoints para recursos relacionado ao Usuário**

| rota                                            | descrição                                                                                                    |
|-------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| <kbd>GET /api/v1/users/{id}</kbd>               | Retornar usuário pelo id [request e response](#get-user)                                                     |
| <kbd>DELETE /api/v1/users/{id}</kbd>            | Deleta Usuário pelo id [request e response](#delete-user)                                                    |
| <kbd>PUT /api/v1/users/{id}</kbd>               | Atualiza Info Usuário pelo id [request e response](#put-user)                                                |
| <kbd>GET /api/v1/users/user/{id}/workouts</kbd> | Retornar todas informações de treino do usuário pelo id usuario [request e response](#get-all-training-user) |

**Endpoints para recursos relacionado a Treinos**

| rota                                 | descrição                                                  |
|--------------------------------------|------------------------------------------------------------|
| <kbd>GET /api/v1/workouts/{id}</kbd> | Recupera Treino pelo id [request e response](#get-workout) |

### Rotas role ADMIN:

**Endpoints para recursos relacionado Usuário**

| rota                                | descrição                                                   |
|-------------------------------------|-------------------------------------------------------------|
| <kbd>POST /api/v1/auth/signup</kbd> | Cadastro de usuário [request e response](#post-user-signup) |

| rota                         | descrição                                                                 |
|------------------------------|---------------------------------------------------------------------------|
| <kbd>GET /api/v1/users</kbd> | Retornar todos usuários ativos [request e response](#get-all-active-user) |

**Endpoints para recursos relacionado a Nome de Exercício**

| rota                                         | descrição                                                                         |
|----------------------------------------------|-----------------------------------------------------------------------------------|
| <kbd>POST /api/v1/exercice-name</kbd>        | Cadastro nome exercício [request e response](#post-exercisename)                  |
| <kbd>GET /api/v1/exercice-name</kbd>         | Recupera todos os nomes de exercicios [request e response](#get-all-exercisename) |
| <kbd>DELETE /api/v1/exercice-name/{id}</kbd> | Deleta nome de exercício pelo id [request e response](#delete-exercisename)       |
| <kbd>PUT /api/v1/exercice-name/{id}</kbd>    | Atualiza nome exercício pelo id [request e response](#put-exercisename)           |
| <kbd>GET /api/v1/exercice-name/{id}</kbd>    | Recupera nome exercício pelo id [request e response](#get-exercisename)           |

**Endpoints para recursos relacionado a Exercícios**

| rota                                           | descrição                                                                           |
|------------------------------------------------|-------------------------------------------------------------------------------------|
| <kbd>POST /api/v1/exercises</kbd>              | Cadastro exercício [request e response](#post-exercise)                             |
| <kbd>DELETE /api/v1/exercises/{id}</kbd>       | Deleta exercício pelo id [request e response](#delete-exercise)                     |
| <kbd>PUT /api/v1/exercises/{id}</kbd>          | Atualiza exercício pelo id [request e response](#put-exercise)                      |
| <kbd>GET /api/v1/exercises/training/{id}</kbd> | Recupera todos exercícios pelo id de treino [request e response](#get-all-exercise) |

**Endpoints para recursos relacionado a Grupo Muscular**

| rota                                         | descrição                                                                     |
|----------------------------------------------|-------------------------------------------------------------------------------|
| <kbd>POST /api/v1/muscle-groups</kbd>        | Cadastro grupo muscular [request e response](#post-musclegroup)               |
| <kbd>DELETE /api/v1/muscle-groups/{id}</kbd> | Deleta grupo muscular pelo id [request e response](#delete-musclegroup)       |
| <kbd>PUT /api/v1/muscle-groups/{id}</kbd>    | Atualiza grupo muscular pelo id [request e response](#put-musclegroup)        |
| <kbd>GET /api/v1/muscle-groups</kbd>         | Recupera todos os grupo musculares [request e response](#get-all-musclegroup) |
| <kbd>GET /api/v1/muscle-groups/{id}</kbd>    | Recupera grupo muscular pelo id [request e response](#get-musclegroup)        |

**Endpoints para recursos relacionado a Periodização(Mesociclos)**

| rota                                            | descrição                                                                              |
|-------------------------------------------------|----------------------------------------------------------------------------------------|
| <kbd>POST /api/v1/periodizations</kbd>          | Cadastra Periodização [request e response](#post-periodization)                        |
| <kbd>DELETE /api/v1/periodizations/{id}</kbd>   | Deleta Periodização pelo id [request e response](#delete-periodization)                |
| <kbd>PUT /api/v1/periodizations/{id}</kbd>      | Atualiza Periodização pelo id [request e response](#put-periodization)                 |
| <kbd>GET /api/v1/periodizations</kbd>           | Recupera todos os Periodizações [request e response](#get-all-periodization)           |
| <kbd>GET /api/v1/periodizations/user/{id}</kbd> | Recupera Periodização pelo id de usuário [request e response](#get-user-periodization) |
| <kbd>GET /api/v1/periodizations/{id}</kbd>      | Recupera Periodização pelo id [request e response](#get-periodization)                 |

**Endpoints para recursos relacionado a Treinos**

| rota                                               | descrição                                                                                           |
|----------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| <kbd>POST /api/v1/workouts</kbd>                   | Cadastro de Treino [request e response](#post-workout)                                              |
| <kbd>DELETE /api/v1/workouts/{id}</kbd>            | Deleta Treino pelo id [request e response](#delete-workout)                                         |
| <kbd>PUT /api/v1/workouts/{id}</kbd>               | Atualiza Treino pelo id [request e response](#put-workout)                                          |
| <kbd>GET /api/v1/workouts/periodization/{id}</kbd> | Recupera todos os treinos pelo id da periodização [request e response](#get-workouts-periodization) |

<h3 id="post-user-login">POST /api/v1/auth/login</h3>

#### REQUEST

Body:

```json
{
  "email": "emailcliente@email.com",
  "password": "Senha Cliente"
}
```

#### RESPONSE

*200 ok*

```json
{
  "token": "token JWT"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "Mensagem informando qual erro"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="post-user-signup">POST /api/v1/auth/signup</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Body:

```json
{
  "firstName": "Nome Cliente",
  "lastName": "Sobrenome Cliente",
  "email": "emailcliente@email.com",
  "phone": "73999999999",
  "role": 0
  //0: User 1: Admin
}
```

**RESPONSE**

*201 Created*
<br>**No Body**
<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "Mensagem informando qual erro"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="post-exercisename">POST /api/v1/exercise-name</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Body:

```json
{
  "exerciseName": "Nome do Exericio",
  "idMuscleGroup": 9007199254740991
}
```

**RESPONSE**

*201 Created*
<br>**No Body**
<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="get-all-exercisename">GET /api/v1/exercise-name</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idExerciseName": 9007199254740991,
    "exerciseName": "Nome do Exercicio",
    "muscleGroup": "Nome do Grupo Muscular"
  }
]

```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="delete-exercisename">DELETE /api/v1/exercise-name/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*
<br>NoBody

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="put-exercisename">PUT /api/v1/exercise-name/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

Body:

```json
{
  "exerciseName": "Nome Exercício",
  "idMuscleGroup": 9007199254740991
}
```

**RESPONSE**

*200 Ok*

```json
{
  "idExerciseName": 9007199254740991,
  "exerciseName": "Nome Exercicio",
  "muscleGroup": "Nome Grupo Muscular"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="get-exercisename">GET /api/v1/exercise-name/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
{
  "idExerciseName": 9007199254740991,
  "exerciseName": "Nome Exercicio",
  "muscleGroup": "Nome Grupo Muscular"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="post-exercise">POST /api/v1/exercise</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Body:

```json
{
  "idTraining": 9007199254740991,
  "series": 1073741824,
  "repetitions": "string",
  "initialLoad": 1073741824,
  "finalLoad": 1073741824,
  "method": "string",
  "exerciseName": 9007199254740991
}
```

**RESPONSE**

*201 Created*
<br>**No Body**
<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="delete-exercise">DELETE /api/v1/exercise/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*
<br>NoBody

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="put-exercise">PUT /api/v1/exercise/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

Body:

```json
{
  "idTraining": 9007199254740991,
  "series": 1073741824,
  "repetitions": "string",
  "initialLoad": 1073741824,
  "finalLoad": 1073741824,
  "method": "string",
  "exerciseName": 9007199254740991
}
```

**RESPONSE**

*200 Ok*

```json
{
  "idExercise": 9007199254740991,
  "series": 1073741824,
  "repetitions": "string",
  "initialLoad": 1073741824,
  "finalLoad": 1073741824,
  "method": "string",
  "exerciseName": "string"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="get-all-exercise">GET /api/v1/exercise-name/training/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
Id treino
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idExercise": 9007199254740991,
    "series": 1073741824,
    "repetitions": "string",
    "initialLoad": 1073741824,
    "finalLoad": 1073741824,
    "method": "string",
    "exerciseName": "string"
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="post-musclegroup">POST /api/v1/muscle-groups</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Body:

```json
{
  "name": "Nome do grupo muscular"
}
```

**RESPONSE**

*201 Created*
<br>**No Body**
<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="delete-musclegroup">DELETE /api/v1/muscle-groups/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*204 No Content*
<br>NoBody

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="put-musclegroup">PUT /api/v1/muscle-groups/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

Body:

```json
{
  "name": "Nome do grupo muscular"
}
```

**RESPONSE**

*200 Ok*

```json
{
  "idMuscleGroup": 9007199254740991,
  "name": "Nome do grupo muscular"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="get-all-musclegroup">GET /api/v1/muscle-groups</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idMuscleGroup": 9007199254740991,
    "name": "string"
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="get-musclegroup">GET /api/v1/muscle-groups/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
{
  "idMuscleGroup": 9007199254740991,
  "name": "Nome Grupo Muscular"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="post-periodization">POST /api/v1/periodizations</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Body:

```json
{
  "name": "Nome da Periodização",
  "numberWeeks": 1073741824,
  "idUser": 9007199254740991
}
```

**RESPONSE**

*201 Created*

```json
{
  "idPeriodization": 9007199254740991,
  "name": "Nome da Periodização",
  "numberWeeks": 1073741824,
  "startDate": "Data de início",
  "createdAt": "Data de criação",
  "updatedAt": "Data de atualização"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="delete-periodization">DELETE /api/v1/periodizations/{id}</h3>

**REQUEST**

Header:

```jsonOk
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*204 No Content*
<br>NoBody

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="put-periodization">PUT /api/v1/periodizations/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

Body:

```json
{
  "name": "Nome da Periodização",
  "numberWeeks": 1073741824
}
```

**RESPONSE**

*200 Ok*

```json
{
  "idPeriodization": 9007199254740991,
  "name": "Nome da Periodização",
  "numberWeeks": 1073741824,
  "startDate": "Data de início",
  "createdAt": "Data de criação",
  "updatedAt": "Data de atualização"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="get-all-periodization">GET /api/v1/periodizations</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idPeriodization": 9007199254740991,
    "name": "Nome da Periodização",
    "numberWeeks": 1073741824,
    "startDate": "Data de início",
    "createdAt": "Data de criação",
    "updatedAt": "Data de atualização"
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="get-user-periodization">GET /api/v1/periodizations/user/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idPeriodization": 9007199254740991,
    "name": "Nome da Periodização",
    "numberWeeks": 1073741824,
    "startDate": "Data de início",
    "createdAt": "Data de criação",
    "updatedAt": "Data de atualização"
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="get-periodization">GET /api/v1/periodizations/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
{
  "idPeriodization": 9007199254740991,
  "name": "Nome da Periodização",
  "numberWeeks": 1073741824,
  "startDate": "Data de início",
  "createdAt": "Data de criação",
  "updatedAt": "Data de atualização"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="get-user">GET /api/v1/users/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
{
  "idUser": 9007199254740991,
  "firstName": "Nome Usuário",
  "lastName": "Sobrenome Usuário",
  "email": "emailusuario@email.com",
  "phone": "77999999999",
  "role": "ROLE_USER",
  "createdAt": "string",
  "updatedAt": "string",
  "active": true
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="delete-user">DELETE /api/v1/users/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*204 No Content*
<br>NoBody

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="put-user">PUT /api/v1/users/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

Body:

```json
{
  "firstName": "Nome Usuário",
  "lastName": "Sobrenome Usuário",
  "email": "emailusuario@email.com",
  "phone": "77999999999"
}
```

**RESPONSE**

*200 Ok*

```json
{
  "firstName": "Nome Usuário",
  "lastName": "Sobrenome Usuário",
  "email": "emailusuario@email.com",
  "phone": "77999999999",
  "role": "ROLE_USER",
  "createdAt": "string",
  "updatedAt": "string",
  "active": true
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="get-all-training-user">GET /api/v1/users/user/{id}/workouts</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable
<br>Id usuário

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "id": 9007199254740991,
    "name": "Nome da Periodização",
    "number_weeks": 1073741824,
    "trainings": [
      {
        "id": 9007199254740991,
        "training_name": "Nome do Treino",
        "training_type": "Tipo de Treino",
        "exercises": [
          {
            "id": 9007199254740991,
            "exercise_name": "Nome do exercício",
            "series": 1073741824,
            "repetitions": "string",
            "method_exercise": "string",
            "initial_load": 1073741824,
            "final_load": 1073741824
          }
        ]
      }
    ]
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="get-all-active-user">GET /api/v1/users</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idUser": 9007199254740991,
    "firstName": "Nome Usuário",
    "lastName": "Sobrenome Usuário",
    "email": "emailusuario@email.com",
    "phone": "77999999999",
    "role": "ROLE_USER",
    "createdAt": "string",
    "updatedAt": "string",
    "active": true
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="get-workout">GET /api/v1/workouts/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
{
  "idTraining": 9007199254740991,
  "idPeriodization": 9007199254740991,
  "trainingType": "A",
  //A, B, C, D, E
  "trainingName": "Nome do treino",
  "createdAt": "Data de criação treino",
  "updatedAt": "Data de atualização treino"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

<h3 id="post-workout">POST /api/v1/workouts</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Body:

```json
{
  "idPeriodization": 9007199254740991,
  "trainingType": 0,
  //0: A, 1:B, 2:C, 3:D, 4:E
  "trainingName": "Nome do treino"
}
```

**RESPONSE**

*201 Created*

```json
{
  "idTraining": 9007199254740991,
  "idPeriodization": 9007199254740991,
  "trainingType": "A",
  //A, B, C, D, E
  "trainingName": "Nome do treino",
  "createdAt": "Data de criação treino",
  "updatedAt": "Data de atualização treino"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="delete-workout">DELETE /api/v1/workouts/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

**RESPONSE**

*204 No Content*
<br>NoBody

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="put-workout">PUT /api/v1/workouts/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable

```
name: id
Type: Integer
```

Body:

```json
{
  "idPeriodization": 9007199254740991,
  "trainingType": 0,
  //0: A, 1:B, 2:C, 3:D, 4:E
  "trainingName": "Nome do treino"
}
```

**RESPONSE**

*200 Ok*

```json
{
  "idTraining": 9007199254740991,
  "idPeriodization": 9007199254740991,
  "trainingType": "A",
  //A, B, C, D, E
  "trainingName": "Nome do treino",
  "createdAt": "Data de criação treino",
  "updatedAt": "Data de atualização treino"
}
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 409, 500
}
```

<h3 id="#get-workouts-periodization">GET /api/v1/workouts/periodization/{id}</h3>

**REQUEST**

Header:

```json
{
  "Authorization": "Bearer tokenJWT"
}
```

Parameters:
<br>Path Variable
Id Periodização

```
name: id
Type: Integer
```

**RESPONSE**

*200 Ok*

```json
[
  {
    "idTraining": 9007199254740991,
    "idPeriodization": 9007199254740991,
    "trainingType": "A",
    //A, B, C, D, E
    "trainingName": "Nome do treino",
    "createdAt": "Data de criação treino",
    "updatedAt": "Data de atualização treino"
  }
]
```

<br>*400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error*

```json
{
  "errors": [
    "string"
  ],
  "code": 400
  //401, 403, 404, 500
}
```

## Licença

[MIT](https://github.com/joaocarlosjunior/fitness-consultant-api/blob/main/LICENSE)


