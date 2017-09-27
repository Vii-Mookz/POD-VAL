package com.hitachi_tstv.mist.it.pod_val_mitsu;


import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yaowaluk on 21/09/2560.
 */

public class TripAdapter extends BaseAdapter {
    TripViewHolder tripViewHolder;
    @BindView(R.id.tripTextviewTL)
    TextView tripTextviewTL;
    private Context context;
    private String[][] planDtl2IdStrings, suppCodeStrings, suppNameStrings, suppSeqStrings;
    private String[] tripStrings,endarraivalDateStrings;

    public TripAdapter(Context context, String[][] planDtl2IdStrings, String[][] suppCodeStrings, String[][] suppNameStrings, String[][] suppSeqStrings, String[] tripStrings,String[] endarraivalDateStrings) {

        this.context = context;
        this.planDtl2IdStrings = planDtl2IdStrings;
        this.suppCodeStrings = suppCodeStrings;
        this.suppNameStrings = suppNameStrings;
        this.suppSeqStrings = suppSeqStrings;
        this.tripStrings = tripStrings;
        this.endarraivalDateStrings = endarraivalDateStrings;
    }

    @Override
    public int getCount() {
        return suppCodeStrings.length;

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

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.trip_listview, viewGroup, false);

            tripViewHolder = new TripViewHolder(view);

            view.setTag(tripViewHolder);

        } else {

            tripViewHolder = (TripViewHolder) view.getTag();
        }

        tripViewHolder.imgStartTL.setImageResource(R.drawable.start);
        tripViewHolder.imgEndTL.setImageResource(R.drawable.end);

        tripViewHolder.tripTextviewTL.setText("Trip " + String.valueOf(i + 1));
        tripViewHolder.stationStartTL.setText(suppCodeStrings[i][0] + ":" + suppNameStrings[i][0]);
        tripViewHolder.stationEndJobTL.setText(suppCodeStrings[i][1] + ":" + suppNameStrings[i][1]);

//        tripViewHolder.tripTextviewTL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, JobActivity.class);
//            }
//        });
        if (!endarraivalDateStrings[i].equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tripViewHolder.linearTrip.setForeground(context.getDrawable(R.drawable.layout_bg_3));
                tripViewHolder.linearTrip.setClickable(true);
            }



        } else {
            tripViewHolder.linearTrip.setForeground(null);
        }
        Log.d("TAG", "End  ==>  " + endarraivalDateStrings[i]);

        return view;
    }

    static class TripViewHolder {
        @BindView(R.id.tripTextviewTL)
        TextView tripTextviewTL;
        @BindView(R.id.imgStartTL)
        ImageView imgStartTL;
        @BindView(R.id.stationStartTL)
        TextView stationStartTL;
        @BindView(R.id.imgEndTL)
        ImageView imgEndTL;
        @BindView(R.id.stationEndJobTL)
        TextView stationEndJobTL;
        @BindView(R.id.linearTrip)
        LinearLayout linearTrip;
        TripViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
