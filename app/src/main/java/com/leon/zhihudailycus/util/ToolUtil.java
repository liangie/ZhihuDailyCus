package com.leon.zhihudailycus.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.leon.zhihudailycus.model.BitmapLruCache;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by leon on 6/7/16.
 */
public class ToolUtil {

    /**
     * @param date 传入日期格式为MM-dd-yyyy
     * @return 返回该日期前七天日期的string数组
     */
    public static String[] getStringArray(String date) {
        String[] str = new String[7];
        Date d = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        try {
            d = formatter.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (d != null) {
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(d);//把当前时间赋给日历
            for (int i = 0; i < 7; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                str[i] = formatter.format(calendar.getTime());
            }
        }
        return str;
    }

    public static void saveStringToSD(String log, String... path) {
        String filePath = "/sdcard/LLLog.txt";
        if (path != null && path.length > 0) {
            filePath = path[0];
        }
        File LogFile = new File(filePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(LogFile);
            outputStream.write(log.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static String getFilesDir(Context context) {
        if (context != null) {
            return context.getFilesDir().getAbsolutePath();
        }
        return "";
    }

    /**
     * 获得存储css文件的目录
     *
     * @param context
     * @return
     */
    public static String getCssFolder(Context context) {
        if (context != null) {
            String path = getFilesDir(context) + ConstantUtil.CSS_FOLDER;
            return "/sdcard/";
//            return path;
        }
        return null;
    }

    /**
     * 获得css文件内容并存储到本地目录
     * @param url
     * @param Queue
     * @param context
     * @return 返回目标css文件的uri值
     */
    public static String getAndStoreCss(final String url, RequestQueue Queue, final Context context) {
        int offset = url.lastIndexOf("/");
        String filename = url.substring(offset, url.length());
        final String fileString = getCssFolder(context) + File.separator + filename + ".css";
        File file = new File(fileString);
        if (!file.exists() || !file.isFile()) {
            StringRequest stringRequest = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("lianglei", "css-response:" + response);
                            if (response != null && response.length() > 0) {
                                saveStringToSD(response, fileString);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("lianglei", "css-error:" + error.toString());
                        }
                    });
            Queue.add(stringRequest);
        }
        return file.toURI().toString();
    }

    public static void networkImageViewUse(RequestQueue mQueue, NetworkImageView iv, String url) {
        ImageLoader imLoader = new ImageLoader(mQueue, new BitmapLruCache());
//        iv.setDefaultImageResId(R.drawable.about_logo);
//        iv.setErrorImageResId(R.drawable.about_logo);
        iv.setImageUrl(url, imLoader);
    }

}
