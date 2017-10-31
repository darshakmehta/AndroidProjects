package mad.com.fragmentdemo;

import android.app.Fragment;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AFragment.OnFragmentTextChange {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo","MainActivity onCreate before inflating Layout");
        setContentView(R.layout.activity_main);
        Log.d("demo","MainActivity onCreate after inflating Layout");

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                AFragment fragment = (AFragment) getFragmentManager().findFragmentById(R.id.fragment);
                if(checkedId == R.id.radioButtonRed){
                    fragment.changeColor(Color.RED);
                }else if(checkedId == R.id.radioButtonGreen){
                    fragment.changeColor(Color.GREEN);
                }else if(checkedId == R.id.radioButtonBlue){
                    fragment.changeColor(Color.BLUE);
                }
            }
        });
    }

    @Override
    public void onTextChanged(String text) {
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(text);
    }
}
