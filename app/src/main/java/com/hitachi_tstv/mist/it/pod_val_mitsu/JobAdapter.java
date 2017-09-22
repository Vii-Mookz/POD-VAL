package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Vipavee on 21/09/2017.
 */

public class JobAdapter extends BaseAdapter {

    Context context;
    String dateString, tripNoString, subJobNoString;
    String[] storeStrings, timeStrings, loginStrings, outTimeStrings, placeStrings;
    String[][] jobNoStrings;


    public JobAdapter(Context context, String dateString) {
        this.context = context;
        this.dateString = dateString;
        this.tripNoString = tripNoString;
        this.subJobNoString = subJobNoString;
        this.storeStrings = storeStrings;
        this.timeStrings = timeStrings;
        this.loginStrings = loginStrings;
        this.outTimeStrings = outTimeStrings;
        this.placeStrings = placeStrings;
        this.jobNoStrings = jobNoStrings;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
