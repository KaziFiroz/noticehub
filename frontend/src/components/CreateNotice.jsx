import React, { useState } from 'react';

function CreateNotice({ onClose, onCreate }) {
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    targetAudience: 'STUDENT'
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onCreate(formData);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div
        className="modal-content"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="modal-header">
          <h2 className="modal-title">Create New Notice</h2>
          <button className="btn-close" onClick={onClose}>
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Title</label>
            <input
              type="text"
              name="title"
              className="form-input"
              value={formData.title}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">Content</label>
            <textarea
              name="content"
              className="form-textarea"
              value={formData.content}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">Target Audience</label>
            <select
              name="targetAudience"
              className="form-select"
              value={formData.targetAudience}
              onChange={handleChange}
              required
            >
              <option value="STUDENT">Students</option>
              <option value="FACULTY">Faculty</option>
              <option value="ADMIN">Admin</option>
            </select>
          </div>

          <div className="form-actions">
            <button
              type="button"
              className="btn-secondary"
              onClick={onClose}
            >
              Cancel
            </button>
            <button type="submit" className="btn-primary">
              Create Notice
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CreateNotice;
