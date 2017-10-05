package mad.com.activityforresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button secondBtn;
    public final static int REQ_CODE = 100;
    public final static String NAME_KEY = "NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secondBtn = (Button) findViewById(R.id.gotoSecond);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE){
            if(resultCode == RESULT_OK){
                String name = data.getExtras().getString(NAME_KEY);
                Toast.makeText(this, "Name received is " + name,Toast.LENGTH_LONG).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Name is not received",Toast.LENGTH_LONG).show();
            }
        }
    }
}
