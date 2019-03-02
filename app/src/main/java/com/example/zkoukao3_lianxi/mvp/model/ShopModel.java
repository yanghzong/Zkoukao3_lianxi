package com.example.zkoukao3_lianxi.mvp.model;

import com.example.zkoukao3_lianxi.MyCallBack;
import com.example.zkoukao3_lianxi.utils.ApiUrl;
import com.example.zkoukao3_lianxi.utils.ICallBack;
import com.example.zkoukao3_lianxi.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ShopModel implements ShString url= UtilsUrl.ShowProductUrl+"?commodityId="+commodityId;
        OkHttpUtils.enqueuGet(url, new Callback() {
@Override
public void onFailure(Call call, final IOException e) {
        handler.post(new Runnable() {
@Override
public void run() {
        callBack.failed(e);
        }
        });

        }

@Override
public void onResponse(Call call, Response response) throws IOException {
        String result = response.body().string();
        Gson gson=new Gson();
final Object o = gson.fromJson(result, type);
        handler.post(new Runnable() {
@Override
public void run() {
        callBack.successful(o);
        }
        });
        }
        },type);pModelInterface{



    @Override
    public void getShopcarIM(String url, final MyCallBack myCallBack, final Type type) {
        OkHttpUtils.getInstance().getshopcar(url,  new ICallBack() {
            @Override
            public void onSuccess(String result) {
                Object o = new Gson().fromJson(result, type);
                if(myCallBack!=null){
                    myCallBack.onSuccess(o);
                }
            }

            @Override
            public void onFailed(String str) {
                   if (myCallBack!=null){
                       myCallBack.onFailed(str);
                   }
            }
        });
    }

    @Override
    public void getShowProductModel(int commodityId, ICallBack callBack, Type type) {

    }
}
