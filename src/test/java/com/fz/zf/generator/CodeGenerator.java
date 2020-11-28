package com.fz.zf.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.ex.framework.util.Lang;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGenerator {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private String driverClassName = "com.mysql.jdbc.Driver";

    @Test
    public void generate() {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        gc.setOutputDir(projectPath + "/target/framework");
        gc.setAuthor("");
        gc.setOpen(false);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        //dsc.setSchemaName("public");
        dsc.setDriverName(driverClassName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setEntity("model");
        pc.setParent(Config.BASE_PACKAGE);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/target/framework/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.no_change);
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        //strategy.setEntityLombokModel(true);
        //strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        //strategy.setInclude(scanner("表名"));
        //strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityLombokModel(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    @Test
    public void generateService() {

        //1.获取所有的model文件名
        List<String> modelNameList = Util.getModelList(Config.JAVA_PATH, Config.MODEL_PACKAGE);

        //2.init freemarker
        FreeMarkerUtils.init(Config.TEMPLATE_FILE_PATH);

        //3.写入Service文件
        Util.writeServiceFiles(modelNameList, Config.TARGET_PATH);

        //4.生成Dsl聚合类
        Util.writeDslFile(modelNameList, Config.TARGET_PATH);

        //5.生成QueryBuilder聚合类
        Util.writeQueryBuilderFile(modelNameList, Config.TARGET_PATH);

        //6.生成Column类
        List<TableInfo> list = Util.getTableInfoList(url, driverClassName, username, password);
        Util.writeColumnFile(list, Config.TARGET_PATH);
    }

    @Test
    public void generateController() {

        //1.获取所有的model文件名
        List<String> modelNameList = Util.getModelList(Config.JAVA_PATH, Config.MODEL_PACKAGE);

        //2.init freemarker
        FreeMarkerUtils.init(Config.TEMPLATE_FILE_PATH);

        //3.写入Controller文件
        Util.writeControllerFiles(modelNameList, Config.TARGET_PATH);
    }

    /**
     * 生成单表的视图: index/add/edit，以及对应的Controller
     */
    @Test
    public void generateViewsAndController() {
        //表名
        String table = "material_slot_relation";
        //实体名
        String model = "MaterialSlotRelation";

        //列表界面的搜索字段
        List<String> searchFields = Lang.list("material_num");

        List<TableInfo> list = Util.getTableInfoList(url, driverClassName, username, password);
        FreeMarkerUtils.init(Config.TEMPLATE_FILE_PATH);
        Util.writeViewFiles(table, searchFields, list, Config.TARGET_PATH);
        Util.writeSingleController(table, model, searchFields, list, Config.TARGET_PATH);
    }

}
