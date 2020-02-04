package com.example.administrator.wanandroid.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

// 文章数据类
public class BaseCustomViewModel implements Serializable, MultiItemEntity {
    private String jumpUrl;
    private String title;
    private String time;
    private String author;
    private boolean isCollect;
    private int type;
    private String path;
    private String classic;
    private String description;
    private int originId;
    private int id;

    public final static int NORMAL = 1;
    public final static int WITH_PIC = 2;
    public final static int PROJECT = 3;

    public BaseCustomViewModel(int type){
        this.type = type;
    }


    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public String getClassic() {
        return classic;
    }

    public void setClassic(String classic) {
        this.classic = classic;
    }
}
