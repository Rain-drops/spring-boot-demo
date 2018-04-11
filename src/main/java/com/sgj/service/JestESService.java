package com.sgj.service;

import java.util.List;

import com.sgj.model.ArticleMain;
import io.searchbox.core.SearchResult.Hit;

public interface JestESService {

	List<Hit<ArticleMain, Void>> multiMatchQuery(String keyword, Integer nowPage) throws Exception;

	List<Hit<ArticleMain, Void>> boolQuery(String keyword, Integer nowPage) throws Exception;

	Long termQuery(String urlPost) throws Exception;
	
	List<Hit<ArticleMain, Void>> termQuery(String articleId, Integer nowPage) throws Exception;
	
	double getTotalCount(int type, String keyword) throws Exception;
	
}
