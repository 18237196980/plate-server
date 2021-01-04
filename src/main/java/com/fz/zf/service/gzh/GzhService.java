package com.fz.zf.service.gzh;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fz.zf.config.PropertiesBean;
import com.fz.zf.model.common.Constast;
import com.fz.zf.model.gzh.*;
import com.fz.zf.model.gzh.menu.*;
import com.fz.zf.util.RedisUtil;
import com.fz.zf.util.ShaUtil;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 公众号业务处理
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class GzhService {

    @Autowired
    PropertiesBean prop;
    @Autowired
    RedisUtil redisUtil;

    public String checkSign(HttpServletRequest req) {
        //获取微信服务器传来的相关参数
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        // 1.将token、timestamp、nonce三个参数进行字典序排序
        log.info("signature:{},token:{},timestamp:{},nonce:{}", signature, prop.gzhToken, timestamp, nonce);
        String tmpStr = ShaUtil.getSHA1(prop.gzhToken, timestamp, nonce);
        //TODO 进行对比
        log.info("随机字符串echostr:{}", echostr);
        log.info("tmpStr:{}", tmpStr);
        if (tmpStr.equals(signature.toUpperCase())) {
            return echostr;
        }
        return null;

    }

    /**
     * 处理微信公众号post请求的返回值
     *
     * @param map
     * @return
     */
    public String processToStrXml(Map<String, String> map) {
        fullMenus();

        String msgType = map.get("MsgType");
        BaseMessage msg = null;
        String res_xml = "";

        // 返回不同消息
        switch (msgType) {
            case "text":
                msg = dealText(map);
                break;
            case "image":
                msg = dealImage(map);
                break;
            case "news":
                msg = dealNews(map);
                break;
            case "event":
                msg = dealEvent(map);
                break;
            default:
                break;
        }
        XStream stream = new XStream();
        processMsg(stream);

        res_xml = stream.toXML(msg);
        return res_xml;
    }

    /**
     * 处理菜单点击事件
     *
     * @param map
     * @return
     */
    private BaseMessage dealEvent(Map<String, String> map) {
        String event = map.get("Event");
        String eventKey = map.get("EventKey");
        switch (event) {
            case "CLICK":
                if (StringUtils.equals(eventKey, "1")) {
                    return dealClickEvent(map);
                }
            case "VIEW":

                break;
            default:
                break;
        }
        return null;
    }

    private BaseMessage dealClickEvent(Map<String, String> map) {
        return new TextMessage(map, "你点击了我");
    }

    /**
     * 加载菜单
     */
    private void fullMenus() {
        Object token = getToken();
        if (token == null) {
            log.info("token获取失败");
        } else {
            MenuBtns menuBtns = new MenuBtns();
            menuBtns.getButton()
                    .add(new ClickRootMenu("点我试试", "click", "1"));

            List<SubButton> sub_button = new ArrayList<>();
            sub_button.add(new ViewSubButton("百度一下", "view", "http://www.soso.com/"));
            sub_button.add(new PhotoSubButton("拍照或选择相册", "pic_photo_or_album", "21"));

            menuBtns.getButton()
                    .add(new TreeRootMenu("分类菜单", sub_button));

            String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

            JSONObject json = JSONUtil.parseObj(menuBtns);
            log.info("哈哈哈:" + json);
            url = url.replace("ACCESS_TOKEN", token.toString());
            String resp = HttpUtil.post(url, json.toString());

            log.info("结果:" + resp);
        }
    }

    private BaseMessage dealNews(Map<String, String> map) {
        String pic_url = "http://mmbiz.qpic.cn/mmbiz_jpg/ibnEc0epnPib8ibKZgnGiacJG55iaclsbK50aJT5oIVPdjl70RVByakpAcp3DvGga2uAvsISCVjvP1w51BuQ2Tu30vw/0";
        String url = "https://www.baidu.com/?tn=62095104_19_oem_dg";

        List<Articles> articles = new ArrayList<>();
        articles.add(new Articles("今晨要闻", "郑州高速免费时间段", pic_url, url));

        NewsMessage new_msg = new NewsMessage(map, articles);
        return new_msg;
    }

    private BaseMessage dealImage(Map<String, String> map) {
        Image image = new Image();
        image.setMediaId("1G_eoDqz4AIPzZxrzpEq1ogoZnha-R9gH0VDHb9GzsBy1zrd7_iLeRpuStCIOrSO");
        ImageMessage img_msg = new ImageMessage(map, image);
        return img_msg;
    }

    private BaseMessage dealText(Map<String, String> map) {
        if (map.get("Content")
               .equals("看看")) {
            return dealNews(map);
        }
        TextMessage text_msg = new TextMessage(map, "对的呢");
        return text_msg;
    }

    // 获取微信公众号token(使用时需判空)
    private Object getToken() {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", prop.gzhAppid);
        params.put("secret", prop.gzhAppsecret);
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String resp = HttpUtil.get(url, params);
        JSONObject json = JSONUtil.parseObj(resp);
        String access_token = json.getStr("access_token");
        Long expires_in = json.getLong("expires_in", 7200l);

        UUID uuid = UUID.randomUUID();
        redisUtil.set(Constast.GZH_TOKEN + uuid, access_token, expires_in);
        log.info("token:" + redisUtil.get(Constast.GZH_TOKEN + uuid));
        return redisUtil.get(Constast.GZH_TOKEN + uuid);
    }

    private void processMsg(XStream stream) {
        stream.processAnnotations(TextMessage.class);
        stream.processAnnotations(ImageMessage.class);
        stream.processAnnotations(NewsMessage.class);
    }
}
