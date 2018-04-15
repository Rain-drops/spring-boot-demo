package com.sgj.webmagic.processor;

import com.sgj.web.enums.PlarformEnum;
import com.sgj.webmagic.pipeline.ElasticsearchPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.Date;
import java.util.List;

public class MtimeRepoPageProcessor extends BasePageProcessor {

    public static final String URL_LIST = "http://news.mtime.com/[a-z]+/";
    public static final String URL_POST = "http://news.mtime.com/\\d+/\\d+/\\d+/\\d+.html"; // 文章页

    private Site site = Site.me().setDomain("mtime.com").setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

        if("http://news.mtime.com/".equals(page.getUrl().toString())){

            List<String> urlPost = page.getHtml()
                    .xpath("//div[@id='leftNews']"
                            + "//ul[@id='newslist']")
                    .links().regex(URL_POST).all();

            if(null != urlPost && urlPost.size() > 0){

                if(checkPage("mtime", page.getUrl().toString(), urlPost.get(0))){
                    page.addTargetRequests(urlPost);
                }else {
                    return;
                }
            }
        }

        if(page.getUrl().regex(URL_LIST).match()
                && !page.getUrl().regex(URL_POST).match()){ // 列表页

            List<String> urlPost = page.getHtml()
                    .xpath("//div[@class='newslist-cont']"
                            + "//ul[@class='list-cont fix']")
                    .links().regex(URL_POST).all();

            if(checkPage("mtime", page.getUrl().toString(), urlPost.get(0))){
                page.addTargetRequests(urlPost);
            }else {
                return;
            }

        } else if(page.getUrl().regex(URL_POST).match()){

            //
            String content = page.getHtml()
                    .xpath("//div[@class='newsl']"
                            + "//div[@id='newsContent']"
                            + "/html()").toString();
            if(null == content){
                return;
            }
            page.putField("articleContent", content);

            String title_1 = page.getHtml()
                    .xpath("//div[@class='newsheadtit']"
                            + "/h2/allText()").toString();

            String title_2 = page.getHtml()
                    .xpath("//div[@class='newsheadtit']"
                            + "/h3/allText()").toString();

            page.putField("articleTitle", title_1 + " > " + title_2);

            page.putField("articleAuthor", null);

            String date = page.getHtml()
                    .xpath("//p[@class='mt15 ml25 newstime ']"
                            + "/text()").toString();
            page.putField("articleDate", date);

            page.putField("articleClass", "news");

            String author = page.getHtml()
                    .xpath("//div[@class='newsl']"
                            + "//p[@class='newsediter']"
                            + "/allText()").toString().trim();

            page.putField("articleUrl", page.getUrl().toString());

            page.putField("articlePage", 1);

            page.putField("articleId", "");

//			page.putField("createdDate", DateUtil.toDateText(DateUtil.now(), DateUtil.DATE_FORMAT));

            page.putField("createdDate", new Date());

            page.putField("sourcePlatform", PlarformEnum.PLARFORM_MTIME);

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = OOSpider.create(new MtimeRepoPageProcessor())
                .addUrl("http://news.mtime.com/2018/03/28/1579311.html")
                .addPipeline(new ElasticsearchPipeline())
                .setExitWhenComplete(true)
                .setScheduler(new QueueScheduler());
        spider.start();
        spider.stop();
    }
}
