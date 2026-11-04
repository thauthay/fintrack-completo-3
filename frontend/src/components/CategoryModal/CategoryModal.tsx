import { useState } from 'react'
import './CategoryModal.css'

interface CategoryModalProps {
  onClose: () => void
  onAdd: (name: string, type: 'income' | 'expense') => void
  initialName?: string
  initialType?: 'income' | 'expense'
  isEditing?: boolean
}

function CategoryModal({
  onClose,
  onAdd,
  initialName = '',
  initialType = 'expense',
  isEditing = false,
}: CategoryModalProps) {
  const [name, setName] = useState(initialName)
  const [type, setType] = useState<'income' | 'expense'>(initialType)

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault()

    if (!name.trim()) {
      return
    }

    onAdd(name.trim(), type)
    onClose()
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div
        className="category-modal"
        onClick={(event) => event.stopPropagation()}
      >
        <div className="modal-header">
          <div>
            <h2>
              {isEditing ? 'Editar categoria' : 'Nova categoria'}
            </h2>

            <p>
              {isEditing
                ? 'Altere os dados da categoria.'
                : 'Crie uma categoria para organizar suas finanças.'}
            </p>
          </div>

          <button
            type="button"
            className="close-button"
            onClick={onClose}
          >
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="category-name">Nome</label>

            <input
              id="category-name"
              type="text"
              placeholder="Ex: Alimentação"
              value={name}
              onChange={(event) => setName(event.target.value)}
            />
          </div>

          <div className="form-group">
            <label htmlFor="category-type">Tipo</label>

            <select
              id="category-type"
              value={type}
              onChange={(event) =>
                setType(event.target.value as 'income' | 'expense')
              }
            >
              <option value="expense">Despesa</option>
              <option value="income">Receita</option>
            </select>
          </div>

          <div className="modal-actions">
            <button
              type="button"
              className="cancel-button"
              onClick={onClose}
            >
              Cancelar
            </button>

            <button
              type="submit"
              className="save-button"
            >
              {isEditing ? 'Salvar alterações' : 'Criar categoria'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default CategoryModal
