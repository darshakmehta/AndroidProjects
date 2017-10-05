package mad.com.activityforresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    private Button mainBtn;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mainBtn = (Button) findViewById(R.id.gotoMain);
        editText = (EditText) findViewById(R.id.result);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if(name == null || name.length() == 0){
                    setResult(RESULT_CANCELED);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.NAME_KEY,name);
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });


    }
}
