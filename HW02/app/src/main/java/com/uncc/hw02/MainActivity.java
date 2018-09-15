package com.uncc.hw02;
/*
*
 Assignment Homework-02
 MainActivity.java
 Gaurav Pareek, Darshak Mehta
* */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    final static String CREATE_NEW_CONTACT_KEY = "CREATE_NEW_CONTACT_KEY";
    final static String DISPLAY_CONTACT_KEY = "DISPLAY_CONTACT_KEY";
    final static String EDIT_CONTACT_KEY = "EDIT_CONTACT_KEY";
    final static String DELETE_CONTACT_KEY = "DELETE_CONTACT_KEY";
    final static int REQ_CODE_DELETE_CONTACT = 1000;
    public static final String VALUE_KEY = "value";
    public static final int REQ_CODE = 100;
    public static final String EDIT_VALUE_KEY = "editValue";
    final static int REQ_CODE_EDIT_CONTACT = 1001;
    private HashMap<String, Contact> dataMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createNewContact(View view)
    {
        Intent intent = new Intent(MainActivity.this, CreateNewContactActivity.class);  //for explicit intent
        intent.putExtra(CREATE_NEW_CONTACT_KEY,dataMap);
        startActivityForResult(intent,REQ_CODE);
    }

    public void displayContact(View view)
    {
        Intent intent = new Intent(MainActivity.this, ContactListActivity.class);  //for explicit intent
        intent.putExtra(DISPLAY_CONTACT_KEY,dataMap);
        startActivity(intent);
    }

    public void deleteContact(View view)
    {
        Intent intent = new Intent(MainActivity.this, ContactListActivity.class);  //for explicit intent
        intent.putExtra(DELETE_CONTACT_KEY,dataMap);
        startActivityForResult(intent,REQ_CODE);
    }

    public void editContact(View view)
    {
        Intent intent = new Intent(MainActivity.this, ContactListActivity.class);  //for explicit intent
        intent.putExtra(EDIT_CONTACT_KEY,dataMap);
        startActivityForResult(intent,REQ_CODE);
    }

    public void finish(View view)
    {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Contact contact = (Contact) data.getExtras().getSerializable(VALUE_KEY);
                    dataMap.put(contact.getPhone(), contact);
                    Intent intent = new Intent(MainActivity.this, ContactListActivity.class);  //for explicit intent
                    intent.putExtra(DISPLAY_CONTACT_KEY,dataMap);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Contact Saved Successfully", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == REQ_CODE_DELETE_CONTACT)
            {
                Log.d("MainActivty","DELETE_CONTACT");
                String key =  data.getExtras().getString(DELETE_CONTACT_KEY);
                dataMap.remove(key);
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);  //for explicit intent
                intent.putExtra(DELETE_CONTACT_KEY,dataMap);
                startActivityForResult(intent,REQ_CODE);
            }
            else if(resultCode == REQ_CODE_EDIT_CONTACT)
            {
                Contact editContact = (Contact) data.getExtras().getSerializable(EDIT_VALUE_KEY);
                String phoneKey = data.getExtras().getString("phoneKey");
                dataMap.remove(phoneKey);
                dataMap.put(editContact.getPhone(),editContact);
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);  //for explicit intent
                intent.putExtra(DISPLAY_CONTACT_KEY,dataMap);
                startActivityForResult(intent,REQ_CODE);
                Toast.makeText(getApplicationContext(), "Contact Edited Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
