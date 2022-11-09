package com.example.ittime.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArticleDto {


    @NotEmpty(message = "Must not be empty")
    private String title;

    @Size(min = 3, message = "The number of letters is less than 30")
    private String description;

    @NotNull(message = "It is necessary to choose a category!!!")
    private Integer categoryId;



}