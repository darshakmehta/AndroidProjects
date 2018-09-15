package com.uncc.hw02;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class DisplayContactActivity extends AppCompatActivity {

    private ImageView contactImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        contactImage = (ImageView) findViewById(R.id.contactPhoto);
        TextView fName = (TextView) findViewById(R.id.contactFirst);
        TextView lName = (TextView) findViewById(R.id.contactLast);
        TextView company = (TextView) findViewById(R.id.company);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        TextView url = (TextView) findViewById(R.id.url);
        TextView address = (TextView) findViewById(R.id.address);
        TextView birthday = (TextView) findViewById(R.id.birthday);
        TextView nickName = (TextView) findViewById(R.id.nickName);
        TextView facebookProfileURL = (TextView) findViewById(R.id.facebookProfileURL);
        TextView twitterProfileURL = (TextView) findViewById(R.id.twitterProfileURL);
        TextView skype = (TextView) findViewById(R.id.skype);
        TextView youtubeChannel = (TextView) findViewById(R.id.youtubeChannel);

        if(getIntent().getExtras()!=null) {
            if (getIntent().getExtras().getSerializable(ContactListActivity.DISPLAY_KEY) != null) {

                final Contact contact = (Contact) getIntent().getExtras().getSerializable(ContactListActivity.DISPLAY_KEY);


                if(contact.getByteArray()[0]!=0){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getByteArray(), 0, contact.getByteArray().length);
                    contactImage.setImageBitmap(bitmap);                }
                else
                {
                    contactImage.setImageResource(R.drawable.add_photo1);
                }
                /*Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getByteArray(), 0, contact.getByteArray().length);

                contactImage.setImageBitmap(bitmap);*/
                fName.setText("First Name: " + contact.getFirstName());
                lName.setText("Last Name: " + contact.getLastName());
                company.setText("Company: " + contact.getCompany());
                phone.setText("Phone: " + contact.getPhone());
                email.setText("Email ID: " + contact.getEmail());
                url.setText("URL: " + contact.getUrl());
                address.setText("Address: " + contact.getAddress());
                birthday.setText("Birthday: " + contact.getBirthday());
                nickName.setText("Nickname: " + contact.getNickName());
                facebookProfileURL.setText("Facebook Profile URL: " + contact.getFacebookProfileURL());
                twitterProfileURL.setText("Twitter Profile URL: " + contact.getTwitterProfileURL());
                skype.setText("Skype: " + contact.getSkype());
                youtubeChannel.setText("Youtube Channel: " + contact.getYoutube());

                url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://" +contact.getUrl()));
                        startActivity(intent);
                    }
                });

                facebookProfileURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://" +contact.getFacebookProfileURL()));
                        startActivity(intent);
                    }
                });

                skype.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://" +contact.getSkype()));
                        startActivity(intent);
                    }
                });

                twitterProfileURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://" +contact.getTwitterProfileURL()));
                        startActivity(intent);
                    }
                });

                youtubeChannel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://" +contact.getYoutube()));
                        startActivity(intent);
                    }
                });
            }
        }

    }
}
