package com.example.zkoukao3_lianxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.zkoukao3_lianxi.R;
import com.example.zkoukao3_lianxi.adapter.XiangqingAdapter;
import com.example.zkoukao3_lianxi.entity.XiangqingEntity;
import com.example.zkoukao3_lianxi.mvp.presenter.ShopPresenter;
import com.example.zkoukao3_lianxi.mvp.view.ShopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class XiangqingActivity extends AppCompatActivity implements ShopView {
    private static final String TAG = "ShowProductActivity";

    @BindView(R.id.product_show)
    RecyclerView productShow;
    @BindView(R.id.wb_show)
    WebView wbShow;


    private XiangqingEntity.ResultBean list;
    private XiangqingAdapter adapter;
    private Unbinder bind;
    private List<String> titles;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ShopPresenter shopPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        bind = ButterKnife.bind(this, this);

        Intent intent = getIntent();
        String s = intent.getStringExtra("cid");
        Log.d(TAG, "onCreate++++++666: " + s);
        int cid = Integer.valueOf(s);
        Log.d(TAG, "onCreate++++++: " + cid);
        initPresenter(cid);
        titles = new ArrayList<>();
        titles.add("商品");
        titles.add("详情");
        titles.add("评论");




    }


    private void initPresenter(int cid) {
        shopPresenter = new ShopPresenter();
        shopPresenter.attach(this);
        shopPresenter.getShowPrpduct(cid);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopPresenter.detach();
        bind.unbind();

    }

    @Override
    public void shopSuccess(Object obj) {
        XiangqingEntity data = (XiangqingEntity) obj;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productShow.setLayoutManager(layoutManager);
        adapter = new XiangqingAdapter (XiangqingActivity.this, data.getResult());
        productShow.setAdapter(adapter);
        WebSettings settings = wbShow.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        String js = "<script type=\"text/javascript\">" +
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";
        wbShow.loadData(data.getResult().getDetails() + js, "text/html; charset=UTF-8", null);
    }

    @Override
    public void shopFailed(String str) {
        Toast.makeText(this, "" , Toast.LENGTH_SHORT).show();
    }
}
