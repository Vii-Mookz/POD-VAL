package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    String[] loginStrings, placeTypeStrings, planDtlIdStrings, timeArrivalStrings, stationNameStrings, transportTypeStrings;

    String worksheetString, dateString, planNoStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ButterKnife.bind(this);

//        Check login
        Resources res = getResources();
        String[] login = res.getStringArray(R.array.login_array);
        loginStrings = getIntent().getStringArrayExtra("Login");
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
                        .add("driver_id", "")
                        .add("plan_id", "")
                        .add("planDtlId", "")

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

                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONObject("tripInfo");

                worksheetString = jsonObject1.getString("work_sheet_no");
                planNoStrings = jsonObject1.getString("planNo");

                JSONArray jsonArray1 = jsonObject1.getJSONArray("DTL");
                planDtlIdStrings = new String[jsonArray1.length()];
                stationNameStrings = new String[jsonArray1.length()];
                timeArrivalStrings = new String[jsonArray1.length()];
                transportTypeStrings = new String[jsonArray1.length()];
                placeTypeStrings = new String[jsonArray1.length()];
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    planDtlIdStrings[j] = jsonObject2.getString("planDtl2_id");
                    stationNameStrings[j] = jsonObject2.getString("suppName");
                    timeArrivalStrings[j] = jsonObject2.getString("timeArrival");
                    transportTypeStrings[j] = jsonObject2.getString("transport_type");
                    placeTypeStrings[j] = jsonObject2.getString("placeType");
                }


                JobAdapter manageJobAdaptor = new JobAdapter(JobActivity.this, planDtlIdStrings, stationNameStrings, timeArrivalStrings,placeTypeStrings);
                lisJAStore.setAdapter(manageJobAdaptor);

                lisJAStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (transportTypeStrings[i].equals("PICK UP")) {
                            Intent intent = new Intent(JobActivity.this, SupplierDeliveryActivity.class);
                            intent.putExtra("Date", dateString);
                            intent.putExtra("Login", loginStrings);
                            intent.putExtra("planDtl2_id", planDtlIdStrings[i]);
                            intent.putExtra("timeArrival", timeArrivalStrings[i]);
                            intent.putExtra("stationName", stationNameStrings[i]);
                            intent.putExtra("transporttype", transportTypeStrings[i]);
                            startActivity(intent);
                        } else{
                            Intent intent = new Intent(JobActivity.this, PlanDeliveryActivity.class);
                            intent.putExtra("Date", dateString);
                            intent.putExtra("Login", loginStrings);
                            intent.putExtra("planDtl2_id", planDtlIdStrings[i]);
                            intent.putExtra("timeArrival", timeArrivalStrings[i]);
                            intent.putExtra("stationName", stationNameStrings[i]);
                            intent.putExtra("transporttype", transportTypeStrings[i]);
                            startActivity(intent);
                    }

                }
                });

                Log.d("Tag", "Worksheet ==> " + worksheetString);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Tag", "Error dddddd on post JSONArray ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());

            }
            Log.d("Tag", "check ==>" + worksheetString);
            Log.d("Tag", "Name ==>" + stationNameStrings[3]);
            Log.d("Tag", "check ==>" + timeArrivalStrings[4]);
            txtWork.setText(worksheetString);
        }

    }

    private class SynUpdateTripStatus extends AsyncTask<Void, Void, String> {
        String timeString, latString, longString;

        public SynUpdateTripStatus(String timeString, String latString, String longString) {
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
                Log.d("Tag", "Error  back ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());
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

                    Log.d("Tag", "Lat/Long : Time ==> " + lat[0] + "/" + lng[0] + " : " + time[0]);
                    Toast.makeText(JobActivity.this, "Success", Toast.LENGTH_LONG).show();
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




}
