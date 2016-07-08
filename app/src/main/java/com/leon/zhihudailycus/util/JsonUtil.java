package com.leon.zhihudailycus.util;

import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.model.bean.DailyStoryBean;
import com.leon.zhihudailycus.model.bean.StoryDetailBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class JsonUtil {

    /**
     * 构建每日日报的数据bean
     *
     * @param jsonOb
     * @return
     * @throws Exception
     */
    public static DailyStoryBean buildLastestStories(JSONObject jsonOb) throws Exception {
        if (jsonOb == null) {
            return null;
        }
        JSONObject jsonObject = jsonOb;
        DailyStoryBean dailyBean = new DailyStoryBean();
        dailyBean.setDate(jsonObject.getString("date"));

        //获取stories
        JSONArray storiesArray = jsonObject.getJSONArray("stories");
        List<BaseStoryBean> commonStories = new ArrayList<>();
        for (int i = 0; i < storiesArray.length(); i++) {
            JSONObject object = (JSONObject) storiesArray.get(i);
            BaseStoryBean bean = new BaseStoryBean();
            bean.setTitle(object.getString("title"));
            bean.setType(object.getInt("type"));
            bean.setGa_prefix(object.getString("ga_prefix"));
            bean.setId(object.getInt("id"));
            bean.setImageAdd(object.getString("images"));
//            bean.setMultipic(object.getBoolean("multipic"));
            commonStories.add(bean);
        }
        dailyBean.setCommonStories(commonStories);

        //获取topStories
        JSONArray topStoriesArray = jsonObject.getJSONArray("top_stories");
        List<BaseStoryBean> topStories = new ArrayList<>();
        for (int j = 0; j < topStoriesArray.length(); j++) {
            JSONObject object = (JSONObject) topStoriesArray.get(j);
            BaseStoryBean bean = new BaseStoryBean();
            bean.setTitle(object.getString("title"));
            bean.setType(object.getInt("type"));
            bean.setGa_prefix(object.getString("ga_prefix"));
            bean.setId(object.getInt("id"));
            bean.setImageAdd(object.getString("image"));
            if (object.length() > 5) {
                bean.setMultipic(object.getBoolean("multipic"));
            }
            topStories.add(bean);
        }
        dailyBean.setTopStories(topStories);

        return dailyBean;
    }

    public static DailyStoryBean buildLastestStories(String jsonString) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
        return buildLastestStories(jsonObject);
    }

    public static DailyStoryBean buildEarlyStories(JSONObject jsonOb) throws Exception {
        if (jsonOb == null) {
            return null;
        }
        JSONObject jsonObject = jsonOb;
        DailyStoryBean dailyBean = new DailyStoryBean();
        dailyBean.setDate(jsonObject.getString("date"));

        //获取stories
        JSONArray storiesArray = jsonObject.getJSONArray("stories");
        List<BaseStoryBean> commonStories = new ArrayList<>();
        for (int i = 0; i < storiesArray.length(); i++) {
            JSONObject object = (JSONObject) storiesArray.get(i);
            BaseStoryBean bean = new BaseStoryBean();
            bean.setTitle(object.getString("title"));
            bean.setType(object.getInt("type"));
            bean.setGa_prefix(object.getString("ga_prefix"));
            bean.setId(object.getInt("id"));
            bean.setImageAdd(object.getString("images"));
//            bean.setMultipic(object.getBoolean("multipic"));
            commonStories.add(bean);
        }
        dailyBean.setCommonStories(commonStories);

        return dailyBean;
    }

    /**
     * 保存当前的storyList到SharedPreferenced以String的形式
     *
     * @param list
     * @return
     */
    public static String buildJsonStringWithStoryList(List<BaseStoryBean> list) {
        StringBuffer jsonString = new StringBuffer();
        JSONObject jsonObject = new JSONObject();
        JSONArray stories = new JSONArray();
        try {
            for (BaseStoryBean bean : list) {
                JSONObject obj = new JSONObject();
                if(!bean.isShowDate()) {
                    obj.put("title", bean.getTitle());
                    obj.put("type", bean.getType());
                    obj.put("ga_prefix", bean.getGa_prefix());
                    obj.put("id", bean.getId());
                    obj.put("images", bean.getImageAdd());
                    obj.put("showDate",false);
                }else{
                    obj.put("date", bean.getDate());
                    obj.put("showDate",true);
                }
                stories.put(obj);
            }
            jsonObject.put("stories", stories);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    /**
     * 用存储在sharedPreferenced中的jsonString来构建StoryList
     *
     * @param jsonString
     * @return
     */
    public static List<BaseStoryBean> buildSharedStoryListWithJsonString(String jsonString) {
        JSONObject jsonObject = null;
        List<BaseStoryBean> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject == null) {
                return null;
            }

            //获取stories
            JSONArray storiesArray = jsonObject.getJSONArray("stories");
//            List<BaseStoryBean> commonStories = new ArrayList<>();
            for (int i = 0; i < storiesArray.length(); i++) {
                JSONObject object = (JSONObject) storiesArray.get(i);
                BaseStoryBean bean = new BaseStoryBean();
                if(!object.getBoolean("showDate")) {
                    bean.setTitle(object.getString("title"));
                    bean.setType(object.getInt("type"));
                    bean.setGa_prefix(object.getString("ga_prefix"));
                    bean.setId(object.getInt("id"));
                    bean.setImageAdd(object.getString("images"));
                    bean.setShowDate(false);
                }else{
                    bean.setShowDate(true);
                    bean.setDate(object.getString("date"));
                }
//            bean.setMultipic(object.getBoolean("multipic"));
                list.add(bean);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /*
    {
  "body": "<div class=\"main-wrap content-wrap\">\n<div class=\"headline\">\n\n<div class=\"img-place-holder\"></div>\n\n\n\n</div>\n\n<div class=\"content-inner\">\n\n\n\n<div class=\"question\">\n<h2 class=\"question-title\">说了 800 遍了，这些照片真不是 P 的！</h2>\n<div class=\"answer\">\n\n<div class=\"meta\">\n<img class=\"avatar\" src=\"http://pic4.zhimg.com/39f634296e15dd39fcf232afcaa49f53_is.jpg\">\n<span class=\"author\">望天儿，</span><span class=\"bio\">透过天地气象看凡间千万事</span>\n</div>\n\n<div class=\"content\">\n<p>天哪！天空看起来好可怕，被这些长得好奇怪的云朵给占据了，还有此起彼伏的闪电，仿佛天就要垮下来了，这不会是世界末日吧&hellip;&hellip;</p>\r\n<p><img class=\"content-image\" src=\"https://pic3.zhimg.com/619df015ea087731e01c436e46653376_b.jpg\" alt=\"\" /></p>\r\n<p>几天前，美国就亲历这样一幅场景。遭到龙卷风和风暴吹袭的德克萨斯州、科罗拉多州和密苏里州，房屋基础设施被破坏、树木损毁，甚至有人受伤死亡。 <strong>然而</strong>，在雷暴天气过后，人们惊奇地发现，天空中形成了蔚为壮观的云海。一小颗一小颗棉花糖似的云朵密集排列，仿佛外星人要来了的奇异感觉。如果你是密恐患者，那更是吃不消。</p>\r\n<p><img class=\"content-image\" src=\"https://pic1.zhimg.com/ff0fd170675532156995a032995693e4_b.png\" alt=\"\" /></p>\r\n<p>这些奇异的云朵被称为<strong>&ldquo;乳状积云&rdquo;、&ldquo;棉花糖云&rdquo;，或者&ldquo;颠簸的云彩&rdquo;。英文名称为 Mammatus Clouds，Mammatus 来自拉丁语 mamma（乳房），因其形状和牛的乳房相似而得名。所以，它又被称为&ldquo;乳房云&rdquo;。</strong></p>\r\n<p><strong>乳状积云是</strong><strong>在积雨云下方形成的乳状型积云</strong><strong>。</strong>当下降气流中温度较冷的空气与上升气流中温度较暖的空气相遇，以柔软、凹凸不平、块状、不透明的形态组成一个个袋子形状的乳状云，<strong>每块云朵半径约 1 至 3 公里，长度基本在 0.5 公里左右</strong>。</p>\r\n<p><strong>一朵乳状云可以存在大约 10 分钟，但一整片天空的乳状云可以保持静态 15 分钟，甚至几个小时。</strong></p>\r\n<p><img class=\"content-image\" src=\"https://pic3.zhimg.com/ae722add76892baa7fda4a1af83a9076_b.png\" alt=\"\" /></p>\r\n<p>美国国家大气研究中心物理学家<strong>Daniel Breed</strong>曾说，<strong>空气的浮力和对流是形成乳状云的关键。&ldquo;这好像是上下颠倒的对流。&rdquo;</strong> 它是自然界中罕见的自然现象，与雷暴、旋转火焰、极光、赤潮、冰圈、重力波云等齐名。<strong>奇特外观让很多人看到后以为即将有大风暴或者大雷雨到来，但实际上，它往往出现在暴雨过后。</strong> 就目前的观测记录来看，<strong>乳状积云在美国西南部出现的频率多一些。</strong>一位名叫 Jason Asselin 曾亲眼见证，<strong>&ldquo;外</strong><strong>面突然变得很黄，感觉奇怪又神秘&hellip;这是我目睹过最疯狂的事。&rdquo;</strong></p>\r\n<p><img class=\"content-image\" src=\"https://pic2.zhimg.com/6c6c3098f46660728826038ee20144b1_b.png\" alt=\"\" /></p>\r\n<p>Daniel Breed 解释说，<strong>东西两个方向的海风碰撞，产生波状扰动，从内陆向西南方向移动。西南方恰恰是形成阵晨风云的一个重要区域。</strong>当潮湿的海洋空气被吹到波状扰动的上方，它会冷却、凝聚形成云。有时，形成的云只有一波，有时，一连串多达十波。<strong>&ldquo;如</strong><strong>果你看天上的云朵，感觉好像是在向后翻滚。事实上，云在最前边不断形成，在最后面又不断消失。这给人一种云总在不停滚动的感觉。&rdquo;</strong><strong>加拿大 Saskatchewan 也曾出现过乳状积云，当地连日饱受龙卷风袭击后，终于放晴的蓝色天空出现了白色的乳状云。</strong></p>\r\n<p><img class=\"content-image\" src=\"https://pic2.zhimg.com/d91f2b355c4d1d94d932c39521c1942d_b.jpg\" alt=\"\" /></p>\r\n<p><strong>千万不要因为好奇而特地去这些地方赏云，否则遇上龙卷风可不是闹着玩儿的。</strong><strong>最后让我们欣赏欣赏它们留下的身影吧&hellip;&hellip;</strong></p>\r\n<p><img class=\"content-image\" src=\"https://pic2.zhimg.com/7586e1b256e539d1413717e97cd65215_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic4.zhimg.com/6f8bdcd9f0adbee057f3ff92fb20ecef_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic2.zhimg.com/3882f15a62ed3588fcc232c1443d5429_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic4.zhimg.com/32757090df6e136aeee72a77bad8227f_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic1.zhimg.com/61541e6e0c72c236e28136c383c97ce4_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic1.zhimg.com/193455b07e5ff7d9463bfde03087e264_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic2.zhimg.com/ee0006e7bee5aa1f041ab5b1dad5ab51_b.jpg\" alt=\"\" /></p>\r\n<p><img class=\"content-image\" src=\"https://pic2.zhimg.com/e7755fce82cbd4ab4814a69bf004cb89_b.jpg\" alt=\"\" /></p>\n\n<div class=\"view-more\"><a href=\"http://zhuanlan.zhihu.com/p/21309393\">查看知乎讨论</a></div>\n\n</div>\n</div>\n</div>\n\n\n</div>\n</div>",
  "image_source": "Lars Plougmann / CC BY-SA",
  "title": "看着怪吓人，但这些世界末日一样的照片还真不是 P 的",
  "image": "http://pic2.zhimg.com/7b1aa420c745d5bd533ec66f7ab5b2ad.jpg",
  "share_url": "http://daily.zhihu.com/story/8412707",
  "js": [],
  "ga_prefix": "060715",
  "images": [
    "http://pic3.zhimg.com/72111b34ba1bdb46ac4142916b39b846.jpg"
  ],
  "type": 0,
  "id": 8412707,
  "css": [
    "http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"
  ]
}
*/
    public static StoryDetailBean buildStoryDetail(JSONObject jsonObject) throws Exception {
        StoryDetailBean storyBean = new StoryDetailBean();
        if (jsonObject == null)
            return null;
        storyBean.setBody(jsonObject.getString("body"));
        storyBean.setImageSource(jsonObject.getString("image_source"));
        storyBean.setTitle(jsonObject.getString("title"));
        storyBean.setImage(jsonObject.getString("image"));
        storyBean.setShareUrl(jsonObject.getString("share_url"));
        storyBean.setJs(jsonObject.getString("js"));
        storyBean.setGa_prefix(jsonObject.getString("ga_prefix"));
        storyBean.setImages(jsonObject.getString("images"));
        storyBean.setType(jsonObject.getInt("type"));
        storyBean.setId(jsonObject.getInt("id"));
        storyBean.setCss(jsonObject.getString("css"));
        return storyBean;
    }

}
