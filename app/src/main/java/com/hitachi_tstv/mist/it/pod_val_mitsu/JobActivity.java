package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JobActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView txtTrip;
    @BindView(R.id.textView2)
    TextView txtWork;
    @BindView(R.id.textView4)
    TextView txtTripdate;
    @BindView(R.id.linJATop)
    LinearLayout linJATop;
    @BindView(R.id.lisJAStore)
    ListView lisJAStore;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.button)
    Button buttonFinish;
    @BindView(R.id.lisJABottom)
    LinearLayout lisJABottom;

    String[] loginStrings, planNoStrings, transportTypeStrings, worksheetNoString,planDtlIdStrings,timeArrivalStrings,stationNameStrings,truckTypeCodeStrings;

    String worksheetString, dateString;
    DialogViewHolder dialogViewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ButterKnife.bind(this);

//        Check login
        Resources res = getResources();
        String[] login = res.getStringArray(R.array.login_array);
        dateString = getIntent().getStringExtra("Date");
        txtTripdate.setText(dateString);
        SynGetJobList synGetJobList = new SynGetJobList(this, login[0]);
        synGetJobList.execute();
    }



    protected class SynGetJobList extends AsyncTask<Void, Void, String> {
        private Context context;
        private String truckIDString;



        public SynGetJobList(Context context, String truckIDString) {
            this.context = context;
            this.truckIDString = truckIDString;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("truck_id", truckIDString)

                        .build();
                Request request = builder.post(requestBody).url(MyConstant.urlGetTrip).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Tag", "Service ==>" + s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                //Set array size
                planDtlIdStrings = new String[jsonArray.length()];
                planNoStrings = new String[jsonArray.length()];
                worksheetNoString = new String[jsonArray.length()];
                stationNameStrings = new String[jsonArray.length()];
                transportTypeStrings = new String[jsonArray.length()];
                timeArrivalStrings = new String[jsonArray.length()];
                truckTypeCodeStrings = new String[jsonArray.length()];

                for (int i = 0;i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    planDtlIdStrings[i] = jsonObject.getString("planDtlId");
                    planNoStrings[i] = jsonObject.getString("planNo");
                    worksheetNoString[i] = jsonObject.getString("work_sheet_no");
                    stationNameStrings[i] = jsonObject.getString("stationName");
                    transportTypeStrings[i] = jsonObject.getString("transport_type");
                    timeArrivalStrings[i] = jsonObject.getString("timeArrival");
                    truckTypeCodeStrings[i] = jsonObject.getString("trucktype_code");


                    JobAdapter manageJobAdaptor = new JobAdapter(context, worksheetNoString, routeNoStrings, departDateStrings, storeStrings);
                    lisJAStore.setAdapter(manageJobAdaptor);
                }


                Log.d("Tag", "Truck ==> " + loginStrings[3]);
                txtWork.setText(worksheetString);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Tag", "Error dddddd on post JSONArray ==> " + e +" Line " + e.getStackTrace()[0].getLineNumber());

            }
            Log.d("Tag", "check ==>" + worksheetString);
        }

    }
    private class SynUpdateTripStatus extends AsyncTask<Void, Void, String> {
        String timeString,  latString, longString;

        public SynUpdateTripStatus(String timeString,  String latString, String longString) {
            this.timeString = timeString;
            this.latString = latString;
            this.longString = longString;

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("drv_username", longString)
//                        .add("truck_id", loginStrings[0])
                        .add("Lat", latString)
                        .add("Lng", longString)
                        .add("stamp", timeString)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.post(requestBody).url(MyConstant.urlGetUpdateDCStart).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Tag", "Error  back ==> " + e +" Line " + e.getStackTrace()[0].getLineNumber());
                return null;
            }
        }
    }
    @OnClick({R.id.btn_start, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:

                final String[] lat = new String[1];
                final String[] lng = new String[1];
                final String[] time = new String[1];
                UtilityClass utilityClass = new UtilityClass(this);
                if (utilityClass.setLatLong(0)) {
                    lat[0] = utilityClass.getLatString();
                    lng[0] = utilityClass.getLongString();
                    time[0] = utilityClass.getDateTime();

                    Log.d("Tag", "Lat/Long : Time ==> " + lat[0] + "/" + lng[0] + " : " + time[0] );

                    SynUpdateTripStatus synUpdateTripStatus = new SynUpdateTripStatus(time[0], lat[0], lng[0]);
                    synUpdateTripStatus.execute();

                } else {
                    Toast.makeText(JobActivity.this, getResources().getText(R.string.err_gps), Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.button:
                break;
        }
    }


        class DialogViewHolder {

        @BindView(R.id.txtSODHeader)
        TextView headerTextView;
        @BindView(R.id.edtSODNo)
        EditText odoNoEditText;
        public DialogViewHolder(View view1) {
            ButterKnife.bind(this, view1);
        }
    }
}
