package com.leon.zhihudailycus.model.bean;

import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class DailyStoryBean {
    private String date;
    private List<BaseStoryBean> commonStories;
    private List<BaseStoryBean> topStories;

    public DailyStoryBean() {
    }

    public DailyStoryBean(String date, List<BaseStoryBean> commonStories, List<BaseStoryBean> topStories) {
        this.date = date;
        this.commonStories = commonStories;
        this.topStories = topStories;
    }

    @Override
    public String toString() {
        return "DailyStoryBean{" +
                "date='" + date + '\'' +
                ", commonStories=" + commonStories +
                ", topStories=" + topStories +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
