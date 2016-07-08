package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.util.ConstantUtil;
import com.leon.zhihudailycus.util.JsonUtil;
import com.leon.zhihudailycus.util.SharedPreferenceUtil;
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
        holder.tvStoryTitle.setText(bean.getTitle());
        String imageAdd = bean.getImageAdd();
        imageAdd = imageAdd.replace("[", "").replace("]", "").replace("\"", "").replace("\\", "");
        ToolUtil.useNetworkImageView(holder.ivStoryImg, imageAdd, mQueue);
        holder.tvPosition.setText(mContext.getString(R.string.story_position,position));

        if (bean.isShowDate()) {
            holder.tvDate.setText(bean.getDate());
            holder.rlDate.setVisibility(View.VISIBLE);
        } else {
            holder.rlDate.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends CommonBaseAdapter.ViewHolder {

        NetworkImageView ivStoryImg;
        TextView tvStoryTitle;
        TextView tvDate;
        LinearLayout llInfo;
        RelativeLayout rlDate;
        TextView tvPosition;

        public ViewHolder(View view) {
            super(view);
            ivStoryImg = (NetworkImageView) view.findViewById(R.id.story_image);
            tvStoryTitle = (TextView) view.findViewById(R.id.story_title);
            tvDate = (TextView) view.findViewById(R.id.date_split);
            llInfo = (LinearLayout) view.findViewById(R.id.base_info_ly);
            rlDate = (RelativeLayout) view.findViewById(R.id.date_ly);
            tvPosition=(TextView)view.findViewById(R.id.story_posi);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //保存当前最新的storyList到SharedPreferenced，用于在无网络连接时的展示数据
        SharedPreferenceUtil.getLocalDataShared(mContext).edit().putString(
                ConstantUtil.LOCAL_DATA_STORY_LIST, JsonUtil.buildJsonStringWithStoryList(mList)).commit();
    }
}
