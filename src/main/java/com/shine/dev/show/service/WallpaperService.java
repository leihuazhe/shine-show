package com.shine.dev.show.service;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.shine.dev.show.common.utils.ToStringTools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


@Service
@Slf4j
public class WallpaperService implements InitializingBean {

  private String ISURL = "687474703a2f2f77616c6c70617065722e6170632e3336302e636e2f696e6465782e706870";

  /**
   * SPEL
   */
  @Value("#{T(com.shine.dev.show.common.utils.Tools).readFileToString('classpath:json/pic.json')}")
  private String picJson;

  private List<ImgInfo> imgInfos;



  public String getWallpaperV2(Integer start, Integer count, Integer category) {

    int totalSize = imgInfos.size();
    int adjustStart = start;
    if (start + count >= totalSize) {
      adjustStart = start % totalSize;
      if (adjustStart + count > totalSize - 1) {
        adjustStart = totalSize - 1 - count;
      }
    }
    log.info("start: {}, adjust start: {}, count: {}, totalSize: {}", start, adjustStart, count, totalSize);
    List<ImgInfo> retList = imgInfos.subList(adjustStart, adjustStart + 12);
    return JSONUtil.toJsonStr(retList);
  }


  public String getWallpaper(Integer start, Integer count, Integer category) {
    StringBuffer buffer = new StringBuffer();
    HttpURLConnection httpConn = null;
    BufferedReader reader = null;
    String strURL;
    try {
      if (category < 1) {
        strURL = ToStringTools.decodeString(ISURL) + "?c=WallPaper&a=getAppsByOrder&order=create_time&start=" + start
            + "&count=" + count + "&from=360chrome";
      } else {
        strURL =
            ToStringTools.decodeString(ISURL) + "?c=WallPaper&a=getAppsByCategory&cid=" + category + "&start=" + start
                + "&count=" + count + "&from=360chrome";
      }

      URL url = new URL(strURL);
      httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod("GET");
      //httpConn.setRequestProperty("Authorization", author);
      httpConn.connect();
      reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        buffer.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (httpConn != null) {
        httpConn.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return buffer.toString();
  }

  public String GetWallpaperCategory() {
    StringBuffer CategoryJson = new StringBuffer();
    HttpURLConnection httpConn = null;
    BufferedReader reader = null;
    try {
      String strURL = ToStringTools.decodeString(ISURL) + "?c=WallPaper&a=getAllCategoriesV2";
      URL url = new URL(strURL);
      httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod("GET");
      httpConn.connect();
      reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        CategoryJson.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (httpConn != null) {
        httpConn.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return CategoryJson.toString();
  }

  @Override
  public void afterPropertiesSet() {
    List<ImgInfo> ret = JSONUtil.toBean(picJson, new TypeReference<List<ImgInfo>>() {
    }.getType(), true);
    log.info("Image size: {}", ret.size());
    this.imgInfos = ret;
  }

  @Data
  private static class ImgInfo {
    private String ImgUrl;
    private String ImgTag;
  }
}
