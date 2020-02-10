package com.misy.photopicker.entity;

import java.util.List;

/**
 * Created by Administrator on 2020/1/7/007.
 */

public class PhotoEntity {
    private String name;
    private int num;
    private List<String> imageList;
    private String parentPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
