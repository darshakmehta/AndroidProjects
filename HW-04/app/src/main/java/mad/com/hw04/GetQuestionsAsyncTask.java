package mad.com.hw04;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class GetQuestionsAsyncTask extends AsyncTask<String, Void, ArrayList<Question>> {
    public AsyncResponse response;
    public GetQuestionsAsyncTask(AsyncResponse response) {
        this.response = response;
    }
    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == con.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                return QuestionUtil.QuestionJSONParser.parseQuestions(sb.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        response.processFinish(questions);

    }
    public interface AsyncResponse {
        void processFinish(ArrayList<Question> questions);
    }
}
