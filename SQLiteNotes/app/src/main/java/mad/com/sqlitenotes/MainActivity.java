package mad.com.sqlitenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseDataManager dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        dm = new DatabaseDataManager(this);

        dm.saveNOte(new Note("Note 1", "Note 1 text"));
        dm.saveNOte(new Note("Note 2", "Note 2 text"));
        dm.saveNOte(new Note("Note 3", "Note 3 text"));
        List<Note> notes = dm.getAllNotes();
        Log.d("demo", notes.toString());
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }
}
