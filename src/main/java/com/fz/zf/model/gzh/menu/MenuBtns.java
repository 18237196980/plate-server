package com.fz.zf.model.gzh.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 主菜单bean
 */
public class MenuBtns {

    private List<RootMenu> button = new ArrayList<>();

    public List<RootMenu> getButton() {
        return button;
    }

    public void setButton(List<RootMenu> button) {
        this.button = button;
    }
}
