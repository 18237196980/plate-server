package com.fz.zf.model.gzh;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
public class BaseMessage {

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("CreateTime")
    private long createTime;

    public BaseMessage(Map<String, String> map) {
        this.toUserName = map.get("FromUserName");
        this.fromUserName = map.get("ToUserName");
        this.createTime = System.currentTimeMillis() / 1000;
    }
}
