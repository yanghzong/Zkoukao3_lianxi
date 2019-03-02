package com.example.zkoukao3_lianxi.utils;

import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OkHttpUtils {

    private static  OkHttpUtils  instance;
    private OkHttpClient client;
    private Retrofit.Builder builder;
    private Handler handler=new Handler();

    private  OkHttpUtils(){
        client=new OkHttpClient.Builder()
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();

        builder=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
    }
     //双重锁
    public static  OkHttpUtils getInstance(){
        if (instance==null){
            synchronized (OkHttpUtils.class){
                if (instance==null){
                    instance=new OkHttpUtils();
                }
            }
        }
        return instance;
    }
    //创建Retrofit对象
    private RetrofitInterface  getRetrofitIViewObject(String url) {
        Retrofit retrofit = builder.baseUrl(url)
                .build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        return retrofitInterface;
    }

    public OkHttpUtils getshopcar(String url, final ICallBack callBack) {
        //得到retrofit对象
        RetrofitInterface retrofitIViewObject = getRetrofitIViewObject(url);
        retrofitIViewObject.getShopcar
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
        return instance;


    }
    public Observer getObserver(final ICallBack callBack) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if(callBack!=null){
                    try {
                        String result = responseBody.string();
                        if(!result.equals("")){
                            callBack.onSuccess(result);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if(callBack!=null){
                            callBack.onFailed(e.getMessage());
                        }
                    }
                }
            }
        };
        return observer;

    }
}
