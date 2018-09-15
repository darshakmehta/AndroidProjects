package com.uncc.hw02;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import static com.uncc.hw02.R.drawable.add_photo;

public class DetailedEditActivity extends AppCompatActivity {
    private String phoneNoKey;
    private ImageView contactImage;
    private final int day = 0,month=0,year=0;
    private final String[] birthday = {""};
    private int profileFlag = 0;
    private HashMap<String, Contact> dataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
        if(getIntent().getExtras()!=null) {
            if(getIntent().getExtras().getSerializable("dataMap")!=null) {
                dataMap = (HashMap)getIntent().getExtras().getSerializable("dataMap");

            }
            if (getIntent().getExtras().getSerializable(ContactListActivity.EDIT_KEY) != null) {
                Contact contact = (Contact) getIntent().getExtras().getSerializable(ContactListActivity.EDIT_KEY);

                EditText first = (EditText)findViewById(R.id.contactFirst);
                EditText last = (EditText)findViewById(R.id.contactLast);
                EditText phone = (EditText)findViewById(R.id.phone);
                EditText company = (EditText)findViewById(R.id.company);
                EditText email = (EditText)findViewById(R.id.email);
                EditText url = (EditText)findViewById(R.id.url);
                EditText address = (EditText)findViewById(R.id.address);
                EditText birthday = (EditText)findViewById(R.id.birthday);
                EditText nickName = (EditText)findViewById(R.id.nickName);
                EditText facebookProfileURL = (EditText)findViewById(R.id.facebookProfileURL);
                EditText twitterProfileURL = (EditText)findViewById(R.id.twitterProfileURL);
                EditText skype = (EditText)findViewById(R.id.skype);
                EditText youtubeChannel = (EditText)findViewById(R.id.youtubeChannel);

                first.setText(contact.getFirstName());
                last.setText(contact.getLastName());
                phone.setText(contact.getPhone());
                phoneNoKey = contact.getPhone();
                company.setText(contact.getCompany());
                email.setText(contact.getEmail());
                url.setText(contact.getUrl());
                address.setText(contact.getAddress());
                birthday.setText(contact.getBirthday());
                nickName.setText(contact.getNickName());
                facebookProfileURL.setText(contact.getFacebookProfileURL());
                twitterProfileURL.setText(contact.getTwitterProfileURL());
                skype.setText(contact.getSkype());
                youtubeChannel.setText(contact.getYoutube());

                contactImage = (ImageView) findViewById(R.id.contactPhoto);
                if(contactImage!=null)
                {
                    profileFlag = 1;
                }

                if(contact.getByteArray()[0]!=0){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getByteArray(), 0, contact.getByteArray().length);
                    contactImage.setImageBitmap(bitmap);                }
                else
                {
                    contactImage.setImageResource(R.drawable.add_photo1);
                }
            }
        }


        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        final EditText birthdayProfile = (EditText) findViewById(R.id.birthday);
        final DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int m= monthOfYear+1;
                        birthday[0] =m +"/" +dayOfMonth +"/" +year;
                        birthdayProfile.setText(birthday[0]);
                    }

                },year,month,day);

        birthdayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog.updateDate(mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });
    }

    public void saveContact(View v)
    {   int validateFlag = 0;
        contactImage = (ImageView) findViewById(R.id.contactPhoto);
        byte[] byteArray = new byte[1];
        Log.d("saveContact","reached save contact");
        EditText fName = (EditText)findViewById(R.id.contactFirst);
        EditText lName = (EditText)findViewById(R.id.contactLast);
        EditText company = (EditText)findViewById(R.id.company);
        EditText phone = (EditText)findViewById(R.id.phone);
        EditText email = (EditText)findViewById(R.id.email);
        EditText url = (EditText)findViewById(R.id.url);
        EditText address = (EditText)findViewById(R.id.address);
        EditText birthday = (EditText)findViewById(R.id.birthday);
        EditText nickName = (EditText)findViewById(R.id.nickName);
        EditText facebookProfileURL = (EditText)findViewById(R.id.facebookProfileURL);
        EditText twitterProfileURL = (EditText)findViewById(R.id.twitterProfileURL);
        EditText skype = (EditText)findViewById(R.id.skype);
        EditText youtubeChannel = (EditText)findViewById(R.id.youtubeChannel);
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
        if(profileFlag == 0) {
            contactImage.setImageResource(R.drawable.add_photo1);
        }else {
            Bitmap bm = ((BitmapDrawable) contactImage.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }

        Contact contact = new Contact(fName.getText().toString(),lName.getText().toString(),company.getText().toString(),phone.getText().toString(),email.getText().toString(),url.getText().toString(),
                address.getText().toString(),birthday.getText().toString(),nickName.getText().toString(),facebookProfileURL.getText().toString()
                ,twitterProfileURL.getText().toString(),skype.getText().toString(),youtubeChannel.getText().toString(),byteArray);

            if(!phoneNoKey.equalsIgnoreCase(contact.getPhone())) {
                if (dataMap == null || !dataMap.containsKey(contact.getPhone())) {
                    Intent intent = new Intent(DetailedEditActivity.this, MainActivity.class);  //for explicit intent
                    intent.putExtra(MainActivity.EDIT_VALUE_KEY, contact);
                    intent.putExtra("phoneKey", phoneNoKey);
                    setResult(MainActivity.REQ_CODE_EDIT_CONTACT, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "This PhoneNo already exist in other contact!", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Intent intent = new Intent(DetailedEditActivity.this, MainActivity.class);  //for explicit intent
                intent.putExtra(MainActivity.EDIT_VALUE_KEY, contact);
                intent.putExtra("phoneKey", phoneNoKey);
                setResult(MainActivity.REQ_CODE_EDIT_CONTACT, intent);
                finish();
            }
    } else {
        message = message + " not valid";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        profileFlag = 1;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            contactImage.setImageBitmap(bm);
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
