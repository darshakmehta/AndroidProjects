package mad.com.fragmentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("demo","MainActivity onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo","MainActivity onCreate before inflating Layout");
        setContentView(R.layout.activity_main);
        Log.d("demo","MainActivity onCreate after inflating Layout");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("demo","MainActivity onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("demo","MainActivity onResume");
    }
}
