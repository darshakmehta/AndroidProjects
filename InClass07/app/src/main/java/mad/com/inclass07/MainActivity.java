package mad.com.inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    static ListView listView;
    static public ArrayList<App> filterAppList;
    static public ArrayList<App> appList;
    String url;
    static public TextView textViewFilter;
    public static AppAdapter adapter;
    public static Switch aSwitch;
    ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DBDataManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        progressDialog = (ProgressDialog) new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        filterAppList = new ArrayList<App>();
        listView = (ListView) findViewById(R.id.listView);
        aSwitch = (Switch) findViewById(R.id.switch1);
        databaseManager = new DBDataManager(this);
        filterAppList = (ArrayList<App>) databaseManager.getAllApps();
        textViewFilter = (TextView) findViewById(R.id.textViewFilter);
        if(filterAppList.isEmpty() || filterAppList==null){
            textViewFilter.setVisibility(View.VISIBLE);
        }else {
            textViewFilter.setVisibility(View.INVISIBLE);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    aSwitch.setText("Ascending");
                    Collections.sort(appList, new PriceComparatorAsc());
                    adapter = new AppAdapter(MainActivity.this,R.layout.row_item,appList);
                    listView.setAdapter(adapter);
                }
                else{
                    aSwitch.setText("Descending");
                    Collections.sort(appList, new PriceComparator());
                    adapter = new AppAdapter(MainActivity.this,R.layout.row_item,appList);
                    listView.setAdapter(adapter);
                }
            }
        });
        findViewById(R.id.imageViewRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                new GetAppAsyncTask(new GetAppAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(ArrayList<App> apps) {
                        progressDialog.dismiss();
                        appList = apps;
                        if(filterAppList!=null && apps!=null) {
                            for (int i=0;i<apps.size();i++) {
                                App app = apps.get(i);
                                for (int j=0;j<MainActivity.filterAppList.size();j++) {
                                    App app1 = MainActivity.filterAppList.get(j);
                                    if (app.getName().equalsIgnoreCase(app1.getName())) {
                                        appList.remove(app);
                                    }
                                }
                            }
                        }
                        if(MainActivity.aSwitch.isChecked()){
                            Collections.sort(MainActivity.appList,new PriceComparatorAsc());
                        }else {
                            Collections.sort(MainActivity.appList,new PriceComparator());
                        }
                        adapter = new AppAdapter(MainActivity.this,R.layout.row_item,appList);
                        listView.setAdapter(adapter);
                    }
                }).execute(url);
            }
        });
        url = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
        if (isConnected()) {

            new GetAppAsyncTask(new GetAppAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(ArrayList<App> apps) {
                    progressDialog.dismiss();
                    appList = apps;
                    if(filterAppList!=null && apps!=null) {
                        for (int i=0;i<apps.size();i++) {
                            App app = apps.get(i);
                            for (int j=0;j<MainActivity.filterAppList.size();j++) {
                                App app1 = MainActivity.filterAppList.get(j);
                                if (app.getName().equalsIgnoreCase(app1.getName())) {
                                    appList.remove(app);
                                }
                            }
                        }
                    }

                    Collections.sort(appList, new PriceComparatorAsc());
                    adapter = new AppAdapter(MainActivity.this,R.layout.row_item,apps);
                    listView.setAdapter(adapter);
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            App app = appList.get(position);
                            Toast.makeText(MainActivity.this,"Added to filter",Toast.LENGTH_SHORT).show();
                            filterAppList.add(app);
                            appList.remove(app);
                            databaseManager.saveApp(app);
                            if(filterAppList.isEmpty() || filterAppList==null){
                                textViewFilter = (TextView) findViewById(R.id.textViewFilter);
                                textViewFilter.setVisibility(View.VISIBLE);
                            }else {
                                textViewFilter.setVisibility(View.INVISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                            // specify an adapter (see also next example)
                            mAdapter = new AppRecyclerView(filterAppList, MainActivity.this, databaseManager);
                            mRecyclerView.setAdapter(mAdapter);
                            return false;

                        }
                    });
                }
            }).execute(url);
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true); //for efficiency purpose

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AppRecyclerView(filterAppList, MainActivity.this, databaseManager);
        mRecyclerView.setAdapter(mAdapter);

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
