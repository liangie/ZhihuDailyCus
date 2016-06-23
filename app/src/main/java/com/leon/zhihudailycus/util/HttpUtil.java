package com.leon.zhihudailycus.util;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by leon on 6/16/16.
 */
public class HttpUtil {

    public static String doOkPost(String stringURL, Map param, OkHttpClient client) {
        try {
            if (client == null) {
                throw new IOException("doOkPost-client is null");
            }

            FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
            Iterator iterator = param.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                formEncodingBuilder.add((String)entry.getKey(),(String)entry.getValue());
            }
            RequestBody body = formEncodingBuilder.build();

            Request request = new Request.Builder()
                    .url(stringURL)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("post failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String changeToJsonString(Map param) {
        JSONObject obj = new JSONObject();
        try {
            Set set = param.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                obj.put(key, param.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj.toString();
    }

    public static String doGet(String urlString, OkHttpClient client) {
        try {
            if(client==null){
                throw new IOException("doGet-client is null");
            }
            Request request = new Request.Builder().url(urlString).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /*
    _xsrf=5f9ff6db0360e12d516d73d54f96d1c3
    &password=LIANGei_621317
    &captcha_type=cn
    &remember_me=true
    &email=soul_hh%40foxmail.com

    email=soul_hh%2540foxmail.com
    &password=LIANGei_621317
    &captcha_type=cn
    &_xsrf=5f9ff6db0360e12d516d73d54f96d1c3
    &remember_me=true
    */
    
    /*
    HTTP/1.1 200 OK; 
    Cache-Control -- no-store;
    Connection -- keep-alive;
    Content-Security-Policy -- default-src *; img-src * data:; frame-src 'self' *.zhihu.com getpocket.com note.youdao.com; script-src 'self' *.zhihu.com *.google-analytics.com zhstatic.zhihu.com res.wx.qq.com 'unsafe-eval'; style-src 'self' *.zhihu.com 'unsafe-inline';
    Content-Type -- application/json;
    Date -- Thu, 16 Jun 2016 10:21:09 GMT;
    Pragma -- no-cache;
    Server -- ZWS;
    Set-Cookie -- q_c1=ff65e0f56726403481757a41901ed523|1466072470000|1466072470000; Domain=zhihu.com; expires=Sun, 16 Jun 2019 10:21:10 GMT; Path=/; _xsrf=; Domain=zhihu.com; expires=Wed, 17 Jun 2015 10:21:10 GMT; Path=/; l_cap_id="OTRlYWFkYTIxODZhNDgyYTk4YjRmNjY5YmU4Mjk1YmE=|1466072470|6938a42a48e52982abf1f6a3fd5397cc368eeccd"; Domain=zhihu.com; expires=Sat, 16 Jul 2016 10:21:10 GMT; Path=/; cap_id="NzQzNmQ2Y2U2MzY5NGM2NmFiMzUwYWQ1Zjk1ZTRkOTU=|1466072470|ba58c6e6b737ae9b018eb6a47e1a2034fc3ca02b"; Domain=zhihu.com; expires=Sat, 16 Jul 2016 10:21:10 GMT; Path=/; n_c=1; Domain=zhihu.com; Path=/;
    Vary -- Accept-Encoding;
    X-Android-Received-Millis -- 1466080689472;
    X-Android-Response-Source -- NETWORK 200;
    X-Android-Sent-Millis -- 1466080689350;
    X-Frame-Options -- DENY;
    X-Req-ID -- 60E88457627D90;
    X-Za-Response-Id -- 679422237f7541e7b6d1d019969882e9;
    content::email=soul_hh%40foxmail.com&password=LIANGei_621317&captcha_type=cn&_xsrf=5f9ff6db0360e12d516d73d54f96d1c3&remember_me=true
    */
}
