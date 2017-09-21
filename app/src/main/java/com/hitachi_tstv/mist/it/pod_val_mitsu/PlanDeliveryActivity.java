package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class PlanDeliveryActivity extends AppCompatActivity {

    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txtHead)
    TextView txtHead;
    @BindView(R.id.txt_qyt)
    TextView txt_qty;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    @BindView(R.id.btn_arrival)
    Button btn_arrival;
    @BindView(R.id.txtHead2)
    TextView txtHead2;
    @BindView(R.id.txt_qty2)
    TextView txt_qty2;
    @BindView(R.id.img_r)
    ImageView img_r;
    @BindView(R.id.img_b)
    ImageView img_b;
    @BindView(R.id.img_l)
    ImageView img_l;
    @BindView(R.id.img_4)
    ImageView img_4;
    @BindView(R.id.img_5)
    ImageView img_5;
    @BindView(R.id.img_6)
    ImageView img_6;
    @BindView(R.id.img_7)
    ImageView img_7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_delivery);
    }

    //Set On Click Listener
    @OnClick({R.id.btn_confirm, R.id.btn_arrival, R.id.img_l, R.id.img_r,R.id.img_b,R.id.img_4,R.id.img_5,R.id.img_6,R.id.img_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.btn_confirm:

                break;
            case R.id.btn_arrival:

                break;
            case R.id.img_l:

                break;
            case R.id.img_r:

                break;
            case R.id.img_b:

                break;
            case R.id.img_4:

                break;
            case R.id.img_5:

                break;
            case R.id.img_6:

                break;
            case R.id.img_7:

                break;
        }
    }
}