package mad.com.explicit_intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static String NAME_KEY = "NAME";
    final static String AGE_KEY = "AGE";
    final static String USER_KEY = "USESR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button2Second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra(NAME_KEY,"DARSHAK MEHTA");
                intent.putExtra(AGE_KEY,(double)23.5); //if not sent it takes the default age in second Activity
                User user = new User("ALice",26.5);
                intent.putExtra(USER_KEY,user);
                startActivity(intent);
            }
        });
    }
}
