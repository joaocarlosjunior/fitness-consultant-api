![bd](https://github.com/joaocarlosjunior/personal-gym-api/assets/83256465/6fd07072-ae61-467b-8e70-b20da115fa0d)

<h2 id="rotas">API Endpoints</h2>
Usuário:

| rota                                  | descrição                                                  |
|---------------------------------------|------------------------------------------------------------|
| <kbd>POST /api/v1/users/signup </kbd> | Cria usuário [request e response](#post-user-signup)       |
| <kbd>GET /api/v1/users/{id} </kbd>    | Retorna usuário pelo id [response](#get-user)              |
| <kbd>DELETE /api/v1/users/{id} </kbd> | Deleta usuário pelo id [response](#delete-user)            |
| <kbd>PUT /api/v1/users/{id} </kbd>    | Atualiza usuário pelo id [request e response](#put-user)   |
| <kbd>GET /api/v1/users </kbd>         | Retorna todos usuários [request e response](#get-all-user) |

Grupo muscular:

| rota                                         | descrição                                                               |
|----------------------------------------------|-------------------------------------------------------------------------|
| <kbd>POST /api/v1/muscle-group" </kbd>       | Cria grupo muscular [request e response](#post-muscle-group)            |
| <kbd>GET /api/v1/muscle-group/{id} </kbd>    | Retorna grupo muscular pelo id [response](#get-muscle-group)            |
| <kbd>DELETE /api/v1/muscle-group/{id} </kbd> | Deleta grupo muscular pelo id [response](#delete-muscle-group)          |
| <kbd>PUT /api/v1/muscle-group/{id} </kbd>    | Atualiza grupo muscular pelo id [request e response](#put-muscle-group) |
| <kbd>GET /api/v1/muscle-group </kbd>         | Retorna todos grupo musculares [response](#get-muscle-group)            |

<h3 id="post-user-signup">POST /api/v1/users/signup</h3>

**REQUEST**
```json
{
  "first_name": "João",
  "last_name": "Carlos",
  "email": "joaocarlos@email.com",
  "phone": "7799999559",
  "password": "123456",
  "role": 0
}
```

**RESPONSE**: 201 CREATED
```json
{
  "id": null,
  "firstName": "João",
  "lastName": "Carlos",
  "email": "joaocarlos@email.com",
  "phone": "7799999559",
  "role": "ROLE_USER",
  "created_at": "21/05/2024 16:09",
  "updated_at": null
}
```

<h3 id="get-user">GET /api/v1/users/{id}</h3>

**RESPONSE**: 200 OK
```json
{
  "id": null,
  "firstName": "João",
  "lastName": "Carlos",
  "email": "joaocarlos@email.com",
  "phone": "7799999559",
  "role": "ROLE_USER",
  "created_at": "21/05/2024 16:14",
  "updated_at": null
}
```
<h3 id="delete-user">DELETE /api/v1/users/{id}</h3>

**RESPONSE**: 204 NO CONTENT
<h3 id="put-user">PUT /api/v1/users/{id}</h3>

**REQUEST**
```json
{
  "first_name": "João",
  "last_name": "Carlos Ribas",
  "email": "joaocarlos@email.com",
  "phone": "7799999559"
}
```

**RESPONSE**: 200 OK
```json
{
  "id": null,
  "firstName": "João",
  "lastName": "Carlos Ribas",
  "email": "joaocarlos@email.com",
  "phone": "7799999559",
  "role": null,
  "created_at": "21/05/2024 16:52",
  "updated_at": "21/05/2024 17:01"
}
```

<h3 id="get-all-user">GET /api/v1/users</h3>

**RESPONSE**: 200 OK
```json
[
  {
    "id": 1,
    "firstName": "João",
    "lastName": "Carlos Ribas",
    "email": "joaocarlos@email.com",
    "phone": "7799999559",
    "role": "ROLE_USER",
    "created_at": "21/05/2024 16:52",
    "updated_at": "21/05/2024 17:01"
  },
  {
    "id": 2,
    "firstName": "João",
    "lastName": "Carlos Ribas",
    "email": "joaocarlos01@email.com",
    "phone": "7799999556",
    "role": "ROLE_USER",
    "created_at": "21/05/2024 16:57",
    "updated_at": null
  }
]
```

<h3 id="post-muscle-group">POST /api/v1/muscle-group</h3>

**REQUEST**
```json
{
  "name":"Pantorilha"
}
```

**RESPONSE**: 201 CREATED

<h3 id="get-muscle-group">GET /api/v1/muscle-group/{id}</h3>

**RESPONSE**: 200 OK
```json
{
  "name": "Pantorilha"
}
```

<h3 id="delete-muscle-group">DELETE /api/v1/muscle-group/{id}</h3>

**RESPONSE**: 204 NO CONTENT

<h3 id="put-muscle-group">PUT /api/v1/muscle-group/{id}</h3>

**REQUEST**
```json
{
  "name":"Peitoral"
}
```

**RESPONSE**: 200 OK
```json
{
  "name":"Peitoral"
}
```

<h3 id="get-muscle-group">GET /api/v1/muscle-group</h3>

**RESPONSE**: 200 OK
```json
[
  {
    "name": "Pantorilha"
  },
  {
    "name": "Peitoral"
  }
]
```


