package com.hitachi_tstv.mist.it.pod_val_mitsu;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imgMALogo)
    ImageView logoImageView;
    @BindView(R.id.edtMAUsername)
    EditText usernameEditText;
    @BindView(R.id.edtMAPassword)
    EditText passwordEditText;
    @BindView(R.id.btnMALogin)
    Button loginButton;

    String[] loginStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loginStrings = new String[3];

    }

    class SyncGetUserLogin extends AsyncTask<Void, Void, String> {
        Context context;
        String usernameString, passwordString;

        public SyncGetUserLogin(Context context, String usernameString, String passwordString) {
            this.context = context;
            this.usernameString = usernameString;
            this.passwordString = passwordString;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("username", usernameString)
                        .add("password", passwordString)
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.post(requestBody).url(MyConstant.urlGetUserLogin).build();
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

            Log.d("VAL-Tag-Main","S ==> " + s);

            if (s.equals("[]")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, getResources().getText(R.string.errLogin), Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Intent intent = new Intent(MainActivity.this, DateDeliveryActivity.class);
                intent.putExtra("Login",loginStrings);
                startActivity(intent);

            }
        }
    }

    @OnClick(R.id.btnMALogin)
    public void onViewClicked() {
        SyncGetUserLogin syncGetUserLogin = new SyncGetUserLogin(this,usernameEditText.getText().toString(),passwordEditText.getText().toString());
        syncGetUserLogin.execute();
    }
}