package com.fz.zf.mq;


import com.fz.zf.service.app.PlateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ListMsgReceiver {

    @Autowired
    PlateService plateService;

    @RabbitListener(queues = RabbitConfig.QUEUE_B)
    public void process(List content) {
        log.info("接收处理【队列B】当中的消息----------");
        plateService.add(content);
    }

}
