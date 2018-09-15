package mad.com.hw04;
/*
* Homework 04
* MainActivity.java
* Gaurav Pareek
* Darshak Mehta
* */
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private Button start;
    private Button exit;
    private ProgressBar pb;
    private TextView tv;
    ImageView iv;
    ArrayList<Question> questionList = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        tv = (TextView) findViewById(R.id.loadingText);
        start = (Button) findViewById(R.id.start);
        start.setEnabled(false);
        exit = (Button) findViewById(R.id.exit);
        if(isConnected()) {
            new GetQuestionsAsyncTask(new GetQuestionsAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(ArrayList<Question> questions) {
                    questionList = questions;
                    pb.setVisibility(View.GONE);
                    tv.setText("Trivia Ready");
                    iv = new ImageView(MainActivity.this);
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.trivia));
                    linearLayout.addView(iv, 0);
                    start.setEnabled(true);
                }
            }).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TriviaActivity.class);
                intent.putExtra("questionList",questionList);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
