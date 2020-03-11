ELASTICSEARCH-AIS
Why use AIS

Simpler code operation close to kibana


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
estemplate.term("worker","farmer");  //This is the result of querying the worker property as farmer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


Auto map results

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @RequestMapping("terms")
    public List<User> terms() throws IOException {
        estemplate.terms("age",25,35,10,68);
        return estemplate.execute(index,User.class);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


 Code in kibana can be copied directly      

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



JSON and Java code can be used to handle complex requirements

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


TO CONFIGURE
elasticsearch.ais.url=127.0.0.1:9200                                      Clusters are separated by commas 

elasticsearch.ais.aisResource=com.ais                                   @The  interface location of the elasticsearch annotation. If it is not  configured, all paths will be scanned, resulting in slow start of  springboot. It is recommended to configure it as the project path 
     
     
elasticsearch.ais.userName=""                                      userName

elasticsearch.ais.passWard=""                                       passWard

elasticsearch.ais.socketTimeout=30000                        Socket connection timeout

elasticsearch.ais.connectTimeout=10000                       connection timeout

elasticsearch.ais.scheme="http"                                    Request mode

elasticsearch.ais.header=""                                                      Request header 
     
elasticsearch.ais.value=""                                                 Request header corresponding value 

elasticsearch.ais.jsonPath=static                               JSON file path 

ESTEPLATE，SIMPLEST OPERATION
Esteplate is the simplest and most commonly used way to operate elastic search in AIS. It allows you to easily implement the required functions in just a few lines of code.

When we use it, we only need to use the injection in spring to make it easy to use it

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Autowired
    Estemplate estemplate;
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~





  I'll write a few simple use examples below, which just show the simplicity of AIS operation. 
     

insert

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Autowired
    Estemplate estemplate;
    
    @RequestMapping("/insert1")
    public void insert1(List<User> list,String index) throws IOException {
      estemplate.insert(list,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


User is an entity class. In fact, our operation is only estemplate.insert (list, index); in such a row, index is the index position corresponding to the insertion, and list is an entity class list. AIs will automatically exclude the null value in the list, so we do not need to judge the null value.
Of course, in addition to these AIS, it also supports the insertion of map objects and single entity classes.

delete"


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Autowired
    Estemplate estemplate;

    @RequestMapping("/delete")
    public void delete(String index,String id) throws IOException {
        estemplate.delete(index,id);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Index is the corresponding index to be deleted, and ID is the ID to be deleted


query

Query is the most important and commonly used function in elastic search
AIS simplifies the query operations that meet the requirements of normal work. For the operations that are too complex, elastic search also provides a way similar to mybatis.


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


For example, we need to query the objects whose name attribute contains Li, worker attribute equals farmer, and age is less than or equal to 18. Only three lines of query and one line of execution are needed.




INSERT
AIS supports three ways of inserting, batch inserting and single data inserting. Its corresponding API is only one line
estemplate.insert(list,index);
List is the data to be inserted. It supports three methods: list, map, and single entity class. Index is the corresponding index (type is discarded in elastic search 7)


If we need to insert a piece of data, user is the corresponding entity class

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   
    @Autowired
    Estemplate estemplate;
    

    public void insert3(String index,User user) throws IOException {
        estemplate.insert(user,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



For cases where a set of data needs to be inserted

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    public void insert1(List<User> list,String index) throws IOException {
      estemplate.insert(list,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



When you need to insert a set of data and customize the corresponding ID of each data


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void insert2(Map<Object,User> map,String index) throws IOException {
        estemplate.insert(map,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The key in the map is the ID you need to set, and the value is the corresponding data






FIND
Query is the most important operation in elastic search. The query encapsulated by esteplate is very similar to the query operation in elastic search. If you are skilled in using kibana, you can use AIS query without difficulty. Of course, if you have not operated kibana, you can also quickly start AIS query.




QUERY RULES
By default, all queries in esteplate are query queries, but filtering queries are also supported. Here's a brief introduction to two kinds of queries in elastic search, filter queries and score queries. Score queries rank query results, but the performance is slightly inferior to filter queries, so we can filter queries for options that do not require query results
Esteplate divides queries into four categories

Queries that must be satisfied by must

Must not

Filter query that must be satisfied by filter

Filter ABCD must not

The default query of esteplate is most query, but the query method of this query can be modified in the first parameter
Such as:
Estemplate.term ("worker", "farmer"); / / this is the result of querying the worker property as farmer

Estemplate.term ("must not", "worker", "farmer"); / / this is the result of querying that the worker property is not farmer

Estemplate.term ("filter_most", "worker", "farmer"); / / this is the result of querying that the worker property is farmer by filtering

Estemplate.term ("filter ﹐ most ﹐ not", "worker", "farmer"); / / this is the result of querying that the worker property is not farmer by filtering

Esteplate will skip null value by default

such as

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        estemplate.wildcard("name","li");
        estemplate.term("worker","");
        estemplate.rangelte("age",18);
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


If worker is null, only name and age will be queried in this query

API
If you have manipulated kibana, these APIs will be very attribute. Their names are the same as those in elasticsearch. If you don't understand them, this article will provide some simple use scenarios. For the specific content of each query, it is more recommended that the official document of elasticsearch be provided
.
.
.
Query common parameters

type，          The default query method is "must". You can select "must not", filter "must", filter "must not"

key，             Corresponding field. The corresponding property of the query is named in elasticsearch

value，          Corresponding value to query

index              Corresponding index

boost             Ranking score proportion, default is 1

Specific API

Query related API

term              Accurate query, required parameters: key, value optional parameters type, boost  

wildcard       Left and right fuzzy query, required parameters: key, value optional parameters: type, boost 

match           Word segmentation query, required parameters key, value optional parameters type, boost  

wildcardLeft                Left fuzzy query, required parameters: key, value optional parameters: type, boost

wildcardRight             Right fuzzy query, required parameters: key, valueoptional parameters: type, boost 

wildcardFree                Custom fuzzy query, required parameters: key, valueoptional parameters: Boost 

terms           It contains the query and the required parameters key and value. The value here can be an array and a number. The optional parameters are: type 

match_phrase        Phrase matching, required parameters key, value, optional parameters: slop: the number of words allowed to be separated between words; type 

match_phrase_prefix     Phrase prefix matching, required parameters key, value, optional parameters: slop: the number of words allowed to be separated between words, max_expansions: specify the maximum number of results for prefix matching; type 

common                    High frequency query, improve the query result by excluding common words in the article, without affecting the performance. Necessary parameters: key, value, cutoff_frequency: exclude the words with frequency in the article, optional parameter: type 

exits                          Determine whether an attribute exists. The required parameter is key. The optional parameter is type 

fuzzy                         For error correction query, how many errors can be allowed in the input parameters? Required parameters: key, value, optional parameters: fuzzy: how many errors can be allowed, prefix_length: exact query length, type 

geo_shape             For geographic location query, the required parameters are: key, coordinates: geographic location coordinate, usually a multidimensional array, relation: query method

ids                             The required parameter values for ID query can be an ID or an array of IDS. Optional parameter: type 

multi_match          Multi field word segmentation query, required parameter value, value to be searched, keys, corresponding field array, optional parameter: type 

more_like_this      It must contain word query. It is required that the content of a certain (one or more) field must contain a certain participle. The required parameters are: Value: the participle that must be included, keys: the field, which can be one or more. Min_term_freq: the minimum number of participles,
Max? Query? Terms: maximum number, optional parameter: type 

percolate              Index attribute query: query the index attribute of a field. Required fields: key, value, field: the field of the type of index query, optional parameter, type

prefix                      Left fuzzy (recommended), required fields, key, value, optional parameters: boost, type 

query_string          A more strict string query (you can perform and or non equal operations, but if the query contains invalid content, the query fails). Required fields: default ﹣ field: corresponding field, query ﹣ query content, optional parameters: type 

range                         Range query, required parameters: key, GTE: value greater than or equal to, LTE is less than or equal to, optional parameters: type

rangegte                   Greater than or equal to range  

rangelte                     Less than or equal to  

regexp                         Regular query, required parameters: key, value, optional parameters: Max ﹣ determined ﹣ states: maximum number required for query, default 10000, flags: optional operator, type 

script                        Script, you can freely embed the statements you want to make. Required parameters: Source: implanted script, optional parameters, Lang, params, type 

simple_query_string         Use simpler symbolic operation to query (such as + - |), and the query is more strict. The required parameters are: value, file: one or more fields, and the optional parameters are: type

wripper                        To accept queries of other queries, the required parameters are: query: Base64 encoding of other queries, and the optional parameters are: type

findFree                        Insert elasticsearch native statement, default parameter: query: inserted statement, optional parameter: type

should                       You can perform operations like or, or you can add points. Minimum? Should? Match: how many items should be met at least 

.
.
.
API related to processing results (these operations are relatively simple and do not depend on display, which will be shown in several common query examples)

source                     Field filter can display several fields in the result, parameter: fields: one or more fields

version                Show version number or not

timeout                  

stored_fields        Another less recommended field filter

stats                     Result statistics

sort                        Sort. If you need to sort multiple values, write the fields to be sorted in odd digits and the sorting rules in even digits

size                         Maximum number of pages

from                       （Page) starting from page

script_fields           Script

profile                      Whether to view the specific aggregate search process and specific time consumption

partial_fields          More powerful and recommended field filtering

indices_boost         Correlation control

highlight                   Highlight, optional field pre tab: add content before highlighted field, post tab: add content after highlighted field

explain                      Open to view how to score

collapse                  Field folding

docvalue_fields      Another query, which saves space but forbids the use of sort, aggregate, access the field from script, etc




Query results: (the following three query methods are uniform and asynchronous, with similar usage)

excute             Get all query results and map them to entity class, parameter: index: index name, Tclass: required return value type, requestmethod: request method

excuteOne       Execute all queries, parameters: index: index name, Tclass: required return value type, requestmethod: request method, Tclass: required return value type

excuteJson      Execute all queries and return the original results. Parameters: index: index name, requestmethod: request method



If you have any questions about these queries, we will show them in a few simple examples






WILDCARD
Wildcard is a fuzzy query, if we have such a set of data


We can do this in AIS
   estemplate.wildcard("name","范");
In this way, you can query the contents with "范" in all names

The query in kibana is like this
   
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
This is to query all the contents beginning with "范" in name

 estemplate.wildcardRight("name","范");  
This is to query the contents at the end of all "范" in name
   
  estemplate.wildcardFree("name","*范*");  
With wildcardfree, you need to insert a matching symbol * or * to match multiple characters, and. To match one
 
   
  

TERM


Term is accurate search

In some versions of elasticsearch, the keyword must be added to the field attribute if it is included in the field attribute. If there is no keyword attribute, it doesn't matter

estemplate.term("name.keyword","奚范");
Query the familiarity of "奚范" in name

kibana

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
more_like_this      必须包含词查询，要求某（一个或多个）字段内容必须包含某一分词，必备参数：value：必须包含的分词，keys：字段，可以是一个或多个，min_term_freq：最小包含数目，max_query_terms：最多包含数目，可选参数：type
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

COMMON QUERY EXAMPLES

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class Finds {


    @Autowired
    Estemplate estemplate;

    private String index="demo";


    /*
    * This query indicates that the age is between 10 and 48, but not between 22-24. The initial of the name is Li. The title contains are. The worker is the teacher or the doctor from 0 to 20
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
    * The name is Zhang San or Liu's age is greater than 20
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


COMMON QUERY EXAMPLES -MVC

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@RestController
public class FindController {


    @Autowired
    Estemplate estemplate;

    private String index="demo";


    /*
     *User is an entity class, and null values are excluded by default in AIS. Therefore, null values do not need to be detected
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


UPDATE
There are three types of modification: one is to modify a single attribute
The key in the map is the field you want to modify, and the value is the value you want to modify

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void update(String id, Map<String,Object> map,String index) throws IOException {
        estemplate.update(id,map,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



This is batch modification. The key of the first map is ID, the key of the map is the field you want to modify, and the value is the value you want to modify

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void update1(Map<String,Map<String,Object>> map,String index) throws IOException {
        estemplate.update(map,index);
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



Another is to modify the query rules
For example, the following is to change the title of all the names containing Li to***

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @RequestMapping("/update_by_query")
    public void update_by_query() throws IOException {
        estemplate.wildcard("name","李");
        Map<String,String> map=new HashMap();
        map.put("title","We are good friends. Our friendship will last forever");
        estemplate.update_by_query(map,index);
    }

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


DELETE
There are two ways to delete AIS
One is to delete based on ID, which can be based on one or more IDS

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void delete(String index,String id) throws IOException {
        estemplate.delete(index,id);
    }


    public void delete(String index,String ...id) throws IOException {
        estemplate.delete(index,id);
    }

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



The other is to delete with the help of query rules. For example, the following example is to delete all ages equal to 10 years old

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void delete_query() throws IOException {
        estemplate.term("age","10");
        estemplate.delete_by_query("demo");
    }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


POJOTEMPLATE-ADD, DELETE AND MODIFY TEMPLATE CLASS
Pojotemplate is a template for defining rules for entity classes. When using it, we only need to inherit the abstract class pojotemplate and define corresponding query rules

For example:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Repository
public class UserTemplate extends POJOTemplate<User> {

    /*
    * catalog index
    * */
    @Override
    public void setIndex() {
        this.index="demo";
    }
    /*
    * Set properties such as paging
    * */
    @Override
    public void setConfig(User entity) {
       estemplate.from(entity.getPageFrom());
       estemplate.size(entity.getPageSize());
    }

    /*
    * Settings can be queried by ID and name
    * */
    @Override
    public void findRule(User user) {
        estemplate.term("id",user.getId());
        estemplate.wildcard("name.keyword",user.getName());
    }


}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

First, we need to define the corresponding index name, then define some of its setting rules (such as page highlighting, etc.) and query rules. Then we can query, modify, delete and other operations according to the corresponding ID or name (query rules are used for deletion and modification by default)

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


SOME RULES USED BY TEMPLATES
POJO is a very easy way to operate elasticsearch. Using it, we can even write a query rule for every attribute in the entity class, and simplify the query, modification, deletion, addition and other operations into a class.

But pay attention when using it

By default, the rules used for deletion, query and modification are query rules. The reason why they can be used together is that esteplate will automatically skip the query of null value. Therefore, if you need to use the custom query findfree, please do a good job in null value processing.

In addition, if you don't want to use the same rule for your own deletion, modification and query, you can define corresponding rules for none of them

Such as:

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
    * Settings can be queried by ID and name
    * */
    @Override
    public void findRule(User user) {
        estemplate.term("id",user.getId());
        estemplate.wildcard("name.keyword",user.getName());
    }

    /*
    * The set can only be deleted with ID
    * */
    @Override
    public void deleteRule(User user){
        estemplate.term("id",user.getId());
    }

    /*
     *The settings can only be modified by name
     * */
    @Override
    public void updateRule(User user){
        estemplate.term("name",user.getId());
    }

}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


@ELASTICSEARCH-THE MOST FREE QUERY
@Elasticsearch is a query method similar to mybatis, but the corresponding is to modify XML query file to JSON file, because the query method of elasticsearch is based on JSON
First of all, let's talk about the rules of JSON. The file name of JSON must be the same as the file name of the interface. Then, the first level of JSON is the corresponding method. In the second quarter, there are three attributes: index corresponding to the index location, requestmethod request method, script corresponding to the script. For the attributes to be replaced, use {} to mark (numbers also use quotation marks in AIS, and {} is not the JSON format), Then we can use @ elasticsearch to return only three results, jsonobject, string, list < entity > format. For normal search, we can use the list format, and other operations depend on the situation.

In addition, when @ Autowired is used, AIS injects the lowercase initial of the interface name into the bean by default,

For example, the interface name is find
be

@Autowired
Find find;
Here are a few examples

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





ESTEMPLATECUSTOM, MORE INTEGRATED WITH KIBANA
@Elasticsearch can satisfy all your operations, but when your operation content is too simple, you can choose esteplatecuston, which is the Java code format of @ elasticsearch
You may find that we are copying the code of kibana and replacing the corresponding field value with {}
In addition, of course, there is not only one method in esteplatecuston, it can also simply perform, and some asynchronous operations and other functions (asynchronous is not perfect but can be used)

Parameters in its method:

Index: index

Requestmethod: request method

Tclass: entity class

Entity: corresponding entity class

URL: request path


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
”@Elasticsearchasync "is the asynchronous operation of @ elasticserach". They use the same way, but the operation of @ elasticsearchasync is not very stable and perfect. I will improve the corresponding functions in time.
