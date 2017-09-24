package mad.com.dynamic_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        ArrayList<User> listOfUsers = new ArrayList<User>();

        listOfUsers = createUsers.generate();

        for(User user: listOfUsers) {
            listitemUI item = new listitemUI(this);
            View itemView = (View) item;
            item.first.setText("First: "+user.firstName);
            item.last.setText("First: "+user.lastName);
            item.age.setText("First: "+user.age);

            container.addView(itemView);
        }

        scrollView.addView(container);
    }
}
