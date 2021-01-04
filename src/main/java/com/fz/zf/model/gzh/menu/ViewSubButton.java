package com.fz.zf.model.gzh.menu;

/**
 * 小菜单(跳转url)
 */
public class ViewSubButton extends SubButton {

    private String url;

    public ViewSubButton(String name, String type, String url) {
        super(name, type);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
