package com.example.ittime.repository;

import com.example.ittime.entity.Article;
import com.example.ittime.projection.ArticleListProjection;
import com.example.ittime.projection.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ArticleRepository extends JpaRepository<Article, Integer> {


    @Query(value = "select a.id,\n" +
            "       a.category_id as categoryId,\n" +
            "       a.title as title,\n" +
            "       a.image_id as imageId\n" +
            "from article a\n" +
            "join category c on c.id = a.category_id"
            , nativeQuery = true)
    Page<ArticleListProjection> getAllArticles(Pageable pageable);

    @Query(value = "select a.id,\n" +
            "       a.category_id as categoryId,\n" +
            "       a.title as title,\n" +
            "       a.description as description,\n" +
            "       a.image_id as imageId\n" +
            "from article a\n" +
            "join category c on c.id = a.category_id"
            , nativeQuery = true)
    ArticleProjection getArticle(Article article);




    boolean existsByTitle(String title);
}
