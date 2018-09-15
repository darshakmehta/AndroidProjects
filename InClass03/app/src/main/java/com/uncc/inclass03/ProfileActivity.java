package com.uncc.inclass03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(getIntent().getExtras()!=null) {
            Profile profile = (Profile)getIntent().getExtras().getSerializable(MainActivity.PROFILE_KEY);

            TextView name =(TextView) findViewById(R.id.profileNameValue);
            TextView email = (TextView) findViewById(R.id.profileEmailValue);
            TextView department = (TextView) findViewById(R.id.profileDepartmentValue);
            TextView mood = (TextView) findViewById(R.id.profileMoodValue);
            ImageView profileImage = (ImageView)findViewById(R.id.profileImageID);
            ImageView moodImage = (ImageView)findViewById(R.id.imageViewMoodImage);

            name.setText(profile.getName());
            email.setText(profile.getEmail());
            department.setText(profile.getDepartment());
            mood.setText("I am "+profile.getMood()+"!");
            Log.d("demo profile getImageId",profile.getImageId()+"");
            profileImage.setImageDrawable(getResources().getDrawable(profile.getImageId()));

            if("Angry".equals(profile.getMood()))
            {
                moodImage.setImageDrawable(getResources().getDrawable(R.drawable.angry));

            }else if("Sad".equals(profile.getMood()))
            {
                moodImage.setImageDrawable(getResources().getDrawable(R.drawable.sad));

            }else if("Happy".equals(profile.getMood()))
            {
                moodImage.setImageDrawable(getResources().getDrawable(R.drawable.happy));

            }else if("Awesome".equals(profile.getMood()))
            {
                moodImage.setImageDrawable(getResources().getDrawable(R.drawable.awesome));

            }

        }
    }
}
