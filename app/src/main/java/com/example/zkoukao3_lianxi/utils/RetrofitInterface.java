package com.example.zkoukao3_lianxi.utils;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitInterface {

    @GET("order/verify/v1/findShoppingCart?sessionId=155149843400331&userId=31")
    Observable<ResponseBody> getShopcar;
}
