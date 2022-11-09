package com.example.ittime.controller;

import com.example.ittime.entity.AttachmentContent;
import com.example.ittime.repository.AttachmentContentRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentContentRepository attachmentContentRepo;

    public AttachmentController(AttachmentContentRepository attachmentContentRepo) {
        this.attachmentContentRepo = attachmentContentRepo;
    }


    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer attachmentId) {
        AttachmentContent attachmentContent = attachmentContentRepo.findByAttachmentId(attachmentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachmentContent.getAttachment().getFileFormat()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + attachmentContent.getAttachment().getFileName() + "\"")
                .body(new ByteArrayResource(attachmentContent.getBytes()));
    }

}
