package com.leon.zhihudailycus.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by leon on 6/13/16.
 */
abstract class CommonBaseAdapter<VH extends CommonBaseAdapter.ViewHolder, T> extends BaseAdapter {

    public List<T> mList;
    public Context mContext;
    public LayoutInflater mInflater;

    public CommonBaseAdapter(List<T> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = null;
        View view;
        view = convertView;
        if (view == null) {
            holder = onCreateViewHolder(parent, position);
            holder.view.setTag(holder);
        } else {
            holder = (VH) view.getTag();
        }
        onBindViewHolder(holder, position);
        return holder.view;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int position);

    public abstract void onBindViewHolder(VH holder, int position);

    public static class ViewHolder {
        View view;

        public ViewHolder(View view) {
            this.view = view;
        }
    }
}
