package mad.com.explicit_intents;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String name = intent.getExtras().getString(MainActivity.NAME_KEY);
            double age = intent.getExtras().getDouble(MainActivity.AGE_KEY,25); //25 is the dafault age
            User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
            Person person = (Person) getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            //Toast.makeText(this,name + " " + age,Toast.LENGTH_LONG).show();
            //Toast.makeText(this,user.toString(),Toast.LENGTH_LONG).show();
            Toast.makeText(this,person.toString(),Toast.LENGTH_LONG).show();
        }else {
            //error has happened
        }
        findViewById(R.id.button2Main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
