import { Outlet } from 'react-router-dom';
import Navbar from './Navbar.tsx';
import Sidebar from './Sidebar.tsx';

const Layout = () => {
  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar />
      <div className="flex">
        <Sidebar />
        <main className="flex-1 p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default Layout;