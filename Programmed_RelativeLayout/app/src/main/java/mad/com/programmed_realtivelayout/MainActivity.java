package mad.com.programmed_realtivelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        RelativeLayout relativeLayout = new RelativeLayout(this); //this refers to the MainActivity
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        setContentView(relativeLayout);

        TextView textView = new TextView(this);
        textView.setTextSize(50);
        textView.setText(R.string.welcome); //Always use Resource id and not hard code string
        textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView.setId(View.generateViewId()); //Api level 17 and more
        relativeLayout.addView(textView);
        // textView.setId(100); //make sure number is not repeated and use only if API level less than 17

        Button button = new Button(this);
        button.setText(R.string.click_button);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.BELOW,textView.getId()); //Only use for relative layout
        button.setLayoutParams(buttonLayoutParams);
        button.setTag(true); //Not sure of children's in a relative layout
        relativeLayout.addView(button);

        //To find Child Count
        relativeLayout.getChildCount();

        //To get child at Index
        relativeLayout.getChildAt(0);

        //To remove particular child
        relativeLayout.removeView(button);

        //To remove at a index
        relativeLayout.removeViewAt(0);

        //To remove all views
        relativeLayout.removeAllViews();


    }
}
