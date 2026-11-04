# Docker

Sobe o PostgreSQL 17 usado no desenvolvimento.

```bash
docker compose up -d      # inicia o banco
docker compose down       # para o banco (os dados são mantidos)
docker compose down -v    # para e apaga os dados
```

| Item | Valor |
|---|---|
| Host / porta | `localhost:5432` |
| Banco | `fintrack` |
| Usuário / senha | `postgres` / `example` |

Os dados ficam no volume `postgres_data`. Essas credenciais são só para uso local.