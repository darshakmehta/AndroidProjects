package com.uncc.hw07;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class UsersScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {
    private ArrayList<Post> postList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FirebaseAuth auth;
    private TextView userName;
    private final String TAG = "tag:UsersScreenActivity";
    private String userId = "";
    LinearLayoutManager layoutManager;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserPostRef = mRootRef.child("userPost");
    DatabaseReference mUser = mRootRef.child("user");
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_screen);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_icon_logo); //keep image size of 48*48 for app icon
        getSupportActionBar().setTitle("  My Social App");

        userName = (TextView)findViewById(R.id.userName);
        final TextView myPost = (TextView)findViewById(R.id.myPost);
        ImageView editProfile = (ImageView)findViewById(R.id.editProfile);
        final ImageView homeButton = (ImageView)findViewById(R.id.imageViewHome);

        auth = FirebaseAuth.getInstance();
        postList = new ArrayList<Post>();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        userId = getIntent().getStringExtra("userId");
        mUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child("firstName").getValue(String.class)+" "+dataSnapshot.child("lastName").getValue(String.class));
                if(!auth.getUid().equals(userId))
                {
                    myPost.setText(dataSnapshot.child("firstName").getValue(String.class)+"'s Posts");
                }
                         }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (!auth.getUid().equals(userId)) {
            editProfile.setVisibility(View.GONE);
        } else {
            homeButton.setImageResource(R.drawable.friends_list);
        }
        loadList();

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewUserScreen);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!auth.getUid().equals(userId)) {
                    startActivity(new Intent(UsersScreenActivity.this, HomeActivity.class));
                }else{
                    startActivity(new Intent(UsersScreenActivity.this,ManageFriendActivity.class));
                }
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersScreenActivity.this,EditProfileActivity.class));
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
            startActivity(new Intent(UsersScreenActivity.this,MainActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }
    void loadList(){
        mUserPostRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Post post = new Post();
                    post.setUser(ds.child("user").getValue(String.class));
                    post.setPost(ds.child("post").getValue(String.class));
                    post.setUserId(ds.child("userId").getValue(String.class));
                    post.setPostTime(ds.child("postTime").getValue(Date.class));
                    loadUserList(ds.child("userId").getValue(String.class),post);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    User loadUserList(String userId, final Post postOld) {
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
                postOld.setUser(ds.child("firstName").getValue(String.class)+" "+ds.child("lastName").getValue(String.class));
                postList.add(postOld);
                Collections.sort(postList, new Comparator<Post>() {
                    public int compare(Post o1, Post o2) {
                        return (-1)*o1.getPostTime().compareTo(o2.getPostTime());

                    }
                });
                layoutManager.scrollToPosition(0);
                mAdapter = new UsersScreenAdaptor(postList, UsersScreenActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user[0];
    }
    public void removeMessage(View view){
        String userId = view.getTag().toString();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
