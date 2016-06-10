package com.leon.zhihudailycus.model.bean;

import java.io.Serializable;

/**
 * Created by leon on 6/7/16.
 */
public class BaseStoryBean implements Serializable {
    private String imageAdd;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private boolean multipic;

    public BaseStoryBean() {
    }

    public BaseStoryBean(String imageAdd, int type, int id, String ga_prefix, String title, boolean multipic) {
        this.imageAdd = imageAdd;
        this.type = type;
        this.id = id;
        this.ga_prefix = ga_prefix;
        this.title = title;
        this.multipic = multipic;
    }

    @Override
    public String toString() {
        return "BaseStoryBean{" +
                "imageAdd='" + imageAdd + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                ", multipic=" + multipic +
                '}';
    }

    public String getImageAdd() {
        return imageAdd;
    }

    public void setImageAdd(String imageAdd) {
        this.imageAdd = imageAdd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }
}
