package com.fz.zf.common;

import com.ex.framework.web.ApiResult;
import com.fz.zf.app.BaseController;
import com.fz.zf.config.PropertiesBean;
import com.fz.zf.service.app.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    FileService fileService;

    @Autowired
    PropertiesBean propertiesBean;

    /**
     * 通用上传
     *
     * @param ext      文件后缀名
     * @param filetype 文件类型，1:头像，2:图片，3:文件
     * @param folder   要保存的文件夹
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("common")
    @ResponseBody
    public ApiResult upload(
            @RequestParam(value = "fileext", defaultValue = "") String ext,
            @RequestParam(value = "filetype", defaultValue = "") String filetype,
            @RequestParam(value = "folder", defaultValue = "") String folder,
            @RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {

            if (file == null || file.isEmpty()) {
                return ApiResult.error("上传失败");
            }

            String oriName = file.getOriginalFilename();
            // 判断文件类型,同时赋值给 filetype
            ext = oriName.substring(oriName.lastIndexOf('.') + 1);
            // 文件后缀名转大写
            String suffix = ext.toUpperCase();
            if (suffix.equals("JPG") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("GIF") || suffix.equals("BMP")) {
                filetype = "2"; // 图片类型
            } else {
                filetype = "3"; // 文件类型
            }

            //文件下载的相对路径，不带前缀"/"，如：download/biz/image/201812/2274524755821.jpg
            String url = fileService.transerFile(ext, filetype, folder, file, request, response);

            //拼接下载的全路径，如：http://xxx.com/download/biz/image/201812/2274524755821.jpg
            String siteUrl = propertiesBean.siteUrl;
            siteUrl = addSeparator(siteUrl);
            if (url.startsWith(File.separator)) {
                url = url.substring(1);
            }
            String header_path = doUrl(siteUrl + url);

            Map<String, Object> map = new HashMap<>();
            //oriName = oriName.substring(oriName.lastIndexOf("/")+1);
            map.put("name", oriName);
            map.put("url", doUrl(url));//相对路径，用于保存到数据库
            map.put("header_path", header_path);//绝对路径，用于页面显示

            return ApiResult.success(map);
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            return ApiResult.error("上传失败");
        }
    }

    private String doUrl(String old_url) {
        String url = old_url.replaceAll("\\\\", "/");
        return url;
    }

    /**
     * 路径结尾添加分隔符
     *
     * @param filePath
     * @return
     */
    private String addSeparator(String filePath) {
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }

        return filePath;
    }
}
