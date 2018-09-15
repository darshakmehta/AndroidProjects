package mad.com.hw04;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBarResult);
        TextView percentageText = (TextView)findViewById(R.id.answer);
        String result = getIntent().getExtras().getString("percentage");
        Double percentage = new Double(result);
        percentageText.setText(percentage.intValue()+"%");
        progressBar.setMax(100);
        progressBar.setProgress(percentage.intValue());
        progressBar.setClickable(false);
        findViewById(R.id.quitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

            findViewById(R.id.tryButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()) {
                    new GetQuestionsAsyncTask(new GetQuestionsAsyncTask.AsyncResponse() {
                        @Override
                        public void processFinish(ArrayList<Question> questions) {
                            Intent intent = new Intent(StatsActivity.this, TriviaActivity.class);
                            intent.putExtra("questionList", questions);
                            startActivity(intent);
                        }
                    }).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
                    }else{
                        Toast.makeText(StatsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
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
