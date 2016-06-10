package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;

import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class DailyAdapter extends BaseAdapter {
    private List<BaseStoryBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public DailyAdapter(List<BaseStoryBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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
        return view;
    }
}
