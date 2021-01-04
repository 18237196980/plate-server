package com.fz.zf.wxgzh;

import com.fz.zf.config.PropertiesBean;
import com.fz.zf.service.gzh.GzhService;
import com.fz.zf.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;


/**
 * 微信公众号controller
 */
@RequestMapping("/gzh")
@RestController
@Slf4j
@SuppressWarnings("all")
public class GzhController {
    @Autowired
    PropertiesBean prop;
    @Autowired
    GzhService gzhService;

    /**
     * 公众号get请求
     *
     * @param record
     * @return
     */
    @GetMapping
    public String getGzh(HttpServletRequest request) {
        log.info("get请求");
        return gzhService.checkSign(request);
    }

    /**
     * 公众号post请求
     *
     * @param record
     * @return
     */
    @PostMapping
    public void postGzh(HttpServletRequest request, HttpServletResponse response) {
        log.info("post请求");

        PrintWriter out = null;
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/xml;charset=utf-8");

            out = response.getWriter();
            Map<String, String> map = MessageUtil.xmlToMap(request);
            log.info("post请求转换为map:" + map);
            String resp_xml = gzhService.processToStrXml(map);

            log.info("回复结果:" + resp_xml);
            out.print(resp_xml); // 将回应发送给微信服务器
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

}
