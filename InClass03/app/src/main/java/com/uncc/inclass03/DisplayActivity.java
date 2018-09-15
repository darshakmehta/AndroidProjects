package com.uncc.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


    }

    public void onClickImage(View view)
    {
        Log.d("demo","imageClicked");
        int value = 0;
        if(view.getId() == R.id.imageAv1)
        {
            Log.d("demo","imageAv1");
            value = R.drawable.avatar_f_1;
        }else if(view.getId() == R.id.imageAv2)
        {
            value = R.drawable.avatar_m_1;
        }else if(view.getId() == R.id.imageAv3)
        {
            value = R.drawable.avatar_f_2;
        }else if(view.getId() == R.id.imageAv4)
        {
            value = R.drawable.avatar_m_2;
        }else if(view.getId() == R.id.imageAv5)
        {
            value = R.drawable.avatar_f_3;
        }else if(view.getId() == R.id.imageAv6)
        {
            value = R.drawable.avatar_m_3;
        }
        else
        {
            value = R.drawable.select_avatar;
        }
        Intent intent = new Intent();
        intent.putExtra(MainActivity.VALUE_KEY,value);
        setResult(RESULT_OK,intent);
        finish();
    }
}
