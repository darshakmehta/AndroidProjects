package mad.com.hw04;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    InputStream in = null;
    AsyncImageResponse imageResponse;
    public GetImageAsyncTask(AsyncImageResponse imageResponse) {
        this.imageResponse = imageResponse;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (isCancelled())
            {
                return (null);
            }
            in = connection.getInputStream();
            Bitmap bitmapImage = BitmapFactory.decodeStream(in);
            return bitmapImage;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Bitmap bitmapImage) {
        super.onPostExecute(bitmapImage);
        imageResponse.setQuestionImage(bitmapImage);
    }

    public interface AsyncImageResponse {
        void setQuestionImage(Bitmap bitmapImage);
    }
}
