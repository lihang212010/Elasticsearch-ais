

ELASTICSEARCH-AIS

demo地址: [https://github.com/lihang212010/Elasticsearch-ais-demo](https://github.com/lihang212010/Elasticsearch-ais-demo)

javadoc:[http://49.232.76.209:8080/download/apidocs/](http://49.232.76.209:8080/download/apidocs/)

maven导入：pom.xml

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        <dependency>
            <groupId>com.github.lihang212010</groupId>
            <artifactId>ais</artifactId>
            <version>1.0.1</version>
        </dependency>

        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-client</artifactId>
            <version>7.3.1</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
            <optional>true</optional>
        </dependency>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
（elasticsearch-rest-client是官方提供的操作Elasticsearch的方式类似与jabc，fastjson是用来解析结果）


为什么使用ais

对空值的处理
更简单接近原生的代码操作

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
estemplate.term("worker","farmer");  //这是查询worker属性为farmer的结果
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


自动映射结果

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @RequestMapping("terms")
    public List<User> terms() throws IOException {
        estemplate.terms("age",25,35,10,68);
        return estemplate.execute(index,User.class);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


可以直接复制kibana中的代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @RequestMapping("/findCustom")
    public List<User>  findCustom(User user) throws IllegalAccessException, NoSuchFieldException, IOException {
        String script="GET demo/_search\n" +
                "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"wildcard\": {\n" +
                "            \"name\": {\n" +
                "              \"value\": \"#{name}\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"term\": {\n" +
                "            \"id\": {\n" +
                "              \"value\": \"#{id}\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"must_not\": [\n" +
                "        {\n" +
                "          \"range\": {\n" +
                "            \"age\": {\n" +
                "              \"gte\": #{age}\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"from\": \"#{pageFrom}\",\n" +
                "  \"size\": \"#{pageSize}\"\n" +
                "}";
       return estemplateCustom.excute(script,user);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



对复杂需求的处理可以使用json和java代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Elasticsearch
public interface UserJson {
    List<User> findName(String name);
    List<User> findIdName(String name,String id);
}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
{
  "findName": {
    "requestMethod": "GET",
    "index": "demo/_search",
    "script": {
      "query": {
        "wildcard": {
          "name": {
            "value": "#{name}"
          }
        }
      }
    }
  },

  "findIdName": {

    "requestMethod": "GET",
    "index": "demo/_search",
    "script": {
      "query": {
        "bool": {
          "must": [
            {
              "wildcard": {
                "name": {
                  "value": "#{name}"
                }
              }
            },
            {
              "term": {
                "id": {
                  "value": "#{id}"
                }
              }
            }
          ]
        }
      }
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


简单配置
elasticsearch.ais.url=node-3:9200                            集群则用逗号隔开

elasticsearch.ais.aisResource=com.ais                         @Elasticsearch注解的接口位置，不配置则扫描所有路径，会导致springboot启动较慢，建议配置为项目路径

elasticsearch.ais.userName=""                                      用户名

elasticsearch.ais.passWard=""                                       密码

elasticsearch.ais.socketTimeout=30000                        socket连接超时时间

elasticsearch.ais.connectTimeout=10000                       connect连接超时时间

elasticsearch.ais.scheme="http"                                    访问方式

elasticsearch.ais.header=""                                            请求头

elasticsearch.ais.value=""                                                请求头对应值

elasticsearch.ais.jsonPath=static                               json文件路径


