package com.shine.dev.show.test.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * GuavaCacheTest
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/15 14:54
 */
public class GuavaCacheTest {

  private LoadingCache<Integer, Long> cache;

  @BeforeEach
  void beforeEach() {

    cache = CacheBuilder.newBuilder()
        .maximumSize(100)
        .expireAfterAccess(5, TimeUnit.SECONDS)
        .expireAfterWrite(20, TimeUnit.SECONDS)
        .build(new CacheLoader<Integer, Long>() {
          @Override
          public Long load(Integer key) {
            return fetch(key);
          }
        });
  }


  @Test
  void testCacheTimes() throws Exception {

    String format = "key:%s,value:%s";
    System.out.println(String.format(format, 1, cache.get(1)));
    System.out.println(String.format(format, 33, cache.get(33)));
    TimeUnit.SECONDS.sleep(10);
    System.out.println(String.format(format, 1, cache.get(1)));
    System.out.println(String.format(format, 33, cache.get(33)));
    TimeUnit.SECONDS.sleep(10);
    System.out.println(String.format(format, 1, cache.get(1)));
    System.out.println(String.format(format, 33, cache.get(33)));
    //
    cache.put(2, 2L);
    TimeUnit.SECONDS.sleep(15);
    System.out.println(String.format(format, 2, cache.get(2)));
    TimeUnit.SECONDS.sleep(8);
    System.out.println(String.format(format, 2, cache.get(2)));
  }

  private Long fetch(int key) {
    System.out.println("Invoke fetch, key: " + key);
    switch (key) {
      case 1:
        return 11L;
      case 2:
        return 22L;
      case 3:
        return 33L;
      default:
        return System.currentTimeMillis();
    }
  }
}
