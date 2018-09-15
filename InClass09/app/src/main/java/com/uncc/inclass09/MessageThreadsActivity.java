package com.uncc.inclass09;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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

import static com.uncc.inclass09.SignUpActivity.MyFAVORITES;

public class MessageThreadsActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager layoutManager;
    public static final String FAVORITES = "Movie_Favorite";
    String token = "";
    Map<String,String> favMovieMap = new HashMap<String, String>();
    ArrayList<MessageThreadResponse> responseList;
    String userId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);
        final EditText userName = (EditText)findViewById(R.id.editTextEmail);
        final EditText editAddMessage = (EditText)findViewById(R.id.editTextNewThread);
        TextView textViewUserName = (TextView)findViewById(R.id.textViewUserName);
        ImageView addImageView = (ImageView)findViewById(R.id.imageViewAdd);
        ImageView logout = (ImageView)findViewById(R.id.imageViewLogout);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewThread);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        sharedpreferences = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                Intent intent = new Intent(MessageThreadsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        token = "BEARER "+sharedpreferences.getString("token",null);
        userId = sharedpreferences.getString("user_id",null);
        String user_name = sharedpreferences.getString("user_name",null);
        textViewUserName.setText(user_name);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editAddMessage.getText().toString().isEmpty()){
                    Toast.makeText(MessageThreadsActivity.this,"Thread name cannot be empty",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("addMessage",token);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("title", editAddMessage.getText().toString())
                            .build();
                    editAddMessage.setText("");
                    Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread/add").addHeader("Authorization", token).post(formBody).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            try {
                                MessageThreadResponse thread = new DeleteMessageUtil.RecipeJSONParser().parseRecipes(response.body().string());
                                Log.d("demo2", thread.toString());
                                responseList.add(thread);
                                new GetAsyncTask().execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        new GetAsyncTask().execute();


    }

    public void updateData(ArrayList<MessageThreadResponse> responseList) {
        mAdapter = new MessageThreadAdapter(responseList, MessageThreadsActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void selectRowForChatRoom(View view){
     int position = Integer.parseInt(view.getTag().toString());
     Log.d("yes","yes"+position);
     MessageThreadResponse messageThreadResponse = responseList.get(position);
     Intent i = new Intent(MessageThreadsActivity.this, ChatroomActivity.class);
     i.putExtra("chatRoomObject",messageThreadResponse.getId());
     i.putExtra("title",messageThreadResponse.getTitle());
     startActivity(i);
    }

    public class GetAsyncTask extends AsyncTask<String, Void, String> {
        BufferedReader reader = null;
        @Override
        protected String doInBackground(String... params) {
           // token = "BEARER eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk2ODY2OTYsImV4cCI6MTU0MTIyMjY5NiwianRpIjoiMkdKV2c3U0hKS3NiT2IyZVNkVzFWayIsInVzZXIiOjF9.rRTLX3i-kFYxAtbhUXrqQKDxXs0KoTEgV4iRX2q3p5M";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread")
                    .addHeader("Authorization",token)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();  //its synchronous task
                responseList  =  MessageThreadUtil.MusicJSONParser.parseTracks(response.body().string());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for(int i=0;i<responseList.size();i++){
                if(responseList.get(i).getUser_id().matches(userId)){
                    responseList.get(i).setImage("0");
                }
                else{
                    responseList.get(i).setImage("4");

                }
            }
            updateData(responseList);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public void remove(View view){
        LinearLayout parentrow = (LinearLayout) view.getParent();
        ImageView favImage = (ImageView) parentrow.getChildAt(1);
        int position = (int) favImage.getTag();
        MessageThreadResponse track = responseList.get(position);
        OkHttpClient client = new OkHttpClient();
     //   token = "BEARER eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MTAwNDE4NDYsImV4cCI6MTU0MTU3Nzg0NiwianRpIjoiMVFBSnBETzRoaVBuSWUyTElzMlpDQiIsInVzZXIiOjE5MX0._-BB3esxOaTs_ipWVpAoAukiczzO9wiOOyvOIQ4_3XU";
        Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread/delete/" + responseList.get(position).getId()).addHeader("Authorization", token).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                new GetAsyncTask().execute();
            }
        });

    }
}
