package com.uncc.hw07;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SignUpActivity extends AppCompatActivity {
    private TextView email,password,firstName,lastName,confirmPassword;
    private Button btnSignUp,btnCancel;
    private EditText birthdayProfile;
    private FirebaseAuth auth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_icon_logo); //keep image size of 48*48 for app icon

        birthdayProfile = (EditText) findViewById(R.id.birthday);
        final int day = 0, month = 0, year = 0;
        final String[] birthday = {""};

        firstName = (TextView)findViewById(R.id.editTextFirstname);
        lastName = (TextView)findViewById(R.id.editTextLastname);
        email = (TextView)findViewById(R.id.editTextEmail);
        password = (TextView)findViewById(R.id.editTextPassword);
        confirmPassword = (TextView)findViewById(R.id.editTextConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnCancel = (Button) findViewById(R.id.buttonCancel);

        auth = FirebaseAuth.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int m = monthOfYear + 1;
                        birthday[0] = m + "/" + dayOfMonth + "/" + year;
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);
                        if(mYear-year>=13 && m<=(mMonth+1)) {
                            birthdayProfile.setText(birthday[0]);
                        }else{
                            Toast.makeText(SignUpActivity.this, "Age should be greater than 13", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, year, month, day);

        birthdayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog.updateDate(mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

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
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()  && !birthdayProfile.getText().toString().isEmpty()) {
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
                                           UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                   .setDisplayName(firstName.getText().toString()+" "+lastName.getText().toString()).build();

                                           firebaseUser.updateProfile(profileUpdates);
                                           writeNewUser(auth.getUid(), email.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),birthdayProfile.getText().toString());
                                           Toast.makeText(SignUpActivity.this, "User registered successfully:", Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                           auth.signOut();
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
                    }else if (birthdayProfile.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Date of Birth cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (confirmPassword.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Confirm password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void writeNewUser(String userId,String email, String firstName,String lastName,String dob) {
        User user = new User(firstName,lastName, email,dob);
        mUserRef.child(userId).setValue(user);
    }
}
