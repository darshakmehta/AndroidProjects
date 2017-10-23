package mad.com.listviewemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Email> emails = new ArrayList<Email>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emails.add(new Email("Hi","Summary 1", "dmehta9@uncc.edu"));
        emails.add(new Email("Hello","Summary 2", "dmehta6@uncc.edu"));
        emails.add(new Email("Whats up?","Summary 3", "dmehta7@uncc.edu"));
        emails.add(new Email("Yes","Summary 4", "dmehta9@uncc.edu"));
        emails.add(new Email("First Email","Summary 5", "dmehta1@uncc.edu"));
        emails.add(new Email("New to here","Summary 6", "dmehta2@uncc.edu"));
        emails.add(new Email("New to Mail","Summary 7", "dmehta3@uncc.edu"));

        ListView listView = (ListView) findViewById(R.id.ListView);

        EmailAdapter adapter = new EmailAdapter(MainActivity.this, R.layout.email_item ,emails);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo","Clicked item " + position);
            }
        });

    }

}
