package com.example.zkoukao3_lianxi.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zkoukao3_lianxi.R;


public class AddDecreaseView  extends RelativeLayout implements View.OnClickListener {
    private TextView txtAdd;
    private TextView txtDecrease;
    private TextView txtNum;
    private int num;
    public interface OnAddClickListener{
        void add(int num);
        void decrease(int num);
    }
    private OnAddClickListener listener;
    public void setOnAddClickListener(OnAddClickListener listener){
        this.listener=listener;
    }
    public AddDecreaseView(Context context) {
        this(context,null);
    }

    public AddDecreaseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddDecreaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context,R.layout.item_add_decrease,this);
        txtAdd=findViewById(R.id.btn_add);
        txtDecrease=findViewById(R.id.btn_jian);
        txtNum=findViewById(R.id.tv_num);
        txtNum.setText("1");
        txtAdd.setOnClickListener(this);
        txtDecrease.setOnClickListener(this);
    }

    public void setNum(int num){
        this.num=num;
        txtNum.setText(num+"");
    }
    public int getNum(){
        return num;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                txtDecrease.setVisibility(VISIBLE);
                num++;
                txtNum.setText(num+"");
                if (listener!=null){
                    listener.add(num);
                }
                break;
            case R.id.btn_jian:
                if (num>1) {
                    num--;
                }if (num<=1){
                txtDecrease.setVisibility(GONE);
            }
                txtNum.setText(num+"");
                if (listener!=null){
                    listener.decrease(num);
                }
                break;
        }
    }
}
