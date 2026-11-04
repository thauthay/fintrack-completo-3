# notification-service

Backend de notificações do FinTrack: cria, lista e marca notificações como lidas.
Java 21 · Spring Boot · Spring Data JPA · PostgreSQL · validação com Bean Validation.

## Como rodar

1. Suba o Postgres (na pasta `docker/`): `docker compose up -d`
2. Neste diretório: `./mvnw spring-boot:run` (porta **8083**)
3. Testes: `./mvnw test`

A tabela `notifications` é criada automaticamente no mesmo banco `fintrack`.

## Endpoints

| Método | Rota | O que faz |
|---|---|---|
| GET | `/api/notifications?unread=true` | lista as notificações, da mais recente para a mais antiga (`unread=true` traz só as não lidas) |
| GET | `/api/notifications/unread-count` | quantidade de não lidas: `{ "count": 2 }` |
| POST | `/api/notifications` | cria `{ "title": "...", "message": "..." }` |
| PATCH | `/api/notifications/{id}/read` | marca como lida (404 se não existir) |

Título (até 120 caracteres) e mensagem (até 500) são obrigatórios. Erros de validação voltam como 400 (`ProblemDetail`).

## Exemplos

```bash
curl -X POST localhost:8083/api/notifications -H 'Content-Type: application/json' \
  -d '{"title":"Gasto alto","message":"Suas despesas com Lazer passaram de R$ 500."}'
curl localhost:8083/api/notifications?unread=true
curl -X PATCH localhost:8083/api/notifications/1/read
```

## Próximos passos

- `auth-service` com JWT e vincular os dados ao usuário
- Gerar notificações no `notification-service` (por exemplo, quando uma despesa passar de certo valor)
- Testes de controller e de integração