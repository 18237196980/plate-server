package com.fz.zf.generator;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

public class Util {

    /**
     * 写入Dao文件
     *
     * @return
     */
    public static void writeDaoFiles(List<String> modelNames, String TARGET_PATH) {
        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String daoPath = targetPath + "dao";


        log("------------------------开始生成Dao文件------------------------");
        for (String modelName : modelNames) {
            //凡是Model名称带dto/vo/$的，都忽略
            if (modelName.toLowerCase()
                         .endsWith("dto") || modelName.toLowerCase()
                                                      .endsWith("vo"))
                continue;

            if (modelName.toLowerCase()
                         .contains("dto") || modelName.toLowerCase()
                                                      .contains("vo"))
                continue;

            if (modelName.contains("$"))
                continue;

            Map<String, Object> map = new HashMap<>();
            map.put("modelName", modelName);
            map.put("modelPackage", Config.MODEL_PACKAGE);
            map.put("daoPackage", Config.DAO_PACKAGE);

            FreeMarkerUtils.writeFile(daoPath, modelName + "Dao.java", "dao.ftl", map);
            log(modelName + "Dao.java");
        }
        log("------------------------Dao文件生成结束------------------------");
    }

    /**
     * 写入Service文件
     *
     * @return
     */
    public static void writeServiceFiles(List<String> modelNames, String TARGET_PATH) {
        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String servicePath = targetPath + "service";

        log("------------------------开始生成Service文件--------------------");
        for (String modelName : modelNames) {
            //凡是Model名称带dto/vo/$的，都忽略
            if (modelName.toLowerCase()
                         .endsWith("dto") || modelName.toLowerCase()
                                                      .endsWith("vo"))
                continue;

            if (modelName.toLowerCase()
                         .contains("dto") || modelName.toLowerCase()
                                                      .contains("vo"))
                continue;

            if (modelName.contains(Config.QUERYWRAPPER_CLASS))
                continue;

            if(modelName.equalsIgnoreCase("Column")){
                continue;
            }

            String tableName = getTableName( modelName);

            Map<String, Object> map = new HashMap<>();
            map.put("modelName", modelName);
            map.put("modelPackage", Config.MODEL_PACKAGE);
            map.put("mapperPackage", Config.DAO_PACKAGE);
            map.put("servicePackage", Config.SERVICE_PACKAGE);
            map.put("BaseCRUDService", Config.BASE_CRUD_SERVICE_CLASS);
            map.put("tableName", tableName);

            FreeMarkerUtils.writeFile(servicePath, modelName + "Service.java", "service.ftl", map);
            log(modelName + "Service.java");
        }
        log("------------------------Service文件生成结束--------------------");
    }

    private static String getTableName(String modelName) {
        try {
            Class clazz = Class.forName(Config.MODEL_PACKAGE + "." +modelName);
            TableName annotation = (TableName)clazz.getAnnotation(TableName.class);
            if(annotation == null){
                return modelName.toLowerCase();
            }

            return annotation.value();

        } catch (ClassNotFoundException e) {

        }
        return "";
    }

    /**
     * 写入Controller文件
     *
     * @return
     */
    public static void writeControllerFiles(List<String> modelNames, String TARGET_PATH) {
        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String controllerPath = targetPath + "controller";

        log("------------------------开始生成Controller文件--------------------");
        for (String modelName : modelNames) {
            //凡是Model名称带dto/vo/$的，都忽略
            if (modelName.toLowerCase()
                         .endsWith("dto") || modelName.toLowerCase()
                                                      .endsWith("vo"))
                continue;

            if (modelName.toLowerCase()
                         .contains("dto") || modelName.toLowerCase()
                                                      .contains("vo"))
                continue;

            if (modelName.contains(Config.QUERYWRAPPER_CLASS))
                continue;

            Map<String, Object> map = new HashMap<>();
            map.put("modelName", modelName);
            map.put("modelName2", StringUtils.uncapitalize(modelName));

            map.put("basePackage", Config.BASE_PACKAGE);
            map.put("modelPackage", Config.MODEL_PACKAGE);
            map.put("mapperPackage", Config.DAO_PACKAGE);
            map.put("servicePackage", Config.SERVICE_PACKAGE);
            map.put("controllerPackage", Config.CONTROLLER_PACKAGE);


            FreeMarkerUtils.writeFile(controllerPath, modelName + "Controller.java", "controller.ftl", map);
            log(modelName + "Controller.java");
        }
        log("------------------------Controller文件生成结束--------------------");
    }

    public static void writeViewFiles(String table, List<String> searchFields, List<TableInfo> list,
                                      String TARGET_PATH) {
        TableInfo t = null;
        for (TableInfo tableInfo : list) {
            if (StringUtils.equals(tableInfo.getName(), table)) {
                t = tableInfo;
                break;
            }
        }

        if (t == null) {
            log("无法找到对应的表：" + table);
            return;
        }

        //searchFields
        List<TableField> searchTableFields = new ArrayList<>();
        t.getFields()
         .forEach(tableField -> {
             //如果没有字段注释，那么注释设置为字段名
             if (StringUtils.isEmpty(tableField.getComment())) {
                 tableField.setComment(tableField.getName());
             }

             for (String name : searchFields) {
                 if (StringUtils.equals(name, tableField.getName())) {
                     searchTableFields.add(tableField);
                     continue;
                 }
             }
         });

        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String viewPath = targetPath + "view";
        log("------------------------开始生成View文件--------------------");
        Map<String, Object> map = new HashMap<>();
        map.put("t", t);
        map.put("searchFields", searchTableFields);
        map.put("fields", t.getFields());

        //1.index
        FreeMarkerUtils.writeFile(viewPath, "index.ftl", "view_index.ftl", map);

        //2.add
        FreeMarkerUtils.writeFile(viewPath, "add.ftl", "view_add.ftl", map);

        //3.edit
        FreeMarkerUtils.writeFile(viewPath, "edit.ftl", "view_edit.ftl", map);

        log("------------------------View文件生成结束--------------------");
    }

    public static void writeSingleController(String table, String model, List<String> searchFields, List<TableInfo> list, String TARGET_PATH) {
        TableInfo t = null;
        for (TableInfo tableInfo : list) {
            if (StringUtils.equals(tableInfo.getName(), table)) {
                t = tableInfo;
                break;
            }
        }

        if (t == null) {
            log("无法找到对应的表：" + table);
            return;
        }

        //searchFields
        List<TableField> searchTableFields = new ArrayList<>();
        t.getFields()
         .forEach(tableField -> {
             for (String name : searchFields) {
                 if (StringUtils.equals(name, tableField.getName())) {
                     searchTableFields.add(tableField);
                     continue;
                 }
             }
         });

        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String viewPath = targetPath + "view";
        log("------------------------开始生成Controller文件--------------------");
        Map<String, Object> map = new HashMap<>();
        map.put("t", t);
        map.put("searchFields", searchTableFields);
        map.put("fields", t.getFields());

        map.put("basePackage", Config.BASE_PACKAGE);
        map.put("modelPackage", Config.MODEL_PACKAGE);
        map.put("mapperPackage", Config.DAO_PACKAGE);
        map.put("servicePackage", Config.SERVICE_PACKAGE);
        map.put("controllerPackage", Config.CONTROLLER_PACKAGE);

        map.put("modelName", model);
        map.put("modelName2", StringUtils.uncapitalize(model));

        FreeMarkerUtils.writeFile(viewPath, model + "Controller.java", "view_controller.ftl", map);

        log("------------------------Controller文件生成结束--------------------");
    }

    /**
     * 写入Dsl查询聚合类
     */
    public static void writeDslFile(List<String> modelNames, String TARGET_PATH) {
        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String modelPackagePath = Config.MODEL_PACKAGE.replace(".", File.separator);

        //聚合类名
        String className = Config.QUERYWRAPPER_CLASS;

        Map<String, Object> map = new HashMap<>();
        map.put("modelPackage", Config.MODEL_PACKAGE);
        map.put("className", className);

        List<Map<String, String>> items = new ArrayList<>();
        for (String modelName : modelNames) {
            //凡是Model名称带dto/vo/$的，都忽略
            if (modelName.toLowerCase()
                         .endsWith("dto") || modelName.toLowerCase()
                                                      .endsWith("vo"))
                continue;

            if (modelName.toLowerCase()
                         .contains("dto") || modelName.toLowerCase()
                                                      .contains("vo"))
                continue;

            if (modelName.contains(Config.QUERYWRAPPER_CLASS))
                continue;

            String litterModel = StringUtils.uncapitalize(modelName);//city

            Map<String, String> qmap = new HashMap<>();
            qmap.put("modelName", modelName);
            qmap.put("litterModel", litterModel);
            qmap.put("modelPackage", Config.MODEL_PACKAGE);
            qmap.put("mapperPackage", Config.DAO_PACKAGE);
            items.add(qmap);
        }
        map.put("items", items);

        FreeMarkerUtils.writeFile(targetPath, className + ".java", "dsl.ftl", map);

        log("------------------------生成QueryWrapper聚合类完成------------------------");
    }

    /**
     * 写入QueryBuilder聚合类
     */
    public static void writeQueryBuilderFile(List<String> modelNames, String TARGET_PATH) {
        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        String modelPackagePath = Config.MODEL_PACKAGE.replace(".", File.separator);

        //聚合类名
        String className = Config.QUERYBUILDER_CLASS;

        Map<String, Object> map = new HashMap<>();
        map.put("modelPackage", Config.MODEL_PACKAGE);
        map.put("daoPackage", Config.DAO_PACKAGE);
        map.put("className", className);

        List<Map<String, String>> items = new ArrayList<>();
        for (String modelName : modelNames) {
            //凡是Model名称带dto/vo/$/Q的，都忽略
            if (modelName.toLowerCase()
                         .endsWith("dto") || modelName.toLowerCase()
                                                      .endsWith("vo"))
                continue;

            if (modelName.toLowerCase()
                         .contains("dto") || modelName.toLowerCase()
                                                      .contains("vo"))
                continue;

            if (modelName.contains(Config.QUERYWRAPPER_CLASS))
                continue;

            if (modelName.contains(Config.QUERYBUILDER_CLASS))
                continue;

            String litterModel = StringUtils.uncapitalize(modelName);//city

            Map<String, String> qmap = new HashMap<>();
            qmap.put("modelName", modelName);
            qmap.put("litterModel", litterModel);
            qmap.put("modelPackage", Config.MODEL_PACKAGE);
            qmap.put("mapperPackage", Config.DAO_PACKAGE);
            items.add(qmap);
        }
        map.put("items", items);

        FreeMarkerUtils.writeFile(targetPath, className + ".java", "q.ftl", map);

        log("------------------------生成QueryBuilder聚合类完成------------------------");
    }

    private static void log(String message) {
        System.out.println(message);
    }

    /**
     * 获取所有model的名称
     *
     * @param sourceDir        工程源码目录
     * @param modelPackageName model包名
     * @return
     */
    public static List<String> getModelList(String sourceDir, String modelPackageName) {

        //model物理目录
        String modelPath = getPackagePath(sourceDir, modelPackageName);

        List<String> list = new ArrayList<String>();

        File modelPathFile = new File(modelPath);
        Iterator<File> files = FileUtils.iterateFiles(modelPathFile, new String[]{"java"}, true);
        while (files.hasNext()) {
            File f = files.next();
            String modelName = f.getName();
            modelName = FilenameUtils.getBaseName(modelName);

            list.add(modelName);
        }

        return list;
    }

    public static List<TableInfo> getTableInfoList(String url, String driverClassName, String username,
                                                   String password) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        //dsc.setSchemaName("public");
        dsc.setDriverName(driverClassName);
        dsc.setUsername(username);
        dsc.setPassword(password);

        ConfigBuilder configBuilder = new ConfigBuilder(null, dsc, null, null, null);
        List<TableInfo> list = configBuilder.getTableInfoList();

        return list;
    }

    /**
     * 从根目录和根目录下面的包名，拼接出包的全路径
     *
     * @param rootPath
     * @param packageName
     * @return
     */
    private static String getPackagePath(String rootPath, String packageName) {
        String packagePath = packageName.replace(".", File.separator);

        if (!rootPath.endsWith(File.separator)) {
            rootPath = rootPath + File.separator;
        }

        //model物理目录
        packagePath = rootPath + packagePath;

        return packagePath;
    }

    /**
     * 写入Column类
     *
     * @param list
     * @param TARGET_PATH
     */
    public static void writeColumnFile(List<TableInfo> list, String TARGET_PATH) {
        String targetPath = TARGET_PATH;
        if (!targetPath.endsWith(File.separator))
            targetPath = targetPath + File.separator;

        Map<String, Object> map = new HashMap<>();
        map.put("modelPackage", Config.MODEL_PACKAGE);
        map.put("list", list);


        FreeMarkerUtils.writeFile(targetPath, "Column.java", "column.ftl", map);

        log("------------------------生成Column类完成------------------------");
    }
}
