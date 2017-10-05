package mad.com.radiobutton_radiogroup;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                Log.d("demo","Checked the "+ rb.getText().toString());
            }
        });

        findViewById(R.id.buttonColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rg.getCheckedRadioButtonId();
                if(id == R.id.red){
                    Log.d("demo", "Checked is Red");
                } else if(id == R.id.green){
                    Log.d("demo", "Checked is Green");
                } else if(id == R.id.blue){
                    Log.d("demo", "Checked is Blue");
                } else if(id == -1){
                    Log.d("demo", "Nothing is Checked");
                }
            }
        });
    }
}
