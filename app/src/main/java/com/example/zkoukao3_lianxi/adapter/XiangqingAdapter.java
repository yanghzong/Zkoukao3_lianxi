package com.example.zkoukao3_lianxi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zkoukao3_lianxi.R;
import com.example.zkoukao3_lianxi.entity.XiangqingEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class XiangqingAdapter  extends RecyclerView.Adapter<XiangqingAdapter.ViewHolder>{
    private Context context;
    private XiangqingEntity.ResultBean list;
    private ArrayList<String> bannerlist;


    public XiangqingAdapter(Context context, XiangqingEntity.ResultBean list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_xiangqing, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String picture = list.getPicture();
        String[] split = picture.split("\\,");
        bannerlist = new ArrayList<>();
        bannerlist.add(split[0]);
        bannerlist.add(split[1]);
        bannerlist.add(split[2]);
        bannerlist.add(split[3]);
        bannerlist.add(split[4]);
        holder.vpBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return bannerlist.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                SimpleDraweeView img = new SimpleDraweeView(context);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setImageURI(bannerlist.get(position));
                container.addView(img);
                return img;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        holder.categoryName.setText(list.getCategoryName());
        holder.commentNum.setText("已售"+list.getCommentNum()+"件");
        holder.commodityName.setText(""+list.getCommodityName());
        holder.price.setText("￥:"+list.getPrice());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private TextView commentNum;
        private TextView commodityName;
        private TextView price;
        private ViewPager vpBanner;

        public ViewHolder(View itemView) {
            super(itemView);
            vpBanner=itemView.findViewById(R.id.vp_banner);
            categoryName=itemView.findViewById(R.id.categoryName);
            commentNum=itemView.findViewById(R.id.commentNum);
            commodityName=itemView.findViewById(R.id.commodityName);
            price=itemView.findViewById(R.id.price);
        }
    }
}
