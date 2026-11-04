# finance-service

Backend do FinTrack: categorias, transações e resumo do dashboard.
Java 21 · Spring Boot · Spring Data JPA · PostgreSQL · validação com Bean Validation.

## Como rodar

1. Suba o Postgres (na pasta `docker/`): `docker compose up -d`
2. Neste diretório: `./mvnw spring-boot:run` (porta **8081**)
3. Testes: `./mvnw test`

Ao iniciar, se a tabela de categorias estiver vazia, são criadas Alimentação, Transporte, Moradia (despesas) e Salário (receita).

## Endpoints

| Método | Rota | O que faz |
|---|---|---|
| GET | `/api/categories` | lista categorias |
| POST | `/api/categories` | cria `{ "name": "Lazer", "type": "expense" }` |
| PUT | `/api/categories/{id}` | edita |
| DELETE | `/api/categories/{id}` | exclui (409 se tiver transações) |
| GET | `/api/transactions?type=&categoryId=&search=` | lista com filtros opcionais |
| POST | `/api/transactions` | cria |
| PUT | `/api/transactions/{id}` | edita |
| DELETE | `/api/transactions/{id}` | exclui |
| GET | `/api/dashboard/summary` | saldo, receitas, despesas e 5 últimas transações |

Corpo de uma transação:

```json
{ "description": "Supermercado", "amount": 150.00, "type": "expense", "categoryId": 1, "date": "2026-10-28" }
```

## Regras de negócio

- O tipo da transação precisa ser igual ao tipo da categoria (422 caso contrário).
- Nome de categoria é único, sem diferenciar maiúsculas/minúsculas (409).
- Não dá para excluir categoria com transações, nem mudar o tipo dela (409).
- Valor deve ser maior que zero; erros de validação voltam como 400 (`ProblemDetail`).

## Exemplos

```bash
curl localhost:8081/api/categories
curl -X POST localhost:8081/api/transactions -H 'Content-Type: application/json' \
  -d '{"description":"Salário","amount":2876,"type":"income","categoryId":4,"date":"2026-10-28"}'
curl localhost:8081/api/dashboard/summary
```

## Próximos passos

- Ligar o frontend (hoje usa dados fixos) e buscar categorias da API
- `auth-service` com JWT e vincular dados ao usuário
- `api-gateway` na frente dos serviços
