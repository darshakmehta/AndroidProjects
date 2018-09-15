package mad.com.inclass13test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateTrip extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<Trip> tripList;

    DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference placeDbRef;
    ArrayList<Place> placeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewPlaces);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        /*Place place = new Place();
        place.setCost("350");
        place.setPlace("Myrtle Beach");
        place.setDuration("3 Days 2 nights");
        ArrayList<Location> location = new ArrayList<Location>();
        */
        placeList = new ArrayList<Place>();
        placeDbRef = firebaseDatabaseReference;
        //firebaseDatabaseReference.setValue(null);
        placeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("demo", dataSnapshot.getChildrenCount()+"");
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Place place = new Place();
                    place.setCost(data.child("Cost").getValue(String.class));
                    place.setDuration(data.child("Duration").getValue(String.class));
                    place.setPlace(data.child("Place").getValue(String.class));
                    place.setLat(data.child("Location").child("Lat").getValue(String.class));
                    place.setLon(data.child("Location").child("Lon").getValue(String.class));
                    placeList.add(place);
                    Log.d("demo", place.toString());
                    Log.d("demo", "text");
                }
                mAdapter = new PlacesAdapter(placeList, CreateTrip.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("testerror", "onCancelled", databaseError.toException());
            }
        });


        findViewById(R.id.buttonViewTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTrip.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTrip.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

}
