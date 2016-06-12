package com.leon.zhihudailycus;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leon.zhihudailycus.model.adapter.MainListAdapter;
import com.leon.zhihudailycus.model.bean.DailyStoryBean;
import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.JsonUtil;
import com.leon.zhihudailycus.util.ToolUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        android.os.Handler.Callback,
        AdapterView.OnItemClickListener {

    private final int BUILD_LATEST_STORIES = 0X10;

    private ListView mListView;
    private RequestQueue mQueue;
    private Handler mHandler = new Handler(this);
    private MainListAdapter mAdapter;
    private List<DailyStoryBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /***************/
        mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(APIUtil.LATEST_STORIES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("lianglei", "response:" + response);
                        Message msg = new Message();
                        msg.what = BUILD_LATEST_STORIES;
                        msg.obj = response;
                        mHandler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lianglei", "error:" + error);
                    }
                });
        mQueue.add(jsonObjectRequest);
//        mQueue.start();

        mListView = (ListView) findViewById(R.id.main_listview);
        mList = new ArrayList<>();
        mAdapter = new MainListAdapter(mList, this, mQueue);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        /************** check ****************/
        checkFolderExists();
        /************** TEST ****************/
        Log.d("lianglei", "filePath:" + ToolUtil.getFilesDir(this));


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
                        Log.d("lianglei", "getBean:\n" + bean.toString());
                        mList.add(bean);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("lianglei", "build LastestStories failed");
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
}
