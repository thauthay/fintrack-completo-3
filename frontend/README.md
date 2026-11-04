# FinTrack — Frontend

Interface web do FinTrack, em React 19, TypeScript e Vite, com React Router.

## Páginas

| Rota | O que mostra |
|---|---|
| `/dashboard` | saldo, receitas, despesas e as últimas transações |
| `/transactions` | lista de transações, com filtros e cadastro, edição e exclusão |
| `/categories` | cadastro, edição e exclusão de categorias |

## Como rodar

Pré-requisitos: Node.js 22.12 ou superior e o backend rodando (veja o [README da raiz](../README.md)).

```bash
cp .env.example .env
npm install
npm run dev
```

Abra http://localhost:5173.

## Configuração

O endereço da API fica em `.env`:

```
VITE_API_URL=http://localhost:8081/api
```

Para passar pelo gateway, use `http://localhost:8080/api`.

## Scripts

| Comando | O que faz |
|---|---|
| `npm run dev` | servidor de desenvolvimento |
| `npm run build` | verifica os tipos e gera a build de produção |
| `npm run lint` | roda o ESLint |
| `npm run preview` | serve a build localmente |

## Estrutura

```
src/
├── api/          # cliente HTTP e chamadas ao backend
├── components/   # Header, Sidebar, modais, lista de transações...
├── layouts/      # layout principal
├── pages/        # Dashboard, Transactions, Categories
└── utils/        # formatação de valores e datas
```# FinTrack — Frontend

Interface web do FinTrack, em React 19, TypeScript e Vite, com React Router.

## Páginas

| Rota | O que mostra |
|---|---|
| `/dashboard` | saldo, receitas, despesas e as últimas transações |
| `/transactions` | lista de transações, com filtros e cadastro, edição e exclusão |
| `/categories` | cadastro, edição e exclusão de categorias |

## Como rodar

Pré-requisitos: Node.js 22.12 ou superior e o backend rodando (veja o [README da raiz](../README.md)).

```bash
cp .env.example .env
npm install
npm run dev
```

Abra http://localhost:5173.

## Configuração

O endereço da API fica em `.env`:

```
VITE_API_URL=http://localhost:8081/api
```

Para passar pelo gateway, use `http://localhost:8080/api`.

## Scripts

| Comando | O que faz |
|---|---|
| `npm run dev` | servidor de desenvolvimento |
| `npm run build` | verifica os tipos e gera a build de produção |
| `npm run lint` | roda o ESLint |
| `npm run preview` | serve a build localmente |

## Estrutura

```
src/
├── api/          # cliente HTTP e chamadas ao backend
├── components/   # Header, Sidebar, modais, lista de transações...
├── layouts/      # layout principal
├── pages/        # Dashboard, Transactions, Categories
└── utils/        # formatação de valores e datas
```