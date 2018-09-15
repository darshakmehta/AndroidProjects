package mad.com.inclass13test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] arraySpinner;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<Trip> tripList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripList = new ArrayList<Trip>();
        this.arraySpinner = new String[] {
                "Cost: Low to High", "Cost: High to Low"
        };

        Spinner s = (Spinner) findViewById(R.id.spinnerCost);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        findViewById(R.id.imageButtonAddTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTrip.class);
                startActivity(intent);
            }
        });

    }
}
