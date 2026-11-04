import { useState } from 'react'
import './TransactionModal.css'
import { getErrorMessage } from '../../api/client'
import type {
  Category,
  TransactionInput,
  TransactionType,
} from '../../api/types'

interface TransactionModalProps {
  categories: Category[]
  onClose: () => void
  onAdd: (input: TransactionInput) => Promise<void>
}

function TransactionModal({
  categories,
  onClose,
  onAdd,
}: TransactionModalProps) {
  const [description, setDescription] = useState('')
  const [amount, setAmount] = useState('')
  const [type, setType] = useState<TransactionType>('expense')
  const [categoryId, setCategoryId] = useState('')
  const [date, setDate] = useState(
    new Date().toISOString().split('T')[0]
  )
  const [submitError, setSubmitError] = useState<string | null>(null)
  const [isSaving, setIsSaving] = useState(false)

  // só mostra categorias do mesmo tipo da transação (regra também validada no backend)
  const availableCategories = categories.filter(
    (category) => category.type === type
  )

  function handleTypeChange(newType: TransactionType) {
    setType(newType)
    setCategoryId('')
  }

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault()

    if (
      !description.trim() ||
      !amount ||
      !categoryId ||
      !date
    ) {
      return
    }

    const numericAmount = Number(
      amount.replace(',', '.')
    )

    if (numericAmount <= 0 || Number.isNaN(numericAmount)) {
      return
    }

    setIsSaving(true)
    setSubmitError(null)

    try {
      await onAdd({
        description: description.trim(),
        amount: numericAmount,
        type,
        categoryId: Number(categoryId),
        date,
      })

      onClose()
    } catch (err) {
      setSubmitError(getErrorMessage(err))
      setIsSaving(false)
    }
  }

  return (
    <div className="transaction-modal-overlay" onClick={onClose}>
      <div
        className="transaction-modal"
        onClick={(event) => event.stopPropagation()}
      >
        <div className="transaction-modal-header">
          <div>
            <h2>Nova transação</h2>

            <p>
              Registre uma receita ou despesa.
            </p>
          </div>

          <button
            type="button"
            className="transaction-close-button"
            onClick={onClose}
          >
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="transaction-form-group">
            <label htmlFor="transaction-type">
              Tipo
            </label>

            <select
              id="transaction-type"
              value={type}
              onChange={(event) =>
                handleTypeChange(
                  event.target.value as TransactionType
                )
              }
            >
              <option value="expense">
                Despesa
              </option>

              <option value="income">
                Receita
              </option>
            </select>
          </div>

          <div className="transaction-form-group">
            <label htmlFor="transaction-description">
              Descrição
            </label>

            <input
              id="transaction-description"
              type="text"
              placeholder="Ex: Supermercado"
              value={description}
              onChange={(event) =>
                setDescription(event.target.value)
              }
            />
          </div>

          <div className="transaction-form-row">
            <div className="transaction-form-group">
              <label htmlFor="transaction-amount">
                Valor
              </label>

              <input
                id="transaction-amount"
                type="text"
                inputMode="decimal"
                placeholder="0,00"
                value={amount}
                onChange={(event) =>
                  setAmount(event.target.value)
                }
              />
            </div>

            <div className="transaction-form-group">
              <label htmlFor="transaction-date">
                Data
              </label>

              <input
                id="transaction-date"
                type="date"
                value={date}
                onChange={(event) =>
                  setDate(event.target.value)
                }
              />
            </div>
          </div>

          <div className="transaction-form-group">
            <label htmlFor="transaction-category">
              Categoria
            </label>

            <select
              id="transaction-category"
              value={categoryId}
              onChange={(event) =>
                setCategoryId(event.target.value)
              }
            >
              <option value="">
                Selecione uma categoria
              </option>

              {availableCategories.map((category) => (
                <option
                  key={category.id}
                  value={String(category.id)}
                >
                  {category.name}
                </option>
              ))}
            </select>
          </div>

          {submitError && (
            <p style={{ color: '#f87171' }}>{submitError}</p>
          )}

          <div className="transaction-modal-actions">
            <button
              type="button"
              className="transaction-cancel-button"
              onClick={onClose}
            >
              Cancelar
            </button>

            <button
              type="submit"
              className="transaction-save-button"
              disabled={isSaving}
            >
              {isSaving ? 'Salvando...' : 'Criar transação'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default TransactionModal
