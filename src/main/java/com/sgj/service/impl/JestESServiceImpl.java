package com.sgj.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.sgj.dao.JestClientUtil;
import com.sgj.dao.JestESDao;
import com.sgj.model.ArticleMain;
import com.sgj.service.JestESService;
import com.sgj.utils.VariablePoolUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

@Service
public class JestESServiceImpl implements JestESService {
	
	@Autowired
	private JestESDao jestESDao;
	
	public void createIndex() throws Exception{  
		 
	    boolean result = false;
		try {
			result = jestESDao.createIndex(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME);
		} catch (Exception e) {
			// 
		}  
   }

	public void createIndexMapping() throws Exception{  
        
		try {
			
			String source = "{\"" + VariablePoolUtil.TYPE_NAME + "\":{\"properties\":{"
                   + "\"articleId\":{\"type\":\"keyword\"}"
                   + ",\"articleTitle\":{\"type\":\"text\", \"analyzer\":\"ik_smart\", \"search_analyzer\":\"ik_max_word\"}"  
                   + ",\"articleAuthor\":{\"type\":\"text\", \"analyzer\":\"ik_max_word\", \"search_analyzer\":\"ik_max_word\"}"
                   + ",\"articleContent\":{\"type\":\"text\", \"analyzer\": \"ik_max_word\", \"search_analyzer\": \"ik_max_word\"}"  
                   + ",\"articleClass\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\", \"search_analyzer\":\"ik_max_word\"}"
                   + ",\"articleUrl\":{\"type\":\"keyword\"}"
                   + ",\"articleDate\":{\"type\":\"text\"}"
//                   + ",\"createdDate\":{\"type\":\"date\", \"format\": \"strict_date_optional_time||epoch_millis\"}"
                   + ",\"createdDate\":{\"type\":\"text\"}"
                   + ",\"articlePage\":{\"type\":\"text\"}"
                   + "}}}";
			
			boolean b = jestESDao.createIndexMapping(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, source);
		} catch (Exception e) {
			// 
		}  
   }
	
	@Override
	public List<Hit<ArticleMain, Void>> multiMatchQuery(String keyword, Integer nowPage) throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "articleTitle", "articleAuthor", "articleClass");

		searchSourceBuilder.query(queryBuilder).sort("createdDate", SortOrder.DESC).sort("articleId");

		searchSourceBuilder.size(10);
		searchSourceBuilder.from(nowPage);
		searchSourceBuilder.fetchSource(null, "articleContent");
		String query = searchSourceBuilder.toString();
		SearchResult result = jestESDao.search(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, query);
		List<Hit<ArticleMain, Void>> hits = result.getHits(ArticleMain.class);
		return hits;
	}
	
	@Override
	public List<Hit<ArticleMain, Void>> boolQuery(String keyword, Integer nowPage) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -3);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		QueryBuilder queryBuilderA = QueryBuilders.multiMatchQuery(keyword, "articleTitle", "articleAuthor", "articleClass");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		QueryBuilder queryBuilderB = QueryBuilders.rangeQuery("createdDate").gt(calendar.getTime());
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(queryBuilderA).must(queryBuilderB);

		searchSourceBuilder.query(queryBuilder).sort("createdDate", SortOrder.DESC).sort("articleId");
		

		searchSourceBuilder.size(10);
		searchSourceBuilder.from(nowPage);
		searchSourceBuilder.fetchSource(null, "articleContent");
		String query = searchSourceBuilder.toString();
		SearchResult result = jestESDao.search(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, query);
		List<Hit<ArticleMain, Void>> hits = result.getHits(ArticleMain.class);
		return hits;
	}

	@Override
	public Long termQuery(String keyword) throws Exception{

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders
//				.termQuery("id", "news-mtime-1579318");// 单值完全匹配查询
				.termQuery("articleUrl", keyword);//单值完全匹配查询

		searchSourceBuilder.query(queryBuilder).size();
		String query = searchSourceBuilder.toString();
		Double result = jestESDao.count(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, query);

		return new Double(result).longValue();
	}
	
	public void deleteIndex() throws Exception {  
        
        boolean result = jestESDao.delete(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME);
        System.out.println(result);  
    }

	@Override
	public List<Hit<ArticleMain, Void>> termQuery(String articleId, Integer nowPage) throws Exception {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders
				.termQuery("articleId", articleId)
				;// 单值完全匹配查询

		searchSourceBuilder.query(queryBuilder);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(nowPage);
		String query = searchSourceBuilder.toString();
		SearchResult result = jestESDao.search(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, query);
		List<Hit<ArticleMain, Void>> hits = result.getHits(ArticleMain.class);
		return hits;
	}

	@Override
	public double getTotalCount(int type, String keyword) throws Exception {
		// 
		SearchSourceBuilder searchSourceBuilder = null;
		String query = null;
		QueryBuilder queryBuilder = null;
		switch (type) {
		case 1:
			searchSourceBuilder = new SearchSourceBuilder();
			queryBuilder = QueryBuilders
				.termQuery("articleId", keyword);
			searchSourceBuilder.query(queryBuilder);
			query = searchSourceBuilder.toString();
			break;
		case 2:
			searchSourceBuilder = new SearchSourceBuilder();
			queryBuilder = QueryBuilders.multiMatchQuery(keyword, "articleTitle", "articleAuthor");
			searchSourceBuilder.query(queryBuilder);
			query = searchSourceBuilder.toString();
			break;

		default:
			break;
		}
		
		return jestESDao.count(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, query);
		
	}
}
