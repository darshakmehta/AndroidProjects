package com.uncc.inclass04;
/*
*Assignment InClass04
 MainActivity.java.
 Gaurav Pareek
 Darshak Mehta
*/
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private TextView result;
    private Handler handler;
    ExecutorService threadpool;
    TextView name;
    TextView dept;
    TextView age;
    TextView zip;
    Boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextView) findViewById(R.id.nameValue);
        dept = (TextView) findViewById(R.id.deptValue);
        age = (TextView) findViewById(R.id.ageValue);
        zip = (TextView) findViewById(R.id.zipValue);

        Button asyncButton = (Button) findViewById(R.id.numberAsyncTask);
        Button threadButton = (Button) findViewById(R.id.numberThread);
        threadpool = Executors.newFixedThreadPool(2);

        result = (TextView) findViewById(R.id.result);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case Dowork.STATUS_START:
                        progressDialog.show();
                        break;
                    case Dowork.STATUS_STEP:
                        progressDialog.setProgress(msg.getData().getInt("PROGRESS"));
                        break;
                    case Dowork.STATUS_DONE:
                        progressDialog.hide();
                        final CharSequence[] password = msg.getData().getCharSequenceArray("RESULT");
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Choose a Password")
                                .setItems(password, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.setText(password[which]);
                                    }
                                }).show().setCancelable(false);
                        break;
                }
                return false;
            }
        });

        asyncButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(name.getText().toString(), dept.getText().toString(), age.getText().toString(), zip.getText().toString()))
                    new DoWorkAsync(name.getText().toString(), dept.getText().toString(), Integer.parseInt(age.getText().toString()), Integer.parseInt(zip.getText().toString())).execute();
            }
        });

        threadButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Generating Passwords");
                progressDialog.setCancelable(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                if (validate(name.getText().toString(), dept.getText().toString(), age.getText().toString(), zip.getText().toString()))
                    threadpool.execute(new Dowork());
            }
        });
    }

    public boolean validate(String personname, String department, String age1, String zipcode) {
        boolean validate = true;
        if (!personname.matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*")) {
            Toast.makeText(this, "Name should contain only Alphabets", Toast.LENGTH_SHORT).show();
            validate = false;

        }
        if (!department.matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*")) {
            Toast.makeText(this, "Department should contain only Alphabets", Toast.LENGTH_SHORT).show();
            validate = false;

        }
        if (!age1.matches("[1-9]+")) {
            Toast.makeText(this, "Age should be positive number", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if (zipcode.matches("[0-9]+") && zipcode.length() == 5) {
            if(validate)
            validate = true;
        } else {
            Toast.makeText(this, "Zip should be 5 digit and a number", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }

    public void clear(View v) {
        name.setText("");
        dept.setText("");
        age.setText("");
        zip.setText("");
        result.setText("");
    }

    public void close(View v) {
        finish();
    }

    class Dowork implements Runnable {
        static final int STATUS_START = 0;
        static final int STATUS_STEP = 1;
        static final int STATUS_DONE = 2;

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = STATUS_START;
            handler.sendMessage(msg);
            CharSequence[] password = new CharSequence[5];
            for (int i = 0; i < 5; i++) {
                password[i] = Util.getPassword(name.getText().toString(), dept.getText().toString(), Integer.parseInt(age.getText().toString()), Integer.parseInt(zip.getText().toString()));
                msg = new Message();
                msg.what = STATUS_STEP;
                Bundle data = new Bundle();
                data.putInt("PROGRESS", 20 * i);
                msg.setData(data);
                handler.sendMessage(msg);
            }
            msg = new Message();
            msg.what = STATUS_DONE;
            Bundle data = new Bundle();
            data.putCharSequenceArray("RESULT", password);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }

    class DoWorkAsync extends AsyncTask<Void, Integer, CharSequence[]> {
        String name;
        String dept;
        int age;
        int zip;

        public DoWorkAsync(String name, String dept, int age, int zip) {
            this.name = name;
            this.dept = dept;
            this.age = age;
            this.zip = zip;
        }

        @Override
        protected CharSequence[] doInBackground(Void... params) {
            CharSequence[] password = new CharSequence[5];
            for (int i = 0; i < 5; i++) {
                password[i] = Util.getPassword(name, dept, age, zip);
                publishProgress(20 * i);
            }
            return password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Generating Passwords");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(CharSequence[] password) {
            super.onPostExecute(password);
            progressDialog.dismiss();
            final CharSequence[] passwordResult = password;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Choose a Password")
                    .setItems(password, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.setText(passwordResult[which]);
                        }
                    }).show().setCancelable(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }


}
