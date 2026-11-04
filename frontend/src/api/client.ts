const API_URL: string =
  import.meta.env.VITE_API_URL ?? 'http://localhost:8081/api'

export class ApiError extends Error {
  status: number

  constructor(message: string, status: number) {
    super(message)
    this.name = 'ApiError'
    this.status = status
  }
}

export async function request<T>(
  path: string,
  options: RequestInit = {},
): Promise<T> {
  let response: Response

  try {
    response = await fetch(`${API_URL}${path}`, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
    })
  } catch {
    throw new ApiError(
      'Não foi possível conectar ao servidor. Verifique se o backend está rodando.',
      0,
    )
  }

  if (!response.ok) {
    let message = `Erro ${response.status}`

    try {
      const problem = await response.json()
      if (problem?.detail) {
        message = problem.detail
      }
    } catch {
      // resposta sem corpo JSON: mantém a mensagem padrão
    }

    throw new ApiError(message, response.status)
  }

  if (response.status === 204) {
    return undefined as T
  }

  return (await response.json()) as T
}

export function getErrorMessage(error: unknown): string {
  return error instanceof Error ? error.message : 'Ocorreu um erro inesperado.'
}
