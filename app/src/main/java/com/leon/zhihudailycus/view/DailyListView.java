package com.leon.zhihudailycus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by leon on 6/7/16.
 */
public class DailyListView extends ListView {
    public DailyListView(Context context) {
        super(context);
    }

    public DailyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 设置不滚动
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
