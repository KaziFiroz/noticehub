package com.noticehub.controller;

import com.noticehub.dto.NoticeDTO;
import com.noticehub.entity.Notice;
import com.noticehub.entity.Role;
import com.noticehub.entity.User;
import com.noticehub.service.NoticeService;
import com.noticehub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    
    private final NoticeService noticeService;
    private final UserService userService;

    @Autowired
    public NoticeController(NoticeService noticeService, UserService userService) {
        this.noticeService = noticeService;
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<?> createNotice(@Valid @RequestBody NoticeDTO noticeDTO, 
                                         Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (user.getRole() == Role.STUDENT) {
                return ResponseEntity.badRequest().body("Students cannot create notices");
            }
            
            Notice notice = noticeService.createNotice(
                noticeDTO.getTitle(),
                noticeDTO.getContent(),
                noticeDTO.getTargetAudience(),
                user
            );
            
            return ResponseEntity.ok(convertToDTO(notice));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getNotices(Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            List<Notice> notices;
            if (user.getRole() == Role.ADMIN) {
                notices = noticeService.getAllNotices();
            } else {
                notices = noticeService.getNoticesByRole(user.getRole());
            }
            
            return ResponseEntity.ok(
                notices.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (user.getRole() != Role.ADMIN) {
                return ResponseEntity.badRequest().body("Only admins can delete notices");
            }
            
            noticeService.deleteNotice(id);
            return ResponseEntity.ok("Notice deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    private NoticeDTO convertToDTO(Notice notice) {
        NoticeDTO dto = new NoticeDTO();
        dto.setId(notice.getId());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setTargetAudience(notice.getTargetAudience().name());
        dto.setCreatedBy(notice.getCreatedBy().getFullName());
        dto.setCreatedAt(notice.getCreatedAt().toString());
        return dto;
    }
}