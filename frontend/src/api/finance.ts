import { request } from './client'
import type {
  Category,
  Summary,
  Transaction,
  TransactionInput,
  TransactionType,
} from './types'

/* ---------- Categorias ---------- */

export function getCategories() {
  return request<Category[]>('/categories')
}

export function createCategory(name: string, type: TransactionType) {
  return request<Category>('/categories', {
    method: 'POST',
    body: JSON.stringify({ name, type }),
  })
}

export function updateCategory(
  id: number,
  name: string,
  type: TransactionType,
) {
  return request<Category>(`/categories/${id}`, {
    method: 'PUT',
    body: JSON.stringify({ name, type }),
  })
}

export function deleteCategory(id: number) {
  return request<void>(`/categories/${id}`, { method: 'DELETE' })
}

/* ---------- Transações ---------- */

export interface TransactionFilters {
  search?: string
  type?: TransactionType | 'all'
  categoryId?: number | 'all'
}

export function getTransactions(filters: TransactionFilters = {}) {
  const params = new URLSearchParams()

  if (filters.search?.trim()) {
    params.set('search', filters.search.trim())
  }
  if (filters.type && filters.type !== 'all') {
    params.set('type', filters.type)
  }
  if (filters.categoryId && filters.categoryId !== 'all') {
    params.set('categoryId', String(filters.categoryId))
  }

  const query = params.toString()
  return request<Transaction[]>(`/transactions${query ? `?${query}` : ''}`)
}

export function createTransaction(input: TransactionInput) {
  return request<Transaction>('/transactions', {
    method: 'POST',
    body: JSON.stringify(input),
  })
}

/* ---------- Dashboard ---------- */

export function getSummary() {
  return request<Summary>('/dashboard/summary')
}
