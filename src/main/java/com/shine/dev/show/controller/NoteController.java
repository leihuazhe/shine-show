package com.shine.dev.show.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shine.dev.show.entity.TNotes;
import com.shine.dev.show.entity.vo.NoteVO;
import com.shine.dev.show.mapper.NotesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * NoteController
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/13 11:51
 */
@Controller
@Slf4j
public class NoteController {

  @Resource
  private NotesMapper notesMapper;


  @RequestMapping(value = {"/note/{title}", "/note"})
  public String index(@PathVariable(value = "title", required = false) String title, Model model) {
    if (StrUtil.isBlankIfStr(title)) {
      title = "default";
    }
    TNotes notes = notesMapper.selectOne(Wrappers.lambdaQuery(TNotes.class).eq(TNotes::getTitle, title));
    if (notes != null) {
      model.addAttribute("noteData", notes.getContent());
    } else {
      model.addAttribute("noteData", "");
    }
    return "note";
  }

  @RequestMapping(value = {"/note_content/{title}", "/note_content"})
  @ResponseBody
  public String queryNoteContent(@PathVariable(value = "title", required = false) String title) {
    if (StrUtil.isBlankIfStr(title)) {
      title = "default";
    }
    TNotes notes = notesMapper.selectOne(Wrappers.lambdaQuery(TNotes.class).eq(TNotes::getTitle, title));
    return notes != null ? notes.getContent() : "";
  }

  @RequestMapping("/saveNote")
  @ResponseBody
  public String saveNote(@RequestBody NoteVO request) {

    String noteContent = request.getContent();
    String title = request.getTitle();
    if (StrUtil.isBlankIfStr(title) || "note".equals(title)) {
      title = "default";
    }
    //log.info("To saved title: {}", title);
    TNotes notes = notesMapper.selectOne(Wrappers.lambdaQuery(TNotes.class).eq(TNotes::getTitle, title));
    if (notes == null) {
      notes = new TNotes();
      notes.setTitle(title);
      notes.setContent(noteContent);
      notesMapper.insert(notes);
      log.info("Newly saved title: {}, note: \n {}", title, notes);
    } else {
      String oldContent = notes.getContent();
      if (!oldContent.equals(noteContent)) {
        notes.setContent(noteContent);
        notesMapper.updateById(notes);
        log.info("Saved title: {}, note: \n {}", title, notes);
      }
    }
    return "ok";
  }

}
