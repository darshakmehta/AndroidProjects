package mad.com.recipe_puppy_finder;

import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    TextView title, ingred, url;
    ImageView previousButton, previousCornerButton, nextCornerButton, iv, nextButton;
    Button finish;
    int currentIndex = 0;
    private String href = "";
    ProgressBar progressBar;
    int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        previousCornerButton = (ImageView) findViewById(R.id.first);
        previousButton = (ImageView) findViewById(R.id.second);
        nextButton = (ImageView) findViewById(R.id.third);
        nextCornerButton = (ImageView) findViewById(R.id.fourth);
        iv = (ImageView)findViewById(R.id.recepieImage);
        title = (TextView)findViewById(R.id.titleRecepie);
        ingred = (TextView)findViewById(R.id.ingredValue);
        url = (TextView)findViewById(R.id.urlValue);
        finish = (Button)findViewById(R.id.finish);
        Picasso.with(this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(iv);
        Intent i = getIntent();
        ArrayList<String> ingredients = i.getStringArrayListExtra("Ingredients");
        StringBuilder sb = new StringBuilder();
        sb.append("&i=");
        for (String ingred : ingredients) {
            sb.append(ingred + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String dish = i.getStringExtra("dish_name");
        if (isConnected()) {
            String url = "http://www.recipepuppy.com/api/?format=xml" + sb.toString() + "&q=" + dish;
            Log.d("demo",url);
            new GetRecipeAsyncTask().execute(url);
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

    class GetRecipeAsyncTask extends AsyncTask<String, Integer, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                if(statusCode == HttpURLConnection.HTTP_OK){
                    InputStream in = con.getInputStream();
                    count+=10;
                    publishProgress(count);
                    return RecipeUtil.RecipesPullParse.parseRecipes(in);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(final ArrayList<Recipe> recipes) {
            super.onPostExecute(recipes);

            if (recipes != null && isConnected()) {

                Recipe recipeArray = recipes.get(currentIndex);
                title.setText("Title:" + recipeArray.getTitle());
                url.setText(recipeArray.getHref());
                ingred.setText(recipeArray.getIngredients());
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentIndex == recipes.size() - 1) {
                            Toast.makeText(RecipeActivity.this, "It is last recipe", Toast.LENGTH_SHORT).show();
                        } else {
                            currentIndex++;
                        }
                        Recipe recipeArray1 = recipes.get(currentIndex);
                        title.setText("Title: " + recipeArray1.getTitle());
                        ingred.setText(recipeArray1.getIngredients());
                        url.setText(recipeArray1.getHref());
                        href = recipeArray1.getHref();

                    }
                });
                nextCornerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentIndex == recipes.size() - 1) {
                            Toast.makeText(RecipeActivity.this, "It is last recipe", Toast.LENGTH_SHORT).show();
                        } else {
                            Recipe recipeArray2 = recipes.get(recipes.size() - 1);
                            title.setText("Title: " + recipeArray2.getTitle());
                            ingred.setText(recipeArray2.getIngredients());
                            url.setText(recipeArray2.getHref());
                            href = recipeArray2.getHref();
                        }
                    }
                });
                previousButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentIndex == 0) {
                            Toast.makeText(RecipeActivity.this, "It is first recipe", Toast.LENGTH_SHORT).show();
                        } else {
                            currentIndex--;
                        }
                        Recipe recipeArray3 = recipes.get(currentIndex);
                        title.setText("Title: " + recipeArray3.getTitle());
                        ingred.setText(recipeArray3.getIngredients());
                        url.setText(recipeArray3.getHref());
                        href = recipeArray3.getHref();
                    }
                });

                previousCornerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentIndex == 0) {
                            Toast.makeText(RecipeActivity.this, "It is first recipe", Toast.LENGTH_SHORT).show();
                        } else {
                            Recipe recipeArray4 = recipes.get(0);
                            title.setText("Title: " + recipeArray4.getTitle());
                            ingred.setText(recipeArray4.getIngredients());
                            url.setText(recipeArray4.getHref());
                            href = recipeArray4.getHref();
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

}
}