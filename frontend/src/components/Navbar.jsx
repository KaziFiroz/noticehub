import React from 'react';

function Navbar({ user, onLogout }) {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <span>📢</span>
        <span>NoticeHub</span>
      </div>

      <div className="navbar-user">
        <div className="user-info">
          <div className="user-name">{user.fullName}</div>
          <div className="user-role">{user.role}</div>
        </div>

        <button className="btn-logout" onClick={onLogout}>
          Logout
        </button>
      </div>
    </nav>
  );
}

export default Navbar;
