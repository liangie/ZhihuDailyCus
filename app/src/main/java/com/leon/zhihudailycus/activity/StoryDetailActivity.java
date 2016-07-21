package com.leon.zhihudailycus.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

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
//    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        mList = (List<BaseStoryBean>) getIntent().getSerializableExtra("commonlist");
        position = getIntent().getIntExtra("position", 0);
        try {
            Log.d("lianglei", position + "; " + mList.get(position).getTitle());
        } catch (Exception e) {
            Log.d("lianglei", "position:" + position);
            e.printStackTrace();
        }
        mQueue = Volley.newRequestQueue(this);
        init();
    }


    private int mPosition = -1;
    private boolean changeDirection = false;
    //手指向左划动
    private final int LEFT_DIREC = 10;
    //手指向右滑动
    private final int RIGHT_DIREC = 11;
    private int pagerDirection = LEFT_DIREC;
    //连续改变滑动方向的计数
    private int successiveChangeDirecCount = 0;

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_detail);
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mAdapter = new StoryDetailAdapter(this, mList, mQueue);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //这里的目的是更具position去获取对应的pager对象
                if (position == -1 || position > mPosition) {
                    //手指左划，position增大
                    changeDirection = (pagerDirection != LEFT_DIREC) ? true : false;
                    if (changeDirection) {
                        successiveChangeDirecCount++;
                    }
                    pagerDirection = LEFT_DIREC;
                } else {
                    //手指右滑，position减小
                    changeDirection = (pagerDirection != RIGHT_DIREC) ? true : false;
                    if (changeDirection) {
                        successiveChangeDirecCount++;
                    }
                    pagerDirection = RIGHT_DIREC;
                }
                ViewGroup currPager = null;
                if (changeDirection) {
                    if (successiveChangeDirecCount > 0) {
                        if (successiveChangeDirecCount % 2 == 1) {
                            currPager = (ViewGroup) mViewPager.getChildAt(0);
                        } else {
                            currPager = (ViewGroup) mViewPager.getChildAt(1);
                        }
                    }
                } else {
                    currPager = (ViewGroup) mViewPager.getChildAt(mViewPager.getChildCount() - 1);
                    successiveChangeDirecCount = 0;
                }

                if(currPager!=null){

                    Log.d("lianglei",((TextView)currPager.findViewById(R.id.story_title)).getText().toString());
                }
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
