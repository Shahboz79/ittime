package com.example.ittime.service;

import com.example.ittime.dto.ApiResponse;
import com.example.ittime.entity.Attachment;
import com.example.ittime.entity.AttachmentContent;
import com.example.ittime.repository.AttachmentContentRepository;
import com.example.ittime.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;


@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }


    public Attachment saveFile(MultipartFile file) {
        try {
            Attachment attch = Attachment.builder()
                    .fileName(file.getOriginalFilename())
                    .size(file.getSize())
                    .fileFormat(file.getContentType())
                    .build();
            attachmentRepository.save(attch);
            attachmentContentRepository.save(new AttachmentContent(file.getBytes(), attch));
            return attch;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Attachment updateFile(Integer id, MultipartFile file) {
        try {
            Attachment attch = Attachment.builder()
                    .fileName(file.getOriginalFilename())
                    .size(file.getSize())
                    .fileFormat(file.getContentType())
                    .build();

            Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
            if (!optionalAttachment.isPresent())
                return saveFile(file);

            Attachment attachment=optionalAttachment.get();
            attachment.setFileFormat(attch.getFileFormat());
            attachment.setFileName(attch.getFileName());
            attachment.setSize(attch.getSize());
            attachmentRepository.save(attachment);

            AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(id);
            byAttachmentId.setAttachment(attachment);
            byAttachmentId.setBytes(file.getBytes());
            attachmentContentRepository.save(byAttachmentId);
            return attachment;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
