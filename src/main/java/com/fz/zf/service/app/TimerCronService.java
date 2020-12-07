package com.fz.zf.service.app;

import com.ex.framework.data.IDUtils;
import com.fz.zf.model.app.Plate;
import com.fz.zf.mq.MsgProducer;
import com.fz.zf.util.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class TimerCronService {

    @Autowired
    MsgProducer msgProducer;

    @Scheduled(cron = "0/12 * * * * *")
    public void add() {
        log.info("进入定时--------------------------------");
        List<Plate> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Plate model = new Plate();
            model.setId(IDUtils.getSequenceStr());
            model.setCode(CodeUtils.getNike(1) + CodeUtils.getABC(1) + CodeUtils.getCheckCode());
            model.setApply_address("3-2-5南");
            model.setApply_person("刘公-" + CodeUtils.getNike(1));
            model.setCreate_user("1");
            model.setPhone(CodeUtils.getTel());
            model.setCreate_time(LocalDateTime.now());

            list.add(model);
        }
        msgProducer.sendList(list);
    }
}
