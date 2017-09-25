package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vipavee on 21/09/2017.
 */

public class JobAdapter extends BaseAdapter {

    private Context context;

    String dateString, tripNoString, subJobNoString;
    String[]  placeTypeStrings, planDtlIdStrings, timeArrivalStrings, stationNameStrings;
    ViewHolder viewholder;

    public JobAdapter(Context context, String[] planDtlIdStrings, String[] stationNameStrings, String[] timeArrivalStrings,String[] placeTypeStrings) {
        this.context = context;
        this.planDtlIdStrings = planDtlIdStrings;
        this.timeArrivalStrings = timeArrivalStrings;
        this.stationNameStrings = stationNameStrings;
        this.placeTypeStrings = placeTypeStrings;
    }



    @Override
    public int getCount() {
        return planDtlIdStrings.length;
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

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_job, parent, false);

            viewholder = new ViewHolder(convertView);

            convertView.setTag(viewholder);

        } else {

            viewholder = (ViewHolder) convertView.getTag();
        }

        if (placeTypeStrings[position].equals("PLANT") ) {
            viewholder.imgView.setImageResource(R.drawable.factory);
            viewholder.imgView.setColorFilter(new LightingColorFilter(Color.BLUE, Color.BLUE));
        }else {

            viewholder.imgView.setImageResource(R.drawable.home1);
        }
        viewholder.txtSup.setText(stationNameStrings[position]);
        viewholder.txtTime.setText(timeArrivalStrings[position]);



        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.imgView)
        ImageView imgView;
        @BindView(R.id.txtSup)
        TextView txtSup;
        @BindView(R.id.txtTime)
        TextView txtTime;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
