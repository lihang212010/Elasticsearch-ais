package com.elasticsearch.ais.template;

import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.ais.Estemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
 *@author:lihang
 *@email:631533483@qq.com
 */
@Component
public abstract class POJOTemplate<T> {

  @Autowired
  protected Estemplate estemplate;
  protected String index = "";

  public String getIndex() {
    return index;
  }

  public abstract void setIndex();

  public abstract void setConfig(T entity);

  public void setIndex(String index) {
    this.index = index;
  }

  public abstract void findRule(T entity);

  public void deleteRule(T entity) {
    this.findRule(entity);
  }

  public void updateRule(T entity) {
    this.findRule(entity);
  }


  public JSONObject findJson(T entity) throws IOException {
    this.setIndex();
    this.setConfig(entity);
    this.findRule(entity);
    return estemplate.executeJSON("get", index + "/_search");
  }

  public List<T> find(T entity) throws IOException {
    this.setIndex();
    this.setConfig(entity);
    this.findRule(entity);
    return (List<T>) estemplate.execute("get", index + "/_search", entity.getClass());
  }

  public JSONObject delete(T entity) throws IOException {
    this.setIndex();
    this.deleteRule(entity);
    return estemplate.executeJSON("post", index + "/_delete_by_query");
  }

  public JSONObject delete(String... id) throws IOException {
    this.setIndex();
    return estemplate.delete(index, id);
  }

  public JSONObject insert(T tclass) throws IOException {
    this.setIndex();
    return estemplate.insert(tclass, index);
  }

  public JSONObject insert(List<T> classList) throws IOException {
    this.setIndex();
    return estemplate.insert(classList, index);
  }

  public <K, V> JSONObject insert(Map<K, V> map, String index) throws IOException {
    this.setIndex();
    return estemplate.insert(map, index);
  }

  public <V> JSONObject update(T entity, Map<String, V> map) throws IOException {
    this.setIndex();
    this.updateRule(entity);
    return estemplate.update_by_query(map, index);
  }


}
