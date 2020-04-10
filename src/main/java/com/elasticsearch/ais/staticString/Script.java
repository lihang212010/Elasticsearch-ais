package com.elasticsearch.ais.staticString;

import com.elasticsearch.ais.utils.StringUtils;

/*
 *@author:lihang
 *@email:631533483@qq.com
 */
public class Script {

  public static String getScript(String must, String must_not, String filter_must,
      String filter_must_not, String set) {
    boolean comma = false;
    boolean comma_ = false;
    String script = "{\n" +
        "  \"query\": {\n" +
        "    \"bool\": {\n";
    if (StringUtils.isNotBlank(must)) {
      script = script + "      \"must\": [" + must + "]";
      comma = true;
    }
    if (StringUtils.isNotBlank(must_not)) {
      if (comma) {
        script = script + ",\n";
      }
      script = script + "      \"must_not\": [" + must_not + "]";
    }
    if (StringUtils.isNotBlank(filter_must) || StringUtils.isNotBlank(filter_must_not)) {
      if (comma) {
        script = script + ",\n";
      }
      script = script + "      \"filter\": [\n" +
          "        {\n" +
          "          \"bool\": {\n";
      if (StringUtils.isNotBlank(filter_must)) {
        script = script + "            \"must\": [" + filter_must + "]";
        comma_ = true;
      }
      if (StringUtils.isNotBlank(filter_must_not)) {
        if (comma_) {
          script = script + ",\n";
        }
        script = script + "            \"must_not\": [" + filter_must_not + "]";
      }
      script = script + "\n         }\n" +
          "        }\n" +
          "      ]\n";

    }
    script = script + "     }\n" +
        "  }\n" + set +
        "}";
    return script;
  }


}

