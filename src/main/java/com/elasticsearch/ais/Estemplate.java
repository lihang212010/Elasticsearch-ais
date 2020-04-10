package com.elasticsearch.ais;

import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.ais.restClient.RestPerform;
import com.elasticsearch.ais.staticString.*;
import com.elasticsearch.ais.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 *@author:lihang
 *@email:631533483@qq.com
 */
@Component
public class Estemplate {

  @Autowired
  private RestPerform restPerform;

  private List must = new ArrayList<String>();
  private List must_not = new ArrayList<String>();
  private List filter_must = new ArrayList<String>();
  private List filter_must_not = new ArrayList<String>();
  private List set = new ArrayList<String>();
  private String request = "get";

  //private RestPerform restPerform=new RestPerformIpml(new Config());

  public void findAll() {
    must.add(Find.findAll());
  }

  public void setRequest(String method) {
    this.request = method;
  }

  public void add(String query) {
    if (query != null) {
      must.add(query);
    }
  }

  public void setadd(String query) {
    if (query != null) {
      set.add(query);
    }
  }

  public void add(String query, String type) {
    if (query != null) {
      if (type.equals("must_not")) {
        must_not.add(query);
      } else if (type.equals("filter_must")) {
        filter_must.add(query);
      } else if (type.equals("filter_must_not")) {
        filter_must_not.add(query);
      } else {
        must.add(query);
      }
    }
  }


  public void term(String key, Object value) {
    String query = Find.term(key.trim(), value);
    add(query);
  }

  public void findFree(String query) {
    if (StringUtils.isNotBlank(query)) {
      add(query);
    }
  }

  public void term(String key, Object value, double boost) {
    String query = Find.term(key.trim(), value, boost);
    add(query);

  }

  public void match(String key, Object value) {
    String query = Find.match(key.trim(), value);
    add(query);
  }

  public void match(String key, Object value, double boost) {
    String query = Find.match(key.trim(), value, boost);
    add(query);
  }

  public void wildcard(String key, Object value) {
    String query = Find.wildcard(key.trim(), value);
    add(query);
  }

  public void wildcard(String key, Object value, double boost) {
    String query = Find.wildcard(key.trim(), value, boost);
    add(query);
  }

  public void wildcardLeft(String key, Object value) {
    String query = Find.wildcardLeft(key.trim(), value);
    add(query);
  }

  public void wildcardLeft(String key, Object value, double boost) {
    String query = Find.wildcardLeft(key.trim(), value, boost);
    add(query);
  }

  public void wildcardRight(String key, Object value) {
    String query = Find.wildcard(key.trim(), value);
    add(query);
  }

  public void wildcardRight(String key, Object value, double boost) {
    String query = Find.wildcard(key.trim(), value, boost);
    add(query);
  }

  public void terms(String key, Object... value) {
    String query = Find.trems(key.trim(), value);
    add(query);
  }

  public void wildcardFree(String key, Object value) {
    String query = Find.wildcardFree(key.trim(), value);
    add(query);
  }

  public void wildcardFree(String key, Object value, double boost) {
    String query = Find.wildcardFree(key.trim(), value, boost);
    add(query);
  }

  public void match_phrase_prefix(String key, Object value) {
    String query = Find.match_phrase_prefix(key.trim(), value, 2);
    add(query);
  }

  public void match_phrase_prefix(String key, Object value, int slop) {
    String query = Find.match_phrase_prefix(key.trim(), value, slop);
    add(query);
  }

  public void match_phrase_prefix(String key, Object value, int slop, int max_expansions) {
    String query = Find.match_phrase_prefix(key.trim(), value, max_expansions, slop);
    add(query);
  }

  public void match_phrase(String key, Object value, int slop) {
    String query = Find.match_phrase(key.trim(), value, slop);
    add(query);
  }

  public void match_phrase(String key, Object value) {
    String query = Find.match_phrase(key.trim(), value, 2);
    add(query);
  }


  public void common(String key, Object value, double cutoff_frequency) {
    String query = Find.common(key.trim(), value, cutoff_frequency);
    add(query);
  }

  public void exits(String key) {
    String query = Find.exits(key.trim());
    add(query);
  }

  public void fuzzy(String key, Object value, int fuzziness, int prefix_length, double boost) {
    String query = Find.fuzzy(key.trim(), value, fuzziness, prefix_length, boost);
    add(query);
  }

  public void fuzzy(String key, Object value, int fuzziness, int prefix_length) {
    String query = Find.fuzzy(key.trim(), value, fuzziness, prefix_length);
    add(query);
  }

  public void fuzzy(String key, Object value, int fuzziness) {
    String query = Find.fuzzy(key.trim(), value, fuzziness, 0);
    add(query);
  }

  public void fuzzy(String key, Object value) {
    String query = Find.fuzzy(key.trim(), value, 2, 0);
    add(query);
  }

  public void geo_shape(String key, Object coordinates, Object relation) {
    String query = Find.geo_shape(key.trim(), coordinates, relation);
    add(query);
  }


  public void ids(Object... values) {
    String query = Find.ids(values);
    add(query);
  }

  public void multi_match(String value, String... keys) {
    String query = Find.multi_match(value, keys);
    add(query);
  }

  public void more_like_this(int min_term_freq, int max_query_terms, String value, String... keys) {
    String query = Find.more_like_this(min_term_freq, max_query_terms, value, keys);
    add(query);
  }

  public void percolate(String key, Object value, String field) {
    String query = Find.percolate(key.trim(), value, field.trim());
    add(query);
  }

  public void prefix(String key, Object value, double boost) {
    String query = Find.prefix(key.trim(), value, boost);
    add(query);
  }

  public void prefix(String key, Object value) {
    String query = Find.prefix(key.trim(), value);
    add(query);
  }

  public void query_string(String default_field, Object query_) {
    String query = Find.query_string(default_field.trim(), query_);
    add(query);
  }

  public void range(String key, Object gte, Object lte) {
    String query = Find.range(key.trim(), gte, lte);
    add(query);
  }

  public void rangelte(String key, Object lte) {
    String query = Find.rangelte(key.trim(), lte);
    add(query);
  }

  public void rangegte(String key, Object gte) {
    String query = Find.rangegte(key.trim(), gte);
    add(query);
  }

  public void regexp(String key, Object value, int max_determinized_states) {
    String query = Find.regexp(key.trim(), value, max_determinized_states);
    add(query);
  }

  public void regexp(String key, String value) {
    String query = Find.regexp(key.trim(), value);
    add(query);
  }

  public void regexp(String key, String value, int max_determinized_states, String flags) {
    String query = Find.regexp(key.trim(), value, max_determinized_states, flags.trim());
    add(query);
  }

  public void script(String source) {
    String query = Find.script(source.trim());
    add(query, "filter_must");
  }

  public void script(String source, String lang) {
    String query = Find.script(source.trim(), lang.trim());
    add(query, "filter_must");
  }

  public void script(String source, String lang, String params) {
    String query = Find.script(source.trim(), lang.trim(), params.trim());
    add(query, "filter_must");
  }

  public void script(String type, String source, String lang, String params) {
    String query = Find.script(source.trim(), lang.trim(), params.trim());
    add(query, type);
  }

  public void simple_query_string(String value, Object... filed) {
    String query_ = Find.simple_query_string(value.trim(), filed);
    add(query_);
  }

  public void wripper(String query) {
    String query_ = Find.wripper(query.trim());
    add(query_);
  }

  public void should(String... script) {
    String query = Should.should(script);
    add(query);
  }

  public void should(int minimum_should_match, String... script) {
    String query = Should.should(minimum_should_match, script);
    add(query);
  }


  public void term(String type, String key, Object value) {
    String query = Find.term(key.trim(), value);
    add(query, type);
  }

  public void findFree(String type, String query) {
    if (StringUtils.isNotBlank(query)) {
      add(query, type);
    }
  }

  public void term(String type, String key, Object value, double boost) {
    String query = Find.term(key.trim(), value, boost);
    add(query, type);

  }

  public void match(String type, String key, Object value) {
    String query = Find.match(key.trim(), value);
    add(query, type);
  }

  public void match(String type, String key, Object value, double boost) {
    String query = Find.match(key.trim(), value, boost);
    add(query, type);
  }

  public void wildcard(String type, String key, Object value) {
    String query = Find.wildcard(key.trim(), value);
    add(query, type);
  }

  public void wildcard(String type, String key, Object value, double boost) {
    String query = Find.wildcard(key.trim(), value, boost);
    add(query, type);
  }

  public void wildcardLeft(String type, String key, Object value) {
    String query = Find.wildcardLeft(key.trim(), value);
    add(query, type);
  }

  public void wildcardLeft(String type, String key, Object value, double boost) {
    String query = Find.wildcardLeft(key.trim(), value, boost);
    add(query, type);
  }

  public void wildcardRight(String type, String key, Object value) {
    String query = Find.wildcard(key.trim(), value);
    add(query, type);
  }

  public void wildcardRight(String type, String key, Object value, double boost) {
    String query = Find.wildcard(key.trim(), value, boost);
    add(query, type);
  }

  public void terms_not(String key, Object... value) {
    String query = Find.trems(key.trim(), value);
    add(query, "must_not");
  }

  public void wildcardFree(String type, String key, Object value) {
    String query = Find.wildcardFree(key.trim(), value);
    add(query, type);
  }

  public void wildcardFree(String type, String key, Object value, double boost) {
    String query = Find.wildcardFree(key.trim(), value, boost);
    add(query, type);
  }

  public void match_phrase_prefix(String type, String key, Object value) {
    String query = Find.match_phrase_prefix(key.trim(), value, 2);
    add(query, type);
  }

  public void match_phrase_prefix(String type, String key, Object value, int slop) {
    String query = Find.match_phrase_prefix(key.trim(), value, slop);
    add(query, type);
  }

  public void match_phrase_prefix(String type, String key, Object value, int slop,
      int max_expansions) {
    String query = Find.match_phrase_prefix(key.trim(), value, max_expansions, slop);
    add(query, type);
  }

  public void match_phrase(String type, String key, Object value, int slop) {
    String query = Find.match_phrase(key.trim(), value, slop);
    add(query, type);
  }

  public void match_phrase(String type, String key, Object value) {
    String query = Find.match_phrase(key.trim(), value, 2);
    add(query);
  }

  public void common(String type, String key, Object value, double cutoff_frequency) {
    String query = Find.common(key.trim(), value, cutoff_frequency);
    add(query, type);
  }

  public void exits(String type, String key) {
    String query = Find.exits(key.trim());
    add(query, type);
  }

  public void fuzzy(String type, String key, Object value, int fuzziness, int prefix_length,
      double boost) {
    String query = Find.fuzzy(key.trim(), value, fuzziness, prefix_length, boost);
    add(query, type);
  }

  public void fuzzy(String type, String key, Object value, int fuzziness, int prefix_length) {
    String query = Find.fuzzy(key.trim(), value, fuzziness, prefix_length);
    add(query, type);
  }

  public void geo_shape(String type_, String type, Object coordinates, Object relation) {
    String query = Find.geo_shape(type.trim(), coordinates, relation);
    add(query, type_);
  }


  public void ids(String type, Object... values) {
    String query = Find.ids(values);
    add(query, type);
  }

  public void multi_match(String type, String value, Object... keys) {
    String query = Find.multi_match(value, keys);
    add(query, type);
  }

  public void more_like_this(String type, int min_term_freq, int max_query_terms, String value,
      String... keys) {
    String query = Find.more_like_this(min_term_freq, max_query_terms, value, keys);
    add(query, type);
  }

  public void percolate(String type, String key, Object value, String field) {
    String query = Find.percolate(key.trim(), value, field.trim());
    add(query, type);
    ;
  }

  public void prefix(String type, String key, Object value, double boost) {
    String query = Find.prefix(key.trim(), value, boost);
    add(query, type);
  }

  public void prefix(String type, String key, Object value) {
    String query = Find.prefix(key.trim(), value);
    add(query, type);
  }

  public void query_string(String type, String default_field, Object query_) {
    String query = Find.query_string(default_field.trim(), query_);
    add(query, type);
  }

  public void range(String type, String key, Object gte, Object lte) {
    String query = Find.range(key.trim(), gte, lte);
    add(query, type);
  }

  public void rangelte(String type, String key, Object lte) {
    String query = Find.rangelte(key.trim(), lte);
    add(query, type);
  }

  public void rangegte(String type, String key, Object gte) {
    String query = Find.rangegte(key.trim(), gte);
    add(query, type);
  }

  public void regexp(String type, String key, Object value, int max_determinized_states) {
    String query = Find.regexp(key.trim(), value, max_determinized_states);
    add(query, type);
  }

  public void regexp(String type, String key, String value) {
    String query = Find.regexp(key.trim(), value);
    add(query, type);
  }

  public void regexp(String type, String key, String value, int max_determinized_states,
      String flags) {
    String query = Find.regexp(key.trim(), value, max_determinized_states, flags.trim());
    add(query, type);
  }

  public void simple_query_string(String type, String value, Object... filed) {
    String query_ = Find.simple_query_string(value.trim(), filed);
    add(query_, type);
  }

  public void wripper(String type, String query) {
    String query_ = Find.wripper(query.trim());
    add(query_, type);
  }

//    public void should(String type, String... script) {
//        String query = Should.should(script);
//        add(query, type);
//    }
//
//    public void should(String type, int minimum_should_match, String... script) {
//        String query = Should.should(minimum_should_match, script);
//        add(query, type);
//    }


  public void source(String... fields) {
    String script = Settings.source(fields);
    setadd(script);
  }

  public void version(boolean type) {
    String script = Settings.version(type);
    setadd(script);
  }

  public void timeout(int i) {
    String script = Settings.timeout(i);
    setadd(script);
  }

  public void stored_fields(String... fields) {
    String script = Settings.stored_fields(fields);
    setadd(script);
  }

  public void stats(String... fields) {
    String script = Settings.stats(fields);
    set.add(fields);
  }

  public void sort(String field, String order) {
    String script = Settings.sort(field, order);
    setadd(script);
  }

  public void sort(String... fieldorder) {
    String script = Settings.sort(fieldorder);
    setadd(script);
  }

  public void size(Integer s) {
    if (s != null) {
      String script = Settings.size(s);
      setadd(script);
    }
  }

  public void script_fields(String source) {
    String script = Settings.script_fields(source);
    setadd(script);
  }

  public void profile(boolean b) {
    String script = Settings.profile(b);
    setadd(script);
  }

  public void partial_fields(String[] include, String[] exclude) {
    String script = Settings.partial_fields(include, exclude);
    setadd(script);
  }

  public void indices_boost(String[] INDEX, int[] boost) {
    String script = Settings.indices_boost(INDEX, boost);
    setadd(script);
  }

  public void highlight(String key) {
    String script = Settings.highlight(key);
    setadd(script);
  }

  public void highlight(String key, String pre_tags, String post_tags) {
    String script = Settings.highlight(key, pre_tags, post_tags);
    setadd(script);
  }

  public void from(Integer i) {
    if (i != null) {
      String script = Settings.from(i);
      setadd(script);
    }
  }

  public void explain(boolean b) {
    String script = Settings.explain(b);
    setadd(script);
  }

  public void docvalue_fields(String... fields) {
    String script = Settings.docvalue_fields(fields);
    setadd(script);
  }

  public void collapse(String fields) {
    String script = Settings.collapse(fields);
    setadd(script);
  }


  public void setSettings(String settings) {
    setadd(settings);
  }


  private String scriptJoin() {
    String must_ = String.join(",", must);
    String mustNot_ = String.join(",", must_not);
    String filter_must_ = String.join(",", filter_must);
    String filter_must_not_ = String.join(",", filter_must_not);
    String set_ = String.join(",", set);
    String script;
    if (StringUtils.isNotBlank(must_) || StringUtils.isNotBlank(mustNot_) && StringUtils
        .isNotBlank(filter_must_) && StringUtils.isNotBlank(filter_must_not_)) {
      if (StringUtils.isNotBlank(set_)) {
        script = Script.getScript(must_, mustNot_, filter_must_, filter_must_not_, "," + set_);
      } else {
        script = Script.getScript(must_, mustNot_, filter_must_, filter_must_not_, "");
      }
      must.clear();
      must_not.clear();
      filter_must.clear();
      filter_must_not.clear();
      set.clear();
      return script;
    }
    return null;
  }

  public <T> JSONObject insert(T tclass, String index) throws IOException {
    String script = Bulk.insert(tclass);
    return restPerform.executeJSON("POST", index + "/_doc", script);
  }

  public <T> JSONObject insert(List<T> classList, String index) throws IOException {
    String script = Bulk.insert(classList);
    return restPerform.executeJSON("POST", index + "/_bulk", script);
  }

  public <K, V> JSONObject insert(Map<K, V> map, String index) throws IOException {
    String script = Bulk.insert(map);
    return restPerform.executeJSON("POST", index + "/_bulk", script);
  }

  public <I, K, V> JSONObject update(Map<I, Map<K, V>> map, String index) throws IOException {
    if (!map.isEmpty() && !map.values().isEmpty()) {
      String script = Bulk.update(map);
      return restPerform.executeJSON("POST", index + "/_bulk", script);
    }
    return null;
  }

  public <K, V> JSONObject update(Object id, Map<K, V> map, String index) throws IOException {
    if (!map.isEmpty()) {
      String script = Bulk.update(id, map);
      return restPerform.executeJSON("POST", index + "/_bulk", script);
    }
    return null;
  }

  public JSONObject delete(String index, Object... ids) throws IOException {
    String script = Bulk.delete(ids);
    if (StringUtils.isNotBlank(script)) {
      return restPerform.executeJSON("POST", index + "/_bulk", script);
    }
    return null;
  }

  public JSONObject delete_by_query(String index) throws IOException {
    String script = scriptJoin();
    if (StringUtils.isNotBlank(script)) {
      return restPerform.executeJSON("POST", index + "/_delete_by_query", script);
    }
    return null;
  }

  public <T> JSONObject update_by_query(Map<String, T> map, String index) throws IOException {
    String script_ = Bulk.update_by_query(map);
    this.setSettings(script_);
    String script = scriptJoin();
    return restPerform.executeJSON("POST", index + "/_update_by_query", script);
  }


  public <T> List<T> execute(String index, Class<T> tClass) throws IOException {
    String script = scriptJoin();
    return restPerform.execute(request, index + "/_search", script, tClass);
  }


  public <T> List<T> executeAsync(String index, Class<T> tClass)
      throws IOException, InterruptedException {
    String script = scriptJoin();
    return restPerform.executeAsync(request, index + "/_search", script, 1, tClass);
  }


  public JSONObject executeJSON(String index) throws IOException {
    String script = scriptJoin();
    return restPerform.executeJSON(request, index + "/_search", script);
  }


  public JSONObject executeJSONAsync(String index) throws IOException, InterruptedException {
    String script = scriptJoin();
    return restPerform.executeJSONAsync(request, index + "/_search", script, 1);
  }


  public <T> T executeOne(String index, Class<T> tClass) throws IOException {
    String script = scriptJoin();
    return restPerform.executeOne(request, index + "_search", script, tClass);
  }

  public <T> T executeOneAsync(String index, Class<T> tClass)
      throws IOException, InterruptedException {
    String script = scriptJoin();
    return restPerform.executeOneAsync(request, index + "_search", script, 1, tClass);
  }


  public <T> T executeOneAsync(String requestMethod, String url, Class<T> tClass)
      throws IOException, InterruptedException {
    String script = scriptJoin();
    return restPerform.executeOneAsync(requestMethod, url, script, 1, tClass);
  }

  public <T> List<T> execute(String requestMethod, String url, Class<T> tClass) throws IOException {
    String script = scriptJoin();
    return restPerform.execute(requestMethod, url, script, tClass);
  }

  public <T> List<T> executeAsync(String requestMethod, String url, Class<T> tClass)
      throws IOException, InterruptedException {
    String script = scriptJoin();
    return restPerform.executeAsync(requestMethod, url, script, 1, tClass);
  }

  public JSONObject executeJSON(String requestMethod, String url) throws IOException {
    String script = scriptJoin();
    return restPerform.executeJSON(requestMethod, url, script);
  }

  public JSONObject executeJSONAsync(String requestMethod, String url)
      throws IOException, InterruptedException {
    String script = scriptJoin();
    return restPerform.executeJSONAsync(requestMethod, url, script, 1);
  }

  public <T> T executeOne(String requestMethod, String url, Class<T> tClass) throws IOException {
    String script = scriptJoin();
    return restPerform.executeOne(requestMethod, url, script, tClass);
  }
}
