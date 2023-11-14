package com.shine.dev.show.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SqlCodeGenerator
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/13 14:47
 */
public class SqlCodeGenerator {


  /**
   * 数据源配置
   */
  private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
      //.Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;MODE=MYSQL", "sa", "");
      .Builder(
      "jdbc:mysql://39.108.110.17:3316/maple?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai",
      "root", Encryptor.decrypt("koPlAftHlMm6tIsJfe6h7w=="));

  /**
   * 执行 run
   */
  public static void main(String[] args) throws SQLException {
    // 初始化数据库脚本
    //initDataSource(DATA_SOURCE_CONFIG.build());
    FastAutoGenerator.create(DATA_SOURCE_CONFIG)

        // 数据库配置
        //.dataSourceConfig((scanner, builder) -> builder.schema(scanner.apply("maple")))
        .dataSourceConfig((builder) -> builder.schema("maple"))
        // 全局配置
        //.globalConfig((scanner, builder) -> builder.author(scanner.apply("Zane Lei")))
        .globalConfig((builder) -> builder
            .author("Zane Lei")
            .outputDir("src/main/java")
        )
        // 包配置
        //.packageConfig((scanner, builder) -> builder.parent(scanner.apply("com.shine.dev.show.mapper")))
        .packageConfig((builder) -> builder.parent("com.shine.dev.show.mapper"))
        // 策略配置
        //.strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("notes,images")))
        .strategyConfig((builder) -> builder
            .addInclude("notes,images")
            .entityBuilder()
            .enableFileOverride()
        )
        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();
  }
  //.templateEngine(new FreemarkerTemplateEngine())

  /**
   * 执行数据库脚本
   */
  protected static void initDataSource(DataSourceConfig dataSourceConfig) throws SQLException {
    Connection conn = dataSourceConfig.getConn();
    InputStream inputStream = SqlCodeGenerator.class.getResourceAsStream("/sql/init.sql");
    ScriptRunner scriptRunner = new ScriptRunner(conn);
    scriptRunner.setAutoCommit(true);
    scriptRunner.runScript(new InputStreamReader(inputStream));
    conn.close();
  }

  /**
   * 策略配置
   */
  protected static StrategyConfig.Builder strategyConfig() {
    return new StrategyConfig.Builder();
  }

  /**
   * 全局配置
   */
  protected static GlobalConfig.Builder globalConfig() {
    return new GlobalConfig.Builder();
  }

  /**
   * 包配置
   */
  protected static PackageConfig.Builder packageConfig() {
    return new PackageConfig.Builder();
  }

  /**
   * 模板配置
   */
  protected static TemplateConfig.Builder templateConfig() {
    return new TemplateConfig.Builder();
  }

  /**
   * 注入配置
   */
  protected static InjectionConfig.Builder injectionConfig() {
    // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
    return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
      System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
    });
  }
}
