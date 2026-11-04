import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import MainLayout from './layouts/MainLayout'
import Dashboard from './pages/Dashboard/Dashboard'
import Transactions from './pages/Transactions/Transactions'
import Categories from './pages/Categories/Categories'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to="/dashboard" replace />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="transactions" element={<Transactions />} />
          <Route path="categories" element={<Categories />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App