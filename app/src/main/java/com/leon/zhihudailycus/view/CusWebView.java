package com.leon.zhihudailycus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by leon on 7/20/16.
 */
public class CusWebView extends WebView {
    public CusWebView(Context context) {
        super(context);
    }

    public CusWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

}
