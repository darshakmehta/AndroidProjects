package mad.com.hw03;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darsh on 9/22/2017.
 */

public class ResultActivity extends Activity {
    ArrayList<String> arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Button finishbtn = (Button) findViewById(R.id.finishbtn);
        Intent i = getIntent();
        arrayList =  i.getStringArrayListExtra("result");
        ArrayList<String> keywords =  i.getStringArrayListExtra("keyword");
        Boolean check = i.getBooleanExtra("check",false);
        ScrollView scrollview = (ScrollView) findViewById(R.id.resultScrollView);

        LinearLayout layout= (LinearLayout) findViewById(R.id.resultLinearLayout);

        if(arrayList.size()>0) {
            for (int index = 0; index < arrayList.size(); index++) {
                TextView textView = new TextView(ResultActivity.this);
                textView.setSingleLine(true);
                LinearLayout.LayoutParams linearLayoutResult = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayoutResult.setMargins(0, 0, 0, 20);
                textView.setLayoutParams(linearLayoutResult);
                textView.setTextSize(20);
                textView.setPadding(50, 0, 0, 30);

                String data = "..." + arrayList.get(index) + "...";
                SpannableString spannable = new SpannableString("..." + arrayList.get(index) + "...");
                String[] colors = {"R", "B", "G"};
                for (int j = 0; j < keywords.size(); j++) {
                    String keyword = keywords.get(j);
                    int position = -1;
                    if (check)
                        position = data.indexOf(keyword);
                    else
                        position = data.toLowerCase().indexOf(keyword.toLowerCase());
                    int length = keyword.length();
                    if (position != -1) {
                        int remainder = j % 3;
                        String color = colors[remainder];
                        if (color.equalsIgnoreCase("R")) {
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), position, position + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        } else if (color.equalsIgnoreCase("B")) {
                            spannable.setSpan(new ForegroundColorSpan(Color.BLUE), position, position + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        } else if (color.equalsIgnoreCase("G")) {
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), position, position + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }
                    }

                }
                textView.setText(spannable);
                layout.addView(textView);
            }
        }
        else
        {
            Toast.makeText(ResultActivity.this, "No Keyword Found", Toast.LENGTH_LONG).show();
        }
        finishbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                setResult(MainActivity.RESULT_OK,intent);
                finish();
            }
        });
    }

}
