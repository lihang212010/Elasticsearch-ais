package com.elasticsearch.ais.staticString;

/*@author:lihang
 *@email:631533483@qq.com
 */

import com.elasticsearch.ais.utils.CollectUtil;
import com.elasticsearch.ais.utils.StringUtils;

public class Settings {

    public  static String source(String ... fields){
        if(CollectUtil.isNotEmpty(fields)){
            String script="\"_source\": ["+CollectUtil.commaSplit(fields)+"]";
        }
        return null;
    }

    public static String version(boolean  type){
        String script="\"version\": "+type;
        return script;
    }
    public static String timeout(int i){
        String script="\"timeout\": \""+i+"s\"";
        return script;
    }
    public static String stored_fields(String ... fields){
        if(CollectUtil.isNotEmpty(fields)) {
            String script = "\"stored_fields\": ["+CollectUtil.commaSplit(fields)+"]";
            return  script;
        }
        return null;
    }
    public static String stats(String ...fields){
        if (CollectUtil.isNotEmpty(fields)){
            String script="\"stats\": ["+CollectUtil.commaSplit(fields)+"]";
            return script;
        }
        return null;
    }
    public static String sort(String field,String order){
        if (StringUtils.isNotBlank(order)&&StringUtils.isNotBlank(field)){
            String script="      \"sort\": [\n" +
                    "        {\n" +
                    "            \""+field+"\": {\n" +
                    "            \"order\": \""+order+"\"\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]";
            return script;
        }
        return null;
    }
    public static String sort(String...  fieldorder){
        if (CollectUtil.isNotEmpty(fieldorder)){
            String script="      \"sort\": ["+CollectUtil.brackersSplit(fieldorder)+"]";

            return script;
        }
        return null;
    }

    public static String size(int s){
        String script="\"size\": "+s;
        return script;
    }
    public  static String script_fields(String source){
        if(StringUtils.isNotBlank(source)){
            String script="      \"script_fields\": {\n" +
                    "        \"script_field\": {\n" +
                    "          \"script\": {\"source\": \""+source+"\"}\n" +
                    "        }\n" +
                    "      }";
            return script;
        }
        return null;
    }

    public static String profile(boolean b){
        String script="\"profile\": \""+b+"\"  ";
        return script;
    }

    public static String partial_fields(String []include,String []exclude){
        if(CollectUtil.isNotEmpty(include)||CollectUtil.isNotEmpty(exclude)){
            String script="      \"partial_fields\": {\n" +
                    "        \"pattial\": {\n" +
                    "          \"include\": ["+CollectUtil.commaSplit(include)+"],\n" +
                    "          \"exclude\": ["+CollectUtil.commaSplit(exclude)+"]\n" +
                    "        }\n" +
                    "      }";
            return script;
        }
        return null;
    }

    public static String indices_boost(String [] INDEX,int [] n){
        if(CollectUtil.isNotEmpty(INDEX)&&CollectUtil.isNotEmpty(n)&&INDEX.length==n.length){
            String []script_=new String[INDEX.length];
            for (int i=0;i<INDEX.length;i++){
                script_[i]='"'+INDEX[i]+'"'+':'+n[i];
            }
            String script="\"indices_boost\": [\n" +String.join(",",script_) +"  ]";
            return script;
        }
        return null;
    }

    public static String highlight(String type){
        if(StringUtils.isNotBlank(type)){
            String script="\"highlight\": {  \"fields\": {\""+type+"\": {}}}";
            return script;
        }
        return null;
    }

    public static String highlight(String type,String pre_tags,String post_tags){
        if(StringUtils.isNotBlank(type)&&StringUtils.isNotBlank(post_tags)&&StringUtils.isNotBlank(pre_tags)){
            String script="  \"highlight\": {\n" +
                    "    \"fields\": {\n" +
                    "      \""+type+"\": {}\n" +
                    "    },\n" +
                    "    \"post_tags\": \""+post_tags+"\",\n" +
                    "    \"pre_tags\": \""+pre_tags+"\"\n" +
                    "  }";
            return script;
        }
        return null;
    }

    public static  String from(int i){
        String script=" \"from\": "+i;
        return script;
    }


    public static String explain(boolean b){
        String script="\"explain\": "+b;
        return script;
    }


    public static String docvalue_fields(String... fields){
        if(CollectUtil.isNotEmpty(fields)){
            String script="\"docvalue_fields\": ["+CollectUtil.commaSplit(fields)+"]";
            return script;

        }
        return null;
    }

    public static String collapse(String fields){
        if(StringUtils.isNotBlank(fields)){
            String script="\"collapse\": {\n" +
                    "    \"field\": \""+fields+"\"\n" +
                    "  }";
            return script;
        }
        return null;
    }




}
