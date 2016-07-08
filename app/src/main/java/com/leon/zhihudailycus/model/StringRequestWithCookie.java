package com.leon.zhihudailycus.model;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leon on 6/16/16.
 */
public class StringRequestWithCookie extends StringRequest {

    private Map<String,String> headers = new HashMap<>();

    public StringRequestWithCookie(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringRequestWithCookie(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public void setCookie(String cookie){
        headers.put("Cookie",cookie);

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
//        return super.getHeaders();
        return headers;
    }
}
