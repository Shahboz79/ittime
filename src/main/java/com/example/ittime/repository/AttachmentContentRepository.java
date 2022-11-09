package com.example.ittime.repository;

import com.example.ittime.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {

    AttachmentContent findByAttachmentId(Integer attachmentId);
}
