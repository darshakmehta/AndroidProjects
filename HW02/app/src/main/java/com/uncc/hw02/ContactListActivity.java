package com.uncc.hw02;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContactListActivity extends AppCompatActivity {
    private String returnKey = "";
    final static String EDIT_KEY = "EDIT_KEY";
    final static String DISPLAY_KEY = "DISPLAY_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        if(getIntent().getExtras().getSerializable(MainActivity.DISPLAY_CONTACT_KEY)!=null) {
            HashMap<String,Contact> dataMap = (HashMap)getIntent().getExtras().getSerializable(MainActivity.DISPLAY_CONTACT_KEY);
            setList(dataMap);
            returnKey = MainActivity.DISPLAY_CONTACT_KEY;
        }
        else if(getIntent().getExtras().getSerializable(MainActivity.EDIT_CONTACT_KEY)!=null)
        {
            HashMap<String,Contact> dataMap = (HashMap)getIntent().getExtras().getSerializable(MainActivity.EDIT_CONTACT_KEY);
            setList(dataMap);
            returnKey = MainActivity.EDIT_CONTACT_KEY;
        }
        else if(getIntent().getExtras().getSerializable(MainActivity.DELETE_CONTACT_KEY)!=null)
        {
            HashMap<String,Contact> dataMap = (HashMap)getIntent().getExtras().getSerializable(MainActivity.DELETE_CONTACT_KEY);
            setList(dataMap);
            returnKey = MainActivity.DELETE_CONTACT_KEY;
        }
    }

    public void setList(final HashMap<String, Contact> dataMap)
    {
        ScrollView sv_main = (ScrollView) findViewById(R.id.svmain);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        Iterator it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            final Contact contact = (Contact) pair.getValue();
            final ListItemUI item = new ListItemUI(this);
            View itemView = (View)item;
            item.first.setText(""+contact.firstName);
            item.last.setText(""+contact.lastName);
            item.phone.setText(""+contact.phone);
            if(contact.getByteArray()[0]!=0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getByteArray(), 0, contact.getByteArray().length);
                item.contactImage.setImageBitmap(bitmap);
            }
            else
            {
                item.contactImage.setImageResource(R.drawable.add_photo1);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(returnKey.equalsIgnoreCase(MainActivity.DISPLAY_CONTACT_KEY))
                    {
                        Contact contactProfile = dataMap.get(item.phone.getText());
                        Intent intent = new Intent(ContactListActivity.this, DisplayContactActivity.class);  //for explicit intent
                        intent.putExtra(DISPLAY_KEY,contactProfile);
                        startActivity(intent);
                    }else if(returnKey.equalsIgnoreCase(MainActivity.EDIT_CONTACT_KEY))
                    {
                        Contact contactProfile = dataMap.get(item.phone.getText());
                        Intent intent = new Intent(ContactListActivity.this, DetailedEditActivity.class);  //for explicit intent
                        intent.putExtra(EDIT_KEY,contactProfile);
                        intent.putExtra("dataMap",dataMap);
                        startActivityForResult(intent,MainActivity.REQ_CODE_EDIT_CONTACT);
                    }else if(returnKey.equalsIgnoreCase(MainActivity.DELETE_CONTACT_KEY))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                        builder.setMessage("Do you really want to delete this?")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("ContactListActivity","Clicked OK");
                                    Intent intent = new Intent();
                                    intent.putExtra(MainActivity.DELETE_CONTACT_KEY,item.phone.getText());
                                    setResult(MainActivity.REQ_CODE_DELETE_CONTACT,intent);
                                    finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("ContactListActivity","Delete Contact Cancelled");
                                    }
                                });

                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });
            container.addView(itemView);
        }
        sv_main.addView(container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MainActivity.REQ_CODE_EDIT_CONTACT)
        {
             if(resultCode == MainActivity.REQ_CODE_EDIT_CONTACT)
            {
                Log.d("ContactListActivity","EDIT_CONTACT");
                Contact editContact = (Contact) data.getExtras().getSerializable(MainActivity.EDIT_VALUE_KEY);
                String phoneNoKey = data.getExtras().getString("phoneKey");
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EDIT_VALUE_KEY,editContact);
                intent.putExtra("phoneKey",phoneNoKey);
                setResult(MainActivity.REQ_CODE_EDIT_CONTACT,intent);
                finish();
            }
        }
    }
}
