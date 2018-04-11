package com.sgj.webmagic.pipeline;


import com.sgj.dao.JestClientUtil;
import com.sgj.dao.JestESDao;
import com.sgj.service.JestESService;
import com.sgj.utils.VariablePoolUtil;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("MysqlPipeline")
public class MysqlPipeline implements Pipeline {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlPipeline.class);

    @Autowired
    JestESService jestESService;
    @Autowired
    JestESDao jestESDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> mapResults = resultItems.getAll();
        if (null != mapResults && null != mapResults.get("articleTitle")) {
            System.out.print(mapResults.get("articleTitle"));
			System.out.print(", " + mapResults.get("articleClass"));
			System.out.print(", " + mapResults.get("articleAuthor"));
			System.out.println(", " + mapResults.get("articleDate"));
			System.out.println(mapResults.get("articleUrl").toString());
        }

        try{
            Long count = jestESService.termQuery(mapResults.get("articleUrl").toString());
            if(null != count && count > 0){
                LOGGER.info("该链接已经抓取过叻！(*￣︶￣(*￣︶￣)");
                return;
            }
            LOGGER.info("持久化该链接！(*￣︶￣(*￣︶￣)");

            List<Object> list = new ArrayList<>();
            list.add(mapResults);

            boolean result = jestESDao.index(JestClientUtil.getJestClient(), VariablePoolUtil.INDEX_NAME, VariablePoolUtil.TYPE_NAME, list);
            LOGGER.info("持久化结果！ (*￣︶￣(*￣︶￣) -->> " + result);
        }catch (Exception e){

        }


    }
}
