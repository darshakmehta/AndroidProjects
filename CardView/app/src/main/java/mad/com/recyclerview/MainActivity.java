package mad.com.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Email> emails = new ArrayList<Email>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emails.add(new Email("Hi", "Summary 1", "dmehta9@uncc.edu"));
        emails.add(new Email("Hello", "Summary 2", "dmehta6@uncc.edu"));
        emails.add(new Email("Whats up?", "Summary 3", "dmehta7@uncc.edu"));
        emails.add(new Email("Yes", "Summary 4", "dmehta9@uncc.edu"));
        emails.add(new Email("First Email", "Summary 5", "dmehta1@uncc.edu"));
        emails.add(new Email("New to here", "Summary 6", "dmehta2@uncc.edu"));
        emails.add(new Email("New to Mail", "Summary 7", "dmehta3@uncc.edu"));

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true); //for efficiency purpose

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new EmailAdapter(emails);
        mRecyclerView.setAdapter(mAdapter);

    }
}
