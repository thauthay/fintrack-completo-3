import './SummaryCard.css'

interface SummaryCardProps {
  title: string
  value: string
  type: 'balance' | 'income' | 'expense'
}

function SummaryCard({ title, value, type }: SummaryCardProps) {
  return (
    <div className={`summary-card ${type}`}>
      <span className="summary-card-title">{title}</span>
      <strong className="summary-card-value">{value}</strong>
    </div>
  )
}

export default SummaryCard