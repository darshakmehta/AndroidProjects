package com.uncc.inclass10;
/*
MainActivity.java
Gaurav Pareek
Darshak Mehta
* */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView email,password;
    private FirebaseAuth auth;
    Button btnSignUp,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (TextView)findViewById(R.id.editTextUsername);
        password = (TextView)findViewById(R.id.editTextPassword);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        auth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() ) {

                    auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login failed! Invalid email or password,\nPlease try again.", Toast.LENGTH_LONG).show();
                                    } else {
                                        startActivity(new Intent(MainActivity.this, ContactListActivity.class));
                                        finish();
                                    }
                                }
                            });
                }else{
                    if (email.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                finish();
            }
        });

    }
}
