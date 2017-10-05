package mad.com.eventhandler;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "OK Button Clicked");
            }
        });
        findViewById(R.id.buttonCancel).setOnClickListener(this);
        findViewById(R.id.buttonAnotherCancel).setOnClickListener(this);
    }

    public void otherButtonClicked(View view){ //Does not required to Implement or declare anonymous class
        Log.d("demo", "Another Button Clicked");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonCancel)
            Log.d("demo", "Cancel Button Clicked");
        else if(v.getId() == R.id.buttonAnotherCancel)
            Log.d("demo", "Another Cancel Button Clicked");
    }
}
