# Elasticsearch-ais
ELASTICSEARCH-AIS

demo地址：[https://github.com/lihang212010/Elasticsearch-ais-demo](https://github.com/lihang212010/Elasticsearch-ais-demo)
使用手册 ：

English explanation: [https://github.com/lihang212010/Elasticsearch-ais/blob/master/README%EF%BC%88English).md](https://github.com/lihang212010/Elasticsearch-ais/blob/master/README%EF%BC%88English).md)



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


ESTEPLATE，最简单的操作
Esteplate是ais中最简单也是最常用的操作Elasticsearch的操作方式，它可以让你只用几行代码就轻松实现所需要的功能。

在使用的时候我们只需要借助spring中的注入，便可以轻松使用

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Autowired
    Estemplate estemplate;
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~





下面我简单的写出几个使用例子，这些例子只是展示ais操作的简单。

插入

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Autowired
    Estemplate estemplate;
    
    @RequestMapping("/insert1")
    public void insert1(List<User> list,String index) throws IOException {
      estemplate.insert(list,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


User是一个实体类实际上我们的操作只有 estemplate.insert(list,index); 这么一行，index是对应插入的索引位置，list则是一个实体类List，ais会自动排除List中的空值，所以并不需要我们对空值进行判断。

当然除了这些ais还支持Map对象的插入和单一实体类的插入。

删除


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Autowired
    Estemplate estemplate;

    @RequestMapping("/delete")
    public void delete(String index,String id) throws IOException {
        estemplate.delete(index,id);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

index是需要删除的对应索引，id则是需要删除的id


查询
查询是Elasticsearch中最重要最常用的功能

而ais简化了足够平常工作需求的查询操作，对于过于复杂的操作，Elasticsearch也提供了类似Mybatis的方式进行操作。


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Autowired
    Estemplate estemplate;    
    
    public List<User> find() throws IOException {
        estemplate.wildcard("name","li");
        estemplate.term("worker","farmer");
        estemplate.rangelte("age",18);
        return estemplate.execute("/demo/user",User.class);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


比如我们需要查询name属性中包含li，worker属性等于farmer，age小于等于18的对象，只需要3行查询和一行执行即可。




插入
ais支持3种方式的插入,批量插入和单一一条数据的插入，它插入的对应api只有一行

estemplate.insert(list,index);

list为需要插入的数据，它支持3种方式，List，Map，和单一实体类，index为对应索引（elasticsearch7中抛弃了type）


对于我们需要插入一条数据的情况，User为对应实体类

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   
    @Autowired
    Estemplate estemplate;
    

    public void insert3(String index,User user) throws IOException {
        estemplate.insert(user,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



对于需要插入一组数据的情况

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    public void insert1(List<User> list,String index) throws IOException {
      estemplate.insert(list,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



对于需要插入一组数据并且自定义每一个数据的对应id时


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void insert2(Map<Object,User> map,String index) throws IOException {
        estemplate.insert(map,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

map中的键为你需要设置的id，值则是对应的数据






查询
查询是Elasticsearch中最重要的操作，Esteplate封装的查询与Elasticsearch中的查询操作是非常相似的，如果你熟练使用kibana，则可以无难度使用ais的查询，当然，你如果没有操作过kibana，也可以很快上手ais的查询。




查询规则
Esteplate默认所有查询为query查询，但是同样支持过滤查询，这里简单说一下Elasticsearch的2种查询，过滤查询（filter）和评分查询（query），评分查询会对查询结果进行打分排序，但性能方面略次于过滤查询，因此我们可以对不要求查询结果的选项进行过滤查询

Esteplate的将查询分为4类

must                  必须满足的查询

must_not         必须不满足的查询

filter_must               必须满足的过滤查询

filter_must_not      必须不满足的过滤查询

Esteplate的默认查询为must查询，但是可以在第一个参数中修改本次查询的查询方式

如：
estemplate.term("worker","farmer");  //这是查询worker属性为farmer的结果

estemplate.term("must_not","worker","farmer");   //这是查询worker属性不为farmer的结果

estemplate.term("filter_must","worker","farmer");   //这是通过过滤查询查询worker属性为farmer的结果

estemplate.term("filter_must_not","worker","farmer");   //这是通过过滤查询查询worker属性不为farmer的结果

Esteplate会默跳过空值
比如

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        estemplate.wildcard("name","li");
        estemplate.term("worker","");
        estemplate.rangelte("age",18);
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

worker如果为空值，则本次查询将只对name和age进行查询


查询相关API
这些API如果你操纵过kibana，一定会非常属性，他和Elasticsearch中的名字是相同的，如果你不太明白，本文将提供一些简单的使用场景，对于每一个查询的具体内容，更建议Elasticsearch官方文档
.
.
.
查询常用参数
type，           查询的方式，默认为must，可以选择must_not，filter_must ，filter_must_not

key，             对应字段，查询的对应属性在Elasticsearch中名称

value，          需要查询的对应值

index              对应索引

boost              排名分数比例，默认为1

具体API


查询相关的API

term              准确查询，必备参数：key，value            可选参数type，boost

wildcard       左右模糊查询，必备参数:key，value   可选参数：type，boost

match           分词查询，必备参数key，value    可选参数type，boost

wildcardLeft                 左模糊查询，必备参数:key，value   可选参数：type，boost

wildcardRight                右模糊查询，必备参数:key，value   可选参数：type，boost

wildcardFree                 自定义模糊查询，必备参数:key，value   可选参数：boost

terms           包含查询，必备参数key，value，这里的value可以是数组和一个数，可选参数：type

match_phrase         短语匹配，必备参数key，value，可选参数：slop：作用为词与词之间允许相隔的单词数目;          type

match_phrase_prefix      短语前缀匹配，必备参数key，value，可选参数：slop：词与词之间允许相隔的单词数目，max_expansions：指定前缀最多匹配多少结果;         type

common                    高频率查询，通过排除文章中的常用词来提高查询结果，不影响性能，必备参数：key，value，cutoff_frequency：排除文章中出现频率多少的词，可选参数：type

exits                          判断某一属性是否存在，必备参数key，可选参数：type

fuzzy                          纠错查询，会允许输入的参数有多少错误，必备参数：key，value，可选参数：fuzziness：允许多少错误，prefix_length：精确查询的长度，type

geo_shape              地理位置查询，必备参数：key，coordinates：地理位置坐标，通常为一个多维数组，relation：查询方式

ids                             id查询，必备参数values，可以是一个id，也可以是一个id组成的数组,可选参数：type

multi_match          多字段分词查询，必备参数value，需要查找的值，keys，对应的字段数组，可选参数：type

more_like_this      必须包含词查询，要求某（一个或多个）字段内容必须包含某一分词，必备参数：value：必须包含的分词，keys：字段，可以是一个或多个，min_term_freq：最小包含数目，
max_query_terms：最多包含数目，可选参数：type

percolate                 索引属性查询，对某一字段的索引属性进行查询，必备字段：key，value，field：存索引查询 的类型的字段，可选参数，type

prefix                        左模糊（较为推荐），必备字段，key，value，可选参数：boost，type

query_string           较为严格的字符串查询（可以进行和或非等操作，但如果查询中包含无效内容，则查询失败），必备字段：default_field：对应字段，query_：查询内容，可选参数：type


range                         范围查询，必备参数：key，gte：大于等于的数值，lte小于等于的数值，可选参数：type

rangegte                    大于等于范围查询，必备参数：key，gte：大于等于的数值，可选参数：type

rangelte                         小于等于范围查询，必备参数：key，lte小于等于的数值，可选参数：type

regexp                         正则查询，必备参数：key，value，可选参数：max_determinized_states：查询所需最大数目，默认10000,flags：可选运算符，type

script                          脚本，可以自由植入自己想进行的语句，必备参数：source：植入的脚本，可选参数，lang，params，type

simple_query_string         使用较为简单的符号操作进行查询（如+-|等），查询更为严格，必备参数：value，filed：一个或多个字段，可选参数：type

wripper                        接受其他查询内容的查询，必备参数：query：其余查询的base64编码，可选参数：type

findFree                        插入Elasticsearch原生的语句，默认参数：query：插入的语句，可选参数：type

should                       可以进行或之类的操作，也可以进行加分，minimum_should_match：最少满足多少项


.
.
.
处理结果相关的API（这些操作较为简单，不依依展示，将会在几个常用查询例子中展示）

source                     字段筛选，可以让结果之显示几个字段，参数：fields：一个或多个字段

version                   是否显示版本号

timeout                  查询时间

stored_fields        另一种不太被推荐的字段筛选

stats                      结果统计

sort                        排序，如果需要对多个值进行排序，请在奇数位写需要排序的字段，偶数位写排序规则

size                         （分页）每一页的最大数目

from                       （分页）从第几页开始查询

script_fields           脚本

profile                      是否查看具体的聚合搜索过程以及具体耗时情况

partial_fields          更加强大更为推荐的字段筛选


indices_boost         相关度控制

highlight                   高亮，可选字段pre_tags：高亮字段前面添加内容，post_tags：后面添加内容

explain                      是否开启查看如何评分

collapse                    字段折叠

docvalue_fields      另一种查询，节省空间但会禁止使用sort、aggregate、access the field from script等



查询结果：（以下3种查询均匀异步查询方法，使用方法类似）

excute             获得所有查询结果并映射到实体类，参数：index：索引名，tClass：需要的返回值类型，requestMethod：请求方式

excuteOne       执行所有查询，参数：index：索引名，tClass：需要的返回值类型，requestMethod：请求方式，tClass：需要的返回值类型

excuteJson      执行所有查询并返回最原始的就是哦你结果，参数：index：索引名，requestMethod：请求方式



如果你对这些查询有所疑问，接下来将会用几个简单的例子展示这些查询






WILDCARD
wildcard是模糊查询，假如，我们有这样一组数据


在ais中我们可以这样
   estemplate.wildcard("name","范");
   这样便会查询于所有名字中包含“范”的内容
   而在kibana中的查询是这样的
   
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

GET demo/_search
{
  "query": {
    "wildcard": {
      "name": {
        "value": "*范*"
      }
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   
 estemplate.wildcardLeft("name","范");  
 这样则是查询name中所有范开头的内容

 estemplate.wildcardRight("name","范");  
 这样则是查询name中所有范结尾的内容
   
  estemplate.wildcardFree("name","*范*");  
 使用wildcardFree则需要自己插入匹配符号*或者.        *是匹配多个字符，.是匹配一个
 
   
  

TERM


term是准确搜索
Elasticsearch一些版本中对字段属性中含有keyword必须加keyword，没有keyword属性则无所谓

estemplate.term("name.keyword","奚范");
查询name中值为奚范的熟悉

kibana中

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "term": {
      "name.keyword": {
        "value": "奚范"
      }
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


MATCH
match是分词查询，什么是分词查询


比如：我们是朋友

它是由我们，朋友，是3个词组成的

在elasticsearch使用match查询这3个词中的任意一个都应该得到我们是朋友结果。

同样我们的查询内容也会被分词，然后去和文章内容的分词比较

match查询是elasticsearch中的核心查询，对于中文查询来说比较著名的是IK分词器
在ais中
estemplate.match("name","奚范");

在kibana中

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "match": {
      "name": {
        "query": "奚范"
      }
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


TERMS
terms又叫包含查询
例如
estemplate.terms("age",25,35,10,68);

它可以查询age属性为23,35,10,68的所有数据，也可以查询age是一个数组，数组中有这几个数字中的数据
比如在程序中执行后这是我得到的一些结果


在kibana中的terms

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "terms": {
      "age": [10,25,35,68]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~






MATCH_PHRASE
match_phrase是短语查询




estemplate.match_phrase("title","We  friends",2);    

2是允许词与词之间最大相隔几个单词
在文中title的内容为We are good friends，而我们查询的内容是We friends，中间少一个good，但是我们允许词与词之间可以有2个相隔单词，所以可以查询到对应内容


kibana中语句

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "match_phrase": {
      "title": {
        "query": "We  friends",
        "slop": 2
      }
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~







MATCH_PHRASE_PREFIX
match_phrase_prefix 比起match_phrase多了个前缀查询


还是这组数据
estemplate.match_phrase_prefix("title","We  good f",2);

这句话的意思是查询语句中We are good然后下一个单词的首字母为f开头的数据，可以允许最多空2个词


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match_phrase_prefix": {
            "title": {
              "query": "We good f",
              "max_expansions": 10,
              "slop":2
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



COMMON
common主要作用是排除查询选项中一些常用词比如英语中的is，are，汉语中的是，我，他之类

参数：cutoff_frequency：出现的频率

使用方式

estemplate.common("title","We are",0.01);

kibana中

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match_phrase_prefix": {
            "title": {
              "query": "We good f",
              "max_expansions": 10,
              "slop":2
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


EXITS
extis   判断某一属性是否存在
   
   estemplate.exits("title");
   
   kibana中使用方式
   
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "common": {
            "title": {
              "query": "We are",
              "cutoff_frequency": 0.01
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


FUZZY
fuzzy是一种允许错误的term查询
参数：fuzziness：允许多少错误

estemplate.fuzzy("name","李松",1);
因为允许一个出差所以可以查询李张，和张松这些数据





kibana中的

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "fuzzy": {
      "name": {
        "value": "孙a",
        "fuzziness": 1
      }
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


IDS
查询某一个id，注意Elasticsearch一般是独立与存储数据的，所以一般会设置id等于数据中的某一字段

使用方式

estemplate.ids("09EprnABqEAuwq_e8YJH","19EprnABqEAuwq_e8YJH");

查询这2个id的对应数据



或者

estemplate.ids("09EprnABqEAuwq_e8YJH");

查询此id对应数据


kibana中

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "ids": {
            "values": [
              "09EprnABqEAuwq_e8YJH",
              "19EprnABqEAuwq_e8YJH"
            ]
          }
        }
      ]
    }
  }
}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


MULTI_MATCH
multi_match          多字段分词查询
.
estemplate.multi_match("are","name","title");

查询在name和title字段中含有are单词的数据
.
kibana中代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "multi_match": {
            "query": "are",
            "fields": [
              "name",
              "title"
            ]
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


MORE_LIKE_THIS
more_like_this      必须包含词查询，要求某（一个或多个）字段内容必须包含某一分词，必备参数：value：必须包含的分词，keys：字段，可以是一个或多个，min_term_freq：最小包含数目，
  max_query_terms：最多包含数目，可选参数：type
.
estemplate.more_like_this(1,10,"are","title");

作用：查询出字段title中含有1-10个are的数据
.
kibana中代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "more_like_this": {
            "fields": [
              "title"
            ],
            "like": "are",
            "min_term_freq": 1,
            "max_query_terms": 10
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


PREFIX
左模糊查询

estemplate.prefix("name","李");

查询所有姓“李”的

.
kibana中代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "prefix": {
            "name": {
              "value": "李"
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


QUERY_STRING
query_string           较为严格的字符串查询（可以进行和或非等操作，但如果查询中包含无效内容，则查询失败），必备字段：default_field：对应字段，query_：查询内容，可选参数：type

estemplate.query_string("name","(孙李) OR (李周)");

查询name为孙李或者李周的人
.

kibana中

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "query_string": {
            "default_field": "name",
            "query": "(孙李)OR(李周)"
          }
        }
      ]
    }
  }
}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


RANGE
range                         范围查询，必备参数：key，gte：大于等于的数值，lte小于等于的数值，可选参数：type
.
estemplate.range("age",10,100);

查询age在10和100之间的数据
.

estemplate.rangelte("age",100);

查询age小于100的数据

estemplate.rangegte("age",10);

查询age大于10的数据
.
kibana中语句

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "range": {
            "age": {
              "gte": 10,
              "lte": 100
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


REGEXP
regexp                         正则查询，必备参数：key，value，可选参数：max_determinized_states：查询所需最大数目，默认10000,flags：可选运算符，type
.
estemplate.regexp("name","孙.?");

查询孙开头的字

当然正则表达式的写法很多，几乎可以满足所有需求，大家可以自行百度
.
kibana中代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "regexp": {
            "name": {
              "value": "孙.?"
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


SCRIPT
script                          脚本，可以自由植入自己想进行的语句，必备参数：source：植入的脚本，可选参数，lang，params，type

script是高级elasticsearch程序员的必备技能，它使用了另一种语言，本文将不在具体解释，只提供一个简单例子
.
estemplate.script("doc['age']>10");

查询age大于10的数据
.
kibana中代码

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
GET demo/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "regexp": {
            "name": {
              "value": "孙.?"
            }
          }
        }
      ]
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



FINDFREE
findFree                        插入Elasticsearch原生的语句，默认参数：query：插入的语句，可选参数：type

对于api中没有的查询方式或者更为麻烦的查询方式或者想自己拼写代码的人可以使用

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        String query="{\n" +
                "    \"term\": {\n" +
                "      \"name.keyword\": {\n" +
                "        \"value\": \"奚范\"\n}" +
                "      }\n" +
                "    }";
        estemplate.findFree(query);
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




SHOULD
should是或操作，在ais中使用着稍显复杂
estemplate.should(1,Find.term("name","张三"),Find.term("name","liu"));
这个是在后面的2个精确情况下必须满足其中其中一个
Find是一个静态类，他的使用方式和Esteplate相似

几个常用的查询例子

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class Finds {


    @Autowired
    Estemplate estemplate;

    private String index="demo";


    /*
    * 这个查询为age在10和48之间,但不在22-24的，name首字母为李的，title中含有are，worker是Teacher或者Doctor的从0位开始到第20位
    * */
    @RequestMapping("find1")
    public List<User> find1() throws IOException {
        estemplate.range("age",10,48);
        estemplate.range("must_not","age",22,24);
        estemplate.prefix("name","李");
        estemplate.wildcard("title","are");
        estemplate.terms("worker","Teacher","Doctor");
        estemplate.from(0);
        estemplate.size(20);
        return estemplate.execute(index,User.class);
    }

    /*
    * 姓名是张三或者liu的age大于20的
    * */
    @RequestMapping("find2")
    public List<User> find2() throws IOException {
        estemplate.script("doc['age']>20");
        estemplate.should(1,Find.term("name","张三"),Find.term("name","liu"));
        estemplate.from(0);
        estemplate.size(20);
        return estemplate.execute(index,User.class);
    }






}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


在前后端分离或者微服务中的简单使用

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class FindController {


    @Autowired
    Estemplate estemplate;

    private String index="demo";


    /*
     * User为实体类,ais中默认会排除空值，所以并不需要检测空值
     * */
    @RequestMapping("find")
    public List<User> find(User user) throws IOException {
        estemplate.prefix("name",user.getName());
        estemplate.wildcard("title",user.getTitle());
        estemplate.term("age",user.getAge());
        estemplate.wildcard("data",user.getDate());
        estemplate.from(0);
        estemplate.size(20);
        return estemplate.execute(index,User.class);
    }



}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


修改
修改有3种，一种是修改单一属性的

Map中的key为你要修改的字段，value为要修改的值

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void update(String id, Map<String,Object> map,String index) throws IOException {
        estemplate.update(id,map,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



这一种是批量修改，第一个Map的key是id，Map中的Map的key为你要修改的字段，value为要修改的值

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void update1(Map<String,Map<String,Object>> map,String index) throws IOException {
        estemplate.update(map,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



还有一种是借助查询规则的修改
例如下面的是将所有名字中包含李的修改title为***

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @RequestMapping("/update_by_query")
    public void update_by_query() throws IOException {
        estemplate.wildcard("name","李");
        Map<String,String> map=new HashMap();
        map.put("title","We are good friends. Our friendship will last forever");
        estemplate.update_by_query(map,index);
    }

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


删除
删除ais有2种删除

一种是根据id删除,可以根据一个或多个id

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void delete(String index,String id) throws IOException {
        estemplate.delete(index,id);
    }


    public void delete(String index,String ...id) throws IOException {
        estemplate.delete(index,id);
    }

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



另一种是借助查询规则进行删除,比如下面的例子是删除所有age等于10岁的

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void delete_query() throws IOException {
        estemplate.term("age","10");
        estemplate.delete_by_query("demo");
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


POJOTEMPLATE-一个实体类的增删改差
POJOTemplate是一个对实体类进行规则定义的模板，在使用它的时候我们只需要继承POJOTemplate这个抽象类，然后定义相对应的查询规则
例如：

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Repository
public class UserTemplate extends POJOTemplate<User> {

    /*
    * 设置索引
    * */
    @Override
    public void setIndex() {
        this.index="demo";
    }
    /*
    * 设置分页等属性
    * */
    @Override
    public void setConfig(User entity) {
       estemplate.from(entity.getPageFrom());
       estemplate.size(entity.getPageSize());
    }

    /*
    * 设置可以通过id和name进行查询
    * */
    @Override
    public void findRule(User user) {
        estemplate.term("id",user.getId());
        estemplate.wildcard("name.keyword",user.getName());
    }


}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

首先我们要定义他对应的索引名字，然后定义它的一些设置规则（如分页高亮等）和查询规则，然后我们便可以根据对应的id或者name进行查询，修改，删除等操作（默认删除和修改使用的是查询规则）


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class FindController {

    @Resource
    UserTemplate userTemplate;

    @RequestMapping("findUser")
    public List<User> findUser(User user) throws IOException {
        return userTemplate.find(user);
    }

    @RequestMapping("updateUser")
    public String updateUser(User user) throws IOException {
        try {
            Map<String,String> map=new HashMap<>();
            map.put("name","走姊姊");
            return userTemplate.update(user,map).toString();
        }catch (Exception e){
            return e.toString();
        }

    }

    @RequestMapping("insertUser")
    public String insertUser(User user){
        try {
           return userTemplate.insert(user).toString();
        } catch (Exception e) {
            return "error" + e;
        }
    }

    @RequestMapping("deleteUser")
    public String deleteUser(User user){
        try {
            return userTemplate.delete(user).toString();
        }catch (Exception e){
            return e.toString();
        }
    }


}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


模板使用的一些规则
POJO是一种非常容易的对elasticsearch操作的方法，使用它可以我们甚至将实体类中没一个属性都去写一个查询规则，将查询，修改，删除，增加等操作简化成一个类。
但是使用它的时候要注意一点
默认删除，查询和修改使用的规则都是查询规则，他们能够共同使用的原因是因为Esteplate会自动跳过对空值的查询，因此，如果你需要使用自定义查询findFree时，请做好空值处理。

另外，如果你不想自己的删除修改和查询使用同一规则，那么你可以给他们没一个都定义对应的规则
如：

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Repository
public class UserTemplate extends POJOTemplate<User> {

    @Override
    public void setIndex() {
        this.index="demo";
    }
    @Override
    public void setConfig(User entity) {
       estemplate.from(entity.getPageFrom());
       estemplate.size(entity.getPageSize());
    }

    /*
    * 设置可以通过id和name进行查询
    * */
    @Override
    public void findRule(User user) {
        estemplate.term("id",user.getId());
        estemplate.wildcard("name.keyword",user.getName());
    }

    /*
    * 设置的只可以使用id进行删除
    * */
    @Override
    public void deleteRule(User user){
        estemplate.term("id",user.getId());
    }

    /*
     * 设置的只可以使用name进行修改
     * */
    @Override
    public void updateRule(User user){
        estemplate.term("name",user.getId());
    }

}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


@ELASTICSEARCH-最为自由的查询
@Elasticsearch是一种类似与Mybatis的查询方式，不过相对应的是把xml查询文件修改为json文件，原因是elasticsearch的查询方式是基于json的
首先说下json的规则，json文件名必须与接口的文件名相同，然后json的第一级是对应的方法，第二季有3个属性，index对应索引位置，requestMethod请求方式，script对应脚本，对于需要替换的属性，使用#{}标注（数字等在ais中同样使用引号，负责#{}便不是json的格式了），然后使用@Elasticsearch只有3种返回结果，JSONObject,String,List<Entity>格式，对于正常的查找，我们使用list格式即可，其余操作按情况而定。
另外在使用@Autowired时，ais之默认把接口名字的首字母小写后注入为bean，
比如接口名字为FInd
则
@Autowired
Find find;


下面简单看下它的几个例子

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



~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Elasticsearch
public interface UserSenior {
    List<User> findSenior(User user);
    List<User> findSeniorTest(User user);
}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
{
  "findSenior": {
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
          ],
          "must_not": [
            {
              "range": {
                "age": {
                  "gte": "#{age}"
                }
              }
            }
          ]
        }
      }
    }
  },
  "findSeniorTest": {
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
          ],
          "must_not": [
            {
              "range": {
                "age": {
                  "gte": "#{age}"
                }
              }
            }
          ]
        }
      },
      "from": "#{pageFrom}",
      "size": "#{pageSize}"
    }
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class FindJson {

    @Autowired
    UserJson userJson;

    @Autowired
    UserSenior userSenior;

    @RequestMapping("/findName")
    public List<User> findName(String name){
        return userJson.findName(name);
    }


    @RequestMapping("/findIdName")
    public List<User> findIdName(String name,String id){
        return userJson.findIdName(name,id);
    }

    @RequestMapping("/findSenior")
    public List<User> findSenior(User user){
        return userSenior.findSenior(user);
    }

    @RequestMapping("/findSeniorTest")
    public List<User> findSeniorTest(User user){
        return userSenior.findSeniorTest(user);
    }

}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~





ESTEMPLATECUSTOM，和KIBANA更为结合
@Elasticsearch可以满足你的所有操作，但是当你的操作内容过于简单时候你可以选择EsteplateCuston，它是@Elasticsearch的java代码格式
你可能发现，我们是将所以kibana的代码复制出来，然后将对应的字段值替换成#{}
另外，EsteplateCuston里的方法当然不只有一种，它还可以单纯的去做执行，和一部分异步操作等功能（异步并不完善但可以使用）

它方法中的参数：
index：索引
requestMethod：请求方式
tClass：实体类.class
entity: 对应实体类
url：请求路径


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class FindCustom {

    @Autowired
    EstemplateCustom estemplateCustom;

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

}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


@ELASTICSEARCHASYNC
@ElasticsearchAsync是@Elasticserach的异步操作，他们的使用方式相同但是@ElasticsearchAsync的操作并不太稳定和完善，我会及时完善对应的功能。
