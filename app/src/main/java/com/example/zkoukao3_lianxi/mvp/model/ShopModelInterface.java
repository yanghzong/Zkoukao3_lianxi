package com.example.zkoukao3_lianxi.mvp.model;

import com.example.zkoukao3_lianxi.MyCallBack;
import com.example.zkoukao3_lianxi.utils.ICallBack;

import java.lang.reflect.Type;

public interface ShopModelInterface {
    void  getShopcarIM(String url, MyCallBack myCallBack, Type type);
    void getShowProductModel(int commodityId, final ICallBack callBack, final Type type);
}
