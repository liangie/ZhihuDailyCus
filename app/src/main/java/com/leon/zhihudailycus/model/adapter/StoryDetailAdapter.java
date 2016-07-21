package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.model.bean.StoryDetailBean;
import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.JsonUtil;
import com.leon.zhihudailycus.util.SharedPreferenceUtil;
import com.leon.zhihudailycus.util.ToolUtil;
import com.leon.zhihudailycus.view.CusScrollView;
import com.leon.zhihudailycus.view.CusWebView;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class StoryDetailAdapter extends PagerAdapter implements Handler.Callback {
    private final int UPDATE_ITEM_VIEW = 0X10;
    private List<BaseStoryBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private RequestQueue mQueue;
    private Handler mHandler;
    private int lastY = 0;
    private int lastTranslationY;
    private int toolbarH = -1;
    String heander = "<html>" +
            "<head>" +
            "<meta charset=\"utf-8\">" +
//            "<link rel=\"stylesheet\" href=\"zh_style.css\" type=\"text/css\"/>" +
            "<link rel=\"stylesheet\" href=\"../css_folder/zh_style.css\" type=\"text/css\"/>" +
            "</head>" +
            "<body>";
    String footer = "</body>" +
            "</html>";

    public StoryDetailAdapter(Context mContext, List<BaseStoryBean> mList, RequestQueue queue) {
        this.mList = mList;
        this.mContext = mContext;
        this.mQueue = queue;
        mInflater = LayoutInflater.from(mContext);
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
        lastY = 0;
        lastTranslationY = 0;
        final BaseStoryBean bean = mList.get(position);
        int id = bean.getId();
        String story_uri = APIUtil.GET_STORY_DETAIL + id;

        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            toolbarH = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }

        View view;
        view = mInflater.inflate(R.layout.story_detail, null);
//        final TextView detailText = (TextView) view.findViewById(R.id.story_detail_text);
//        detailText.setMovementMethod(LinkMovementMethod.getInstance());
        final Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final CusWebView webView = (CusWebView) view.findViewById(R.id.web_view);
        final NetworkImageView headerImg = (NetworkImageView) view.findViewById(R.id.header_img);
        final TextView imageSource = (TextView) view.findViewById(R.id.image_source);
        final CusScrollView mScrollView = (CusScrollView) view.findViewById(R.id.sv_story);
        final TextView title = (TextView) view.findViewById(R.id.story_title);
        final RelativeLayout headerHolder = (RelativeLayout) view.findViewById(R.id.header_holder);

        ViewGroup.LayoutParams params = headerHolder.getLayoutParams();
        params.height = ToolUtil.dpToPx(200, mContext.getResources());
        headerHolder.setLayoutParams(params);
        CusScrollView.scrollChangedListener listener = new CusScrollView.scrollChangedListener() {
            @Override
            public void setView(View v) {

            }

            @Override
            public void setAttrs(int L, int T, int oldL, int oldT) {
                //向上划为负，向下划为正
                int offset = lastY - T;
                int newT = lastTranslationY + offset / 4;
                newT = newT > 0 ? 0 : (newT < -toolbarH ? -toolbarH : newT);
                mToolbar.setAlpha((float) (1 - Math.abs(newT) * 1.0 / toolbarH));
//                Log.d("lianglei", "newT:" + newT + "; lastY:" + lastY + "; T:" + T + "; offset:" + offset + "; lastTransY:" + lastTranslationY);
                if (lastY != 0 || lastTranslationY != 0) {
                    mToolbar.setTranslationY(newT);
                }
                headerHolder.setTranslationY(-T / 2);
//                mScrollView.setTranslationY(newT);
                lastY = T;
                lastTranslationY = newT;
            }
        };
        mScrollView.setListener(listener);

        mToolbar.setNavigationIcon(android.R.drawable.ic_btn_speak_now);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        String storyString = SharedPreferenceUtil.getLocalDataShared(mContext).getString("story_" + bean.getId(), "");
        if (!"".equals(storyString)) {
            try {
                JSONObject response = new JSONObject(storyString);
                useStoryJson(webView, imageSource, title, headerImg, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(story_uri, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            SharedPreferenceUtil.getLocalDataShared(mContext).edit().putString("story_" + bean.getId(), response.toString()).commit();
                            useStoryJson(webView, imageSource, title, headerImg, response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            mQueue.add(jsonObjectRequest);
        }

        TextView blocker = (TextView) view.findViewById(R.id.head_blocker);
        ViewGroup.LayoutParams blockParams = blocker.getLayoutParams();
        blockParams.height = toolbarH + ToolUtil.dpToPx(200, mContext.getResources());
        blocker.setLayoutParams(blockParams);

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

    private void useStoryJson(WebView webView, TextView imageSource, TextView title, NetworkImageView headerImg, JSONObject response) {
        try {
            StoryDetailBean bean = JsonUtil.buildStoryDetail(response);
            if (bean != null) {
                String aab = heander + bean.getBody() + footer;
                String filename = ToolUtil.getHtmlStoryFolder(mContext) + File.separator + bean.getId() + ".html";
                ToolUtil.saveStringToSD(aab, filename);
                webView.loadUrl((Uri.fromFile(new File(filename))).toString());
                imageSource.setText(bean.getImageSource());
                title.setText(bean.getTitle());
                ToolUtil.networkImageViewUse(headerImg, bean.getImage(), mQueue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        for (int j = 0; j < container.getChildCount(); j++) {
            ViewGroup curr = (ViewGroup) container.getChildAt(j);
//            ViewGroup curr = (ViewGroup) container.getChildAt(container.getChildCount());
            for (int i = 0; i < curr.getChildCount(); i++) {
                View view = curr.getChildAt(i);
                if (view instanceof CusScrollView) {
                    int offset = ((CusScrollView) view).getVerticalScrollOffset();
                    if (offset != 0) {
                        //这个判断条件可以极大减少smoothScrollTo的执行次数，
                        ((CusScrollView) view).smoothScrollTo(0, 0);
                    }
                    break;
                }
            }
        }
    }

}
