package com.fz.zf.mq;


import com.fz.zf.model.common.Constast;
import com.fz.zf.util.CodeUtils;
import com.fz.zf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
@Slf4j
public class MsgReceiver {
    @Autowired
    RedisUtil redisUtil;

    @RabbitHandler
    public void process(String phone) {
        log.info("接收【队列A】当中的手机号： " + phone);

        String phone_code = CodeUtils.getCode(4);
        redisUtil.set(Constast.PHONE_CODE + phone, phone_code, Constast.PHONE_CODE_EXPIRE);

        log.info("redis验证码-------------------------------:" + redisUtil.get(Constast.PHONE_CODE + phone));
        log.info("向手机号【" + phone + "】发送验证码----------:" + phone_code);
    }

}
