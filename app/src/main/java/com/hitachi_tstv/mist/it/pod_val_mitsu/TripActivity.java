package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TripActivity extends AppCompatActivity {

    @BindView(R.id.dateBtnTrip)
    Button dateBtnTrip;
    @BindView(R.id.imgDriverTrip)
    ImageView imgDriverTrip;
    @BindView(R.id.truckIdLblTrip)
    TextView truckIdLblTrip;
    @BindView(R.id.truckIdValTrip)
    TextView truckIdValTrip;
    @BindView(R.id.truckTypeLblTrip)
    TextView truckTypeLblTrip;
    @BindView(R.id.truckTypeValTrip)
    TextView truckTypeValTrip;
    @BindView(R.id.driverNameLblTrip)
    TextView driverNameLblTrip;
    @BindView(R.id.driverNameValTrip)
    TextView driverNameValTrip;
    @BindView(R.id.middenLinTrip)
    LinearLayout middenLinTrip;
    @BindView(R.id.tripListviewTrip)
    ListView tripListviewTrip;
    private String[][] suppSeqStrings,suppCodeStrings,suppNameStrings,planDtl2IdStrings;
    private String[] loginStrings,tripString,planDtlIdStrings,placeTypeStrings,transportTypeStrings;
//    String planDateStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        ButterKnife.bind(this);


        //get Inten data
        loginStrings = getIntent().getStringArrayExtra("Login");




        SynTripData synTripData = new SynTripData(TripActivity.this);
        synTripData.execute();
    }

    private class SynTripData extends AsyncTask<String, Void, String> {

        private Context context;

        public SynTripData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody  =  new FormBody.Builder()
                        .add("isAdd","true")
                        .add("driver_id",loginStrings[0])
                        .add("planId","")
                        .build();
                //  Log.d("Tag", "Driver:" + loginStrings[0]);
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(MyConstant.urlGetPlanTrip).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.d("Tag", "e doInBack ==>" + e.toString()+"line::"+e.getStackTrace()[0].getLineNumber());
                return null;
            }

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Tag", "JSON---->" + s);

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONObject("truckInfo");

                String pathImg = MyConstant.serverString+MyConstant.projectString+"/"+"app/MasterData/driver/avatar/"+loginStrings[3];
                Log.d("Tag", "pathImg---->" + pathImg);

                dateBtnTrip.setText(jsonObject1.getString("planDate"));
                driverNameValTrip.setText(loginStrings[1]);
                truckIdValTrip.setText(jsonObject1.getString("license"));
                truckTypeValTrip.setText(jsonObject1.getString("truckType_code"));
                Picasso.with(context)
                        .load(pathImg)
                        .into(imgDriverTrip);
                if (loginStrings[6].equals("M") ) {
                    imgDriverTrip.setImageResource(R.drawable.male);

                }else {

                    imgDriverTrip.setImageResource(R.drawable.female);
                }


                JSONArray jsonArray = jsonObject.getJSONArray("tripInfo");
                planDtlIdStrings = new String[jsonArray.length()];
                placeTypeStrings = new String[jsonArray.length()];
                transportTypeStrings = new String[jsonArray.length()];

                suppSeqStrings = new String[jsonArray.length()][];
                suppCodeStrings = new String[jsonArray.length()][];
                suppNameStrings = new String[jsonArray.length()][];
                planDtl2IdStrings = new String[jsonArray.length()][];

                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    planDtlIdStrings[i] = jsonObject3.getString("planDtlId");
                    placeTypeStrings[i] = jsonObject3.getString("placeType");
                    transportTypeStrings[i] = jsonObject3.getString("transport_type");

                    Log.d("Tag","------>planDtlIdStrings:::--> "+planDtlIdStrings[i]);

                    //JSONObject jsonObject4 = jsonObject3.getJSONObject("detail");

                    JSONArray detailArray = jsonObject3.getJSONArray("detail");

                    suppSeqStrings[i] = new String[detailArray.length()];
                    suppCodeStrings[i] = new String[detailArray.length()];
                    suppNameStrings[i] = new String[detailArray.length()];
                    planDtl2IdStrings[i] = new String[detailArray.length()];

                    for(int j = 0; j < detailArray.length(); j++) {
                        JSONObject jsonObject5  = detailArray.getJSONObject(j);
                        suppSeqStrings[i][j] = jsonObject5.getString("suppSeq");
                        suppCodeStrings[i][j] = jsonObject5.getString("suppCode");
                        suppNameStrings[i][j] = jsonObject5.getString("suppName");
                        planDtl2IdStrings[i][j] = jsonObject5.getString("planDtl2Id");
                        Log.d("Tag","------>planDtl2Id:::--> "+i+j);

                    }
                }


                TripAdapter tripAdapter = new TripAdapter(TripActivity.this, planDtl2IdStrings, suppCodeStrings, suppNameStrings,suppSeqStrings,tripString);
                tripListviewTrip.setAdapter(tripAdapter);

                tripListviewTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        String DateTrip = dateBtnTrip.toString();
                        Intent intent = new Intent(TripActivity.this, JobActivity.class);
                        intent.putExtra("Login", loginStrings);
                        intent.putExtra("planDtlId", planDtlIdStrings[i]);

                        Log.d("Tag", "intent::: PlanDtlId:::--->" + planDtlIdStrings[i]);
                        Log.d("Tag", "intent::: loginStrings:::--->" + loginStrings);

                        startActivity(intent);
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Tag", "Exception:::::--->" + e + e.getStackTrace()[0].getLineNumber());
            }
        }
    }


    @OnClick(R.id.dateBtnTrip)
    public void onViewClicked() {
        Intent intent = new Intent(TripActivity.this, DateDeliveryActivity.class);
        intent.putExtra("Login", loginStrings);
        startActivity(intent);
    }
}
