package com.leon.zhihudailycus.util;

/**
 * Created by leon on 6/6/16.
 */
public class APIUtil {

    /* 当日最新story列表 */
    public static final String LATEST_STORIES = "http://news-at.zhihu.com/api/4/news/latest";
    /**
     * 获取单篇日报的详细内容，后接目标日报的id号
     * eg：http://news-at.zhihu.com/api/4/news/8412707
     */
    public static final String GET_STORY_DETAIL = "http://news-at.zhihu.com/api/4/news/";
}
