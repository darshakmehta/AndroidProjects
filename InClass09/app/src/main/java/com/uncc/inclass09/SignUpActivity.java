package com.uncc.inclass09;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    public static final String MyFAVORITES = "MyToken" ;
    public static final String FAVORITES = "Movie_Favorite";
    SharedPreferences sharedpreferences;
    List<LoginResponse> responseList;
    Map<String,String> favMovieMap = new HashMap<String, String>();;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText firstName = (EditText) findViewById(R.id.editTextFirstName);
        final EditText lastName = (EditText) findViewById(R.id.editTextLastName);
        final EditText email = (EditText) findViewById(R.id.editTextEmail);
        final EditText password = (EditText) findViewById(R.id.editTextPassword);
        final EditText repeatPassword = (EditText) findViewById(R.id.editTextRepeatPassword);
        Button signUp = (Button) findViewById(R.id.buttonSignUpPage);
        Button cancel = (Button) findViewById(R.id.buttonCancel);
        sharedpreferences = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean validate = validate(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), repeatPassword.getText().toString());
                    if (validate){
                    if(password.getText().toString().matches(repeatPassword.getText().toString())) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("fname", firstName.getText().toString()).add("lname", lastName.getText().toString())
                                .add("email", email.getText().toString())
                                .add("password", password.getText().toString())
                                .build();
                        Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/signup").post(formBody).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    final LoginResponse loginResponse = SignUpLoginUtil.MusicJSONParser.parseTracks(response.body().string());
                                    if ("ok".equalsIgnoreCase(loginResponse.getStatus())) {
                                        // favMovieMap.put(loginResponse.getUser_email(),loginResponse.getToken());
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("token", loginResponse.getToken());
                                        editor.putString("user_id", loginResponse.getUser_id());
                                        editor.putString("user_name", loginResponse.getUser_fname() + " " + loginResponse.getUser_lname());
                                        editor.commit();
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SignUpActivity.this,"User has been created",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Intent i = new Intent(SignUpActivity.this, MessageThreadsActivity.class);
                                        //i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                                        i.putExtra("userEmail", loginResponse.getUser_email());
                                        startActivity(i);
                                        finishAffinity();
                                    } else {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {

                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(SignUpActivity.this,"password & repeatpassword are not matching",Toast.LENGTH_SHORT).show();
                    }

                }
                }
            });

    }

    public boolean validate(String firstName,String lastName,String email,String password,String repeatpassword){
        boolean flag = true;
        StringBuilder error= new StringBuilder();
        if("".equalsIgnoreCase(firstName)){
            error.append("firstName");
            flag = false;
        } if("".equalsIgnoreCase(lastName)){
            error.append(", lastName");
            flag = false;
        } if("".equalsIgnoreCase(email)){
            error.append(", email");
            flag = false;
        }
         if("".equalsIgnoreCase(password)){
            error.append(", password");
            flag = false;
        } if("".equalsIgnoreCase(repeatpassword)){
            error.append(", repeatpassword");
            flag = false;
        }
       if(!flag)
       Toast.makeText(SignUpActivity.this,error+" cannot be empty",Toast.LENGTH_SHORT).show();
       return flag;
    }
}
