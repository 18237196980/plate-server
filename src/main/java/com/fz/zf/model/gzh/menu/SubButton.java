package com.fz.zf.model.gzh.menu;

/**
 * 小菜单的基类
 */
public class SubButton extends AbstractBtn {
    private String type;

    public SubButton(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
