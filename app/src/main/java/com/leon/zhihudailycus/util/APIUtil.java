package com.leon.zhihudailycus.util;

/**
 * Created by leon on 6/6/16.
 */
public class APIUtil {

    /* 当日最新story列表 */
    public static final String LATEST_STORY_LIST = "http://news-at.zhihu.com/api/4/news/latest";
    /**
     * 获取单篇日报的详细内容，后接目标日报的id号
     * eg:http://news-at.zhihu.com/api/4/news/8412707
     */
    public static final String GET_STORY_DETAIL = "http://news-at.zhihu.com/api/4/news/";

    /**
     * 获取指定日期的前一天的story list
     * eg:要获取20131118的list，则请求
     * http://news.at.zhihu.com/api/4/news/before/20131119
     */
    public static final String EARLY_STORY_LIST = "http://news.at.zhihu.com/api/4/news/before/";

    /**
     * 用户登录
     */
    public static final String USER_LOGIN="http://www.zhihu.com/login/email";

    /**
     * 查看所有关注者
     */
    public static final String USER_FOLLOWEES = "http://www.zhihu.com/people/liang-lei-17/followees";
}
