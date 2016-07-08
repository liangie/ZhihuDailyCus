package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.util.ToolUtil;

import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class DailyAdapter extends BaseAdapter implements Handler.Callback {
    private List<BaseStoryBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private Handler mHandler;
    private RequestQueue mQueue;

    public DailyAdapter(List<BaseStoryBean> mList, Context mContext, RequestQueue queue) {
        this.mList = mList;
        this.mContext = mContext;
        this.mQueue = queue;
        mInflater = LayoutInflater.from(mContext);
        mHandler = new Handler(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseStoryBean bean = (BaseStoryBean)getItem(position);
        View view;
        view = convertView;
        if(view == null){
            view=mInflater.inflate(R.layout.daily_item_layout, null);
        }
        ((TextView)view.findViewById(R.id.story_title)).setText(bean.getTitle());
        /******* imageRequest *********/
        String add = bean.getImageAdd().replace("[","").replace("]","").replace("\"","").replace("\\","");
        Log.d("lianglei","imageAdd:"+add);
        NetworkImageView imageView = (NetworkImageView)view.findViewById(R.id.story_iamge);
        ToolUtil.networkImageViewUse(mQueue, imageView, add);

        return view;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
