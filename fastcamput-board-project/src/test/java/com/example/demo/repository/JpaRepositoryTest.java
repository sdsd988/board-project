package com.example.demo.repository;

import com.example.demo.config.JpaConfig;
import com.example.demo.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }



    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        //given

        articleRepository.save(Article.of("new article1", "new content", "#Spring"));
        articleRepository.save(Article.of("new article2", "new content", "#Spring"));
        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles).isNotNull().hasSize(2);
    }


    @DisplayName("update 테스트")
    @Test
    void update_test() {

        //given
        Article savedArticle = articleRepository.save(Article.of("new article1", "new content", "#Spring"));
        var updateHashtag = "#SpringBoot";
        savedArticle.setHashtag(updateHashtag);

        //when
        articleRepository.saveAndFlush(savedArticle);

        //then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }
}