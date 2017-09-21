package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

public class SupplierDeliveryActivity extends AppCompatActivity {


    @BindView(R.id.txt_name)
    TextView text_name;
    @BindView(R.id.btn_arrival)
    Button btn_arrival;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    @BindView(R.id.progess_truck)
    BootstrapProgressBar progress_truck;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.img_right)
    ImageView img_right;
    @BindView(R.id.img_back)
    ImageView img_back;
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
        setContentView(R.layout.activity_supplier_delivery);



    }

    //Set On Click Listener
    @OnClick({R.id.btn_save, R.id.btn_confirm, R.id.btn_arrival, R.id.img_left, R.id.img_right,R.id.img_back,R.id.img_4,R.id.img_5,R.id.img_6,R.id.img_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_save:

                break;
            case R.id.btn_confirm:

                break;
            case R.id.btn_arrival:

                break;
            case R.id.img_left:

                break;
            case R.id.img_right:

                break;
            case R.id.img_back:

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
