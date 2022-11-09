package com.example.ittime.service;


import com.example.ittime.dto.ApiResponse;
import com.example.ittime.dto.ArticleDto;
import com.example.ittime.entity.Article;
import com.example.ittime.entity.Attachment;
import com.example.ittime.entity.Category;
import com.example.ittime.projection.ArticleListProjection;
import com.example.ittime.projection.ArticleProjection;
import com.example.ittime.repository.ArticleRepository;
import com.example.ittime.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    final ArticleRepository articleRepository;
    final CategoryRepository categoryRepository;

    final AttachmentService attachmentService;

    public ArticleService(ArticleRepository articleRepository,
                          CategoryRepository categoryRepository,
                          AttachmentService attachmentService) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentService = attachmentService;
    }


    public ApiResponse saveArticle(ArticleDto articleDto, MultipartFile image) {

        Integer categoryId = articleDto.getCategoryId();
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new IllegalStateException("Category not found!!");
        }
        boolean existsByTitle = articleRepository.existsByTitle(articleDto.getTitle());
        if (existsByTitle)
            return new ApiResponse("title exists", false);


        Attachment savedImage = attachmentService.saveFile(image);

        Article article = Article
                .builder()
                .description(articleDto.getDescription())
                .title(articleDto.getTitle())
                .category(optionalCategory.get())
                .image(savedImage)
                .build();


        articleRepository.save(article);
        return new ApiResponse("added", true);

    }

    public List<ArticleListProjection> getAllArticles(Integer page) {
        if (page < 1)
            throw new IllegalStateException("Bad req...");

        Pageable pageAble = PageRequest.of(page - 1, 2, Sort.Direction.DESC, "updated_at");
        Page<ArticleListProjection> all = articleRepository.getAllArticles(pageAble);
        List<ArticleListProjection> list = all.toList();
        return list;
    }

    public ApiResponse updateArticle(Integer id, ArticleDto articleDto, MultipartFile image) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty())
            return new ApiResponse("article not found", false);
        Article article = optionalArticle.get();
        if (articleDto.getCategoryId()!=null) {
            Optional<Category> optionalCategory = categoryRepository.findById(articleDto.getCategoryId());
            if (optionalArticle.isEmpty()){
            return new ApiResponse("category not found", false);}
            else{
                article.setCategory(optionalCategory.get());
            }

        }
        if (articleDto.getTitle()!=null){
        article.setTitle(articleDto.getTitle());}
        if (articleDto.getDescription()!=null){
        article.setDescription(articleDto.getDescription());}
        if (!image.isEmpty()){
        article.setImage(attachmentService.updateFile(article.getImage().getId(), image));}

        articleRepository.save(article);
        return new ApiResponse("updated", true);
    }

    public ApiResponse deleteArticle(Integer id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty())
            return new ApiResponse("article not found", false);
        articleRepository.delete(optionalArticle.get());
        return new ApiResponse("deleted", true);

    }

    public ApiResponse getArticle(Integer id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty())
            return new ApiResponse("article not found", false);
        ArticleProjection articleProjection = articleRepository.getArticle(optionalArticle.get());

        return new ApiResponse("get", articleProjection, true);
    }
}
