package com.sgj.webmagic.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.Map;

public abstract class BasePageProcessor implements PageProcessor {

    private static Map<String, String> incrementalFlag = new HashMap<>();
//	private Set<String> urls = new HashSet<String>();

    /**
     * 检查列表页的数据是否有刷新
     * @param domain
     * @param urlList
     * @param urlPost
     * @return
     */
    protected boolean checkPage(String domain, String urlList, String urlPost){

        StringBuilder nowPageClass = new StringBuilder();
        nowPageClass.append(urlList.substring(7, urlList.indexOf(".")));

        String nowPageClassBAttr[] = urlList.split("/");
        switch (nowPageClassBAttr.length) {
            case 2:
                break;
            case 3:
                break;
            case 4:
                nowPageClass.append("-").append(nowPageClassBAttr[3]);
                break;
            default:
                nowPageClass.append("-").append(nowPageClassBAttr[3]).append("-").append(nowPageClassBAttr[4]);
                break;
        }

        String articleId = urlPost.substring(urlPost.lastIndexOf("/")+1, urlPost.lastIndexOf("."));
        String articleOldId = incrementalFlag.get(nowPageClass.toString() + "-" + domain +"-"+1);
        if(!articleId.equals(articleOldId)){
            incrementalFlag.put(nowPageClass.toString() + "-" + domain +"-"+1, articleId);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public abstract Site getSite();

    @Override
    public abstract void process(Page page);
}
