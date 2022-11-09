package com.example.ittime.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@Entity(name = "attachment_contents")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachmentContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private byte[] bytes;

    @OneToOne
    private Attachment attachment;

    @Override
    public String toString() {
        return "AttachmentContent{" +
                "id=" + id +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }

    public AttachmentContent(byte[] bytes, Attachment attachment) {
        this.bytes = bytes;
        this.attachment = attachment;
    }
}
