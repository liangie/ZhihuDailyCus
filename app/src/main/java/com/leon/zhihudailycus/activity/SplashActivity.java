package com.leon.zhihudailycus.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.leon.zhihudailycus.MainActivity;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.util.ConstantUtil;
import com.leon.zhihudailycus.util.SharedPreferenceUtil;
import com.leon.zhihudailycus.view.BaseActivity;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by leon on 6/6/16.
 */
public class SplashActivity extends BaseActivity {

    public static final String START_IMAGE_FOLDER = "/sdcard/startImages";
    private RequestQueue mQueue;
    private ImageView mStartImage;
    private TextView mImageInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        mQueue = Volley.newRequestQueue(this);

        initView();

        String imageUrl = SharedPreferenceUtil.getLocalDataShared(this).getString(ConstantUtil.START_IMAGE_URL, null);
        if (imageUrl != null) {
            final File startImage = new File(START_IMAGE_FOLDER + File.separator + imageUrl.substring(imageUrl.lastIndexOf("/"), imageUrl.length()));
            Log.d("lianglei", "startImage::" + startImage);
            Observable.just(startImage)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<File, Bitmap>() {
                        @Override
                        public Bitmap call(File file) {
                            if (file != null) {
                                return BitmapFactory.decodeFile(file.getAbsolutePath());
                            } else {
//                                Log.d("lianglei", startImage.getAbsolutePath() + " - file not exists!--");
                            }
                            return null;
                        }
                    })
                    .subscribe(new Action1<Bitmap>() {
                        @Override
                        public void call(Bitmap bitmap) {
                            if (bitmap != null) {
                                mStartImage.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            } else {
//                                Log.d("lianglei", "bitmap is null!--");
                            }
                        }
                    });

        } else {
//            Log.d("lianglei", "imageUrl shared is null");
        }

        new Simple().start();
//        RemoteService.checkStartImage(mStartImage);

        //启动远程服务
//        Intent intent  = new Intent(this, RemoteService.class);
//        startService(intent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2800);
    }

    private void initView() {
        mStartImage = (ImageView) findViewById(R.id.start_image_iv);
        mImageInfo = (TextView) findViewById(R.id.image_info_tv);
    }

    public static final String url = "https://api.github.com/repos/square/retrofit/contributors";
    public interface Github{

        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors (@Path("owner") String owner, @Path("repo") String repo);
    }


    private class Simple extends Thread{
        @Override
        public void run() {
            super.run();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Github github = retrofit.create(Github.class);
            Call<List<Contributor>> call = github.contributors("square","retrofit");
            try{
                List<Contributor> list = call.execute().body();
                for(Contributor c:list){
                    Log.d("lianglei","****   "+c.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class Contributor{
        private String login;
        private int id;
        private int contributions;

        public Contributor(String login, int id, int contributions) {
            this.login = login;
            this.id = id;
            this.contributions = contributions;
        }

        @Override
        public String toString() {
            return "Contributor{" +
                    "login='" + login + '\'' +
                    ", id=" + id +
                    ", contributions=" + contributions +
                    '}';
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getContributions() {
            return contributions;
        }

        public void setContributions(int contributions) {
            this.contributions = contributions;
        }
    }

}
