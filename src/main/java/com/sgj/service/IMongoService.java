package com.sgj.service;

import com.sgj.model.ArticleMain;

public interface IMongoService {

    void saveArticle(ArticleMain articleMain);

    ArticleMain findArticleById(String articleId);

}
