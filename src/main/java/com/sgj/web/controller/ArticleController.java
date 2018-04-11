package com.sgj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sgj.model.ArticleMain;
import com.sgj.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/article")
public class ArticleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/v1/getArticleList", method = RequestMethod.GET)
	public Map<Object, Object> articleList(
			@RequestParam(value="keyword")String keyword,
			@RequestParam(value="page")Integer page) {
		Map<Object, Object> result = new HashMap<>();
		
		try {
			
			List<ArticleMain> articleMains = articleService.getArticleList(page, keyword);
			
			double totalCount = articleService.getTotalCount(2, keyword);
			
			result.put("code", 200);
			result.put("msg", articleMains);
			result.put("total", totalCount);
		} catch (Exception e) {
			// 
			LOGGER.error("/article/v1/getArticleList", e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/v1/getArticleDetailsList", method = RequestMethod.GET)
	public Map<Object, Object> articleDetailsList(
			@RequestParam(value="articleId")String articleId,
			@RequestParam(value="page")Integer page) {
		Map<Object, Object> result = new HashMap<>();
		
		try {
			
			List<ArticleMain> articleMains = articleService.getArticleDetailsList(page, articleId);
			double totalCount = articleService.getTotalCount(1, articleId);
			result.put("code", 200);
			result.put("msg", articleMains);
			result.put("total", totalCount);
		} catch (Exception e) {
			// 
			LOGGER.error("/article/v1/getArticleDetailsList", e);
		}
		
		return result;
	}
}
