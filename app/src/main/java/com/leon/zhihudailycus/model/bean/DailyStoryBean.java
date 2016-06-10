package com.leon.zhihudailycus.model.bean;

import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class DailyStoryBean {
    private String data;
    private List<BaseStoryBean> commonStories;
    private List<BaseStoryBean> topStories;

    public DailyStoryBean() {
    }

    public DailyStoryBean(String data, List<BaseStoryBean> commonStories, List<BaseStoryBean> topStories) {
        this.data = data;
        this.commonStories = commonStories;
        this.topStories = topStories;
    }

    @Override
    public String toString() {
        return "DailyStoryBean{" +
                "data='" + data + '\'' +
                ", commonStories=" + commonStories +
                ", topStories=" + topStories +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<BaseStoryBean> getCommonStories() {
        return commonStories;
    }

    public void setCommonStories(List<BaseStoryBean> commonStories) {
        this.commonStories = commonStories;
    }

    public List<BaseStoryBean> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<BaseStoryBean> topStories) {
        this.topStories = topStories;
    }
}
