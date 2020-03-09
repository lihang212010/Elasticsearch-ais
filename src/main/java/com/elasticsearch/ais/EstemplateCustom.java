package com.elasticsearch.ais;

import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.ais.restClient.RestPerform;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.ResponseListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@author:lihang
 *@email:631533483@qq.com
 */
public class EstemplateCustom {

    @Autowired
    private RestPerform restPerform;

    public <T> T executeOne(String requestMethod, String url,String script,Class<T> tClass) throws IOException {
        return restPerform.executeOne(requestMethod,url,script,tClass);
    }

    public <T> List<T> execute(String requestMethod, String url, String script, Class<T> tClass) throws IOException { ;
        return restPerform.execute(requestMethod,url,script,tClass);
    }

    public JSONObject executeJSON(String requestMethod,String url,String script) throws IOException { ;
        return restPerform.executeJSON(requestMethod,url,script);
    }


    public <T> List<T> excute(String script,Class<T> tClass) throws IOException {
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.execute(s[0],s[1],script.substring(index),tClass);
    }

    public JSONObject excuteJSON(String script) throws IOException {
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.executeJSON(s[0],s[1],script.substring(index));
    }

    public <T> T excuteOne(String script,Class<T> tClass) throws IOException {
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.executeOne(s[0],s[1],script.substring(index),tClass);
    }

    public <T> List<T> excute(String script,T entity) throws IOException, NoSuchFieldException, IllegalAccessException {
        script=this.replaceEntity(script,entity);
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return (List<T>) restPerform.execute(s[0],s[1],script.substring(index),entity.getClass());
    }

    public <T> JSONObject excuteJSON(String script,T entity) throws IOException, NoSuchFieldException, IllegalAccessException {
        script=this.replaceEntity(script,entity);
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.executeJSON(s[0],s[1],script.substring(index));
    }

    public <T> T excuteOne(String script,T entity) throws IOException, NoSuchFieldException, IllegalAccessException {
        script=this.replaceEntity(script,entity);
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return (T) restPerform.executeOne(s[0],s[1],script.substring(index),entity.getClass());
    }


    private <T> String replaceEntity(String script,T entity) throws NoSuchFieldException, IllegalAccessException {
        String regex = "#\\{[^}]*\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(script);
        while (matcher.find()){
            String s=matcher.group();
            String name=s.substring(2,s.length()-1);
            Class t=entity.getClass();
            Field field=t.getDeclaredField(name);
            field.setAccessible(true);
            Object rep=field.get(entity);
            script = script.replaceAll("#\\{" + name + "\\}", rep.toString());
        }
        return script;
    }

    public <T> T executeOneAsync(String requestMethod, String url,String script,Class<T> tClass) throws IOException, InterruptedException {
        return restPerform.executeOneAsync(requestMethod,url,script,1,tClass);
    }

    public <T> List<T> executeAsync(String requestMethod, String url, String script, Class<T> tClass) throws IOException, InterruptedException { ;
        return restPerform.executeAsync(requestMethod,url,script,1,tClass);
    }

    public JSONObject executeJSONAsync(String requestMethod,String url,String script) throws IOException, InterruptedException { ;
        return restPerform.executeJSONAsync(requestMethod,url,script,1);
    }


    public <T> List<T> excuteAsync(String script,Class<T> tClass) throws IOException {
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.execute(s[0],s[1],script.substring(index),tClass);
    }

    public JSONObject excuteJSONAsync(String script) throws IOException, InterruptedException {
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.executeJSONAsync(s[0],s[1],script.substring(index),1);
    }

    public <T> T excuteOneAsync(String script,Class<T> tClass) throws IOException, InterruptedException {
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.executeOneAsync(s[0],s[1],script.substring(index),1,tClass);
    }

    public <T> List<T> excuteAsync(String script,T entity) throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        script=this.replaceEntity(script,entity);
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return (List<T>) restPerform.executeAsync(s[0],s[1],script.substring(index),1,entity.getClass());
    }

    public <T> JSONObject excuteJSONAsync(String script,T entity) throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        script=this.replaceEntity(script,entity);
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return restPerform.executeJSONAsync(s[0],s[1],script.substring(index),1);
    }

    public <T> T excuteOneAsync(String script,T entity) throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        script=this.replaceEntity(script,entity);
        int index=script.indexOf("\n");
        String []s=script.trim().substring(0,index).split("\\s+");
        return (T) restPerform.executeOneAsync(s[0],s[1],script.substring(index),1,entity.getClass());
    }

    public void executeAsync(String method, String url, String script, ResponseListener responseListener) throws IOException {
       restPerform.executeAsync(method,url,script,responseListener);
    }


}
