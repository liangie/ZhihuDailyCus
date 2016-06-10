package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.BitmapLruCache;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.model.bean.StoryDetailBean;
import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.JsonUtil;
import com.leon.zhihudailycus.util.ToolUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class StoryDetailAdapter extends PagerAdapter implements Handler.Callback {
    private final int UPDATE_ITEM_VIEW = 0X10;
    private final String REPLACE_CSS_FILE="REPLACE_CSS_FILE";
    private List<BaseStoryBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private RequestQueue mQueue;
    private Handler mHandler;
    private String uri, uri02, uri03;
    File css = new File("/sdcard/zh_style.css");
    String heander = "<html>" +
            "<head>" +
            "<meta charset=\"utf-8\">" +
            "<link rel=\"stylesheet\" href=\""+REPLACE_CSS_FILE+"\" type=\"text/css\"/>" +
//            "<link rel=\"stylesheet\" href=\"zh_style.css\" type=\"text/css\"/>" +
            "</head>" +
            "<body>";
    String footer = "</body>" +
            "</html>";

    public StoryDetailAdapter(Context mContext, List<BaseStoryBean> mList, RequestQueue queue) {
        this.mList = mList;
        this.mContext = mContext;
        this.mQueue = queue;
        mInflater = LayoutInflater.from(mContext);
//        Log.d("lianglei", "list-3:" + mList.toString());
//        File file = new File("/sdcard/zhihu_body.html");
//        uri = "file:///storage/sdcard0/zhihu_body.html";
//        uri02 = "file:///storage/sdcard0/zhihu_body02.html";
//        uri03 = "file:///storage/sdcard0/zhihu_body03.html";
        File file = new File("/sdcard/test.html");
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseStoryBean bean = mList.get(position);
        int id = bean.getId();
        String story_uri = APIUtil.GET_STORY_DETAIL + id;
        View view;
        view = mInflater.inflate(R.layout.story_detail, null);
        final TextView textView = (TextView) view.findViewById(R.id.story_detail_text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        final WebView webView = (WebView) view.findViewById(R.id.web_view);
        final NetworkImageView headerImg = (NetworkImageView) view.findViewById(R.id.header_img);
        final TextView imageSource = (TextView) view.findViewById(R.id.image_source);
        final TextView title = (TextView) view.findViewById(R.id.story_title);
        final RelativeLayout headerHolder = (RelativeLayout) view.findViewById(R.id.header_holder);
        ViewGroup.LayoutParams params = headerHolder.getLayoutParams();
        params.height = ToolUtil.dpToPx(200, mContext.getResources());
        headerHolder.setLayoutParams(params);
//        webView.loadUrl(uri);
//        headerHolder.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(story_uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            StoryDetailBean bean = JsonUtil.buildStoryDetail(response);
                            if (bean != null) {
                                String cssURL = bean.getCss();
                                cssURL = cssURL.replace("[","").replace("]","").replace("\"","");
                                Log.d("lianglei","cssURL:"+cssURL);
                                String cssUri = ToolUtil.getAndStoreCss(cssURL, mQueue, mContext);
                                heander.replace(REPLACE_CSS_FILE, cssUri);
                                String aab = heander + bean.getBody() + footer;
                                ToolUtil.saveStringToSD(aab, "/sdcard/test.html");
                                webView.loadUrl("file:///storage/sdcard0/test.html");
//                                webView.loadDataWithBaseURL("file:///android_asset/", heander + bean.getBody() + footer, "text/html", "utf-8", null);

                                imageSource.setText(bean.getImageSource());
                                title.setText(bean.getTitle());
                                networkImageViewUse(headerImg, bean.getImage());

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mQueue.add(jsonObjectRequest);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_ITEM_VIEW:

                break;
        }
        return false;
    }


    private void networkImageViewUse(NetworkImageView iv, String url) {
        ImageLoader imLoader = new ImageLoader(mQueue, new BitmapLruCache());
//        iv.setDefaultImageResId(R.drawable.about_logo);
//        iv.setErrorImageResId(R.drawable.about_logo);
        iv.setImageUrl(url, imLoader);
    }
}
