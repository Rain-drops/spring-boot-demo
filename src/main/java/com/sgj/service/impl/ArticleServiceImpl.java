package com.sgj.service.impl;

import com.sgj.model.ArticleMain;
import com.sgj.service.ArticleService;
import com.sgj.service.JestESService;
import io.searchbox.core.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private JestESService jestService;

    @Override
    public List<ArticleMain> getArticleList(int page, String keyword) throws Exception{

        List<SearchResult.Hit<ArticleMain, Void>> hits = jestService.multiMatchQuery(keyword, page*10);
//		List<Hit<ArticleMain, Void>> hits = jestService.boolQuery(keyword, page*10);

        List<ArticleMain> articleMains = new ArrayList<>();

        for(SearchResult.Hit<ArticleMain, Void> article : hits){
            articleMains.add(article.source);
        }
        return articleMains;
    }

    @Override
    public List<ArticleMain> getArticleDetailsList(int page, String articleId) throws Exception {
        //
        List<SearchResult.Hit<ArticleMain, Void>> hits = jestService.termQuery(articleId, page*10);

        List<ArticleMain> articleMains = new ArrayList<>();

        for(SearchResult.Hit<ArticleMain, Void> article : hits){
            articleMains.add(article.source);
        }
        return articleMains;
    }

    @Override
    public double getTotalCount(int type, String keyword) throws Exception {

        return jestService.getTotalCount(type, keyword);
    }
}
