package com.fz.zf.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling    // 开启定时任务
public class SpringTask {

    /*@Autowired
    MsgProducer msgProducer;

    @Scheduled(cron = "0/3 * * * * *")
    public void schedul()  {
        msgProducer.sendMsg("刘备");
    }*/
}
