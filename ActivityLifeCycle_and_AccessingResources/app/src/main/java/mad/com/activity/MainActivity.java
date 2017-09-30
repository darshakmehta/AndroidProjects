package mad.com.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("demo","inside onCreate!");
        Button b = (Button) findViewById(R.id.button1);
        String myMsg = getResources().getString(R.string.Button_Text);
        String []questions = getResources().getStringArray(R.array.Questions);
        Log.d("demo",myMsg);
        for(String member: questions)
        Log.d("demo",member);
        ImageView iv = new ImageView(this);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.darshak));
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.linear);
        linearLayout.addView(iv);
    }
    @Override
    protected void onStart() {
        Log.d("demo","inside onStart!");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("demo","inside onRestart!");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("demo","inside onResume!");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("demo","inside onPause!");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("demo","inside onStop!");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("demo","inside onDestroy!");
        super.onDestroy();
    }
}
