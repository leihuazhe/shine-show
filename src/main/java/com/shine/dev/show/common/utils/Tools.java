package com.shine.dev.show.common.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author struy
 */
public class Tools {
  /**
   * get a UUID
   *
   * @return UUID
   */
  public static String uid() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static String uidWithSuffix(String fileName) {
    return UUID.randomUUID().toString().replace("-", "") + "." + suffix(fileName);
  }

  public static String readFileToString(String path) {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  public static String asString(Resource resource) {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }


  /**
   * get finle suffix
   *
   * @param fileName
   * @return
   */
  public static String suffix(String fileName) {
    return fileName.lastIndexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")) : "";
  }

  /**
   * Add the end to the folder path
   * and if folder path Non-existent then mkdirs
   *
   * @param path folder path
   * @param mk   Whether or not a directory is generated
   * @return
   */
  public static String folderHelper(String path, Boolean mk) {

    path = !path.endsWith(File.separator) ?
        path + File.separator :
        path;

    if (mk) {
      File file = new File(path);
      if (!new File(path).exists()) {
        file.mkdirs();
      }
    }
    return path;
  }

  /**
   * Create date level folders
   *
   * @return
   */
  public static String dateFolders(Boolean url) {

    Calendar date = Calendar.getInstance();
    StringBuffer path = new StringBuffer();
    if (!url) {
      path.append(date.get(Calendar.YEAR)
              + File.separator
              + (date.get(Calendar.MONTH) + 1)
              + File.separator
          /*+ date.get(Calendar.DAY_OF_MONTH)*/);
      return folderHelper(path.toString(), false);
    } else {
      path.append(date.get(Calendar.YEAR)
          + "-"
          + (date.get(Calendar.MONTH) + 1)
          + "-"
          + date.get(Calendar.DAY_OF_MONTH));
      return path.toString() + "/";
    }

  }

  // UTF-8编码
  public static String toUTF8(String s) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c >= 0 && c <= 255) {
        sb.append(c);
      } else {
        byte[] b;
        try {
          b = Character.toString(c).getBytes("utf-8");
        } catch (Exception ex) {
          System.out.println(ex);
          b = new byte[0];
        }
        for (int j = 0; j < b.length; j++) {
          int k = b[j];
          if (k < 0)
            k += 256;
          sb.append("%" + Integer.toHexString(k).toUpperCase());
        }
      }
    }
    return sb.toString();
  }


  /**
   * delete folders or file
   *
   * @param fileName
   * @return successful return true，failed return false
   */
  public static boolean foldersFileDelete(String fileName) {
    File file = new File(fileName);
    if (!file.exists()) {
      return false;
    } else {
      if (file.isFile())
        return deleteFile(fileName);
      else
        return deleteDirectory(fileName);
    }
  }

  /**
   * delete single file
   *
   * @param fileName
   * @return successful return true，failed return false
   */
  public static boolean deleteFile(String fileName) {
    File file = new File(fileName);

    if (file.exists() && file.isFile()) {
      if (file.delete()) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * delete folders and file
   *
   * @param dir folders
   * @return successful return true，failed return false
   */
  public static boolean deleteDirectory(String dir) {

    dir = folderHelper(dir, false);

    File dirFile = new File(dir);

    if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
      return false;
    }
    boolean flag = true;
    // Delete all the files in the folder, including subdirectories
    File[] files = dirFile.listFiles();
    for (int i = 0; i < files.length; i++) {
      // delete a sub file
      if (files[i].isFile()) {
        flag = deleteFile(files[i].getAbsolutePath());
        if (!flag)
          break;
      }
      // delete a sub dir
      else if (files[i].isDirectory()) {
        flag = deleteDirectory(files[i]
            .getAbsolutePath());
        if (!flag)
          break;
      }
    }
    if (!flag) {
      return false;
    }
    // delete the current directory
    if (dirFile.delete()) {
      return true;
    } else {
      return false;
    }
  }

}
