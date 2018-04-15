package com.sgj.service.impl;

import com.sgj.model.ArticleMain;
import com.sgj.service.IMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoServiceImpl implements IMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveArticle(ArticleMain articleMain) {

    }

    @Override
    public ArticleMain findArticleById(String articleId) {
        return null;
    }
}
