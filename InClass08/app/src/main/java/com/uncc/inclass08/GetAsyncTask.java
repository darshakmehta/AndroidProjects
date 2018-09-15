package com.uncc.inclass08;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gaurav on 10/23/2017.
 */

public class GetAsyncTask extends AsyncTask<String, Integer, ArrayList<Recepie>> {
    BufferedReader reader = null;
    public AsyncResponse response;
    ProgressBar progressBar;
    public GetAsyncTask(AsyncResponse response) {
        this.response = response;
    }
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
        Log.d("demo",s.toString());
        response.processFinish(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
    public interface AsyncResponse {
        void processFinish(ArrayList<Recepie> s);
    }
}
