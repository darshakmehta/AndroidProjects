package com.uncc.inclass09;
/*
* MainActivity.java
* InClass09
* Gaurav Pareek
* Darshak Mehta
* */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String token = "";
    public static final String MyFAVORITES = "MyToken" ;
    public static final String FAVORITES = "Movie_Favorite";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText email = (EditText)findViewById(R.id.editTextEmail);
        final EditText password = (EditText)findViewById(R.id.editTextPassword);
        Button signUp = (Button) findViewById(R.id.buttonSignUp);
        Button login = (Button) findViewById(R.id.buttonLogin);
        sharedpreferences = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setText("");
                password.setText("");
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!"".equalsIgnoreCase(email.getText().toString()) && !"".equalsIgnoreCase(password.getText().toString())) {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", email.getText().toString())
                            .add("password", password.getText().toString())
                            .build();
                    Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/login").post(formBody).build();


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                final LoginResponse loginResponse = SignUpLoginUtil.MusicJSONParser.parseTracks(response.body().string());
                                if ("ok".equalsIgnoreCase(loginResponse.getStatus())) {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("token", loginResponse.getToken());
                                    editor.putString("user_id", loginResponse.getUser_id());
                                    editor.putString("user_name", loginResponse.getUser_fname()+" "+loginResponse.getUser_lname());
                                    editor.commit();
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this,"Welcome " + loginResponse.getUser_fname()+" "+loginResponse.getUser_lname(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Intent i = new Intent(MainActivity.this, MessageThreadsActivity.class);
                                    startActivity(i);
                                    finishAffinity();

                                } else {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this,"Email Id and Password cannot be empty",Toast.LENGTH_SHORT).show();

                }
                }

        });
    }

}
