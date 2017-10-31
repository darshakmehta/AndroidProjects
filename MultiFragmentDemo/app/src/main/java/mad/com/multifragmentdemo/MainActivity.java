package mad.com.multifragmentdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener, FirstFragment.OnFragmentTextChange, FirstFragment.OnRecyclerFragmentInteractionListener {

    public ArrayList<String> ingredients = new ArrayList<String>();
    private int count = 1;
    private String dishName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new FirstFragment(), "first")
                .commit();
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
    public void onTextChanged(String text, ArrayList<String> list) {
        dishName = text;
        Log.d("demo",dishName);
        ingredients = list;
    }

    @Override
    public void onRecyclerFragmentInteraction() {

    }

    @Override
    public void gotoNextFragment() {

    }
}
