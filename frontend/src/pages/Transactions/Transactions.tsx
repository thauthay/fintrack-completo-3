import { useEffect, useState } from 'react'
import './Transactions.css'
import TransactionModal from '../../components/TransactionModal/TransactionModal'
import {
  createTransaction,
  getCategories,
  getTransactions,
} from '../../api/finance'
import { getErrorMessage } from '../../api/client'
import type {
  Category,
  Transaction,
  TransactionInput,
  TransactionType,
} from '../../api/types'
import { formatCurrency } from '../../utils/format'

function Transactions() {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [transactions, setTransactions] = useState<Transaction[]>([])
  const [categories, setCategories] = useState<Category[]>([])
  const [error, setError] = useState<string | null>(null)
  const [reloadKey, setReloadKey] = useState(0)

  const [search, setSearch] = useState('')
  const [typeFilter, setTypeFilter] = useState<TransactionType | 'all'>('all')
  const [categoryFilter, setCategoryFilter] = useState<string>('all')

  useEffect(() => {
    let cancelled = false

    getCategories()
      .then((data) => {
        if (!cancelled) {
          setCategories(data)
        }
      })
      .catch((err: unknown) => {
        if (!cancelled) {
          setError(getErrorMessage(err))
        }
      })

    return () => {
      cancelled = true
    }
  }, [])

  useEffect(() => {
    let cancelled = false

    // pequeno atraso para não chamar a API a cada letra digitada na busca
    const timer = setTimeout(() => {
      getTransactions({
        search,
        type: typeFilter,
        categoryId: categoryFilter === 'all' ? 'all' : Number(categoryFilter),
      })
        .then((data) => {
          if (!cancelled) {
            setTransactions(data)
            setError(null)
          }
        })
        .catch((err: unknown) => {
          if (!cancelled) {
            setError(getErrorMessage(err))
          }
        })
    }, 300)

    return () => {
      cancelled = true
      clearTimeout(timer)
    }
  }, [search, typeFilter, categoryFilter, reloadKey])

  async function handleAddTransaction(input: TransactionInput) {
    await createTransaction(input)
    setReloadKey((key) => key + 1)
  }

  function formatDate(date: string) {
    return new Date(`${date}T00:00:00`).toLocaleDateString('pt-BR')
  }

  return (
    <div className="transactions-page">
      <div className="transactions-header">
        <div>
          <h2>Transações</h2>
          <p>Gerencie suas receitas e despesas.</p>
        </div>

        <button
          className="add-transaction-button"
          onClick={() => setIsModalOpen(true)}
        >
          + Nova transação
        </button>
      </div>

      <div className="transactions-filters">
        <input
          type="text"
          placeholder="Buscar transação..."
          value={search}
          onChange={(event) => setSearch(event.target.value)}
        />

        <select
          value={typeFilter}
          onChange={(event) =>
            setTypeFilter(event.target.value as TransactionType | 'all')
          }
        >
          <option value="all">Todas</option>
          <option value="income">Receitas</option>
          <option value="expense">Despesas</option>
        </select>

        <select
          value={categoryFilter}
          onChange={(event) => setCategoryFilter(event.target.value)}
        >
          <option value="all">Todas as categorias</option>
          {categories.map((category) => (
            <option key={category.id} value={String(category.id)}>
              {category.name}
            </option>
          ))}
        </select>
      </div>

      {error && <p style={{ color: '#f87171' }}>{error}</p>}

      <div className="transactions-list">
        {transactions.map((transaction) => (
          <div className="transaction-card" key={transaction.id}>
            <div className="transaction-icon">
              {transaction.type === 'income' ? '💼' : '📁'}
            </div>

            <div className="transaction-info">
              <strong>{transaction.description}</strong>

              <span>
                {transaction.category} • {formatDate(transaction.date)}
              </span>
            </div>

            <span
              className={
                transaction.type === 'income'
                  ? 'transaction-income'
                  : 'transaction-expense'
              }
            >
              {transaction.type === 'income' ? '+ ' : '- '}
              {formatCurrency(transaction.amount)}
            </span>
          </div>
        ))}
      </div>

      {isModalOpen && (
        <TransactionModal
          categories={categories}
          onClose={() => setIsModalOpen(false)}
          onAdd={handleAddTransaction}
        />
      )}
    </div>
  )
}

export default Transactions
