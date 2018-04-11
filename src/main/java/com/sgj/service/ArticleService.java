package com.sgj.service;

import com.sgj.model.ArticleMain;

import java.util.List;

public interface ArticleService {

    List<ArticleMain> getArticleList(int page, String keyWord) throws Exception;

    List<ArticleMain> getArticleDetailsList(int page, String articleId) throws Exception;

    double getTotalCount(int type, String keyword) throws Exception;
}
