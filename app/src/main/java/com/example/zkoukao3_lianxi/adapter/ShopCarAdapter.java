package com.example.zkoukao3_lianxi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zkoukao3_lianxi.R;
import com.example.zkoukao3_lianxi.entity.ShopCarEntity;
import com.example.zkoukao3_lianxi.selfview.AddDecreaseView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ShopCarAdapter  extends RecyclerView.Adapter<ShopCarAdapter.ViewHolder>  {
    public interface OnShopCartClickListener {
        void onShopCartClick(int position, boolean isChecked);
    }

    private OnShopCartClickListener listener;

    public void setOnShopCartClickListener(OnShopCartClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddDecreaseListener{
        void onChang(int position,int num);
    }
    private OnAddDecreaseListener addListener;
    public void setOnAddDecreaseListener(OnAddDecreaseListener listener){
        this.addListener=listener;
    }

    //点击进入详情的接口回调
    public interface OnCommodityClickListener {
        void onCommodityClick(int commodityId);
    }

    private  OnCommodityClickListener commodityClickListener;

    public void setOnProductClickListener(OnCommodityClickListener listener) {
        this.commodityClickListener = listener;
    }
    private Context context;
    private List<ShopCarEntity.ResultBean> list;

    public ShopCarAdapter(Context context, List<ShopCarEntity.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shopcar, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ShopCarEntity.ResultBean resultBean = list.get(position);
        holder.shopcarImg.setImageURI(resultBean.getPic());
        holder.shopcarName.setText(resultBean.getCommodityName());
        holder.shopcarPrice.setText("￥:"+resultBean.getPrice());
        holder.cvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int commodityId = resultBean.getCommodityId();
                commodityClickListener.onCommodityClick(commodityId);
            }
        });
        holder.shopcarAdd.setNum(resultBean.getCount());
        holder.shopcarAdd.setOnAddClickListener(new AddDecreaseView.OnAddClickListener() {
            @Override
            public void add(int num) {
                resultBean.setCount(num);
                if (addListener!=null){
                    addListener.onChang(position,num);
                }
            }

            @Override
            public void decrease(int num) {
                resultBean.setCount(num);
                if (addListener!=null){
                    addListener.onChang(position,num);
                }
            }
        });

        holder.cbDan.setOnCheckedChangeListener(null);
        holder.cbDan.setChecked(resultBean.isCheck());
        holder.cbDan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                resultBean.setCheck(b);
                if (listener!=null){
                    listener.onShopCartClick(position,b);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AddDecreaseView shopcarAdd;
        private CheckBox cbDan;
        private SimpleDraweeView shopcarImg;
        private TextView shopcarName;
        private TextView shopcarPrice;
        private final CardView cvView;

        public ViewHolder(View itemView) {
            super(itemView);
            cbDan=itemView.findViewById(R.id.cb_dan);
            shopcarImg=itemView.findViewById(R.id.shopcar_img);
            shopcarName=itemView.findViewById(R.id.shopcar_name);
            shopcarPrice=itemView.findViewById(R.id.shopcar_price);
            shopcarAdd=itemView.findViewById(R.id.shopcar_add);
            cvView = itemView.findViewById(R.id.cv_view);
        }
    }
}