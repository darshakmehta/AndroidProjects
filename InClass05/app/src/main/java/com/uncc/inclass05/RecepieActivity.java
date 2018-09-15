package com.uncc.inclass05;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecepieActivity extends AppCompatActivity {

    TextView title;
    TextView ingred;
    TextView url;
    int currentIndex = 0;
    private String href = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie);
        ImageView previousCornerButton = (ImageView) findViewById(R.id.first);
        ImageView previousButton = (ImageView) findViewById(R.id.second);
        ImageView nextButton = (ImageView) findViewById(R.id.third);
        ImageView nextCornerButton = (ImageView) findViewById(R.id.fourth);
        final ImageView iv = (ImageView)findViewById(R.id.recepieImage);
        title = (TextView)findViewById(R.id.titleRecepie);
        ingred = (TextView)findViewById(R.id.ingredValue);
        url = (TextView)findViewById(R.id.urlValue);
        Button finish = (Button)findViewById(R.id.finish);
        Intent i = getIntent();
        final ArrayList<Recepie> arrayList = (ArrayList<Recepie>) i.getSerializableExtra("recepie");

        if(isConnected()) {
            Recepie recepieData = arrayList.get(currentIndex);
            title.setText("Title: "+recepieData.getTitle().trim());
            ingred.setText(recepieData.getIngredients());
            url.setText(recepieData.getHref());
            href = recepieData.getHref();
            new GetImageData().execute(recepieData.getThumbnil());

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecepieActivity.this,ProgressActivity.class);
                    setResult(ProgressActivity.RESULT_OK, intent);
                    finish();}
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( currentIndex == arrayList.size()-1){
                        Toast.makeText(RecepieActivity.this, "It is last recipes", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        currentIndex++;
                    }
                    Recepie recepieData1 = arrayList.get(currentIndex);
                    title.setText("Title: "+recepieData1.getTitle().trim());
                    ingred.setText(recepieData1.getIngredients());
                    url.setText(recepieData1.getHref());
                    href = recepieData1.getHref();
                    if(recepieData1.getThumbnil()!=null && !"".equalsIgnoreCase(recepieData1.getThumbnil())) {
                        new GetImageData().execute(recepieData1.getThumbnil());
                    }
                    else {
                        iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                    }
                }
            });
            nextCornerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( currentIndex == arrayList.size()-1){
                        Toast.makeText(RecepieActivity.this, "It is last recipe", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Recepie recepieData2 = arrayList.get(arrayList.size() - 1);
                        title.setText("Title: " + recepieData2.getTitle().trim());
                        ingred.setText(recepieData2.getIngredients());
                        url.setText(recepieData2.getHref());
                        href = recepieData2.getHref();
                        if (recepieData2.getThumbnil() != null && !"".equalsIgnoreCase(recepieData2.getThumbnil())) {
                            new GetImageData().execute(recepieData2.getThumbnil());
                        } else {
                             iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                        }
                        currentIndex = arrayList.size()-1;
                    }
                }
            });

            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( currentIndex == 0){
                        Toast.makeText(RecepieActivity.this, "It is first recipes", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        currentIndex--;
                    }
                    Recepie recepieData3 = arrayList.get(currentIndex);
                    title.setText("Title: "+recepieData3.getTitle().trim());
                    ingred.setText(recepieData3.getIngredients());
                    url.setText(recepieData3.getHref());
                    href = recepieData3.getHref();
                    if(recepieData3.getThumbnil()!=null && !"".equalsIgnoreCase(recepieData3.getThumbnil())) {
                        new GetImageData().execute(recepieData3.getThumbnil());
                    }
                    else {
                        iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                    }
                }
            });

            previousCornerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( currentIndex == 0){
                        Toast.makeText(RecepieActivity.this, "It is first recipe", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Recepie recepieData4 = arrayList.get(0);
                        title.setText("Title: " + recepieData4.getTitle().trim());
                        ingred.setText(recepieData4.getIngredients());
                        url.setText(recepieData4.getHref());
                        href = recepieData4.getHref();
                        if (recepieData4.getThumbnil() != null && !"".equalsIgnoreCase(recepieData4.getThumbnil())) {
                            new GetImageData().execute(recepieData4.getThumbnil());
                        } else {
                             iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                        }
                        currentIndex = 0;
                    }
                }
            });

            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(href));
                    startActivity(intent);
                }
            });

        }


        }

public class GetImageData extends AsyncTask<String, Void, Bitmap> {
    InputStream in = null;

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
        if (bitmapImage != null) {
            ImageView iv = (ImageView)findViewById(R.id.recepieImage);
            iv.setImageBitmap(bitmapImage);
            Log.d("Image", "ImageChanged");
        } else {
            Log.d("Demo", "Null Result");
        }
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


