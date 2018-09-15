package com.uncc.hw07;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ArrayList<Post> postList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FirebaseAuth auth;
    private final String TAG = "tag:HomeActivity";
    private String postTime = "";
    LinearLayoutManager layoutManager;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserPostRef = mRootRef.child("userPost");
    DatabaseReference mUserRef = mRootRef.child("userFriend");
    DatabaseReference mUser = mRootRef.child("user");
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_icon_logo); //keep image size of 48*48 for app icon
        getSupportActionBar().setTitle("  My Social App");

        TextView userName = (TextView)findViewById(R.id.userName);
        Button postButton = (Button)findViewById(R.id.imageViewWritePost);
        ImageView imageViewFriends = (ImageView)findViewById(R.id.imageViewFriends);
        final EditText postMessage = (EditText)findViewById(R.id.editTextWritePost);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        auth = FirebaseAuth.getInstance();
        postList = new ArrayList<Post>();
        loadList(auth.getCurrentUser().getUid(),auth.getCurrentUser().getDisplayName());
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewHome);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        loadFriendList();
        userName.setText(auth.getCurrentUser().getDisplayName());

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,EditProfileActivity.class));
                finish();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(postMessage.getText().toString().length()>200) && !"".equalsIgnoreCase(postMessage.getText().toString())){
               writePost(auth.getCurrentUser().getUid(),auth.getCurrentUser().getDisplayName(),postMessage.getText().toString(),new Date());
                postMessage.setText("");
                }
                else{
                    if("".equalsIgnoreCase(postMessage.getText().toString())){
                        Toast.makeText(HomeActivity.this, "Post can't be blank", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HomeActivity.this, "Post has a character limit of 200", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imageViewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ManageFriendActivity.class));
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            signOut();
            auth.signOut();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void loadFriendList(){
        mUserRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    AddFriend user = new AddFriend();
                    if("FF".equalsIgnoreCase(ds.child("status").getValue(String.class))) {
                        user.setUserFriendId(ds.child("userFriendId").getValue(String.class));
                        loadUserList(ds.child("userFriendId").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    User loadUserList(String userId) {
        final ArrayList<String> userList = new ArrayList<String>();
        final User[] user = new User[1];
        mUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                //userList.add(ds.getKey());
                user[0] = new User();
                user[0].setFirstName(ds.child("firstName").getValue(String.class));
                user[0].setLastName(ds.child("lastName").getValue(String.class));
                user[0].setDob(ds.child("dob").getValue(String.class));
                user[0].setEmail(ds.child("email").getValue(String.class));
                user[0].setUserId(ds.getKey());
                loadList(ds.getKey(),ds.child("firstName").getValue(String.class)+" "+ds.child("lastName").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user[0];
    }
    void loadList(String userId, final String userName){
               mUserPostRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // String userName = "";
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Post post = new Post();
                    post.setUser(userName);
                    post.setPost(ds.child("post").getValue(String.class));
                    post.setUserId(ds.child("userId").getValue(String.class));
                    post.setPostTime(ds.child("postTime").getValue(Date.class));
                    postList.add(post);
                }
                Collections.sort(postList, new Comparator<Post>() {
                    public int compare(Post o1, Post o2) {
                        return (-1)*o1.getPostTime().compareTo(o2.getPostTime());
                    }
                });
                layoutManager.scrollToPosition(0);
                mAdapter = new HomeScreenAdapter(postList, HomeActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }

    private void writePost(String userId,String userName,String postMessage,Date currentDate) {
        Post postObject = new Post(userName,postMessage,currentDate,userId);
        mUserPostRef.child(userId).child(currentDate+"").setValue(postObject);
        postList.add(postObject);
        Collections.sort(postList, new Comparator<Post>() {
            public int compare(Post o1, Post o2) {
                return (-1)*o1.getPostTime().compareTo(o2.getPostTime());
            }
        });
        mAdapter = new HomeScreenAdapter(postList, HomeActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void visitMemberWall(View view){
        String userId = view.getTag().toString();
         Intent i = new Intent(HomeActivity.this,UsersScreenActivity.class);
                i.putExtra("userId",userId);
                startActivity(i);
        finish();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
