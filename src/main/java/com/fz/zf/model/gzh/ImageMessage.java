package com.fz.zf.model.gzh;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * 公众号回复 文本图片实体
 */
@XStreamAlias("xml")
public class ImageMessage extends BaseMessage {

    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("Image")
    private Image image;

    public ImageMessage(Map<String, String> map, Image image) {
        super(map);
        this.msgType = "image";
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
