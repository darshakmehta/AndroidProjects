package mad.com.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class SecondActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
        Intent intent = getIntent();
        String value;
        if(intent.getExtras()!=null) {
            if(intent.getExtras().containsKey(MainActivity.NAME_KEY)){
                value = intent.getStringExtra(MainActivity.NAME_KEY);
                Toast.makeText(this,value,Toast.LENGTH_LONG).show();
            }
        }
        Button button = (Button) findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.RESULT_KEY,"Successful");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
