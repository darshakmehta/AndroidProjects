package mad.com.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    final static String NAME_KEY = "name";
    final static String RESULT_KEY = "result";
    final static int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Explicit Intent
        Button explicit = (Button) findViewById(R.id.explicit);
        explicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SecondActivity.class);
                intent.putExtra(NAME_KEY,"DARSHAK");
                startActivity(intent);
            }
        });

        //Implicit Intent
        Button implicit = (Button) findViewById(R.id.implicit);
        implicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("android.intent.action.VIEW"); //custom action
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("http://www.google.com"));
                startActivity(intent2);
            }
        });

        //Get Result
        Button result = (Button) findViewById(R.id.result);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SecondActivity.class);
                intent.putExtra(NAME_KEY,"DARSHAK");
                startActivityForResult(intent,REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            if(resultCode == RESULT_OK && data.getExtras().containsKey(RESULT_KEY)){
                Toast.makeText(this,data.getExtras().getString(RESULT_KEY),Toast.LENGTH_LONG).show();
            }else {
                //error has happened !!
            }
        }

    }
}
