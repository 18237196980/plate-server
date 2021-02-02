package com.fz.zf.generator;

/**
 * 代码生成配置
 * 一般情况下，只需要修改项目根包名即可
 */
public class Config {

    /**
     * 项目根包名
     */
    // public static final String BASE_PACKAGE = "com.zf.factory.application";
    public static final String BASE_PACKAGE = "com.fz.zf";

    /**
     * Model包名
     */
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model.app";

    /**
     * Mapper包名
     */
    public static final String DAO_PACKAGE = BASE_PACKAGE + ".mapper";

    /**
     * Service包名
     */
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service.app";

    /**
     * Controller包名
     */
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";

    /**
     * BaseCRUDService类名称
     */
    public static final String BASE_CRUD_SERVICE_CLASS = "com.ex.framework.base.BaseCRUDService";

    /**
     * QueryWrapper聚合类名
     */
    public static final String QUERYWRAPPER_CLASS = "$";

    /**
     * QueryBuilder聚合类名
     */
    public static final String QUERYBUILDER_CLASS = "Q";
    //------------------------------------------------------------------

    /**
     * 模板位置
     */
    public static final String TEMPLATE_FILE_PATH = "src/test/java/template";

    /**
     * Java源码位置
     */
    public static final String JAVA_PATH = "src/main/java";

    /**
     * 生成文件存放目录
     */
    public static final String TARGET_PATH = "target/framework";

}
