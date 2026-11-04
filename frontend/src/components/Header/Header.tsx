import './Header.css'

function Header() {
  return (
    <header className="header">
      <div className="header-title">
        <h1>Dashboard</h1>
        <p>Visão geral das suas finanças</p>
      </div>

      <div className="header-actions">
        <button className="notification-button" aria-label="Notificações">
          🔔
        </button>

        <div className="user-profile">
          <div className="user-avatar">T</div>

          <div className="user-info">
            <span className="user-name">Thauany</span>
            <span className="user-role">Minha conta</span>
          </div>
        </div>
      </div>
    </header>
  )
}

export default Header