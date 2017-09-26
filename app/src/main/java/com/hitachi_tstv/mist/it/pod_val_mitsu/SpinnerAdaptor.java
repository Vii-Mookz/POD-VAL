package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tunyaporn on 9/21/2017.
 */

public class SpinnerAdaptor extends BaseAdapter {

    Context context;
    String[] sizeStrings;
    SpinnerViewHolder spinnerViewHolder;

    public SpinnerAdaptor(Context context, String[] sizeStrings) {
        this.context = context;
        this.sizeStrings = sizeStrings;
    }

    @Override
    public int getCount() {
        return sizeStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.percentage_spinner, null);

            spinnerViewHolder = new SpinnerViewHolder(view);

            view.setTag(spinnerViewHolder);
        }else {
            spinnerViewHolder = (SpinnerViewHolder) view.getTag();
        }

        spinnerViewHolder.progressSpinner.setProgress(Integer.parseInt(sizeStrings[i]));

        return view;
    }

    static class SpinnerViewHolder {
        @BindView(R.id.spinnerProgress)
        BootstrapProgressBar progressSpinner;
        @BindView(R.id.linSpinner)
        LinearLayout spinnerLinearLayout;

        SpinnerViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}