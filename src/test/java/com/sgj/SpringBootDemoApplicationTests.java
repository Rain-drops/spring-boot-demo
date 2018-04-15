package com.sgj;

import com.sgj.model.ArticleMain;
import com.sgj.service.ArticleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Before
    public void setUp(){

    }

    @After
    public void setDown(){

    }

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void saveArticleLoads() {

        try {
            ArticleMain articleMain = new ArticleMain();
            articleMain.setArticleId("0x0001");
            articleMain.setArticleAuthor("小杰1");
            articleMain.setArticleTitle("呵呵1");
            articleMain.setArticleUrl("http://");
            articleMain.setArticleClass("开心");
            articleMain.setArticleDate("2018-04-13");
            articleMain.setArticlePage("1");
            articleMain.setCreatedDate(new Date());
            articleMain.setSourcePlatform("不开心");

            mongoOperations.insert(articleMain, "articleMain");

//            mongoOperations.save(articleMain);

        }catch (Exception e){

        }

    }
    @Test
    public void getArticleLoads() {

        try {
            List<ArticleMain> articleMains =  mongoOperations.findAll(ArticleMain.class);
            for(ArticleMain mains : articleMains){
                System.out.println(mains.getArticleTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
