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
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new AFragment(), "tag_afragment")
                .commit();

        getFragmentManager().beginTransaction()
                .add(R.id.container, new AFragment(), "tag_afragment_1")
                .commit();

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                AFragment fragment = (AFragment) getFragmentManager().findFragmentByTag("tag_afragment");
                AFragment fragment1 = (AFragment) getFragmentManager().findFragmentByTag("tag_afragment_1");

                if(checkedId == R.id.radioButtonRed){
                    fragment.changeColor(Color.RED);
                    fragment1.changeColor(Color.RED);
                }else if(checkedId == R.id.radioButtonGreen){
                    fragment.changeColor(Color.GREEN);
                    fragment1.changeColor(Color.GREEN);
                }else if(checkedId == R.id.radioButtonBlue){
                    fragment.changeColor(Color.BLUE);
                    fragment1.changeColor(Color.BLUE);
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
