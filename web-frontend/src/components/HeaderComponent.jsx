import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { logoutUser } from '../services/AuthService';
import './styles/HeaderComponent.css';
import logo from '../images/logo_full.png';

const HeaderComponent = () => {
  const [loggedIn, setLoggedIn] = useState(localStorage.getItem('loggedIn') === 'true');
  const [username, setUsername] = useState(localStorage.getItem('username') || 'Guest');

  useEffect(() => {
    const handleStorageChange = () => {
      setLoggedIn(localStorage.getItem('loggedIn') === 'true');
      setUsername(localStorage.getItem('username') || 'Guest');
    };

    window.addEventListener('storage', handleStorageChange);
    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  const handleLogout = async () => {
    try {
      const response = await logoutUser();
      if (response.status === 200) {
        localStorage.clear(); // Clear all items from localStorage
        setLoggedIn(false);
        window.location.href = '/';
      }
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  return (
    <header>
      <nav className='navbar navbar-expand-lg navbar-light bg-light'>
        <div className="container-fluid">
          <div className="navbar-header d-flex align-items-center">
            <Link className="navbar-brand" to={loggedIn ? '/feed' : '/'}>
              <img alt="Logo" src={logo} style={{ height: '30px' }} />
              <div className="logoname">LionEats Web App</div>
            </Link>
          </div>
          <div className="collapse navbar-collapse d-flex justify-content-between">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link" to="/about">About Us</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/dishes">Dishes</Link>
              </li>
            </ul>
            <ul className="navbar-nav ml-auto d-flex justify-content-end">
              {loggedIn ? (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/profile">{username}</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/upload">Upload Image</Link>
                  </li>
                  <li className="nav-item">
                    <button className="nav-link btn btn-link" onClick={handleLogout}>Logout</button>
                  </li>
                </>
              ) : (
                <>
                  <li className="nav-item">
                    <span className="nav-link">Guest</span>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/registerAccount">Register Account</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/login">Log In</Link>
                  </li>
                </>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default HeaderComponent;
