package com.leon.zhihudailycus.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.felipecsl.gifimageview.library.GifImageView;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.model.StringRequestWithCookie;
import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.HttpUtil;
import com.leon.zhihudailycus.util.ToolUtil;
import com.leon.zhihudailycus.view.BaseActivity;

import java.util.HashMap;

/**
 * Created by leon on 6/14/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetCaptcha, btnLogin;
    TextView captchaResult, loginResult;
    EditText captchaEdit, emailEdit, passwordEdit, captchaTypeEdit, sxrfEdit, captchaAdd;
    CheckBox rememberCB;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(this);
        initView();
    }

    private void initView() {
        btnGetCaptcha = (Button) findViewById(R.id.bt_captcha);
        btnLogin = (Button) findViewById(R.id.bt_login);
        btnGetCaptcha.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        captchaResult = (TextView) findViewById(R.id.tv_captcha_result);
        loginResult = (TextView) findViewById(R.id.tv_login_result);

        captchaAdd = (EditText) findViewById(R.id.ev_captcha);
        captchaEdit = (EditText) findViewById(R.id.et_login_captcha);
        captchaTypeEdit = (EditText) findViewById(R.id.et_login_captcha_type);
        emailEdit = (EditText) findViewById(R.id.et_login_email);
        passwordEdit = (EditText) findViewById(R.id.et_login_password);
        sxrfEdit = (EditText) findViewById(R.id.et_login_xsrf);

        rememberCB = (CheckBox) findViewById(R.id.cb_remember_me);

        findViewById(R.id.bt_get_followers).setOnClickListener(this);

        /*************************/
        captchaAdd.setText(getString(R.string.captcha_add, System.currentTimeMillis()));
        ((WebView)findViewById(R.id.wv_captcha)).loadUrl(captchaAdd.getText().toString());
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.image_test);
        ToolUtil.useNetworkImageView(imageView, captchaAdd.getText().toString(), mQueue);
        final ImageView image2 = (ImageView) findViewById(R.id.image_2);
        ImageRequest imageRequest = new ImageRequest(captchaAdd.getText().toString(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                image2.setImageBitmap(response);
            }
        }, 100, 100, Bitmap.Config.ARGB_4444, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(imageRequest);
        /***************/

        GifImageView gifImageView = (GifImageView) findViewById(R.id.image_gif);

        /*StringRequest stringRequest = new StringRequest(captchaAdd.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
//                            String imageDataString = com.leon.zhihudailycus.Base64.encodeBase64URLSafeString(response.getBytes());
                            byte[] ig = response.getBytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(ig, 0, ig.length);

                            imageView.setImageBitmap(bitmap);
                            File file = new File("/sdcard/captcha.gif");
                            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
                            fileOutputStream.write(response.getBytes());
                            fileOutputStream.flush();
                            fileOutputStream.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        mQueue.add(stringRequest);*/

    }

    private String cookie = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_captcha:

                break;
            case R.id.bt_login:
                /*
                 _xsrf=5f9ff6db0360e12d516d73d54f96d1c3
                 &password=LIANGei_621317
                 &captcha_type=cn
                 &remember_me=true
                 &email=soul_hh%40foxmail.com
                **/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("_xsrf", "5f9ff6db0360e12d516d73d54f96d1c3");
                        params.put("password", "LIANGei_621317");
                        params.put("captcha_type", "cn");
                        params.put("remember_me", "true");
                        params.put("email", "soul_hh@foxmail.com");
                        try {
                            cookie = HttpUtil.doPost(APIUtil.USER_LOGIN, params);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.bt_get_followers:
                StringRequestWithCookie stringRequest = new StringRequestWithCookie("http://www.zhihu.com/people/liang-lei-17/followers",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.print("lianglei-"+response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                stringRequest.setCookie(cookie);
                mQueue.add(stringRequest);
                break;
        }
    }
}