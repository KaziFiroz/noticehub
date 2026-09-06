import React from 'react';

function NoticeBoard({ notices, loading, onDelete, userRole }) {
  if (loading) {
    return (
      <div className="empty-state">
        <div className="empty-icon">⏳</div>
        <h2 className="empty-title">Loading notices...</h2>
      </div>
    );
  }

  if (notices.length === 0) {
    return (
      <div className="empty-state">
        <div className="empty-icon">📭</div>
        <h2 className="empty-title">No Notices Yet</h2>
        <p className="empty-text">
          {userRole === 'STUDENT'
            ? 'Check back later for new announcements'
            : 'Create your first notice to get started'}
        </p>
      </div>
    );
  }

  return (
    <div className="notices-grid">
      {notices.map((notice) => (
        <div key={notice.id} className="notice-card">
          <div className="notice-header">
            <h2 className="notice-title">{notice.title}</h2>
            {userRole === 'ADMIN' && (
              <button
                className="btn-delete"
                onClick={() => onDelete(notice.id)}
              >
                Delete
              </button>
            )}
          </div>

          <p className="notice-content">{notice.content}</p>

          <div className="notice-footer">
            <div className="notice-meta">
              <div className="notice-author">
                By: {notice.createdBy}
              </div>
              <div>
                {new Date(notice.createdAt).toLocaleString()}
              </div>
            </div>
            <span className="notice-badge">
              {notice.targetAudience}
            </span>
          </div>
        </div>
      ))}
    </div>
  );
}

export default NoticeBoard;
