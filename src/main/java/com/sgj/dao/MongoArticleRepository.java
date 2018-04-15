package com.sgj.dao;

import com.sgj.model.ArticleMain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoArticleRepository extends MongoRepository<ArticleMain, String> {

    List<ArticleMain> findByArticleId(String articleId);
}
