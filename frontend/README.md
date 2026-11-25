# Front-end do LojaCarro

## Como rodar

O front-end usa Vite e espera o back-end em `http://localhost:8080`.

```bash
corepack yarn install
corepack yarn dev
```

Acesse `http://localhost:5173/login.html`.

## Configuração

A URL do back-end é definida por `VITE_API_BASE_URL` (veja `.env.exemple`).
O valor padrão é `http://localhost:8080`.

## Login

Credenciais de demonstração:

| Usuário | Senha |
|---|---|
| `admin` | `admin123` |
| `vendedor` | `vendas123` |

Após o login, o token retornado pelo back-end é enviado no cabeçalho
`Authorization: Bearer <token>` nas requisições ao CRUD de carros.

## Build

```bash
corepack yarn build
```
