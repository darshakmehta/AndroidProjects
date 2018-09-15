package com.uncc.inclass10;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    TextView email,password,firstName,lastName,confirmPassword;
    Button btnSignUp,btnCancel;
    private FirebaseAuth auth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstName = (TextView)findViewById(R.id.editTextFirstname);
        lastName = (TextView)findViewById(R.id.editTextLastname);
        email = (TextView)findViewById(R.id.editTextEmail);
        password = (TextView)findViewById(R.id.editTextPassword);
        confirmPassword = (TextView)findViewById(R.id.editTextConfirmPassword);
      //  forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnCancel = (Button) findViewById(R.id.buttonCancel);

        auth = FirebaseAuth.getInstance();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()) {
                   if(password.getText().toString().matches(confirmPassword.getText().toString())) {
                       auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                               .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                                   @Override
                                   public void onComplete(@NonNull Task task) {
                                       if (!task.isSuccessful()) {
                                           Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                                   Toast.LENGTH_SHORT).show();
                                       } else {
                                           final FirebaseUser firebaseUser = auth.getCurrentUser();
                                           writeNewUser(auth.getUid(), email.getText().toString(), firstName.getText().toString(), lastName.getText().toString());
                                           Toast.makeText(SignUpActivity.this, "User registered successfully:", Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(SignUpActivity.this, ContactListActivity.class));
                                           finish();
                                       }
                                   }
                               });
                   }else
                   {
                       Toast.makeText(SignUpActivity.this, "Password, ConfirmPassword are not matching", Toast.LENGTH_SHORT).show();
                   }
                } else {
                    if (firstName.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (lastName.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (confirmPassword.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Confirm password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void writeNewUser(String userId,String email, String firstName,String lastName) {
        User user = new User(firstName,lastName, email);
        mUserRef.child(userId).setValue(user);
    }
}
