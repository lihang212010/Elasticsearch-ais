package com.elasticsearch.ais.staticString;


import com.alibaba.fastjson.JSON;
import com.elasticsearch.ais.utils.CollectUtil;
import com.elasticsearch.ais.utils.StringUtils;


/*
 *@author:lihang
 *@email:631533483@qq.com
 */
public class Find {

    public static String findAll(){
        String query="{\n" +
                "    \"match_all\": {}\n" +
                "  }";
        return query;
    }

    public static String term(String key,Object value){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
        String query="{\n" +
                "    \"term\": {\n" +
                "      \""+key+"\": {\n" +
                "        \"value\": "+ JSON.toJSONString(value)+"\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        return  query;
        }
        return null;
    }

    public static String term(String key,Object value,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "    \"term\": {\n" +
                    "      \""+key+"\": {\n" +
                    "        \"value\": "+JSON.toJSONString(value)+", \n" +
                    "        \"boost\": "+boost+"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }";
            return  query;
        }
        return null;
    }

    public static String match(String key,Object value){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"match\": {\n" +
                    "     \""+key+"\": \""+value.toString().trim()+"\"\n" +
                    "   }\n" +
                    "  }";
            return  query;
        }
        return null;
    }

    public static String match(String key,Object value,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query=" {\n" +
                    "    \"match\": {\n" +
                    "      \""+key+"\": {\n" +
                    "        \"boost\": "+boost+",\n" +
                    "        \"query\": \""+value.toString().trim()+"\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  } ";
            return  query;
        }
        return null;
    }

    public static String wildcard(String key,Object value){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \"*"+value.toString().trim()+"*\"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  }";
            return  query;
        }
        return null;
    }

    public static String wildcard(String key,Object value,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \"*"+value.toString().trim()+"*\",\n" +
                    "       \"boost\": "+boost+"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  } ";
            return  query;
        }
        return null;
    }
    public static String wildcardLeft(String key,Object value){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \"*"+value.toString().trim()+"\"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  }";
            return  query;
        }
        return null;
    }

    public static String wildcardLeft(String key,Object value,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \"*"+value.toString().trim()+"\",\n" +
                    "       \"boost\": "+boost+"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  } ";
            return  query;
        }
        return null;
    }
    public static String wildcardRight(String key,Object value){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \""+value.toString().trim()+"*\"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  }";
            return  query;
        }
        return null;
    }

    public static String wildcardRight(String key,Object value,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \""+value.toString().trim()+"*\",\n" +
                    "       \"boost\": "+boost+"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  } ";
            return  query;
        }
        return null;
    }

    public static String trems(String key,Object... value){
        if(StringUtils.isNotBlank(key)&& CollectUtil.isNotEmpty(value)){
            String query="{\n" +
                    "   \"terms\": {\n" +
                    "     \""+key+"\":["+CollectUtil.commaSplit(value)+"]\n" +
                    "   }\n" +
                    "  }";
            return  query;
        }
        return null;
    }
    public static String wildcardFree(String key,Object value){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \""+value.toString().trim()+"\"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  }";
            return  query;
        }
        return null;
    }

    public static String wildcardFree(String key,Object value,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "   \"wildcard\": {\n" +
                    "     \""+key+"\": {\n" +
                    "       \"value\": \""+value.toString().trim()+"\",\n" +
                    "       \"boost\": "+boost+"\n" +
                    "     }\n" +
                    "   }\n" +
                    "  } ";
            return  query;
        }
        return null;
    }

    public static String match_phrase_prefix(String key,Object value,int slop){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "          \"match_phrase_prefix\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"query\": \""+value.toString().trim()+"\",\n" +
                    "              \"max_expansions\": "+slop+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }

    public static String match_phrase_prefix(String key,Object value,int max_expansions,int slop){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "          \"match_phrase_prefix\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"query\": \""+value.toString().trim()+"\",\n" +
                    "              \"max_expansions\": "+max_expansions+",\n" +
                    "              \"slop\":"+slop+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }

    public static String match_phrase(String key,Object value,int slop){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "          \"match_phrase\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"query\": \""+value.toString().trim()+"\",\n" +
                    "              \"slop\": "+slop+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }
    public static String common(String key,Object value,double cutoff_frequency){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query=" {\n" +
                    "          \"common\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"query\": \""+value.toString().trim()+"\",\n" +
                    "              \"cutoff_frequency\": "+cutoff_frequency+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }
    public static String exits(String key){
        if(StringUtils.isNotBlank(key)){
            String query=" {\n" +
                    "          \"exists\": {\n" +
                    "            \"field\": \""+key+"\"\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String fuzzy(String key,Object value,int fuzziness,int prefix_length,double boost){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "          \"fuzzy\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.toString().trim()+"\",\n" +
                    "              \"fuzziness\": "+fuzziness+",\n" +
                    "              \"prefix_length\": "+prefix_length+",\n" +
                    "              \"boost\": "+boost+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }

    public static String fuzzy(String key,Object value,int fuzziness,int prefix_length){
        if(StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "          \"fuzzy\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.toString().trim()+"\",\n" +
                    "              \"fuzziness\": "+fuzziness+",\n" +
                    "              \"prefix_length\": "+prefix_length+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }
    public static String geo_shape(String type,Object coordinates,Object relation){
        if(StringUtils.isNotBlank(type)&&StringUtils.isNotEmpty(coordinates)){
            String query="{\n" +
                    "          \"geo_shape\": {\n" +
                    "            \"location\": {\n" +
                    "              \"shape\": {\n" +
                    "                \"type\": \" "+type+"\",\n" +
                    "                \"coordinates\": ["+coordinates.toString().trim()+"]\n" +
                    "              }\n" +
                    "            },\n" +
                    "            \"relation\": \""+relation+"\"\n" +
                    "          }\n" +
                    "        }";
            return  query;
        }
        return null;
    }


    public static String ids(String type,Object... values){
        if(StringUtils.isNotBlank(type)&&CollectUtil.isNotEmpty(values)){
            String query=" {\n" +
                    "          \"ids\": {\n" +
                    "            \"type\": \""+type+"\",\n" +
                    "            \"values\": ["+CollectUtil.commaSplit(values)+"]\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }

    public static String ids(Object... values){
        if(CollectUtil.isNotEmpty(values)){
            String query=" {\n" +
                    "          \"ids\": {\n" +
                    "            \"values\": ["+CollectUtil.commaSplit(values)+"]\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }


    public static String multi_match(Object value,Object... keys){
        if(StringUtils.isNotEmpty(value)&&CollectUtil.isNotEmpty(keys)){
            String query=" {\n" +
                    "          \"multi_match\": {\n" +
                    "            \"query\": \""+value+"\",\n" +
                    "            \"fields\": ["+CollectUtil.commaSplit(keys)+"]\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }

    public static String more_like_this(int min_term_freq,int max_query_terms,Object value,Object... keys){
        if(StringUtils.isNotEmpty(value)&&CollectUtil.isNotEmpty(keys)){
            String query="{\n" +
                    "          \"more_like_this\": {\n" +
                    "            \"fields\": ["+CollectUtil.commaSplit(keys)+"],\n" +
                    "            \"like\": \""+value.toString().trim()+"\",\n" +
                    "            \"min_term_freq\": "+min_term_freq+",\n" +
                    "            \"max_query_terms\": "+max_query_terms+"\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String percolate(String key,Object value,String field){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)&&StringUtils.isNotBlank(field)){
            String query="        {\n" +
                    "          \"percolate\": {\n" +
                    "            \"field\": \""+field+"\",\n" +
                    "            \"document\": {\n" +
                    "              \""+key+"\": \""+value.toString().trim()+"\"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String prefix(String key,Object value,double boost){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query=" {\n" +
                    "          \"prefix\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.toString().trim()+"\",\n" +
                    "              \"boost\": "+boost+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String prefix(String key,Object value){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query=" {\n" +
                    "          \"prefix\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.toString().trim()+"\"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String query_string(String default_field,Object query_){
        if (StringUtils.isNotBlank(default_field)&&StringUtils.isNotEmpty(query_)){
            String query=" {\n" +
                    "          \"query_string\": {\n" +
                    "            \"default_field\": \""+default_field+"\",\n" +
                    "            \"query\": \""+query_.toString().trim()+"\"\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }

    public static  String range(String key,Object gte,Object lte){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(gte)&&StringUtils.isNotEmpty(lte)){
            String query="{\n" +
                    "          \"range\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"gte\": "+JSON.toJSONString(gte)+",\n" +
                    "              \"lte\": "+JSON.toJSONString(lte)+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static  String rangelte(String key,Object lte){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(lte)){
            String query="{\n" +
                    "          \"range\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"lte\": "+JSON.toJSONString(lte)+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static  String rangegte(String key,Object gte){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(gte)){
            String query="{\n" +
                    "          \"range\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"gte\": "+JSON.toJSONString(gte)+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }

    public static String regexp(String key,Object value,int max_determinized_states){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query=" {\n" +
                    "          \"regexp\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.toString().trim()+"\",\n" +
                    "              \"max_determinized_states\": "+max_determinized_states+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String regexp(String key,String value){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)){
            String query="{\n" +
                    "          \"regexp\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.trim()+"\"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }

    public static String regexp(String key,String value,int max_determinized_states,String flags){
        if (StringUtils.isNotBlank(key)&&StringUtils.isNotEmpty(value)&&StringUtils.isNotBlank(flags)){
            String query=" {\n" +
                    "          \"regexp\": {\n" +
                    "            \""+key+"\": {\n" +
                    "              \"value\": \""+value.trim()+"\",\n" +
                    "              \"max_determinized_states\": "+max_determinized_states+",\n" +
                    "               \"flags\": \""+flags+"\"\n"+
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return query;
        }
        return null;
    }
    public static String script(String source){
        if(StringUtils.isNotBlank(source)){
            String script="{\n" +
                    "          \"script\": {\n" +
                    "            \"script\": {\n" +
                    "              \"source\": \""+source+"\"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }";
            return  script;
        }
        return null;
    }

    public static String script(String source,String lang){
        if(StringUtils.isNotBlank(source)&&StringUtils.isNotBlank(lang)){
            String script="  {\n" +
                    "          \"script\": {\n" +
                    "            \"script\": \""+source+"\",\n" +
                    "            \"lang\": \""+lang+"\"\n" +
                    "          }";
            return  script;
        }
        return null;
    }

    public static String script(String source,String lang,String params){
        if(StringUtils.isNotBlank(source)&&StringUtils.isNotBlank(lang)){
            String script="{\n" +
                    "        \"script\": {\n" +
                    "          \"script\": {\n" +
                    "            \"source\": \""+source+"\",\n" +
                    "            \"lang\": \""+lang+"\",\n" +
                    "            \"params\": {"+params+"}\n" +
                    "          }\n" +
                    "        }\n" +
                    "      }";
            return  script;
        }
        return null;
    }
    public static String simple_query_string(String query,Object... filed){
        if (StringUtils.isNotBlank(query)&&CollectUtil.isNotEmpty(filed)){
            String script="{\n" +
                    "          \"simple_query_string\": {\n" +
                    "            \"query\": \""+query+"\",\n" +
                    "            \"fields\": ["+CollectUtil.commaSplit(filed)+"]\n" +
                    "          }\n" +
                    "        }";
            return script;
        }
        return null;
    }
    public static String wripper(String query){
        if(StringUtils.isNotBlank(query)){
            String script="{\n" +
                    "          \"wrapper\": {\n" +
                    "            \"query\": \""+query+"\"\n" +
                    "          }\n" +
                    "        }";
            return script;
        }
        return null;
    }



}
