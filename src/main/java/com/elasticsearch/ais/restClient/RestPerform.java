package com.elasticsearch.ais.restClient;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.client.ResponseListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/*
 *@author:lihang
 *@email:631533483@qq.com
 */
@Component
public interface RestPerform {

 String executeString(String method,String endpoint,String script) throws IOException;

 <T> List<T>  execute(String method, String endpoint, String script, Class<T> tClass) throws IOException;
 JSONObject executeJSON(String method, String endpoint, String script) throws IOException;
 <T> T  executeOne(String method, String endpoint, String query, Class<T> tClass) throws IOException;

 <T> List<T>  executeAsync(String method, String endpoint, String query,int num, Class<T> tClass) throws IOException, InterruptedException;
 JSONObject executeJSONAsync(String method, String endpoint, String query,int num) throws IOException, InterruptedException;
 <T> T  executeOneAsync(String method, String endpoint, String query, int num,Class<T> tClass) throws IOException, InterruptedException;
 void executeAsync(String method, String endpoint, String query, ResponseListener responseListener) throws IOException;
}
