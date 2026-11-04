import { NavLink } from 'react-router-dom'
import './Sidebar.css'

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <span className="logo-icon">◈</span>
        <span>FinTrack</span>
      </div>

      <nav className="sidebar-nav">
        <NavLink to="/dashboard" className="nav-item">
          <span>▦</span>
          Dashboard
        </NavLink>

        <NavLink to="/transactions" className="nav-item">
          <span>↔</span>
          Transações
        </NavLink>

        <NavLink to="/categories" className="nav-item">
          <span>◉</span>
          Categorias
        </NavLink>
      </nav>

      <div className="sidebar-bottom">
        <NavLink to="/settings" className="nav-item">
          <span>⚙</span>
          Configurações
        </NavLink>
      </div>
    </aside>
  )
}

export default Sidebar