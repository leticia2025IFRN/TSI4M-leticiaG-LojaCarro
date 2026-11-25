# Atividade Laboral, LojaCarro

Este projeto faz parte de uma atividade em sala para a disciplina de Processo de Desenvolvimento e Implantação de Sistemas Web.

## Requisitos

- Java 17
- Maven (use o wrapper `./mvnw`)

## Perfis

| Perfil | Banco | Como usar |
|---|---|---|
| `test` (padrão) | H2 em memória | `./mvnw spring-boot:run` |
| `dev` | MySQL local | `APP_PROFILE=dev ./mvnw spring-boot:run` |
| `prod` | MySQL | `APP_PROFILE=prod` + variáveis `DB_URL`, `DB_USER`, `DB_PASSWORD` |

Para o perfil `dev`, o padrão é `jdbc:mysql://localhost:3306/carros` com usuário/senha `root/root`.
Esses valores podem ser sobrescritos por `DB_URL`, `DB_USER` e `DB_PASSWORD`.

O perfil `test` habilita o console do H2 em `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:carros`, usuário/senha: `test/test`).

## Executar

```bash
./mvnw spring-boot:run
```

## Testes

```bash
./mvnw test
```

## Empacotar

```bash
./mvnw clean package
```

## Endpoints

### Autenticação

- `POST /auth/login` — autenticar e obter token
- `POST /auth/logout` — invalidar token

### Carros (requer `Authorization: Bearer <token>`)

- `GET /carro` — listar
- `GET /carro/{id}` — buscar por ID
- `POST /carro` — criar
- `PUT /carro/{id}` — atualizar
- `DELETE /carro/{id}` — remover

### Usuários (requer `Authorization: Bearer <token>`)

- `GET /usuario` — listar
- `GET /usuario/{id}` — buscar por ID
- `POST /usuario` — criar
- `PUT /usuario/{id}` — atualizar
- `DELETE /usuario/{id}` — remover

Exemplos de requisições estão em `http/endpoints.http`.

## Frontend

O front-end usa Vite e deve rodar na porta 5173. O CORS já aceita por padrão
`http://127.0.0.1:5173` e `http://localhost:5173`; para outra origem, defina `CORS_ORIGINS`
com a origem completa (esquema + host + porta, sem caminho e sem barra final).

```bash
cd frontend
corepack yarn install
corepack yarn dev
```

A URL da API é lida de `VITE_API_BASE_URL` (padrão `http://localhost:8080`).
Depois acesse `http://localhost:5173/login.html`.

### Login

A autenticação é feita no back-end (`POST /auth/login`), que valida a senha armazenada com hash BCrypt
e devolve um token. O token fica no `sessionStorage` e é enviado no cabeçalho `Authorization` das
requisições a `/carro` e `/usuario`. Os usuários de demonstração são criados automaticamente na inicialização:

| Usuário | Senha |
|---|---|
| `admin` | `admin123` |
| `vendedor` | `vendas123` |

Os tokens ficam em memória no servidor: ao reiniciar o back-end, é necessário fazer login novamente.
