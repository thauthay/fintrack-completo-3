# api-gateway

Ponto de entrada único do FinTrack: recebe as requisições em `/api/**` e repassa para o serviço responsável.
Java 21 · Spring Boot · Spring Cloud Gateway (Server Web MVC).

## Como rodar

Na pasta `backend/api-gateway`: `./mvnw spring-boot:run` (porta **8080**). Os serviços de destino precisam estar no ar.

## Rotas

As rotas ficam em `src/main/resources/application.yaml`, em `spring.cloud.gateway.server.webmvc.routes`.

| Caminho | Destino |
|---|---|
| `/api/categories/**` | finance-service (`localhost:8081`) |
| `/api/transactions/**` | finance-service (`localhost:8081`) |
| `/api/dashboard/**` | finance-service (`localhost:8081`) |
| `/api/notifications/**` | notification-service (`localhost:8083`) |

Exemplo: `curl localhost:8080/api/dashboard/summary` chega no finance-service.

## Observações

- A versão do Spring Cloud (`2025.1.2`) é a primeira compatível com o Spring Boot 4.1.0 usado no projeto.
- O auth-service ainda não tem endpoints, então não há rota para ele.
- O frontend continua apontando direto para o finance-service. Para passar pelo gateway, defina `VITE_API_URL=http://localhost:8080/api` no `.env` do frontend.
