package com.leon.zhihudailycus.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.leon.zhihudailycus.util.APIUtil;
import com.leon.zhihudailycus.util.HttpUtil;
import com.leon.zhihudailycus.util.ToolUtil;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by leon on 8/16/16.
 */
public class RemoteService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void checkStartImage(final ImageView imageView) {

        String imageUrl = APIUtil.START_IMAGE_720;
        Observable.just(imageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                            downloadAndSaveImage(s);
                        return BitmapFactory.decodeFile("/sdcard/startImage.jpg");
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if (bitmap != null)
                            imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                });
    }

    private static void downloadAndSaveImage(String imageUrl) {
        try {
            String jsonStr = HttpUtil.doGet(imageUrl, new OkHttpClient());
            if (jsonStr == null)
                return;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (jsonObject == null)
                return;
            byte[] imageBytes = ToolUtil.getFileByte(jsonObject.getString("img"));
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            if (image != null) {
                ToolUtil.saveBitmapAsFile(image, "/sdcard/startImage.jpg");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
