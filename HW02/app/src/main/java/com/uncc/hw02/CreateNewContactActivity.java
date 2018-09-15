package com.uncc.hw02;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewContactActivity extends AppCompatActivity {
    private ImageView contactPhoto;
    private int profileFlag = 0;
    private HashMap<String, Contact> dataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int day = 0, month = 0, year = 0;
        final String[] birthday = {""};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
        final EditText birthdayProfile = (EditText) findViewById(R.id.birthday);

        if(getIntent().getExtras().getSerializable(MainActivity.CREATE_NEW_CONTACT_KEY)!=null) {
            dataMap = (HashMap)getIntent().getExtras().getSerializable(MainActivity.CREATE_NEW_CONTACT_KEY);

        }
        contactPhoto = (ImageView) findViewById(R.id.contactPhoto);
        contactPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int m = monthOfYear + 1;
                        birthday[0] = m + "/" + dayOfMonth + "/" + year;
                        birthdayProfile.setText(birthday[0]);
                    }

                }, year, month, day);

        birthdayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog.updateDate(mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
    }

    public void saveContact(View v) {
        int validateFlag = 0;
        contactPhoto = (ImageView) findViewById(R.id.contactPhoto);
        byte[] byteArray = new byte[1];
        Log.d("saveContact", "reached save contact");
        EditText fName = (EditText) findViewById(R.id.contactFirst);
        EditText lName = (EditText) findViewById(R.id.contactLast);
        EditText company = (EditText) findViewById(R.id.company);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText email = (EditText) findViewById(R.id.email);
        EditText url = (EditText) findViewById(R.id.url);
        EditText address = (EditText) findViewById(R.id.address);
        EditText birthday = (EditText) findViewById(R.id.birthday);
        EditText nickName = (EditText) findViewById(R.id.nickName);
        EditText facebookProfileURL = (EditText) findViewById(R.id.facebookProfileURL);
        EditText twitterProfileURL = (EditText) findViewById(R.id.twitterProfileURL);
        EditText skype = (EditText) findViewById(R.id.skype);
        EditText youtubeChannel = (EditText) findViewById(R.id.youtubeChannel);
        String message = "";
        if (fName.getText().toString().matches("")) {
            message = message + "(Firstname)";
            validateFlag = 1;
        }

        if (lName.getText().toString().matches("")) {
            message = message + "  (Lastname)";
            validateFlag = 1;
        }

        if (!phone.getText().toString().matches("[0-9+]+")) {
            message = message + "  (Phoneno)";
            validateFlag = 1;
        }

        if (!email.getText().toString().matches("")) {
            if (isValidEmail(email.getText().toString())) {
            } else {
                validateFlag = 1;
                message = message + "  (Email)";
            }
        }

        if (validateFlag == 0) {
            if (profileFlag == 0) {
            //    contactPhoto.setImageResource(R.drawable.add_photo1);
            }
            else {
                Bitmap bm = ((BitmapDrawable) contactPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            }

            Contact contact = new Contact(fName.getText().toString(), lName.getText().toString(), company.getText().toString(), phone.getText().toString(), email.getText().toString(), url.getText().toString(),
                    address.getText().toString(), birthday.getText().toString(), nickName.getText().toString(), facebookProfileURL.getText().toString()
                    , twitterProfileURL.getText().toString(), skype.getText().toString(), youtubeChannel.getText().toString(), byteArray);

            if(dataMap == null || !dataMap.containsKey(phone.getText().toString())) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, contact);
                setResult(RESULT_OK, intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "This PhoneNo already exist in other contact!", Toast.LENGTH_SHORT).show();
            }
        } else {
            message = message + " not valid";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            contactPhoto.setImageBitmap(bm);
            profileFlag = 1;
        }
    }

    public boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

