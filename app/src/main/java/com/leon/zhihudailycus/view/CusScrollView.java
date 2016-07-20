package com.leon.zhihudailycus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by leon on 6/27/16.
 */
public class CusScrollView extends ScrollView {

    private scrollChangedListener listener;

    public CusScrollView(Context context) {
        super(context);
    }

    public CusScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
//            Log.d("lianglei", "---" + t);
            listener.setAttrs(l, t, oldl, oldt);
        }
    }

    public void setListener(scrollChangedListener listener) {
        this.listener = listener;
    }

    public interface scrollChangedListener {
        View view = null;

        void setView(View v);

        void setAttrs(int L, int T, int oldL, int oldT);
    }

    public int getVerticalScrollOffset(){
        return computeVerticalScrollOffset();
    }
}
