package com.leon.zhihudailycus.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.adapter.StoryDetailAdapter;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 6/7/16.
 */
public class StoryDetailActivity extends BaseActivity {

    private ViewPager mViewPager;
    private List<BaseStoryBean> mList;
    private StoryDetailAdapter mAdapter;
    private RequestQueue mQueue;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        mList = (List<BaseStoryBean>)getIntent().getSerializableExtra("commonlist");
        position = getIntent().getIntExtra("position",0);
        Log.d("lianglei","mList.size-1:"+mList.size()+"; position-1:"+position);
        rebuildList(mList, position);
        Log.d("lianglei","mList.size-2:"+mList.size()+"; position-2:"+position);
        mQueue = Volley.newRequestQueue(this);
        init();
    }

    private void init(){
        mViewPager = (ViewPager)findViewById(R.id.viewpager_detail);
        if(mList==null){
            mList = new ArrayList<>();
        }
        mAdapter = new StoryDetailAdapter(this, mList,mQueue);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void rebuildList(List<BaseStoryBean> list, int position){
        int headDateCount = 0;
        int bottomDateCount = 0;
        List<BaseStoryBean> headList = list.subList(0,position-1);
        List<BaseStoryBean> bottomList = list.subList(position+1, list.size());
        List<BaseStoryBean> removeList= new ArrayList<>();

        Log.d("lianglei",(position-1)+"("+headList.size()+")-headList:"+headList.toString());
        Log.d("lianglei",(position+":"+list.size())+"("+bottomList.size()+");bottomList:"+bottomList.toString());
        for(int i =0;i<position;i++){
            if(list.get(i).isShowDate()){
                headDateCount++;
                removeList.add(list.get(i));
            }
        }
        for(int i=position+1;i<list.size();i++){
            if(list.get(i).isShowDate()){
                bottomDateCount++;
                removeList.add(list.get(i));
            }
        }
        mList.removeAll(removeList);
        this.position -= headDateCount;
    }
}
