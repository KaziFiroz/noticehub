import React, { useState, useEffect } from 'react';
import NoticeBoard from './NoticeBoard';
import CreateNotice from './CreateNotice';
import { noticeAPI } from '../services/api';

function Dashboard({ user }) {
  const [notices, setNotices] = useState([]);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchNotices();
  }, []);

  const fetchNotices = async () => {
    try {
      setLoading(true);
      const response = await noticeAPI.getNotices();
      setNotices(response.data);
    } catch (error) {
      console.error('Error fetching notices:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateNotice = async (noticeData) => {
    try {
      await noticeAPI.createNotice(noticeData);
      setShowCreateModal(false);
      fetchNotices();
    } catch (error) {
      alert(
        'Error creating notice: ' +
          (error.response?.data || 'Please try again')
      );
    }
  };

  const handleDeleteNotice = async (id) => {
    if (window.confirm('Are you sure you want to delete this notice?')) {
      try {
        await noticeAPI.deleteNotice(id);
        fetchNotices();
      } catch (error) {
        alert(
          'Error deleting notice: ' +
            (error.response?.data || 'Please try again')
        );
      }
    }
  };

  const canCreateNotice =
    user.role === 'ADMIN' || user.role === 'FACULTY';

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1 className="dashboard-title">Notice Board</h1>
        {canCreateNotice && (
          <button
            className="btn-create"
            onClick={() => setShowCreateModal(true)}
          >
            + Create Notice
          </button>
        )}
      </div>

      <NoticeBoard
        notices={notices}
        loading={loading}
        onDelete={handleDeleteNotice}
        userRole={user.role}
      />

      {showCreateModal && (
        <CreateNotice
          onClose={() => setShowCreateModal(false)}
          onCreate={handleCreateNotice}
        />
      )}
    </div>
  );
}

export default Dashboard;
