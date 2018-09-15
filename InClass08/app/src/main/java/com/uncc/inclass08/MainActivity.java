package com.uncc.inclass08;
/*
* Assignment InClass08
 * Gaurav Pareek
 * Darshak Mehta
 * MainActivity.java
* */
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProgressFragment.OnProgressFragmentInteractionListener,
        RecipeDetailsFragment.OnRecepieFragmentInteractionListener,SearchFragment.OnFragmentTextChange{
    public ArrayList<String> ingredients = new ArrayList<String>();
    private int count = 1;
    private String dishName;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (Button)findViewById(R.id.search_button);
        getFragmentManager().beginTransaction()
                .add(R.id.container, new SearchFragment(), "SearchFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onProgressFragmentInteraction(ArrayList<Recepie> recepiesList) {

        if(recepiesList.size()>0) {
            RecipeDetailsFragment ldf = new RecipeDetailsFragment();
            FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction ft = fm.beginTransaction();
            Bundle args = new Bundle();
            args.putSerializable("recepieList", recepiesList);
            ldf.setArguments(args);
            ft.replace(R.id.container, ldf);
            ft.addToBackStack(null);
            ft.commit();
            getSupportActionBar().setTitle("Recipes");
        }else{
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new SearchFragment(), "SearchFragment")
                    .commit();
            Toast.makeText(MainActivity.this, "No Recipe Found", Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle("Recipe Puppy");
        }
    }

    @Override
    public void onRecepieFragmentInteraction(Uri uri) {

    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onTextChanged(String dishName, ArrayList<String> list) {

    }


}
