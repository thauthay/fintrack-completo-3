import { Outlet } from 'react-router-dom'
import './MainLayout.css'
import Sidebar from '../components/Sidebar/Sidebar'
import Header from '../components/Header/Header'

function MainLayout() {
  return (
    <div className="main-layout">
      <Sidebar />

      <div className="main-area">
        <Header />

        <main className="main-content">
          <Outlet />
        </main>
      </div>
    </div>
  )
}

export default MainLayout