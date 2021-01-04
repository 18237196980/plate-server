package com.fz.zf.model.gzh.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类菜单(多个小菜单)
 */
public class TreeRootMenu extends RootMenu {

    private List<SubButton> sub_button = new ArrayList<>();

    public TreeRootMenu(String name, List<SubButton> sub_button) {
        super(name);
        this.sub_button = sub_button;
    }

    public List<SubButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<SubButton> sub_button) {
        this.sub_button = sub_button;
    }
}
