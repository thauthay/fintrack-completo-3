import './TransactionList.css'

interface Transaction {
  description: string
  category: string
  date: string
  amount: string
  type: 'income' | 'expense'
}

const transactions: Transaction[] = [
  {
    description: 'Salário',
    category: 'Salário',
    date: '30/08/2026',
    amount: '+ R$ 3.500,00',
    type: 'income',
  },
  {
    description: 'Supermercado',
    category: 'Alimentação',
    date: '29/08/2026',
    amount: '- R$ 185,90',
    type: 'expense',
  },
  {
    description: 'Netflix',
    category: 'Entretenimento',
    date: '28/08/2026',
    amount: '- R$ 39,90',
    type: 'expense',
  },
  {
    description: 'Freelance',
    category: 'Renda extra',
    date: '27/08/2026',
    amount: '+ R$ 800,00',
    type: 'income',
  },
]

function TransactionList() {
  return (
    <section className="transaction-list">
      <div className="transaction-list-header">
        <h2>Últimas transações</h2>
        <button>Ver todas</button>
      </div>

      <div className="transactions">
        {transactions.map((transaction) => (
          <div className="transaction" key={`${transaction.description}-${transaction.date}`}>
            <div className="transaction-info">
              <div className="transaction-icon">
                {transaction.type === 'income' ? '↑' : '↓'}
              </div>

              <div>
                <strong>{transaction.description}</strong>
                <span>{transaction.category}</span>
              </div>
            </div>

            <div className="transaction-details">
              <span>{transaction.date}</span>

              <strong className={transaction.type}>
                {transaction.amount}
              </strong>
            </div>
          </div>
        ))}
      </div>
    </section>
  )
}

export default TransactionList