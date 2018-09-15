package com.uncc.inclass09;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.uncc.inclass09.SignUpActivity.MyFAVORITES;

public class ChatroomActivity extends AppCompatActivity {
    ArrayList<ChatRoomThreadResponse> responseList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager layoutManager;
    SharedPreferences sharedpreferences;
    public static final String MyFAVORITES = "MyToken" ;
    String token = "";
    String userId = "";
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        ImageView home= (ImageView) findViewById(R.id.imageViewHome);
        sharedpreferences = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewChatroom);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        id = getIntent().getStringExtra("chatRoomObject");
        String title =  getIntent().getStringExtra("title");
        TextView titleTextView = (TextView)findViewById(R.id.textViewChatroomThreadName);
        titleTextView.setText(title);
        token = "BEARER "+sharedpreferences.getString("token",null);
        userId = sharedpreferences.getString("user_id",null);
        new GetAsyncTask().execute();
        final EditText editTextmessage= (EditText) findViewById(R.id.editTextMessage);
        Button button= (Button) findViewById(R.id.imageViewSendMessage);

        home.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                        finish();
                                      }
                                  });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("message", editTextmessage.getText().toString())
                        .add("thread_id",id)
                        .build();
                editTextmessage.setText("");
                Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/message/add").addHeader("Authorization", token).post(formBody).build();
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
        });
    }

    public void removemessage(View view) {
        LinearLayout parentrow = (LinearLayout) view.getParent();
        ImageView favImage = (ImageView) parentrow.getChildAt(1);
        int position = (int) favImage.getTag();
        ChatRoomThreadResponse track = responseList.get(position);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/message/delete/" + responseList.get(position).getId()).addHeader("Authorization", token).build();
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

    public void updateData(ArrayList<ChatRoomThreadResponse> responseList) {
        Log.d("responseData",responseList.size()+"");
        for(int i=0;i<responseList.size();i++){
            if(responseList.get(i).getUser_id().matches(userId)){
                responseList.get(i).setImage("0");
            }
            else{
                responseList.get(i).setImage("4");
            }
        }
        Collections.reverse(responseList);
        layoutManager.scrollToPosition(responseList.size() - 1);
        mAdapter = new ChatRoomMessageAdapter(responseList, ChatroomActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class GetAsyncTask extends AsyncTask<String, Void, String> {
        BufferedReader reader = null;
        @Override
        protected String doInBackground(String... params) {
            String url = "http://ec2-54-164-74-55.compute-1.amazonaws.com/api/messages/"+id;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization",token)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();  //its synchronous task
                responseList  =  ChatRoomThreadUtil.MusicJSONParser.parseTracks(response.body().string());
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
            updateData(responseList);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
