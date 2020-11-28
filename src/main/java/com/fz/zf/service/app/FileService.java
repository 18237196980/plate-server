package com.fz.zf.service.app;

import com.fz.zf.config.PropertiesBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    PropertiesBean propertiesBean;

    private String getFileType(String fileType) {
        switch (fileType) {
            case "1":
                return "header";
            case "2":
                return "image";
            case "3":
                return "file";
            default:
                return "file";
        }
    }

    /**
     * 保存文件，返回文件对应的Url（相对路径，不带部署域名）
     *
     * @param ext           文件后缀名
     * @param fileType      文件类型（业务自定义）1:头像，2:图片，3:文件
     * @param folder        要保存的文件夹
     * @param multipartFile
     * @param request
     * @param response
     * @return
     */
    public String transerFile(String ext, String fileType, String folder, MultipartFile multipartFile,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        String oriName = multipartFile.getOriginalFilename();
        String separator = File.separator;

        // 配置文件中指定的上传文件保存路径，如：F:\temp
        String filePath = propertiesBean.uploadPath;
        filePath = removeSeparator(filePath);
        filePath = filePath + separator;

        //是否有指定保存的子目录，如：biz
        if (!StringUtils.isEmpty(folder)) {
            folder = addSeparator(folder);
            filePath += folder;
        }

        //最终物理目录：配置的物理目录\指定的子目录\文件类型\年月\
        //如：F:\temp\biz\image\201812\
        String uploadPath = filePath + separator + getFileType(fileType) + separator + format(new Date(),
                "yyyyMM") + separator;
        //配置的下载根目录，此映射在WebConfig中配置，如配置成"downlaod"
        String downloadPath = "download";
        //相对的下载目录：download/biz/image/201812/
        String outputPath = downloadPath + separator + folder + getFileType(fileType) + separator + format(new Date(),
                "yyyyMM") + separator;

        //保存文件
        try {
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String random = UUID.randomUUID()
                    .toString();

            //最终保存的文件名：随机数字.文件后缀，如：2274524755821.jpg
            String fileName = random + "." + getExtension(multipartFile);

            //最终的物理路径：F:\temp\biz\image\201812\2274524755821.jpg
            String destPath = uploadPath + fileName;

            //最终输出的下载Url：download/biz/image/201812/2274524755821.jpg
            outputPath += fileName;

            File destFile = new File(destPath);
            multipartFile.transferTo(destFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //最终输出的下载Url：download/biz/image/201812/2274524755821.jpg
        return outputPath;
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

    /**
     * 路径结尾移除分隔符
     *
     * @param filePath
     * @return
     */
    private String removeSeparator(String filePath) {
        if (filePath.endsWith(File.separator)) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }

        return filePath;
    }

    /**
     * 获取上传文件的真实后缀
     *
     * @param multipartFile
     * @return
     */
    private String getExtension(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        //ios直接上传UIImage的话，没有后缀
        if (StringUtils.isEmpty(extension)) {
            extension = "jpg";
        }

        return extension;
    }

    /**
     * 日期转换成字符串
     *
     * @param iDate
     * @param format
     * @return
     */
    private static String format(Date iDate, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(iDate);
    }
}
