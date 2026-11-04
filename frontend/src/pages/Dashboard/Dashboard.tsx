import { useEffect, useState } from 'react'
import './Dashboard.css'
import SummaryCard from '../../components/SummaryCard/SummaryCard'
import { getSummary } from '../../api/finance'
import { getErrorMessage } from '../../api/client'
import type { Summary } from '../../api/types'
import { formatCurrency } from '../../utils/format'

function Dashboard() {
  const [summary, setSummary] = useState<Summary | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let cancelled = false

    getSummary()
      .then((data) => {
        if (!cancelled) {
          setSummary(data)
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

  const display = (value: number | undefined) =>
    value === undefined ? '—' : formatCurrency(value)

  return (
    <div>
      <div className="dashboard-header">
        <h2>Olá, Renan</h2>
        <p>Aqui está o resumo das suas finanças.</p>
      </div>

      {error && <p style={{ color: '#f87171' }}>{error}</p>}

      <div>
        <SummaryCard
          title="Saldo atual"
          value={display(summary?.balance)}
          type="balance"
        />

        <SummaryCard
          title="Receitas"
          value={display(summary?.income)}
          type="income"
        />

        <SummaryCard
          title="Despesas"
          value={display(summary?.expenses)}
          type="expense"
        />
      </div>
    </div>
  )
}
export default Dashboard
