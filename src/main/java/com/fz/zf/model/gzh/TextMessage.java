package com.fz.zf.model.gzh;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * 公众号回复 文本消息实体
 */
@XStreamAlias("xml")
public class TextMessage extends BaseMessage {

    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("Content")
    private String content;

    public TextMessage(Map<String, String> map, String content) {
        super(map);
        this.content = content;
        this.msgType = "text";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
