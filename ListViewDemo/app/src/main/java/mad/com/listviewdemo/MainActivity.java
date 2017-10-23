package mad.com.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //String[] colors = {"red", "blue", "green", "white", "black", "orange"};
    ArrayList<Color> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors = new ArrayList<Color>();
        colors.add(new Color("Red","#000000"));
        colors.add(new Color("Blue","#0000FF"));
        colors.add(new Color("Green","#006600"));
        colors.add(new Color("Black","#000000"));
        colors.add(new Color("Orange","#FF6600"));
        colors.add(new Color("Grey","#BBBBBB"));
        colors.add(new Color("Brown","#654321"));
        colors.add(new Color("Unknown","#123456"));

        ListView listView = (ListView) findViewById(R.id.myListView);
        //ArrayAdapter<Color> adapter = new ArrayAdapter<Color>(this, android.R.layout.simple_list_item_1, colors);

        ColorAdapter adapter = new ColorAdapter(this, R.layout.row_item_layout, colors);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true); //OR adapter.notifyDataSetChanged();

        /*adapter.add(new Color("Purple"));
        adapter.remove(colors.get(0));
        adapter.insert(new Color("Brown"), 0);*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo","Position " + position + ", Value "+ colors.get(position).toString());
            }
        });

    }
}
