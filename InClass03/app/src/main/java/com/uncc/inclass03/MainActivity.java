package com.uncc.inclass03;

/*
Assignment InClass 03.
MainActivity.java
Gaurav Pareek
Darshak  Mehta
 */
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    public static final String VALUE_KEY = "value";
    final static String PROFILE_KEY = "PROFILE_KEY";
    String department = "";
    int image = R.drawable.select_avatar;;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView moodText =(TextView) findViewById(R.id.moodText);
                ImageView moodImage = (ImageView)findViewById(R.id.imageViewMood);
                if(progress == 0)
                {
                    moodText.setText("Angry");
                    moodImage.setImageDrawable(getResources().getDrawable(R.drawable.angry));
                }else if(progress == 1)
                {
                    moodText.setText("Sad");
                    moodImage.setImageDrawable(getResources().getDrawable(R.drawable.sad));

                }else if(progress == 2)
                {
                    moodText.setText("Happy");
                    moodImage.setImageDrawable(getResources().getDrawable(R.drawable.happy));


                }else if(progress == 3)
                {
                    moodText.setText("Awesome");
                    moodImage.setImageDrawable(getResources().getDrawable(R.drawable.awesome));


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void onClickSubmit(View view) {
        TextView moodText =(TextView) findViewById(R.id.moodText);
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        ImageView profileImage = (ImageView)findViewById(R.id.avatar);
        int validate = 1;

        if(nameText.getText().toString() == null  || "".equals(nameText.getText().toString())){
            Toast.makeText(this,"Name cannot be empty", Toast.LENGTH_SHORT).show();
            validate = 0;
        }
        if(isValidEmail(email.getText().toString())) {
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


            Profile profile = new Profile(nameText.getText().toString(), email.getText().toString(), department.toString(), moodText.getText().toString(), image);
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);  //for explicit intent
            intent.putExtra(PROFILE_KEY, profile);
            startActivity(intent);
        }
    }

    public void loadProfileImage(View view)
    {
        Intent intent = new Intent(MainActivity.this,DisplayActivity.class);  //for explicit intent
        startActivityForResult(intent,REQ_CODE);

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
}
