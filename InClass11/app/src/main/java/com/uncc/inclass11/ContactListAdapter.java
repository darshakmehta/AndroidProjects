package com.uncc.inclass11;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private FirebaseStorage storageUpload = FirebaseStorage.getInstance();
    private StorageReference storage = FirebaseStorage.getInstance().getReference();
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
                .inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(mContact.get(position).getFirst_name()+" "+mContact.get(position).getLast_name());
        holder.email.setText(mContact.get(position).getEmail());
        holder.phone.setText(mContact.get(position).getPhone());
        if(mContact.get(position).getContactImage()!=null && !"".equalsIgnoreCase(mContact.get(position).getContactImage()))
        Picasso.with(mContext).load(mContact.get(position).getContactImage()).into(holder.contactImage);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Do you really want to delete?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mRootRef.child("contacts").child(auth.getCurrentUser().getUid()).child(mContact.get(position).getPhone()).setValue(null);
                                        mContact.remove(position);
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent i = new Intent(mContext,EditContact.class);
                                                 i.putExtra("phone",mContact.get(position).getPhone());
                                                 mContext.startActivity(i);
                                             }
                                         });

    }

    @Override
    public int getItemCount() {
        return mContact.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contactImage,delete,edit;
        TextView name, phone, email;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            contactImage = (ImageView) itemView.findViewById(R.id.imageButtonContact);
            delete = (ImageView) itemView.findViewById(R.id.imageViewDelete);

            edit = (ImageView) itemView.findViewById(R.id.imageViewEdit);

            name = (TextView) itemView.findViewById(R.id.textViewName);
            phone = (TextView) itemView.findViewById(R.id.textViewPhone);
            email = (TextView) itemView.findViewById(R.id.textViewEmail);

        }
    }
}