package com.fz.zf.util;

import com.fz.zf.model.app.SysAdmin;

import java.util.List;

public class DemoResults {
    private Integer code;
    private String msg;
    private Integer count;
    private List<SysAdmin> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SysAdmin> getData() {
        return data;
    }

    public void setData(List<SysAdmin> data) {
        this.data = data;
    }

    public DemoResults(Integer code, String msg, Integer count, List<SysAdmin> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
