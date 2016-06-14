package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.util.ToolUtil;

import java.util.List;

/**
 * Created by leon on 6/13/16.
 */
public class StoryListAdapter extends CommonBaseAdapter<StoryListAdapter.ViewHolder, BaseStoryBean> {

    private RequestQueue mQueue;

    public StoryListAdapter(List<BaseStoryBean> mList, Context mContext, RequestQueue mQueue) {
        super(mList, mContext);
        this.mQueue = mQueue;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = mInflater.inflate(R.layout.daily_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

//    private void setHeight(final View convertView) {
//        int height = mScreenWidth / 4 - 10;
//        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaseStoryBean bean = mList.get(position);
        holder.storyTitle.setText(bean.getTitle());
        String imageAdd = bean.getImageAdd();
        imageAdd = imageAdd.replace("[", "").replace("]", "").replace("\"", "").replace("\\", "");
        ToolUtil.useNetworkImageView(holder.storyImg, imageAdd, mQueue);
    }

    public class ViewHolder extends CommonBaseAdapter.ViewHolder {

        NetworkImageView storyImg;
        TextView storyTitle;

        public ViewHolder(View view) {
            super(view);
            storyImg = (NetworkImageView) view.findViewById(R.id.story_image);
            storyTitle = (TextView) view.findViewById(R.id.story_title);
        }
    }
}
