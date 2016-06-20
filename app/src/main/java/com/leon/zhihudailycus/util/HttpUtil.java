package com.leon.zhihudailycus.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by leon on 6/16/16.
 */
public class HttpUtil {

    /*
    _xsrf=5f9ff6db0360e12d516d73d54f96d1c3
    &password=LIANGei_621317
    &captcha_type=cn
    &remember_me=true
    &email=soul_hh%40foxmail.com
    * */
    public static String doPost(String stringURl, Map param) throws IOException, JSONException {

        // 请求服务器
        URL postUrl = new URL(stringURl);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setReadTimeout(10 * 1000);
        connection.setConnectTimeout(5 * 1000);
        connection.setInstanceFollowRedirects(true);

        connection.connect();

        if (param != null && param.size() > 0) {
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());
            StringBuffer content = new StringBuffer("");
            Iterator iterator = param.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                content.append((String) entry.getKey()).append("=").append(URLEncoder.encode((String) entry.getValue(), "utf-8")).append("&");
//                content.append("&").append(KEY_VALIDATION).append("=").append(encrypMd5.toLowerCase());
            }
            /****************/
//            connection.getHeaderFields();
//            Iterator iterator1 = connection.getHeaderFields().entrySet().iterator();
//            while (iterator1.hasNext()) {
//                Map.Entry entry = (Map.Entry) iterator1.next();
//                StringBuffer value = new StringBuffer();
//                for (String s : (List<String>) entry.getValue()) {
//                    value.append(s).append("; ");
//                }
//
//            }
            /****************/
            String result = content.substring(0, content.length() - 1);
            content.replace(0, content.length(), result);
            out.writeBytes(content.toString());
            out.flush();
            out.close();
        }
//        System.out.println("************************************************");
//        System.out.println("input = data=" + sb.toString() + "&" + KEY_VALIDATION + "=" + encrypMd5.toLowerCase());
//        System.out.println("************************************************");

        System.out.print("lianglei,Set-Cookie:"+connection.getHeaderField("Set-Cookie"));;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line = "";
        StringBuffer response = new StringBuffer("");
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        System.out.println("************************************************");
        System.out.println("response = " + response);
        System.out.println("************************************************");
//        return new JSONObject(response.toString());
        return connection.getHeaderField("Set-Cookie");
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


    public static String doGet(String stringURl) throws Exception {
        URL url = new URL(stringURl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(15 * 1000);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);

        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line = "";
        StringBuffer response = new StringBuffer("");
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
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
