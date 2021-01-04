package com.fz.zf.model.gzh.menu;

/**
 * 普通根菜单
 */
public class ClickRootMenu extends RootMenu {

    private String type;
    private String key;

    public ClickRootMenu(String name, String type, String key) {
        super(name);
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ClickRootMenu{" +
                "type='" + type + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
