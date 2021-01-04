package com.fz.zf.model.gzh.menu;

public abstract class AbstractBtn {

    private String name;

    @Override
    public String toString() {
        return "AbstractBtn{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractBtn(String name) {
        this.name = name;
    }
}
