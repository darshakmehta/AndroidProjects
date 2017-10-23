package mad.com.recipe_puppy_finder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> ingredients = new ArrayList<String>();
    private Button searchButton;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.dishName);
                String s = e.getText().toString();
                if (!s.isEmpty() && (ingredients.size() != 0)) {
                    Intent i = new Intent(MainActivity.this, RecipeActivity.class);
                    i.putExtra("Ingredients", ingredients);
                    i.putExtra("dish_name", s);
                    startActivity(i);
                } else if (ingredients.size() == 0) {
                    Toast.makeText(MainActivity.this, "Please add atleast one Ingredient", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please add a Dish", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addIngredients(View v) {
        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.parent_layout);
        ImageView iv;
        EditText e;
        if (count <= 5) {
            if (Integer.parseInt(v.getTag().toString()) == 1) {
                ViewGroup vg = (ViewGroup) v.getParent();
                EditText editText = (EditText) vg.getChildAt(0);
                if ((editText.getText().toString().equals(""))) {
                    Toast.makeText(MainActivity.this, "Keyword cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    ingredients.add(editText.getText().toString());
                    if (count < 5) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View child = inflater.inflate(R.layout.row_activity, null);
                        parent_layout.addView(child, parent_layout.getChildCount() - 1);
                        iv = (ImageView) v;
                        iv.setTag(0);
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.remove));
                        count++;
                    } else {
                        iv = (ImageView) v;
                        iv.setTag(0);
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.remove));
                    }
                }
            } else {
                if (count == 5) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View child = inflater.inflate(R.layout.row_activity, null);
                    parent_layout.addView(child, parent_layout.getChildCount() - 1);
                }
                ViewGroup vg = (ViewGroup) v.getParent();
                EditText editText = (EditText) vg.getChildAt(0);
                ingredients.remove(editText.getText().toString());
                parent_layout.removeView(vg);
                if (count != 1)
                    count--;
            }
        } else {
            Toast.makeText(this, "Can add only 5 Ingredients", Toast.LENGTH_LONG).show();
        }
    }

}
