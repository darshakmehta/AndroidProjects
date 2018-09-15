package mad.com.hw03;

/*
* Assignment HW03
* Gaurav Pareek
* Darshak Mehta
* MainActivity.java
* */
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ExecutorService threadpool;
    ProgressBar progressBar;
    Handler handler;
    LinearLayout parent_Scroll;
    LayoutInflater inflater;
    View child;
    private CheckBox checkBox;
    private static ArrayList<String> words = new ArrayList<String>();
    private int size = 0;
    private static int count = 0;
    private static ArrayList<String> result_keywords = new ArrayList<String>();;
    private ArrayList<String> fileLines = new ArrayList<>();
    public static final int REQ_CODE = 100;
    private int word_box_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = (Button) findViewById(R.id.search);
        progressBar = (ProgressBar)findViewById(R.id.progress);

        parent_Scroll = (LinearLayout) findViewById(R.id.parent_Scroll);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = inflater.inflate(R.layout.row_activity, null);
        parent_Scroll.addView(child, parent_Scroll.getChildCount());
        word_box_count++;

        search.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                count = 0;
                checkBox = (CheckBox)findViewById(R.id.checkBox);
                int checked = 0;
                boolean check = checkBox.isChecked();
                if(check)
                    checked = 1;

                size = words.size();
                if (size < 1) {
                    Toast.makeText(MainActivity.this, "Please Enter a Keyword", Toast.LENGTH_LONG).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    result_keywords = new ArrayList<String>();
                    fileLines = new ArrayList<String>();
                    generateFile();
                    progressBar.setProgress(1);
                    progressBar.setClickable(false);
                    progressBar.setMax(100);
                    threadpool = Executors.newFixedThreadPool(words.size());
                    for (int i = 0; i < size; i++) {
                       handler = new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                switch (msg.what)
                                {
                                    case doWork.STATUS_START:
                                        break;
                                    case doWork.STATUS_STEP:
                                        progressBar.setProgress(msg.getData().getInt("PROGRESS"));
                                        break;
                                    case doWork.STATUS_DONE:
                                        Intent i = new Intent(MainActivity.this,ResultActivity.class);
                                        i.putStringArrayListExtra("result",result_keywords);
                                        i.putStringArrayListExtra("keyword",words);
                                        i.putExtra("check",checkBox.isChecked());
                                        startActivityForResult(i,REQ_CODE);
                                        break;
                                }
                                return false;
                            }
                        });
                        threadpool.execute(new doWork(words.get(i),checked));
                    }

                }
        }
        });

    }
    public void addKeyword(View v){
        ImageView iv;
        EditText e;
        if(word_box_count <= 20){
            if(Integer.parseInt(v.getTag().toString()) == 1){
                ViewGroup vg = (ViewGroup) v.getParent();
                EditText editText = (EditText)vg.getChildAt(0);
                if((editText.getText().toString().equals(""))){
                    Toast.makeText(MainActivity.this, "Keyword cannot be empty", Toast.LENGTH_LONG).show();
                }else {
                    words.add(editText.getText().toString());
                    if(word_box_count < 20){
                        child = inflater.inflate(R.layout.row_activity, parent_Scroll);
                        ViewGroup vg2 = (ViewGroup) parent_Scroll.getChildAt(parent_Scroll.getChildCount()-1);
                        EditText et = (EditText) vg2.getChildAt(0);
                        et.requestFocus();
                        iv = (ImageView)v;
                        iv.setTag(0);
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.remove));
                        word_box_count++;
                    }else {
                        iv = (ImageView)v;
                        iv.setTag(0);
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.remove));
                    }
                }
            }else {
                if(word_box_count == 20){
                    child = inflater.inflate(R.layout.row_activity, parent_Scroll);
                }
                ViewGroup vg = (ViewGroup) v.getParent();
                EditText editText = (EditText)vg.getChildAt(0);
                words.remove(editText.getText().toString());
                parent_Scroll.removeView(vg);
                if(word_box_count!=1)
                    word_box_count--;
            }
        }
    }
    @Override
    public void onClick(View v) {
    }
    class doWork implements Runnable{
        String keyword;
        int check;
        public doWork(String keyword,int check) {
            this.keyword = keyword;
            this.check = check;
        }

        static final int STATUS_START = 0;
        static final int STATUS_STEP = 1;
        static final int STATUS_DONE = 2;
        @Override
        public void run() {
            Message msg = new Message();
            Bundle data = new Bundle();

            synchronized(this) {
                wordSearch(fileLines, keyword, check);
                msg = new Message();
                msg.what = STATUS_STEP;
                data = new Bundle();
                Double keywordList = new Double(words.size());
                Double cofficient = count/keywordList;
                Double progressCount = cofficient*100;
                Log.d("ProgessBar",progressCount.intValue()+"");
                data.putInt("PROGRESS",progressCount.intValue());
                msg.setData(data);
                handler.sendMessage(msg);
            }



            if(count == words.size()) {
                Log.d("thread","executed");
                msg = new Message();
                msg.what = STATUS_DONE;
                handler.sendMessage(msg);
            }
        }
    }

    private void generateFile()
    {        String str = null;
        try {
            InputStream inputStream = getAssets().open("textfile.txt");
            int size = inputStream.available();
            str = "";
            BufferedReader buffer = null;
            try {
                try {
                    buffer = new BufferedReader(new BufferedReader(new InputStreamReader(inputStream)));
                    String lineContent = null;

                    while ((lineContent = buffer.readLine()) != null) {
                        fileLines.add(lineContent.replaceAll("\t", " "));
                    }
                }
                finally {
                    if (inputStream != null)
                        inputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void wordSearch(ArrayList<String> fileLines1, String keyWord,int check){
        for(int i = 0; i < fileLines1.size(); i++){
            String line = fileLines1.get(i);
            String[] lineWords;
            if (check == 1) {
                lineWords = line.split(" ");
            } else {
                keyWord = keyWord.toLowerCase();
                lineWords = line.toLowerCase().split(" ");
            }

            ArrayList<String> wordsList = new ArrayList<String>(Arrays.asList(lineWords));

            if(wordsList.indexOf(keyWord) > 0){

                int wordIndex = wordsList.indexOf(keyWord);
                int oldIndex = wordIndex;

                while(oldIndex >= 0){
                    int index = TextUtils.join(" ", Arrays.copyOfRange(lineWords, 0, wordIndex)).length();

                    oldIndex = (wordsList.subList(wordIndex + 1, wordsList.size())).indexOf(keyWord);

                    wordIndex = wordIndex + oldIndex + 1;
                    if(index != 0){
                        index = index + 1;
                    }

                    int beginIndex = index - 10;

                    String oldStartString = "";

                    boolean lastWord = false;
                    int lastStringBalance = 0;

                    if(i == fileLines1.size() - 1){
                        int availableLength = line.length() - (index + keyWord.length());
                        int requiredLength = 20 - keyWord.length();
                        if(availableLength < requiredLength){
                            lastWord = true;
                            if(index >= requiredLength - availableLength + 10){
                                oldStartString = line.substring(index - requiredLength + availableLength - 10, index);
                            } else{
                                oldStartString = line.substring(0, index);
                                lastStringBalance = requiredLength - availableLength + 10 - oldStartString.length();
                            }
                        }

                    }

                    if(beginIndex < 0 || lastWord){
                        beginIndex = 0;
                        int currentIndex = i;
                        int balanceLength = 10 - index;

                        if(lastWord){
                            balanceLength = lastStringBalance;
                            beginIndex = index;
                        }

                        while(balanceLength > 0 && currentIndex - 1 >= 0){

                            String oldLine = fileLines1.get(currentIndex - 1);

                            if(oldLine.length() < balanceLength){
                                oldStartString = oldLine + oldStartString;
                                balanceLength = balanceLength - oldLine.length();
                            } else{
                                oldStartString =  oldLine.substring(oldLine.length() - balanceLength) + oldStartString;
                                balanceLength = 0;
                            }

                            currentIndex = currentIndex - 1;
                        }
                    }

                    if(index == 0 && i != 0){
                        oldStartString = oldStartString.substring(1) + " ";
                    }

                    String startString = oldStartString + line.substring(beginIndex, index);

                    int endIndex = index - startString.length() + 30;

                    String endString;

                    if(endIndex >= line.length()){
                        endString = line.substring(index + keyWord.length());
                    } else{
                        endString = line.substring(index + keyWord.length(), endIndex);
                    }

                    if(endString.equals("") && !lastWord){
                        endString = " ";
                    }

                    String finalString = startString + line.substring(index, index + keyWord.length()) + endString;


                    int currentIndex = i;

                    while(finalString.length() < 30 && currentIndex + 1 < fileLines1.size()){

                        String nextLine = fileLines1.get(currentIndex + 1);

                        int balanceLength = 30 - finalString.length();

                        if(nextLine.length() < balanceLength){
                            finalString = finalString + nextLine;
                        } else{
                            finalString = finalString + nextLine.substring(0, balanceLength);
                        }

                        currentIndex = currentIndex + 1;
                    }
                    result_keywords.add(finalString);
                }
            }
        }
        count++;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                words = new ArrayList<String>();
                parent_Scroll.removeAllViews();
                child = inflater.inflate(R.layout.row_activity, null);
                parent_Scroll.addView(child, parent_Scroll.getChildCount());
                word_box_count=1;
                progressBar.setProgress(0);
                progressBar.setVisibility(View.GONE);
                checkBox.setChecked(false);
            }

        }
    }
}
