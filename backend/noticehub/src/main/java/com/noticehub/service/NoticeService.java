package com.noticehub.service;

import com.noticehub.entity.Notice;
import com.noticehub.entity.Role;
import com.noticehub.entity.User;
import com.noticehub.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticeService {
    
    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }
    
    public Notice createNotice(String title, String content, String targetAudience, User createdBy) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setTargetAudience(Role.valueOf(targetAudience.toUpperCase()));
        notice.setCreatedBy(createdBy);
        return noticeRepository.save(notice);
    }
    
    public List<Notice> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public List<Notice> getNoticesByRole(Role role) {
        return noticeRepository.findByTargetAudienceOrderByCreatedAtDesc(role);
    }
    
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}