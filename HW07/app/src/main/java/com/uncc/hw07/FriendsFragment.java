package com.uncc.hw07;

/**
 * Created by gaurav on 11/16/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {
    private ArrayList<AddFriend> friendsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FirebaseAuth auth;
    private final String TAG = "tag:FriendsFragment";
    private String postTime = "";
    LinearLayoutManager layoutManager;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("userFriend");
    DatabaseReference mUser = mRootRef.child("user");

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment,container,false);
        friendsList = new ArrayList<AddFriend>();
        auth = FirebaseAuth.getInstance();
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewFriends);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        loadList();
        return view;
    }

    void loadList(){
        mUserRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    AddFriend user = new AddFriend();
                    if("FF".equalsIgnoreCase(ds.child("status").getValue(String.class))) {
                        user.setStatus(ds.child("status").getValue(String.class));
                        user.setFriendName(ds.child("friendName").getValue(String.class));
                        user.setUserFriendId(ds.child("userFriendId").getValue(String.class));
                        user.setUserId(ds.child("userId").getValue(String.class));
                        loadUserList(ds.child("userFriendId").getValue(String.class),user);

                    }
                }

                /*layoutManager.scrollToPosition(0);
                mAdapter = new FriendsAdapter(friendsList, getActivity());
                mRecyclerView.setAdapter(mAdapter);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    User loadUserList(String userId, final AddFriend userOld) {
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
                userOld.setFriendName(ds.child("firstName").getValue(String.class)+" "+ds.child("lastName").getValue(String.class));
                friendsList.add(userOld);
                layoutManager.scrollToPosition(0);
                mAdapter = new FriendsAdapter(friendsList, getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user[0];
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/
}
