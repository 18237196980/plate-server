package com.fz.zf.util;

import com.ex.framework.web.LayTableResult;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 构建菜单列表所需数据格式
 * @param <T>
 */
public class LayTable<T> implements Serializable {
    private int code = 0;
    private String msg = "";
    private long count;
    private List<T> data = new ArrayList();


    public LayTable() {
    }


    public static <T> LayTable<T> success(long total, List<T> rows) {
        return new LayTable(0, "", total, rows);
    }

    public LayTable(int code, String msg, long count, List<T> data) {
        if (StringUtils.isEmpty(msg)) {
            msg = "";
        }

        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }


    public static <T> LayTable<T> error(String msg) {
        return new LayTable(-1, msg, 0L, new ArrayList());
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
