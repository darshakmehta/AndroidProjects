package com.uncc.inclass10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    ArrayList<Contact> mContact;
    Context mContext;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth;

    public ContactListAdapter(ArrayList<Contact> mContact, Context mContext) {
        this.mContact = mContact;
        this.mContext = mContext;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(mContact.get(position).getName());
        holder.email.setText(mContact.get(position).getEmail());
        holder.phone.setText(mContact.get(position).getPhone());
        holder.dept.setText(mContact.get(position).dept);
        holder.contactImage.setImageResource(mContact.get(position).getImageResId());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mRootRef.child("contacts").child(auth.getCurrentUser().getUid()).child(mContact.get(position).getPhone()).setValue(null);
                mContact.remove(position);
                notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContact.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView name, email, phone, dept;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            contactImage = (ImageView) itemView.findViewById(R.id.imageButtonContact);
            name = (TextView) itemView.findViewById(R.id.textViewName);
            email = (TextView) itemView.findViewById(R.id.textViewEmail);
            phone = (TextView) itemView.findViewById(R.id.textViewPhone);
            dept = (TextView) itemView.findViewById(R.id.textViewDept);


        }
    }
}