package com.fz.zf.generator;


import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class FreeMarkerUtils {

    private static String suffix = ".ftl";

    private FreeMarkerUtils() {
    }

    private static final Configuration config = new Configuration(Configuration.VERSION_2_3_28);

    public static void init(String templatePath) {
        try {
            config.setDefaultEncoding("UTF-8");

            //自动识别标签语法(<>或者[])
            config.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
//            getInstance().config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//            getInstance().config.setCacheStorage(NullCacheStorage.INSTANCE);
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setCacheStorage(NullCacheStorage.INSTANCE);

            //template
            config.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Template getTemplate(String templateName) throws IOException {
        return config.getTemplate(templateName);
    }

    public static void clearCache() {
        config.clearTemplateCache();
    }

    /**
     * 写文件
     *
     * @param filePath     生成的路径
     * @param fileName     要生成的文件名
     * @param templateName 模板名
     * @param map          数据
     */
    public static void writeFile(String filePath, String fileName, String templateName, Map<String, Object> map) {

        Writer out = null;
        try {
            Template template = getTemplate(templateName);

            if (!filePath.endsWith(File.separator)) {
                filePath = filePath + File.separator;
            }

            String fullPath = filePath + fileName;
            File file = new File(fullPath);
            if (!file.getParentFile()
                     .exists()) {
                file.getParentFile()
                    .mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            template.process(map, out);
            out.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
