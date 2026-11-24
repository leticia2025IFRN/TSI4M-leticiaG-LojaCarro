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

### Carros

- `GET /carro` — listar
- `GET /carro/{id}` — buscar por ID
- `POST /carro` — criar
- `PUT /carro/{id}` — atualizar
- `DELETE /carro/{id}` — remover

### Usuários

- `GET /usuario` — listar
- `GET /usuario/{id}` — buscar por ID
- `POST /usuario` — criar
- `PUT /usuario/{id}` — atualizar
- `DELETE /usuario/{id}` — remover

Exemplos de requisições estão em `http/endpoints.http`.

## Frontend

Abra `frontend/login.html` com um servidor local (por exemplo, Live Server na porta 5500).
O CORS aceita por padrão `http://127.0.0.1:5500` e `http://localhost:5500`; para outra origem, defina
`CORS_ORIGINS` com a origem completa (esquema + host + porta, sem caminho e sem barra final).

### Login

O acesso às páginas é protegido por uma sessão no `sessionStorage`. Credenciais de demonstração:

| Usuário | Senha |
|---|---|
| `admin` | `admin123` |
| `vendedor` | `vendas123` |

A autenticação atual é apenas no front-end (protótipo). Para segurança real, ela deve ser substituída
por um endpoint de login no backend com senha armazenada com hash e sessão/token autenticado no servidor.
