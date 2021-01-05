package com.fz.zf.model.gzh;

import java.util.Map;

public class SendTempModel {


    /**
     * touser : OPENID
     * template_id : ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY
     * url : http://weixin.qq.com/download
     * miniprogram : {"appid":"xiaochengxuappid12345","pagepath":"index?foo=bar"}
     * data : {"first":{"value":"恭喜你购买成功！","color":"#173177"},"keyword1":{"value":"巧克力","color":"#173177"},"keyword2":{"value":"39.8元","color":"#173177"},"keyword3":{"value":"2014年9月22日","color":"#173177"},"remark":{"value":"欢迎再次购买！","color":"#173177"}}
     */

    private String touser;
    private String template_id;
    private Map<String, Object> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
