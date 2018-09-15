package com.uncc.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewContactActivity extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    public static final String VALUE_KEY = "value";
    final static String PROFILE_KEY = "PROFILE_KEY";
    int image = R.drawable.select_avatar;
    private FirebaseAuth auth;
    String department = "";
    RadioGroup rg;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("contacts");
    ArrayList<Contact> responseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
        auth = FirebaseAuth.getInstance();
        responseList = new ArrayList<Contact>();
    }

    public void loadProfileImage(View view)
    {
        Intent intent = new Intent(CreateNewContactActivity.this,DisplayActivity.class);  //for explicit intent
        startActivityForResult(intent,REQ_CODE);
    }

    public void onClickSubmit(View view) {
        TextView phone =(TextView) findViewById(R.id.phone);
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        ImageView profileImage = (ImageView)findViewById(R.id.avatar);
        int validate = 1;

        if(nameText.getText().toString() == null  || "".equals(nameText.getText().toString())){
            Toast.makeText(this,"Name cannot be empty", Toast.LENGTH_SHORT).show();
            validate = 0;
        }
        if(phone.getText().toString() == null  || "".equals(phone.getText().toString())){
            Toast.makeText(this,"Phone cannot be empty", Toast.LENGTH_SHORT).show();
            validate = 0;
        }
        if(isValidEmail(email.getText().toString())) {
            if(validate==1)
                validate = 1;
        }else {
            Toast.makeText(this,"Email id is not valid", Toast.LENGTH_SHORT).show();
            validate = 0;
        }


        if(validate == 1) {
            rg = (RadioGroup) findViewById(R.id.radioGroupDepartment);
            Log.d("demo", "checked radio button is ");
            if (rg.getCheckedRadioButtonId() == R.id.radioButtonSIS) {
                Log.d("Department", "SIS");
                department = "SIS";
            } else if (rg.getCheckedRadioButtonId() == R.id.radioButtonCS) {
                Log.d("Department", "CS");
                department = "CS";
            } else if (rg.getCheckedRadioButtonId() == R.id.radioButtonBio) {
                Log.d("Department", "BIO");
                department = "BIO";
            }

            final Contact contact = new Contact(nameText.getText().toString(),email.getText().toString(),phone.getText().toString(),department.toString(),image);
            Log.d("contacat",contact.toString());
            writeNewContact(auth.getUid(),nameText.getText().toString(),email.getText().toString(),phone.getText().toString(),department.toString(),image);


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
                        Intent intent = new Intent();
                        intent.putExtra(ContactListActivity.VALUE_KEY,responseList);
                        startActivity(new Intent(CreateNewContactActivity.this, ContactListActivity.class));

                       // startActivity(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView personImage = (ImageView)findViewById(R.id.avatar);

        if(requestCode == REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                int value = data.getExtras().getInt(VALUE_KEY);
                Log.d("demo","Value received is :"+value);
                personImage.setImageDrawable(getResources().getDrawable(value));
                image = value;
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Log.d("demo","No Value received");
            }
        }
    }
    public boolean isValidEmail(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void writeNewContact(String userId,String name, String email, String phone, String dept, int imageResId) {
        Contact contact = new Contact(name,  email,  phone,  dept,  imageResId);
        mUserRef.child(userId).child(phone).setValue(contact);
    }
}
