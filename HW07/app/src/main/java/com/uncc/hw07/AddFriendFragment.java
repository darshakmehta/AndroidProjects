package com.uncc.hw07;

/**
 * Created by gaurav on 11/16/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class AddFriendFragment extends Fragment {
    ViewPager view_pager;
    private ArrayList<User> friendsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FirebaseAuth auth;
    private final String TAG = "tag:AddFriendFragment";
    private String postTime = "";
    LinearLayoutManager layoutManager;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("user");
    DatabaseReference mUserFriendRef = mRootRef.child("userFriend");


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.addfriend_fragment,container,false);
        friendsList = new ArrayList<User>();
        auth = FirebaseAuth.getInstance();
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewAddFriend);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        loadList();
        return view;
    }

    void loadList(){
        final ArrayList<String> userList = new ArrayList<String>();
        mUserFriendRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    userList.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    boolean flag = true;
                    User user = new User();
                    user.setFirstName(ds.child("firstName").getValue(String.class));
                    user.setLastName(ds.child("lastName").getValue(String.class));
                    user.setUserId(ds.getKey());
                    if(!auth.getUid().equals(ds.getKey())) {
                     for(String friendKey:userList){
                         if(ds.getKey().equals(friendKey)){
                             flag = false;
                         }
                     }
                     if(flag)
                     friendsList.add(user);
                    }
                }

                layoutManager.scrollToPosition(0);
                mAdapter = new AddFriendAdapter(friendsList, getActivity());
                mRecyclerView.setAdapter(mAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
