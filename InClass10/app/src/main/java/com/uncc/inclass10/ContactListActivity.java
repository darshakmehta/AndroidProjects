package com.uncc.inclass10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactListActivity extends Activity {

    Button newContact, logout;
    private FirebaseAuth auth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<Contact> responseList;
    public static final int REQ_CODE = 100;
    public static final String VALUE_KEY = "value";
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("contacts");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactlistactivity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.contactListRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        responseList = new ArrayList<Contact>();
        Button btnLogout = (Button) findViewById(R.id.buttonLogout);
        auth = FirebaseAuth.getInstance();


        mRootRef.child("contacts").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Contact contact = new Contact();
                    contact.setName(ds.child("name").getValue(String.class));
                    contact.setDept(ds.child("dept").getValue(String.class));
                    contact.setPhone(ds.child("phone").getValue(String.class));
                    int image = ds.child("imageResId").getValue(Integer.class);
                    contact.setImageResId(image);
                    contact.setEmail(ds.child("email").getValue(String.class));
                    responseList.add(contact);
                }
                mAdapter = new ContactListAdapter(responseList, ContactListActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        findViewById(R.id.buttonCreateNewContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this,CreateNewContactActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                FirebaseUser user = auth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                    startActivityForResult(intent,REQ_CODE);
                    finishAffinity();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                responseList  =(ArrayList<Contact>) data.getSerializableExtra(VALUE_KEY);
                mAdapter = new ContactListAdapter(responseList, ContactListActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Log.d("demo","No Value received");
            }
        }
    }
}
