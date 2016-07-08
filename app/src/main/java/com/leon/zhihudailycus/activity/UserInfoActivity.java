package com.leon.zhihudailycus.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.util.ConstantUtil;
import com.leon.zhihudailycus.util.SharedPreferenceUtil;
import com.leon.zhihudailycus.util.ToolUtil;
import com.leon.zhihudailycus.view.BaseActivity;

/**
 * Created by leon on 6/24/16.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private RequestQueue mQueue;
    private TextView tvLogout;
    private NetworkImageView ivUserAvatar;
    private EditText etUserName;
    private String userName;
    private String userId;
    private String avatarSrcS;
    private String avatarSrcL;
    private Toolbar toolbar;


    private TextView tvTest;
    private String testString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mQueue = Volley.newRequestQueue(this);
        testString = getUserInfoFromShared();
        init();
    }

    private void init() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvLogout = (TextView) findViewById(R.id.tv_logout);
        ivUserAvatar = (NetworkImageView) findViewById(R.id.iv_avatar);
        etUserName = (EditText) findViewById(R.id.user_name);
        tvTest = (TextView) findViewById(R.id.test_show);
        tvTest.setText(testString);

        toolbar.setTitle("title");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.checkbox_off_background);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*ImageRequest imageRequest = new ImageRequest(avatarSrcL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ivUserAvatar.
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_4444,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lianglei","userImage set error");
                    }
                }

        );*/
        ToolUtil.useNetworkImageView(ivUserAvatar, avatarSrcL, mQueue);
        etUserName.setText(userName);
        etUserName.setEnabled(false);
        tvLogout.setOnClickListener(this);
    }

    private String getUserInfoFromShared() {
        userName = SharedPreferenceUtil.getLocalDataShared(this).getString(ConstantUtil.USER_NAME, "n/a");
        userId = SharedPreferenceUtil.getLocalDataShared(this).getString(ConstantUtil.USER_ID, "n/a");
        avatarSrcS = SharedPreferenceUtil.getLocalDataShared(this).getString(ConstantUtil.USER_AVATAR_SRC_S, "n/a");
        avatarSrcL = SharedPreferenceUtil.getLocalDataShared(this).getString(ConstantUtil.USER_AVATAR_SRC_L, "n/a");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name:" + userName + "\n");
        stringBuilder.append("id:" + userId + "\n");
        stringBuilder.append("avatarSrc:" + avatarSrcL);
        Log.d("lianglei",avatarSrcS);
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:

                break;
        }
    }
}
