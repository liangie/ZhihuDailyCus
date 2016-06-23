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
//        Log.d("lianglei","beforePosition:"+position);
//        rebuildList(mList, position);
//        Log.d("lianglei","afterPosition:"+position);
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
        List<BaseStoryBean> removeList= new ArrayList<>();

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
        Log.d("lianglei","headDateCount:"+headDateCount);
        Log.d("lianglei","removeList:"+removeList.toString());
        mList.removeAll(removeList);
        this.position -= headDateCount;
    }
}
