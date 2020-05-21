package com.angel.crowd.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    //  当前节点的id
    private Integer id;
    //  当前节点的父类id
    private Integer pid;
    //  当前节点名称
    private String name;
    //  与当前节点相关联的页面
    private String url;
    //  当前节点的图标样式
    private String icon;
    //  存储当前节点的子节点
    private List<Menu> children=new ArrayList<Menu>();

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Menu() {
    }

    public Menu(Integer id, Integer pid, String name, String url, String icon, List<Menu> children, Boolean open) {

        this.id = id;
        this.pid = pid;
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.children = children;
        this.open = open;
    }

    //  控制节点是否默认为打开装，设置为true默认为打开
    private  Boolean open=true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}