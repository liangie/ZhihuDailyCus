package com.leon.zhihudailycus.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.HttpUtil;
import com.leon.zhihudailycus.util.PersistentCookieStore;
import com.leon.zhihudailycus.util.ToolUtil;
import com.leon.zhihudailycus.view.BaseActivity;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;

/**
 * Created by leon on 6/14/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    Button btnLogin;
    EditText emailEdit, passwordEdit;
    CheckBox rememberCB;
    private RequestQueue mQueue;
    private Handler mHandler;
    private OkHttpClient client = null;
    private String _xsrf = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(this);
        mHandler = new Handler(this);
        client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(new PersistentCookieStore(), CookiePolicy.ACCEPT_ALL));
        initView();
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.bt_login);
        btnLogin.setOnClickListener(this);
        emailEdit = (EditText) findViewById(R.id.et_login_email);
        passwordEdit = (EditText) findViewById(R.id.et_login_password);
        rememberCB = (CheckBox) findViewById(R.id.cb_remember_me);

        findViewById(R.id.bt_get_followers).setOnClickListener(this);

        /*************************/
        String captChaAddStr = getString(R.string.captcha_add, System.currentTimeMillis());
        ((WebView) findViewById(R.id.wv_captcha)).loadUrl(captChaAddStr);
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.image_test);
        ToolUtil.useNetworkImageView(imageView, captChaAddStr, mQueue);
        /***************/
    }

    private String cookie = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                /*
                 _xsrf=5f9ff6db0360e12d516d73d54f96d1c3
                 &password=LIANGei_621317
                 &captcha_type=cn
                 &remember_me=true
                 &email=soul_hh%40foxmail.com
                **/

                getXSRF();
                break;
            case R.id.bt_get_followers:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response = HttpUtil.doGet("http://www.zhihu.com/people/liang-lei-17/followers", client);
                        if (response != null) {
                            Log.d("lianglei", "getFollowers:" + response);

                            Document doc = Jsoup.parse(response);
                            Elements ele = doc.select("a.zm-item-link-avatar");
                            for (Element el : ele) {
                                String name = el.attr("title");
                                Log.d("lianglei", "follower:" + name);
                            }

                            File file = new File("/sdcard/myzhihu.html");
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                fos.write(response.getBytes());
                                fos.flush();
                                fos.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("lianglei", "getFollowers failed!");
                        }
                    }
                }).start();
                break;
        }
    }

    public void getXSRF() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = HttpUtil.doGet("https://www.zhihu.com", client);
                    if (response != null) {
                        Document doc = Jsoup.parse(response);
                        Element element = doc.select("input[type=hidden]").first();
                        if (element != null) {
                            _xsrf = element.attr("value");
                            mHandler.sendEmptyMessage(GOT_XSRF);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private final int TOAST_MSG = 0X00;
    private final int GOT_XSRF = 0x10;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case TOAST_MSG:
                String str = (String) msg.obj;
                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                break;
            case GOT_XSRF:
                login();
                break;
        }
        return false;
    }

    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("_xsrf", _xsrf);
                params.put("password", "LIANGei_621317");
                params.put("captcha_type", "cn");
                params.put("remember_me", "true");
                params.put("email", "soul_hh@foxmail.com");
                Message message = new Message();
                message.what = TOAST_MSG;
                try {
                    String response = HttpUtil.doOkPost(APIUtil.USER_LOGIN, params, client);
                    if (response != null) {
                        JSONObject jsonOB = new JSONObject(response);
                        String msg = jsonOB.getString("msg");
                        int r = jsonOB.getInt("r");
                        message.obj = msg;
                    } else {
                        message.obj = "登录失败-_-";
                    }
                } catch (Exception e) {
                    message.obj = "登录出错-_-";
                    e.printStackTrace();
                }
                mHandler.sendMessage(message);
            }
        }).start();
    }
}
