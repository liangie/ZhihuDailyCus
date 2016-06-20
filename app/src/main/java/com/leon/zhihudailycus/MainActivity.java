package com.leon.zhihudailycus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leon.zhihudailycus.activity.LoginActivity;
import com.leon.zhihudailycus.activity.StoryDetailActivity;
import com.leon.zhihudailycus.model.adapter.StoryListAdapter;
import com.leon.zhihudailycus.model.bean.BaseStoryBean;
import com.leon.zhihudailycus.model.bean.DailyStoryBean;
import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.ConstantUtil;
import com.leon.zhihudailycus.util.JsonUtil;
import com.leon.zhihudailycus.util.SharedPreferenceUtil;
import com.leon.zhihudailycus.util.ToolUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        android.os.Handler.Callback,
        AdapterView.OnItemClickListener,
        View.OnClickListener {

    private final int BUILD_LATEST_STORIES = 0X10;
    private final int BUILD_EARLY_STORY_LIST = 0X11;
    private final int BUILD_SHARED_STORY_LIST = 0X12;

    private String Today;
    private String EarliestDate;

    private ListView mListView;
    private RequestQueue mQueue;
    private Handler mHandler = new Handler(this);
    //    private SingleMainAdapter mAdapter;
    private StoryListAdapter mAdapter;
    private List<BaseStoryBean> mList = new ArrayList<>();
    private View mFooter;
    private View mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /***************/
        mFooter = LayoutInflater.from(this).inflate(R.layout.main_list_footer, null);
        mQueue = Volley.newRequestQueue(this);

        getNewestStorylist();
//        mQueue.start();

        findViewById(R.id.load_more_ly).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.main_listview);
        mList = new ArrayList<>();
//        mAdapter = new SingleMainAdapter(mList, this);
        mAdapter = new StoryListAdapter(mList, this, mQueue);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean canLoadMore = false;
            private boolean refreshResent = true;

            /**
             * 0:IDLE
             * 1:scroll
             * 2:fling
             * @param view
             * @param scrollState
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0 && canLoadMore && mList.size() != 0) {
                    if (mListView.getFooterViewsCount() == 0) {
                        mListView.addFooterView(mFooter);
                    }
                    getEarlyStoryList(EarliestDate);
                } else if (scrollState == 0 && refreshResent) {
//                    if (mListView.getHeaderViewsCount() == 0) {
//                        mListView.addHeaderView(mHeader);
//                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    canLoadMore = true;
                } else {
                    canLoadMore = false;
                }
                if (firstVisibleItem == 0) {
                    refreshResent = true;
                } else {
                    refreshResent = false;
                }
            }
        });
        /************** check ****************/
        checkFolderExists();
        /************** TEST ****************/


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case BUILD_LATEST_STORIES:
                    JSONObject jsonOb = (JSONObject) msg.obj;
                    if (jsonOb != null) {
                        DailyStoryBean bean = JsonUtil.buildLastestStories(jsonOb);
                        Today = bean.getDate();
                        EarliestDate = bean.getDate();
                        List<BaseStoryBean> list = bean.getCommonStories();
                        mList.add(new BaseStoryBean(true, bean.getDate()));
                        mList.addAll(list);
//                        mList.add(bean);
                        mAdapter.notifyDataSetChanged();
                    } else {
                    }
                    break;
                case BUILD_EARLY_STORY_LIST:
                    JSONObject newData = (JSONObject) msg.obj;
                    if (newData != null) {
                        DailyStoryBean bean = JsonUtil.buildEarlyStories(newData);
                        List<BaseStoryBean> list = bean.getCommonStories();
                        mList.add(new BaseStoryBean(true, bean.getDate()));
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        EarliestDate = ToolUtil.getYestodayString(EarliestDate);
                    } else {
                    }
                    mListView.removeFooterView(mFooter);
                    break;
                case BUILD_SHARED_STORY_LIST:
                    String jsonString = (String)msg.obj;
                    if(jsonString!=null && jsonString.length()>0){
                        List<BaseStoryBean> list = JsonUtil.buildSharedStoryListWithJsonString(jsonString);
                        if(list!=null && list.size()>0) {
                            mList.clear();
                            mList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    }else{
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, StoryDetailActivity.class);
        intent.putExtra("commonlist", (Serializable) mList);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_more_ly:
                getEarlyStoryList(EarliestDate);
                break;
        }
    }

    /**
     * 获得当天的最新数据
     */
    private void getNewestStorylist() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(APIUtil.LATEST_STORY_LIST, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Message msg = new Message();
                        msg.what = BUILD_LATEST_STORIES;
                        msg.obj = response;
                        mHandler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String storyListString = SharedPreferenceUtil.getLocalDataShared(MainActivity.this).getString(ConstantUtil.LOCAL_DATA_STORY_LIST, null);
                        if (storyListString != null && storyListString.length() > 0) {
                            Message msg = new Message();
                            msg.what = BUILD_SHARED_STORY_LIST;
                            msg.obj = storyListString;
                            mHandler.sendMessage(msg);

                        }
                    }
                });
        mQueue.add(jsonObjectRequest);
    }

    private boolean loadingMore = false;

    /**
     * 获得指定日期的story list
     * http://news.at.zhihu.com/api/4/news/before/20131119
     *
     * @param dateString
     * @return
     */
    private synchronized void getEarlyStoryList(final String dateString) {
        if (loadingMore)
            return;
        loadingMore = true;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(APIUtil.EARLY_STORY_LIST + dateString, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Message msg = new Message();
                        msg.what = BUILD_EARLY_STORY_LIST;
                        msg.obj = response;
                        mHandler.sendMessage(msg);
                        loadingMore = false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingMore = false;
                    }
                });
        mQueue.add(jsonObjectRequest);
    }

    /**
     * 检查一些文件目录是否存在
     * 不存在则新建
     */
    private void checkFolderExists() {
        //检查存储css的目录
        checkFolder(ToolUtil.getCssFolder(this));
    }

    private void checkFolder(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
    }

//
//    _xsrf:5f9ff6db0360e12d516d73d54f96d1c3
//    password:LIANGei_621317
//    captcha_type:cn
//    remember_me:true
//    email:soul_hh@foxmail.com
}
