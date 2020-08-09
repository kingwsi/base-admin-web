package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.kingwsi.bs.entity.common.BaseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * description: CodeGeneratorService <br>
 * date: 2020/8/6 10:06 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Component
public class CodeGeneratorService {
    public static void main(String[] args) {

        // 逻辑删除
        // 搜索支持 ? LIKE/EQUALS/NONE
        //

        String entityName = "Dictionary";
        String tableName = "sys_dictionaries";
        String prefix = "sys_";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("ws");
        gc.setOpen(false);
        gc.setEntityName(entityName);
        gc.setServiceName(entityName + "Service");
        gc.setMapperName(entityName + "Mapper");
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setControllerName(entityName + "Controller");
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.123.232:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.kingwsi.test");
        pc.setService("service");
        pc.setController("api");
        pc.setEntity("entity." + entityName.toLowerCase());
        pc.setMapper("mapper");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 自定义属性注入
        // 在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/convertMapper.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/" + tableInfo.getEntityName() + "ConvertMapper" + StringPool.DOT_JAVA;
            }

        });
        /*EntityVO*/
        focList.add(new FileOutConfig("/templates/entityVO.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }

        });
        /*index.vue*/
        focList.add(new FileOutConfig("/templates/index.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/index.vue";
            }

        });
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase());
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setController("templates/controller.java");
        templateConfig.setServiceImpl("");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setXml("templates/mapper.xml");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(prefix);
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        ConfigBuilder configBuilder = new ConfigBuilder(pc, dsc, strategy, templateConfig, gc);
//        List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
//        System.out.println(tableInfoList.size());
        mpg.execute();
    }

    public List<TableInfo> getTableInfo() {
        String entityName = "Dictionary";
        String tableName = "sys_dictionaries";
        String prefix = "sys_";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("ws");
        gc.setOpen(false);
        gc.setEntityName(entityName);
        gc.setServiceName(entityName + "Service");
        gc.setMapperName(entityName + "Mapper");
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setControllerName(entityName + "Controller");
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.123.232:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.kingwsi.test");
        pc.setService("service");
        pc.setController("api");
        pc.setEntity("entity." + entityName.toLowerCase());
        pc.setMapper("mapper");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 自定义属性注入
        // 在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/convertMapper.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/" + tableInfo.getEntityName() + "ConvertMapper" + StringPool.DOT_JAVA;
            }

        });
        focList.add(new FileOutConfig("/templates/entityVO.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }

        });
        focList.add(new FileOutConfig("/templates/index.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/kingwsi/test/entity/" + entityName.toLowerCase() + "/" + tableInfo.getEntityName() + "/index.vue";
            }

        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setController("templates/controller.java");
        templateConfig.setServiceImpl("");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setXml("templates/mapper.xml");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(prefix);
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        ConfigBuilder configBuilder = new ConfigBuilder(pc, dsc, strategy, templateConfig, gc);
        List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
        return tableInfoList;
    }
}
