package com.example.zkoukao3_lianxi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zkoukao3_lianxi.fragment.DindanFragment;
import com.example.zkoukao3_lianxi.fragment.ShopCarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_zhu)
    ViewPager vpZhu;
    @BindView(R.id.tv_shopcar)
    TextView tvShopcar;
    @BindView(R.id.tv_dindan)
    TextView tvDindan;
    private Unbinder bind;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        fragmentList = new ArrayList<>();
        fragmentList.add(new ShopCarFragment());
        fragmentList.add(new DindanFragment());
        vpZhu.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        vpZhu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeBackGround(position);

            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void changeBackGround(int position) {
        switch (position){
            case 0:
               tvShopcar.setTextColor(Color.RED);
               tvDindan.setTextColor(Color.BLACK);
                break;
            case 1:
                tvDindan.setTextColor(Color.RED);
                tvShopcar.setTextColor(Color.BLACK);
        }
    }

    @OnClick({R.id.tv_shopcar, R.id.tv_dindan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcar:
                vpZhu.setCurrentItem(0);
                changeBackGround(0);

                break;
            case R.id.tv_dindan:
               vpZhu.setCurrentItem(1);
               changeBackGround(1);
                break;
        }
    }
}
