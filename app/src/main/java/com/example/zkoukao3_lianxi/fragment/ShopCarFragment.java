package com.example.zkoukao3_lianxi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.zkoukao3_lianxi.R;
import com.example.zkoukao3_lianxi.XiangqingActivity;
import com.example.zkoukao3_lianxi.adapter.ShopCarAdapter;
import com.example.zkoukao3_lianxi.entity.ShopCarEntity;
import com.example.zkoukao3_lianxi.mvp.presenter.ShopPresenter;
import com.example.zkoukao3_lianxi.mvp.view.ShopView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopCarFragment extends Fragment  implements ShopView {
    @BindView(R.id.rv_shopcar)
    RecyclerView rvShopcar;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_price_all)
    TextView tvPriceAll;
    @BindView(R.id.tv_tijiao)
    TextView tvTijiao;
    Unbinder unbinder;
    private List<ShopCarEntity.ResultBean> shopcarlist=new ArrayList<>();
    private ShopCarAdapter shopCarAdapter;
    private ShopPresenter shopPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopcar, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        initPresenter();
    }

    private void initPresenter() {
        shopPresenter = new ShopPresenter();
        shopPresenter.attach(this);
        shopPresenter.getShopcarIP();
    }

    private void initAdapter() {
        shopCarAdapter = new ShopCarAdapter(getActivity(),shopcarlist);
        rvShopcar.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rvShopcar.setAdapter(shopCarAdapter);
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = cbAll.isChecked();
                for (ShopCarEntity.ResultBean resultBean : shopcarlist) {
                    resultBean.setCheck(checked);
                }
                shopCarAdapter.notifyDataSetChanged();
                caclulatePrice();
            }
        });
        shopCarAdapter.setOnShopCartClickListener(new ShopCarAdapter.OnShopCartClickListener() {
            @Override
            public void onShopCartClick(int position, boolean isChecked) {
                if (!isChecked){
                    cbAll.setChecked(false);
                }else {
                    boolean isAllSelected=true;
                    for (ShopCarEntity.ResultBean resultBean : shopcarlist) {
                        if (!resultBean.isCheck()){
                            isAllSelected=false;
                            break;
                        }
                    }
                    cbAll.setChecked(isAllSelected);
                    shopCarAdapter.notifyDataSetChanged();
                }
                caclulatePrice();
            }
        });
        shopCarAdapter.setOnAddDecreaseListener(new ShopCarAdapter.OnAddDecreaseListener() {
            @Override
            public void onChang(int position, int num) {
                caclulatePrice();
            }
        });
        shopCarAdapter.setOnProductClickListener(new ShopCarAdapter.OnCommodityClickListener() {
            @Override
            public void onCommodityClick(int commodityId) {
                EventBus.getDefault().postSticky(commodityId+"");
                Intent intent=new Intent(getActivity(),XiangqingActivity.class);
                startActivity(intent);
            }
        });
    }




    public void caclulatePrice(){
        float price=0;
        for (ShopCarEntity.ResultBean resultBean : shopcarlist) {
            if (resultBean.isCheck()) {
                price+=resultBean.getPrice()*resultBean.getCount();
            }
        }
        tvPriceAll.setText("总价:"+price);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        shopPresenter.detach();
    }

    @OnClick({R.id.tv_price_all, R.id.tv_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_price_all:
                break;
            case R.id.tv_tijiao:
                break;
        }
    }

    @Override
    public void shopSuccess(Object obj) {
        ShopCarEntity shopCarEntity= (ShopCarEntity) obj;
        List<ShopCarEntity.ResultBean> result = shopCarEntity.getResult();
        if (result!=null){
            shopcarlist.clear();
            shopcarlist.addAll(result);
        }
        shopCarAdapter.notifyDataSetChanged();

    }

    @Override
    public void shopFailed(String str) {

    }
}
