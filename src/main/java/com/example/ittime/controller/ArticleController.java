package com.example.ittime.controller;


import com.example.ittime.dto.ApiResponse;
import com.example.ittime.dto.ArticleDto;
import com.example.ittime.projection.ArticleListProjection;
import com.example.ittime.repository.ArticleRepository;
import com.example.ittime.service.ArticleService;
import com.example.ittime.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public ArticleController(
            CategoryService categoryService,
            ArticleService articleService,
            ArticleRepository articleRepository) {
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }


    @GetMapping("/list")
    public ResponseEntity<?> getAllArticles(@RequestParam(defaultValue = "1") Integer page) {
        List<ArticleListProjection> articleList = articleService.getAllArticles(page);

        return ResponseEntity.ok(articleList);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Integer id){
        ApiResponse apiResponse = articleService.getArticle(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }


    @PostMapping("/save")
    public ResponseEntity<?> saveArticle(@RequestParam String article,
                                         @RequestParam("file") MultipartFile image) {
        ArticleDto articleDto = null;
        try {
            articleDto = new ObjectMapper().readValue(article, ArticleDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiResponse apiResponse = articleService.saveArticle(articleDto, image);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(
            @PathVariable Integer id,
            @RequestParam String article,
            @RequestParam("file") MultipartFile image) {
        ArticleDto articleDto = null;
        try {
            articleDto = new ObjectMapper().readValue(article, ArticleDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiResponse apiResponse = articleService.updateArticle(id, articleDto, image);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id){
        ApiResponse apiResponse = articleService.deleteArticle(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }

}
