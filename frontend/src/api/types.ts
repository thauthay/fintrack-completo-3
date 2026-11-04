export type TransactionType = 'income' | 'expense'

export interface Category {
  id: number
  name: string
  type: TransactionType
}

export interface Transaction {
  id: number
  description: string
  amount: number
  type: TransactionType
  categoryId: number
  category: string
  date: string
}

export interface TransactionInput {
  description: string
  amount: number
  type: TransactionType
  categoryId: number
  date: string
}

export interface Summary {
  balance: number
  income: number
  expenses: number
  recentTransactions: Transaction[]
}
