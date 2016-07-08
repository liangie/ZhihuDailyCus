package com.leon.zhihudailycus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by leon on 7/2/16.
 */
public class AutoHideToolbarListView extends ListView {

    public AutoHideToolbarListView(Context context) {
        super(context);
    }

    public AutoHideToolbarListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHideToolbarListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.d("lianglei", "l:" + l + " ;t:" + t + " ;oldl:" + oldl + " ;oldt:" + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        Log.d("lianglei", "scrollX:" + scrollX + " ;scrollY:" + scrollY + " ;clampedX:" + clampedX + " ;clampedY:" + clampedY);
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
