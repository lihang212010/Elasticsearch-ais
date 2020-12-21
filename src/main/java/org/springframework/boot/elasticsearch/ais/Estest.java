package org.springframework.boot.elasticsearch.ais;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lihang
 * @email 631533483@qq.com
 */
public class Estest {


  static String a="hello world";
  public static void main(String[] args) {
    Map map=new HashMap<String,String>();
    map.put("object","hello world");
    Long start1= System.currentTimeMillis();
    for (int i=0;i<10000;i++){
      System.out.println(map.get("onject"));
    }
    Long end1=System.currentTimeMillis();

    Long start2= System.currentTimeMillis();
    for (int i=0;i<10000;i++){
      System.out.println(Estest.a);
    }
    Long end2=System.currentTimeMillis();
    System.out.println(end1-start1);
    System.out.println(end2-start2);

  }


}
