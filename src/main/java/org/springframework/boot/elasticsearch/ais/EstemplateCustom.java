package org.springframework.boot.elasticsearch.ais;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elasticsearch.client.ResponseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.elasticsearch.ais.restClient.RestPerform;


/*
 *@author:lihang
 *@email:631533483@qq.com
 */
public class EstemplateCustom {

  @Autowired
  private RestPerform restPerform;

  /**
   * Executing scripts and parsing returned values.
   *
   * @param requestMethod Request mode(get,put,post,delete)
   * @param url           url
   * @param script        JSON script
   * @param tClass        data type
   * @param <T>           data type
   * @return data
   */
  public <T> T executeOne(String requestMethod, String url, String script, Class<T> tClass)
      throws IOException {
    return restPerform.executeOne(requestMethod, url, script, tClass);
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param requestMethod Request mode(get,put,post,delete)
   * @param url           url
   * @param script        JSON script
   * @param tClass        data type
   * @param <T>           data type
   * @return data list
   */
  public <T> List<T> execute(String requestMethod, String url, String script, Class<T> tClass)
      throws IOException {
    ;
    return restPerform.execute(requestMethod, url, script, tClass);
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param requestMethod Request mode(get,put,post,delete)
   * @param url           url
   * @param script        JSON script
   * @return data JSON
   */
  public JSONObject executeJSON(String requestMethod, String url, String script)
      throws IOException {
    ;
    return restPerform.executeJSON(requestMethod, url, script);
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param tClass data type
   * @param script String script
   * @param <T>    data type
   * @return data list
   */
  public <T> List<T> excute(String script, Class<T> tClass) throws IOException {
    int index = script.indexOf("\n");
    String[] s = script.trim().substring(0, index).split("\\s+");
    return restPerform.execute(s[0], s[1], script.substring(index), tClass);
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param script String script
   * @param entity data
   * @param <T>    data type
   * @return data list
   */
  public <T> List<T> excute(String script, T entity)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    script = this.replaceEntity(script, entity);
    int index = script.indexOf("\n");
    String[] s = script.trim().substring(0, index).split("\\s+");
    return (List<T>) restPerform.execute(s[0], s[1], script.substring(index), entity.getClass());
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param entity data
   * @param script String script
   * @param <T>    data type
   * @return data JSON
   */
  public <T> JSONObject excuteJSON(String script, T entity)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    script = this.replaceEntity(script, entity);
    int index = script.indexOf("\n");
    String[] s = script.trim().substring(0, index).split("\\s+");
    return restPerform.executeJSON(s[0], s[1], script.substring(index));
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param script String script
   * @return data JSON
   */
  public JSONObject excuteJSON(String script) throws IOException {
    int index = script.indexOf("\n");
    String[] s = script.trim().substring(0, index).split("\\s+");
    return restPerform.executeJSON(s[0], s[1], script.substring(index));
  }

  /**
   * Executing scripts and parsing returned values.
   *
   * @param tClass data type
   * @param script String script
   * @param <T>    data type
   * @return data
   */
  public <T> T excuteOne(String script, Class<T> tClass) throws IOException {
    int index = script.indexOf("\n");
    String[] s = script.trim().substring(0, index).split("\\s+");
    return restPerform.executeOne(s[0], s[1], script.substring(index), tClass);
  }


  /**
   * Executing scripts and parsing returned values.
   *
   * @param entity data
   * @param script String script
   * @param <T>    data type
   * @return data
   */
  public <T> T excuteOne(String script, T entity)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    script = this.replaceEntity(script, entity);
    int index = script.indexOf("\n");
    String[] s = script.trim().substring(0, index).split("\\s+");
    return (T) restPerform.executeOne(s[0], s[1], script.substring(index), entity.getClass());
  }

  /**
   * #{} replace.
   *
   * @param script String script
   * @param <T>    data type
   * @return String script
   */
  private <T> String replaceEntity(String script, T entity)
      throws NoSuchFieldException, IllegalAccessException {
    String regex = "#\\{[^}]*\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(script);
    while (matcher.find()) {
      String s = matcher.group();
      String name = s.substring(2, s.length() - 1);
      Class t = entity.getClass();
      Field field = t.getDeclaredField(name);
      field.setAccessible(true);
      Object rep = field.get(entity);
      script = script.replaceAll("#\\{" + name + "\\}", rep.toString());
    }
    return script;
  }



  public void executeAsync(String method, String url, String script,
      ResponseListener responseListener) throws IOException {
    restPerform.executeAsync(method, url, script, responseListener);
  }





}
