package com.shine.dev.show.test;

import cn.hutool.core.io.FileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;

/**
 * TODO
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/14 16:42
 */
public class JsonTest {

  public static void main(String[] args) {
    String json =
        FileUtil.readString(new File("/Users/maple/shine/shine-show/src/main/resources/json/pic2.json"),
            Charset.defaultCharset());

    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    com.alibaba.fastjson.JSONObject imgjo = com.alibaba.fastjson.JSONObject.parseObject(json);
    //可以使用parseObject(params，Class<T> clazz)直接转换成需要的Bean
    JSONArray imgjson = JSONArray.fromObject(imgjo.getString("data"));
    for (int i = 0; i < imgjson.size(); i++) {
      JSONObject job = imgjson.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
      jsonObject.put("ImgUrl", job.get("url"));
      jsonObject.put("ImgTag", job.get("utag"));
      jsonArray.add(i, jsonObject);
    }

    System.out.println(jsonArray);


  }
}
