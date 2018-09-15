package mad.com.hw04;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.width;
import static mad.com.hw04.R.attr.height;

public class TriviaActivity extends Activity {
    ImageView iv;
    ProgressBar progressBar;
    private GetImageAsyncTask getImageAsyncTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        final TextView questionNo = (TextView) findViewById(R.id.questionNo);
        final TextView timeLeft = (TextView) findViewById(R.id.timeLeftValue);
        final TextView question = (TextView) findViewById(R.id.question);
        final TextView loadingText = (TextView) findViewById(R.id.loadingText);
        progressBar = (ProgressBar) findViewById(R.id.progressBarimage);
        iv = (ImageView) findViewById(R.id.questionImage);
        findViewById(R.id.quitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TriviaActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        final HashMap<Integer, Integer> answerMap = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> questionMap = new HashMap<Integer, Integer>();
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final int[] questionCount = {0};
        Intent intent = getIntent();
        final ArrayList<Question> questionList = (ArrayList<Question>) getIntent().getExtras().getSerializable("questionList");
        Log.d("demo", questionList.toString());
        final CountDownTimer mytimer;
        mytimer = new CountDownTimer(121000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeLeft.setText(millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                int countCorrect = 0;
                for (Map.Entry<Integer, Integer> e : answerMap.entrySet()) {
                    Integer key = e.getKey();
                    Integer answer = e.getValue();
                    Integer exactAnswer = questionMap.get(key);
                    if (exactAnswer == (answer + 1)) {
                        countCorrect++;
                    }
                }
                Double percentage = (new Double(countCorrect) / new Double(questionList.size())) * 100;
                Intent i = new Intent(TriviaActivity.this, StatsActivity.class);
                i.putExtra("percentage", percentage.toString());
                this.cancel();
                startActivity(i);
            }
        };
        mytimer.start();
        if(isConnected()) {
        new GetImageAsyncTask(new GetImageAsyncTask.AsyncImageResponse() {
            @Override
            public void setQuestionImage(Bitmap bitmapImage) {
                if (bitmapImage != null) {
                    iv.setImageBitmap(bitmapImage);
                    progressBar.setVisibility(View.GONE);
                    loadingText.setVisibility(View.GONE);
                    Log.d("Image", "ImageChanged");
                } else {
                    Log.d("Demo", "Null Result");
                }
                questionNo.setText("Q." + (questionList.get(0).getId() + 1));
                question.setText(questionList.get(0).getText());
                addRadioButtons(questionList.get(0).getChoices().size(), questionList.get(0).getChoices());

            }
        }).execute(questionList.get(0).getImage());
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                RadioGroup radioButtonGroup = (RadioGroup) findViewById(Integer.parseInt(String.valueOf(1000)));
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                final int idx = radioButtonGroup.indexOfChild(radioButton);
                answerMap.put((questionList.get(questionCount[0]).getId() + 1), idx);
                questionMap.put((questionList.get(questionCount[0]).getId() + 1), questionList.get(questionCount[0]).getAnswer());
                questionCount[0]++;

                Log.d("answer", idx + "");
                iv.setImageBitmap(null);
                progressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
                if (questionCount[0] < questionList.size()) {
                    if (getImageAsyncTask != null && getImageAsyncTask.getStatus() != AsyncTask.Status.FINISHED)
                        getImageAsyncTask.cancel(true);
                    getImageAsyncTask = new GetImageAsyncTask(new GetImageAsyncTask.AsyncImageResponse() {
                        @Override
                        public void setQuestionImage(Bitmap bitmapImage) {
                            if (bitmapImage != null) {
                                progressBar.setVisibility(View.GONE);
                                loadingText.setVisibility(View.GONE);
                                iv.setImageBitmap(bitmapImage);
                                Log.d("Image", "ImageChanged");
                            } else {
                                Log.d("Demo", "Null Result");
                            }

                        }
                    });
                    if (questionList.get(questionCount[0]).getImage() != null && !"".equalsIgnoreCase(questionList.get(questionCount[0]).getImage())) {
                        questionNo.setText("Q." + (questionList.get(questionCount[0]).getId() + 1));
                        question.setText(questionList.get(questionCount[0]).getText());
                        ViewGroup viewGroup = ((ViewGroup) findViewById(R.id.radioGroup));
                        viewGroup.removeAllViews();
                        addRadioButtons(questionList.get(questionCount[0]).getChoices().size(), questionList.get(questionCount[0]).getChoices());
                        getImageAsyncTask.execute(questionList.get(questionCount[0]).getImage());
                    } else {
                        questionNo.setText("Q." + (questionList.get(questionCount[0]).getId() + 1));
                        question.setText(questionList.get(questionCount[0]).getText());
                        answerMap.put((questionList.get(questionCount[0]).getId()) + 1, idx);
                        questionMap.put((questionList.get(questionCount[0]).getId()) + 1, questionList.get(questionCount[0]).getAnswer());
                        ViewGroup viewGroup = ((ViewGroup) findViewById(R.id.radioGroup));
                        viewGroup.removeAllViews();
                        addRadioButtons(questionList.get(questionCount[0]).getChoices().size(), questionList.get(questionCount[0]).getChoices());

                    }
                } else {
                    radioButtonGroup = (RadioGroup) findViewById(Integer.parseInt(String.valueOf(1000)));
                    radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    radioButton = radioButtonGroup.findViewById(radioButtonID);
                    int id = questionList.get(questionList.size() - 1).getId() + 1;
                    answerMap.put(id, radioButtonGroup.indexOfChild(radioButton));
                    questionMap.put(id, questionList.get(questionList.size() - 1).getAnswer());

                    //take to next activity with data
                    int countCorrect = 0;
                    for (Map.Entry<Integer, Integer> e : answerMap.entrySet()) {
                        Integer key = e.getKey();
                        Integer answer = e.getValue();
                        Integer exactAnswer = questionMap.get(key);
                        if (exactAnswer == (answer + 1)) {
                            countCorrect++;
                        }
                    }
                    Double percentage = (new Double(countCorrect) / new Double(answerMap.size())) * 100;
                    Intent intent = new Intent(TriviaActivity.this, StatsActivity.class);
                    intent.putExtra("percentage", percentage.toString());
                    if (mytimer != null) {
                        mytimer.cancel();
                    }
                    startActivity(intent);
                }
            }else {
                Toast.makeText(TriviaActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
            }
        });

    }

    public void addRadioButtons(int number, ArrayList<String> choices) {
        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(TriviaActivity.this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setId(Integer.parseInt(String.valueOf(1000)));
            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText(choices.get(i-1));
                ll.addView(rdbtn);
            }
            ViewGroup viewGroup = ((ViewGroup) findViewById(R.id.radioGroup));
            viewGroup.addView(ll);
        }
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
