package com.fz.zf;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.util.Lang;
import com.fz.zf.mapper.Q;
import com.fz.zf.model.app.GoodOrder;
import com.fz.zf.model.app.Plate;
import com.fz.zf.model.gzh.DataBean;
import com.fz.zf.model.gzh.SendTempModel;
import com.fz.zf.mq.MsgProducer;
import com.fz.zf.service.app.GoodOrderService;
import com.fz.zf.service.app.PlateService;
import com.fz.zf.service.gzh.GzhService;
import com.fz.zf.util.CodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.NamingEnumeration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootZfApplicationTests {

    @Autowired
    PlateService plateService;

    @Autowired
    MsgProducer msgProducer;

    @Autowired
    GoodOrderService goodOrderService;

    @Autowired
    GzhService gzhService;

    @Test
    public void gzhSetTemplate() {
        /*Object token = gzhService.getToken();
        String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+token;

        Map<String,Object> map =new HashMap<>();
        map.put("industry_id1", "1");
        map.put("industry_id2", "31");
        JSONObject jsonObject=new JSONObject(map);
        String post = HttpUtil.post(url, jsonObject.toString());
        System.out.println(post);*/

        Object token = gzhService.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        SendTempModel model = new SendTempModel();
        model.setTemplate_id("3s3ewkB7oD4xSh5IwqJIjm7mzlihu0rxk4IGI6_7v_k");
        model.setTouser("ovzQ2640QXMz1hEKJbKim2pcsDzg");

        Map<String, Object> map = new HashMap<>();
        map.put("first", new DataBean("您好，您本月物业费已出", "#173177"));
        map.put("userName", new DataBean("申女士", "#173177"));
        map.put("address", new DataBean("郑州市电厂路华瑞紫光苑6-3-28", "#173177"));
        map.put("pay", new DataBean("79.15", "#FA3342"));
        map.put("remark", new DataBean("请尽快缴纳，如有疑问，请咨询狗蛋儿", "#173177"));
        model.setData(map);
        JSONObject json = JSONUtil.parseObj(model);
        System.out.println("结果json:" + json);

        String resp = HttpUtil.post(url, json.toString());

        System.out.println("结果aaa:" + resp);
    }

    @Test
    public void addOrder() {
        goodOrderService.delete(Q.GoodOrder());
        List<GoodOrder> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            GoodOrder model = new GoodOrder();
            model.setId(IDUtils.getSequenceStr());
            model.setOrder_num(IDUtils.getSequenceStr());
            // model.setOrder_amount(getRandomRedPacketBetweenMinAndMax(BigDecimal.valueOf(1l), BigDecimal.valueOf(100l)));
            model.setOrder_amount(BigDecimal.valueOf(10l));
            model.setUid("1");
            model.setStatus(0);

            list.add(model);
        }
        goodOrderService.add(list);
    }

    public static BigDecimal getRandomRedPacketBetweenMinAndMax(BigDecimal min, BigDecimal max) {
        float minF = min.floatValue();
        float maxF = max.floatValue();

        //生成随机数
        BigDecimal db = new BigDecimal(Math.random() * (maxF - minF) + minF);

        //返回保留两位小数的随机数。不进行四舍五入
        return db.setScale(0, BigDecimal.ROUND_DOWN);
    }


    @Test
    public void add() {
        // long current = System.currentTimeMillis();
        List<Plate> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Plate model = new Plate();
            model.setId(IDUtils.getSequenceStr());
            model.setCode("沪C" + CodeUtils.getCheckCode());
            model.setApply_address("3-1-2南");
            model.setApply_person("段祺瑞");
            model.setCreate_user("1");
            model.setPhone("18711112222");
            model.setCreate_time(LocalDateTime.now());

            list.add(model);
        }
        msgProducer.sendList(list);
    }

}
