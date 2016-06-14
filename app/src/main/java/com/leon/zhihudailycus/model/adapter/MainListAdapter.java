package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.activity.StoryDetailActivity;
import com.leon.zhihudailycus.model.bean.DailyStoryBean;
import com.leon.zhihudailycus.view.DailyListView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leon on 6/6/16.
 */
public class MainListAdapter extends BaseAdapter {
    private List<DailyStoryBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MainListAdapter(List<DailyStoryBean> mList, Context mContext) {
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
        final DailyStoryBean bean = (DailyStoryBean) getItem(position);

        View view;
        view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.main_list_item, null);
        }
        ((TextView) view.findViewById(R.id.main_item_tv)).setText(bean.getDate());
        DailyListView subListView = (DailyListView) view.findViewById(R.id.sub_listview);
        DailyAdapter dailyAdapter = new DailyAdapter(bean.getCommonStories(), mContext);
        subListView.setAdapter(dailyAdapter);
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, StoryDetailActivity.class);
                intent.putExtra("commonlist",(Serializable)bean.getCommonStories());
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
