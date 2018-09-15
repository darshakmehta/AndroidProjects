package com.uncc.inclass05;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {

    ProgressBar progressBar;
    public static final int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        Intent i = getIntent();
        ArrayList<String> ingredients = i.getStringArrayListExtra("Ingredients");
        StringBuilder sb = new StringBuilder();
        sb.append("i=");
        for (String ingred : ingredients) {
            sb.append(ingred + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String dish = i.getStringExtra("dish_name");


        if (isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            String url = "http://www.recipepuppy.com/api/?" + sb.toString() + "&q=" + dish;
            new GetAsyncTask().execute(url);
        }
    }

    public class GetAsyncTask extends AsyncTask<String, Integer, ArrayList<Recepie>> {
        BufferedReader reader = null;

        @Override
        protected ArrayList<Recepie> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                publishProgress(20);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                    publishProgress(50);
                }
                return RecipeUtil.PersonJsonParser.parsePerson(stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recepie> s) {
            super.onPostExecute(s);
            progressBar.setProgress(70);
            if (s.size() > 0) {
                Log.d("Demo", s.toString());
                progressBar.setProgress(100);
                Intent i = new Intent(ProgressActivity.this, RecepieActivity.class);
                i.putExtra("recepie", s);
                startActivityForResult(i, REQ_CODE);
            } else {
                finish();
                Toast.makeText(ProgressActivity.this, "No recipes found", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
            progressBar.setClickable(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                finish();
            }

        }
    }
}
