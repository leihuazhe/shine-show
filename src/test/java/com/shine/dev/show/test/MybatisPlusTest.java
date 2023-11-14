package com.shine.dev.show.test;

import com.shine.dev.show.mapper.NotesMapper;
import com.shine.dev.show.entity.TNotes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * MybatisPlusTest
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/13 15:34
 */
@SpringBootTest
public class MybatisPlusTest {


  @Autowired
  private NotesMapper userMapper;

  @Test
  public void testSelect() {
    System.out.println(("----- selectAll method test ------"));
    List<TNotes> userList = userMapper.selectList(null);
    userList.forEach(System.out::println);
  }

}
