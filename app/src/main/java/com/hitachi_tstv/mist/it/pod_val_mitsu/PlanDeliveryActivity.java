package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;

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

public class PlanDeliveryActivity extends AppCompatActivity {

    @BindView(R.id.progess_truck)
    BootstrapProgressBar progessTruck;
    @BindView(R.id.spnSDAPercentage)
    Spinner percentageSpinner;

    @BindView(R.id.linPDABottom)
    LinearLayout linPDABottom;
    private String[] planDtl2IdStrings, amountStrings, loginStrings, datePlanStrings;
    private String planDtlIdString, planDtl2IdString, planNameString, positionString, planIdString,
            timeArrivalString, transporttypeString, suppLatString, suppLonString,dateString, suppRadiusString,totalPercentageString,spinnerValueString,flagArrivalString;

    Boolean doubleBackPressABoolean = false;
    @BindView(R.id.txt_name)
    TextView factoryTextviewPD;

    @BindView(R.id.btn_arrival)
    Button btnArrivalPD;
    @BindView(R.id.btn_confirm)
    Button btnDeparturePD;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressABoolean) {
            Intent intent1 = new Intent(PlanDeliveryActivity.this, JobActivity.class);
            intent1.putExtra("Login", loginStrings);
            intent1.putExtra("planDtlId", planDtlIdString);
            intent1.putExtra("position", positionString);
            intent1.putExtra("planDate", dateString);
            intent1.putExtra("planId", planIdString);
            startActivity(intent1);
            finish();
        }

        doubleBackPressABoolean = true;
        Toast.makeText(this, getResources().getText(R.string.check_back), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackPressABoolean = false;
            }
        }, 2000);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Intent intent1 = new Intent(PlanDeliveryActivity.this, PlanDeliveryActivity.class);
                intent1.putExtra("planDtl2_id", planDtl2IdString);
                intent1.putExtra("Login", loginStrings);
                intent1.putExtra("planDtlId", planDtlIdString);
                intent1.putExtra("position", positionString);
                intent1.putExtra("Date", dateString);
                intent1.putExtra("planId", planIdString);
                intent1.putExtra("stationName", planNameString);
                intent1.putExtra("transporttype", transporttypeString);
                startActivity(intent1);
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_delivery);
        ButterKnife.bind(this);

        //get Intent data
        loginStrings = getIntent().getStringArrayExtra("Login");
        dateString = getIntent().getStringExtra("Date");
        planIdString = getIntent().getStringExtra("planId");
        planDtl2IdString = getIntent().getStringExtra("planDtl2_id");
        planDtlIdString = getIntent().getStringExtra("planDtlId");
        planNameString = getIntent().getStringExtra("stationName");
        transporttypeString = getIntent().getStringExtra("transporttype");
        positionString = getIntent().getStringExtra("position");
        timeArrivalString = getIntent().getStringExtra("timeArrival");

        // PlanDeliveryAdapter planDeliveryAdapter = new PlanDeliveryAdapter(PlanDeliveryActivity.this, planDtl2IdStrings, amountStrings, planDtlIdString);
        // lisPDAPlan.setAdapter(planDeliveryAdapter);

        factoryTextviewPD.setText(planNameString);
        SynDeliveryData synDeliveryData = new SynDeliveryData(PlanDeliveryActivity.this);
        synDeliveryData.execute();

    }

    String[] getSizeSpinner(int size) {
        String[] sizeStrings;
        switch (size) {
            case 100:
                sizeStrings = new String[11];
                sizeStrings[0] = "100";
                sizeStrings[1] = "90";
                sizeStrings[2] = "80";
                sizeStrings[3] = "70";
                sizeStrings[4] = "60";
                sizeStrings[5] = "50";
                sizeStrings[6] = "40";
                sizeStrings[7] = "30";
                sizeStrings[8] = "20";
                sizeStrings[9] = "10";
                sizeStrings[10] = "0";
                break;
            case 90:
                sizeStrings = new String[10];
                sizeStrings[0] = "90";
                sizeStrings[1] = "80";
                sizeStrings[2] = "70";
                sizeStrings[3] = "60";
                sizeStrings[4] = "50";
                sizeStrings[5] = "40";
                sizeStrings[6] = "30";
                sizeStrings[7] = "20";
                sizeStrings[8] = "10";
                sizeStrings[9] = "0";
                break;
            case 80:
                sizeStrings = new String[9];
                sizeStrings[0] = "80";
                sizeStrings[1] = "70";
                sizeStrings[2] = "60";
                sizeStrings[3] = "50";
                sizeStrings[4] = "40";
                sizeStrings[5] = "30";
                sizeStrings[6] = "20";
                sizeStrings[7] = "10";
                sizeStrings[8] = "0";
                break;
            case 70:
                sizeStrings = new String[8];
                sizeStrings[0] = "70";
                sizeStrings[1] = "60";
                sizeStrings[2] = "50";
                sizeStrings[3] = "40";
                sizeStrings[4] = "30";
                sizeStrings[5] = "20";
                sizeStrings[6] = "10";
                sizeStrings[7] = "0";
                break;
            case 60:
                sizeStrings = new String[7];
                sizeStrings[0] = "60";
                sizeStrings[1] = "50";
                sizeStrings[2] = "40";
                sizeStrings[3] = "30";
                sizeStrings[4] = "20";
                sizeStrings[5] = "10";
                sizeStrings[6] = "0";
                break;
            case 50:
                sizeStrings = new String[6];
                sizeStrings[0] = "50";
                sizeStrings[1] = "40";
                sizeStrings[2] = "30";
                sizeStrings[3] = "20";
                sizeStrings[4] = "10";
                sizeStrings[5] = "0";
                break;
            case 40:
                sizeStrings = new String[5];
                sizeStrings[0] = "40";
                sizeStrings[1] = "30";
                sizeStrings[2] = "20";
                sizeStrings[3] = "10";
                sizeStrings[4] = "0";
                break;
            case 30:
                sizeStrings = new String[4];
                sizeStrings[0] = "30";
                sizeStrings[1] = "20";
                sizeStrings[2] = "10";
                sizeStrings[3] = "0";
                break;
            case 20:
                sizeStrings = new String[3];
                sizeStrings[0] = "20";
                sizeStrings[1] = "10";
                sizeStrings[2] = "0";
                break;
            case 10:
                sizeStrings = new String[2];
                sizeStrings[0] = "10";
                sizeStrings[1] = "0";
                break;
            case 0:
                sizeStrings = new String[1];
                sizeStrings[0] = "0";
                break;
            default:
                sizeStrings = null;
                break;
        }

        return sizeStrings;
    }

    BootstrapBrand[] getColorSpinner(int color) {
        BootstrapBrand[] colorStrings;
        switch (color) {
            case 100:
                colorStrings = new BootstrapBrand[11];

                colorStrings[0] = DefaultBootstrapBrand.DANGER;
                colorStrings[1] = DefaultBootstrapBrand.DANGER;
                colorStrings[2] = DefaultBootstrapBrand.DANGER;
                colorStrings[3] = DefaultBootstrapBrand.WARNING;
                colorStrings[4] = DefaultBootstrapBrand.WARNING;
                colorStrings[5] = DefaultBootstrapBrand.WARNING;
                colorStrings[6] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[7] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[8] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[9] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[10] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 90:
                colorStrings = new BootstrapBrand[10];
                colorStrings[0] = DefaultBootstrapBrand.DANGER;
                colorStrings[1] = DefaultBootstrapBrand.DANGER;
                colorStrings[2] = DefaultBootstrapBrand.DANGER;
                colorStrings[3] = DefaultBootstrapBrand.WARNING;
                colorStrings[4] = DefaultBootstrapBrand.WARNING;
                colorStrings[5] = DefaultBootstrapBrand.WARNING;
                colorStrings[6] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[7] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[8] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[9] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 80:
                colorStrings = new BootstrapBrand[9];
                colorStrings[0] = DefaultBootstrapBrand.DANGER;
                colorStrings[1] = DefaultBootstrapBrand.DANGER;
                colorStrings[2] = DefaultBootstrapBrand.DANGER;
                colorStrings[3] = DefaultBootstrapBrand.WARNING;
                colorStrings[4] = DefaultBootstrapBrand.WARNING;
                colorStrings[5] = DefaultBootstrapBrand.WARNING;
                colorStrings[6] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[7] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[8] = DefaultBootstrapBrand.SUCCESS;


                break;
            case 70:
                colorStrings = new BootstrapBrand[8];
                colorStrings[0] = DefaultBootstrapBrand.WARNING;
                colorStrings[1] = DefaultBootstrapBrand.WARNING;
                colorStrings[2] = DefaultBootstrapBrand.WARNING;
                colorStrings[3] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[4] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[5] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[6] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[7] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 60:
                colorStrings = new BootstrapBrand[7];
                colorStrings[0] = DefaultBootstrapBrand.WARNING;
                colorStrings[1] = DefaultBootstrapBrand.WARNING;
                colorStrings[2] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[3] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[4] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[5] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[6] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 50:
                colorStrings = new BootstrapBrand[6];

                colorStrings[0] = DefaultBootstrapBrand.WARNING;
                colorStrings[1] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[2] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[3] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[4] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[5] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 40:
                colorStrings = new BootstrapBrand[5];
                colorStrings[0] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[1] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[2] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[3] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[4] = DefaultBootstrapBrand.SUCCESS;

                break;
            case 30:
                colorStrings = new BootstrapBrand[4];
                colorStrings[0] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[1] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[2] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[3] = DefaultBootstrapBrand.SUCCESS;

                break;
            case 20:
                colorStrings = new BootstrapBrand[3];

                colorStrings[0] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[1] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[2] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 10:
                colorStrings = new BootstrapBrand[2];
                colorStrings[0] = DefaultBootstrapBrand.SUCCESS;
                colorStrings[1] = DefaultBootstrapBrand.SUCCESS;
                break;
            case 0:
                colorStrings = new BootstrapBrand[1];
                colorStrings[0] = DefaultBootstrapBrand.SUCCESS;
                break;
            default:
                colorStrings = null;
                break;
        }

        return colorStrings;
    }

    private class SynDeliveryData extends AsyncTask<String, Void, String> {
        private Context context;
        UtilityClass utilityClass;

        public SynDeliveryData(Context context) {
            this.context = context;
            utilityClass = new UtilityClass(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            try {
                String deviceId = utilityClass.getDeviceID();
                String serial = utilityClass.getSerial();
                String deviceName = utilityClass.getDeviceName();
                Log.d("Tag", deviceId + "  " + serial + "device name " + deviceName);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("planDtl2_id", planDtl2IdString)
                        .add("device_id", deviceId)
                        .add("serial", serial)
                        .add("device_name",deviceName)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(MyConstant.urlGetTripDetailDelivery).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Tag", "IoException..." + e.getStackTrace()[0].getLineNumber());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Tag", "OnpostExecute:::--->" + s);

            //JSONArray jsonArray = new JSONArray(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    suppLatString = jsonObject.getString("supp_lat");
                    suppLonString = jsonObject.getString("supp_lon");
                    suppRadiusString = jsonObject.getString("supp_radius");

                    flagArrivalString = jsonObject.getString("flagArrivaled");

                    Log.d("Tag", "suppLatString::::" + suppLatString);
                    Log.d("Tag", "suppLonString::::" + suppLonString);
                    Log.d("Tag", "suppRadiusString::::" + suppRadiusString);


                    Log.d("Tag", "A " + jsonObject.getString("total_percent_load").equals("null"));
                    Log.d("Tag", "B " + jsonObject.getString("total_percent_load"));

                    if (!(jsonObject.getString("total_percent_load").equals("null"))) {
                        totalPercentageString = jsonObject.getString("total_percent_load");
                    }else{
                        totalPercentageString = "100";
                    }
                    Float aFloat = Float.parseFloat(totalPercentageString);

                    progessTruck.setProgress(Math.round(aFloat));

                    final String[] size = getSizeSpinner(Math.round(aFloat));
                    final BootstrapBrand[] color = getColorSpinner(Math.round(aFloat));
                    spinnerValueString = size[0];



                    SpinnerAdaptor spinnerAdaptor = new SpinnerAdaptor(context, size,color);
                    percentageSpinner.setAdapter(spinnerAdaptor);
                    percentageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            spinnerValueString = size[i];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                    if (flagArrivalString.equals("Y")) {
                        btnArrivalPD.setVisibility(View.INVISIBLE);
                        percentageSpinner.setEnabled(true);
                        btnDeparturePD.setEnabled(true);


                    } else {
                        btnDeparturePD.setEnabled(false);
                        percentageSpinner.setEnabled(false);

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @OnClick({R.id.btn_arrival, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_arrival:

                final UtilityClass utilityClass = new UtilityClass(PlanDeliveryActivity.this);
                if (loginStrings[4].equals("Y")) {
                    if (utilityClass.setLatLong(0)) {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle(R.string.alert);
                        dialog.setIcon(R.drawable.warning);
                        dialog.setCancelable(true);
                        dialog.setMessage(R.string.arrivalDialog);

                        AlertDialog.Builder builder = dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SynUpdateArrival synUpdateArrival = new SynUpdateArrival(utilityClass.getLatString(), utilityClass.getLongString(), utilityClass.getTimeString());
                                synUpdateArrival.execute();
                            }
                        });

                        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }

                break;
            case R.id.btn_confirm:

                final UtilityClass utilityClass1 = new UtilityClass(PlanDeliveryActivity.this);
                if (loginStrings[5].equals("Y")) {
                    if (utilityClass1.setLatLong(0)) {
                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                        dialog1.setTitle(R.string.alert);
                        dialog1.setIcon(R.drawable.warning);
                        dialog1.setCancelable(true);
                        dialog1.setMessage(R.string.departDialog);

                        dialog1.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (utilityClass1.setLatLong(0)) {
                                    SynUpdateDeparture synUpdateDeparture = new SynUpdateDeparture(utilityClass1.getLatString(), utilityClass1.getLongString(), utilityClass1.getTimeString(),spinnerValueString);
                                    synUpdateDeparture.execute();
                                } else {
                                    Toast.makeText(getBaseContext(), getResources().getText(R.string.err_gps1), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog1.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        dialog1.show();

                    }
                }

                break;
        }


    }

    private class SynUpdateArrival extends AsyncTask<Void, Void, String> {
        String latString, longString, timeString;

        public SynUpdateArrival(String latString, String longString, String timeString) {
            this.latString = latString;
            this.longString = longString;
            this.timeString = timeString;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Log.d("Tag", "Lat/Long : Plan ==> " + planDtl2IdString + "," + loginStrings[7] + "," + latString+ "," +longString);

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("drv_username", loginStrings[7])
                        .add("planDtl2_id", planDtl2IdString)
                        .add("lat", latString)
                        .add("lng", longString)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(MyConstant.urlUpdateArrival).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("Tag", "e doInBack ==>" + e.toString() + "line::" + e.getStackTrace()[0].getLineNumber());
                return "";
            }
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Tag", "onPostExecute:::-----> " + s);
            if (s.equals("Success")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), R.string.save_success, Toast.LENGTH_LONG).show();

                        btnArrivalPD.setVisibility(View.INVISIBLE);
                        percentageSpinner.setEnabled(true);
                        btnDeparturePD.setEnabled(true);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), getResources().getText(R.string.save_error), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private class SynUpdateDeparture extends AsyncTask<Void, Void, String> {
        String latString, longString, timeString, percentString;



        public SynUpdateDeparture(String latString, String longString, String timeString,String percentString) {
            this.latString = latString;
            this.longString = longString;
            this.timeString = timeString;
            this.percentString  = percentString;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("Driver_Name", loginStrings[7])
                        .add("PlanDtl2_ID", planDtl2IdString)
                        .add("percent_load", percentString)
                        .add("lat", latString)
                        .add("lon", longString)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(MyConstant.urlUpdateDeparture).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("Tag", "e doInBack ==>" + e.toString() + "line::" + e.getStackTrace()[0].getLineNumber());
                return "";
            }

        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            Log.d("Tag", "onPostExecute:::---->Departure:::: " + s);
            if (s.equals("OK")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), R.string.save_success, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PlanDeliveryActivity.this, JobActivity.class);
                        intent.putExtra("Login", loginStrings);
                        intent.putExtra("planDtlId", planDtlIdString);
                        intent.putExtra("position", positionString);
                        intent.putExtra("planDate", dateString);
                        intent.putExtra("planId", planIdString);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
    }


}

