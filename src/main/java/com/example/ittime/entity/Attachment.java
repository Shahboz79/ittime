package com.example.ittime.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "attachments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fileName;

    private String fileFormat;

    private Long size;

    @OneToOne(mappedBy = "attachment",cascade = CascadeType.ALL)
    private AttachmentContent attachmentContent;




}
