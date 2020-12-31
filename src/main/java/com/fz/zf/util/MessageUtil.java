package com.fz.zf.util;

import com.fz.zf.model.gzh.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    /**
     * XML转map集合
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            SAXReader reader = new SAXReader();

            InputStream ins = request.getInputStream();
            Document doc = reader.read(ins);

            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String beanToXml(TextMessage t) {
        XStream xstream = new XStream();
        xstream.alias("xml", t.getClass());
        return xstream.toXML(t);
    }
}
