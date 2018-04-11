package com.sgj.dao;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class JestClientUtil {

    private static JestClient jestClient;

    private static String indexName = "article_index";
    private static String typeName = "article_type_name";

    private JestClientUtil() {
        if(null == jestClient){
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(new HttpClientConfig
                    .Builder("http://192.168.0.30:9200")
                    .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create())
                    .connTimeout(1500)
                    .readTimeout(3000)
                    .multiThreaded(true)
                    .build());
            jestClient = factory.getObject();
        }
    }

    public static JestClient getJestClient() {
        if(null == jestClient){
            synchronized (JestClientUtil.class) {
                if(null == jestClient){
                    new JestClientUtil();
                }
            }
        }
        return jestClient;
    }

}
