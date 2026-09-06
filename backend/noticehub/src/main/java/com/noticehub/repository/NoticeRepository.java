package com.noticehub.repository;

import com.noticehub.entity.Notice;
import com.noticehub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTargetAudienceOrderByCreatedAtDesc(Role targetAudience);
    List<Notice> findAllByOrderByCreatedAtDesc();
}