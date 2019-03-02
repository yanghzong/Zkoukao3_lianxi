package com.example.zkoukao3_lianxi.mvp.presenter;

import com.example.zkoukao3_lianxi.MyCallBack;
import com.example.zkoukao3_lianxi.entity.ShopCarEntity;
import com.example.zkoukao3_lianxi.entity.XiangqingEntity;
import com.example.zkoukao3_lianxi.mvp.model.ShopModel;
import com.example.zkoukao3_lianxi.mvp.view.ShopView;
import com.example.zkoukao3_lianxi.utils.ApiUrl;
import com.example.zkoukao3_lianxi.utils.ICallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ShopPresenter implements ShopPresenterInterface{
    private ShopModel shopModel;
    private ShopView shopView;

    public  void attach(ShopView shopView){
        shopModel=new ShopModel();
        this.shopView=shopView;
    }
    public  void  detach(){
        if (shopModel!=null){
            shopModel=null;
        }
        if (shopView!=null){
            shopView=null;
        }
    }

    @Override
    public void getShopcarIP() {
        Type type=new TypeToken<ShopCarEntity>(){}.getType();
        shopModel.getShopcarIM(ApiUrl.BaseUrl, new MyCallBack() {
            @Override
            public void onSuccess(Object obj) {
               ShopCarEntity shopCarEntity= (ShopCarEntity) obj;
                if (shopCarEntity!=null){
                    shopView.shopSuccess(shopCarEntity);
                }
            }

            @Override
            public void onFailed(String str) {
                shopView.shopFailed(str);
            }


        },type);
    }

    @Override
    public void getShowPrpduct(int commodityId) {
        Type type=new TypeToken<XiangqingEntity>(){}.getType();
        shopModel.getShowProductModel(commodityId, new ICallBack() {
            @Override
            public void successful(Object o) {
                XiangqingEntity data= (XiangqingEntity) o;
                if (data!=null & "0000".equals(data.getStatus())){
                    shopView.shopSuccess(data);
                }
            }

            @Override
            public void failed(Exception e) {
                
            }
        },type);
    }
}
