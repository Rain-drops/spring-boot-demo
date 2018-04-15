package com.sgj.web.task;

import com.sgj.webmagic.processor.MtimeRepoPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
@EnableScheduling
public class MtimeScheduled {

    private static volatile boolean flag = false;

    @Qualifier("ElasticsearchPipeline")
    @Autowired
    private Pipeline elasticPipeline;

    @Scheduled(cron = "0 0/100 * * * ?")
    public void getArticleElastic(){
        if(flag){
           return;
        }
        flag = true;
        Spider spider = OOSpider.create(new MtimeRepoPageProcessor())
                .addUrl("http://news.mtime.com/2018/03/28/1579311.html")
                .addPipeline(elasticPipeline)
                .setExitWhenComplete(true)
                .setScheduler(new QueueScheduler());
        spider.start();
        spider.stop();
    }

}
