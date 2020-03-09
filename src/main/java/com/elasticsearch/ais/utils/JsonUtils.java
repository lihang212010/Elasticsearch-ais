package com.elasticsearch.ais.utils;

/*
 *@author:lihang
 *@email:631533483@qq.com
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {

    public static JSONObject readJsonFromClassPath(String path) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(path);
        InputStream inputStream = classPathResource.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        inputStream.close();
        result.close();
        return JSONObject.parseObject(result.toString());

    }

    public  static  <T> String replaceEntity(String script,T entity) throws NoSuchFieldException, IllegalAccessException {
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
            matcher.replaceAll(rep.toString());
        }
        return script;
    }


    public  static  String replaceEntity(String script,Class<?> tclass,Object o) throws NoSuchFieldException, IllegalAccessException {
        String regex = "#\\{[^}]*\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(script);
        while (matcher.find()){
            String s=matcher.group();
            String name=s.substring(2,s.length()-1);
            Class t=tclass;
            Field field=t.getDeclaredField(name);
            field.setAccessible(true);
            Object rep=field.get(o);
            script=script.replace(s,rep.toString());
        }
        //script=script.replaceAll(regex, "");
        return script;
    }




}