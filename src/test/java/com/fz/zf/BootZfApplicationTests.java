package com.fz.zf;

import com.ex.framework.data.IDUtils;
import com.ex.framework.util.Lang;
import com.fz.zf.model.app.Plate;
import com.fz.zf.mq.MsgProducer;
import com.fz.zf.service.app.PlateService;
import com.fz.zf.util.CodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootZfApplicationTests {

    @Autowired
    PlateService plateService;

    @Autowired
    MsgProducer msgProducer;

    @Test
    @Scheduled(cron = "0/3 * * * * *")
    public void mq() {
        msgProducer.sendMsg("键盘");
    }

    @Test
    public void add() {
        List<Plate> list = Lang.list();

        for (int i = 0; i < 10000; i++) {
            Plate model = new Plate();
            model.setId(IDUtils.getSequenceStr());
            model.setCode("豫A" + CodeUtils.getCheckCode());
            model.setApply_address("6-3-2西");
            model.setApply_person("李宗仁");
            model.setCreate_user("1");
            model.setPhone("1827398630");
            model.setCreate_time(LocalDateTime.now());

            list.add(model);
        }

        boolean add = plateService.add(list);

        System.out.println("结果:" + add);

    }




}
