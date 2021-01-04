package com.fz.zf.model.gzh.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 小菜单(照片)
 */
public class PhotoSubButton extends SubButton {

    private String key;

    private List<SubButton> sub_button = new ArrayList<>();

    public PhotoSubButton(String name, String type, String key) {
        super(name, type);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<SubButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<SubButton> sub_button) {
        this.sub_button = sub_button;
    }
}
