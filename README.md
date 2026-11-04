# FinTrack

Aplicação web de finanças pessoais: cadastro de categorias e transações (receitas e despesas) e um dashboard com saldo, total de receitas, total de despesas e as últimas movimentações.

> **Status:** em desenvolvimento. O `finance-service`, o `notification-service`, o `api-gateway` e o frontend estão implementados. O `auth-service` é apenas o esqueleto do projeto.

## Stack

| Camada | Tecnologias |
|---|---|
| Frontend | React 19, TypeScript, Vite, React Router |
| Backend | Java 21, Spring Boot, Spring Data JPA, Bean Validation, Spring Cloud Gateway |
| Banco de dados | PostgreSQL 17 (via Docker) |
| Testes | JUnit e Mockito (testes unitários dos services) |

## Estrutura

```
fintrack/
├── frontend/                  # SPA em React + TypeScript
├── backend/
│   ├── api-gateway/           # ponto de entrada único (porta 8080)
│   ├── finance-service/       # categorias, transações e dashboard (porta 8081)
│   ├── notification-service/  # notificações (porta 8083)
│   └── auth-service/          # esqueleto, ainda sem implementação
└── docker/
    └── docker-compose.yml     # PostgreSQL para desenvolvimento
```

## Como rodar

Pré-requisitos: Java 21, Node.js e Docker.

**1. Banco de dados**

```bash
cd docker
docker compose up -d
```

**2. Backend** (porta 8081)

```bash
cd backend/finance-service
./mvnw spring-boot:run
```


**3. Notification service e API gateway** (opcional, em terminais separados)

```bash
cd backend/notification-service && ./mvnw spring-boot:run   # porta 8083
cd backend/api-gateway && ./mvnw spring-boot:run            # porta 8080
```

**4. Frontend** (porta 5173)

```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

Abra http://localhost:5173.

O frontend usa o finance-service direto (`http://localhost:8081/api`). Para passar pelo gateway, troque para `VITE_API_URL=http://localhost:8080/api` no `.env`.

### Testes

```bash
cd backend/finance-service && ./mvnw test
cd backend/notification-service && ./mvnw test
cd backend/api-gateway && ./mvnw test
```

## API

Principais rotas (pelo gateway na porta 8080, ou direto em cada serviço):

| Método | Rota | Descrição |
|---|---|---|
| GET / POST | `/api/categories` | lista e cria categorias |
| PUT / DELETE | `/api/categories/{id}` | edita e exclui categoria |
| GET / POST | `/api/transactions` | lista (filtros opcionais `type`, `categoryId`, `search`) e cria transações |
| PUT / DELETE | `/api/transactions/{id}` | edita e exclui transação |
| GET | `/api/dashboard/summary` | saldo, receitas, despesas e 5 últimas transações |
| GET / POST | `/api/notifications` | lista (filtro opcional `unread=true`) e cria notificações |
| PATCH | `/api/notifications/{id}/read` | marca a notificação como lida |
| GET | `/api/notifications/unread-count` | quantidade de notificações não lidas |

A documentação completa de cada serviço está em [`finance-service`](backend/finance-service/README.md), [`notification-service`](backend/notification-service/README.md) e [`api-gateway`](backend/api-gateway/README.md).

## Regras de negócio

- O tipo da transação precisa ser igual ao tipo da categoria (erro 422 caso contrário).
- O nome da categoria é único, sem diferenciar maiúsculas de minúsculas (erro 409).
- Não é possível excluir uma categoria que tenha transações, nem alterar o tipo dela (erro 409).
- O valor da transação deve ser maior que zero. Erros de validação retornam 400 no formato `ProblemDetail`.

## Roadmap

- [ ] `auth-service` com autenticação JWT e dados vinculados ao usuário
- [ ] finance-service gerando notificações automaticamente
- [ ] Frontend consumindo o gateway e as notificações
- [ ] Testes de integração e de controllers
- [ ] Dockerfile para cada serviço
