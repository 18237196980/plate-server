package com.fz.zf.model.gzh;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;
import java.util.Map;

/**
 * 公众号回复 文本图文实体
 */
@XStreamAlias("xml")
public class NewsMessage extends BaseMessage {

    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("ArticleCount")
    private String articleCount;
    @XStreamAlias("Articles")
    private List<Articles> articles;

    public NewsMessage(Map<String, String> map, List<Articles> articles) {
        super(map);
        this.msgType = "news";
        this.articles = articles;
        this.articleCount = articles.size() + "";
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(String articleCount) {
        this.articleCount = articleCount;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
