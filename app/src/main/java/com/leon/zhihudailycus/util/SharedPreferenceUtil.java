package com.leon.zhihudailycus.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leon on 6/13/16.
 */
public class SharedPreferenceUtil {

    public static synchronized SharedPreferences getLocalDataShared(Context context) {
        return context.getSharedPreferences(ConstantUtil.LOCAL_DATA_SHARED_NAME, Context.MODE_PRIVATE);
    }
}
