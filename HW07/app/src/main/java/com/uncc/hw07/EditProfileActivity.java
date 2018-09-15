package com.uncc.hw07;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserPostRef = mRootRef.child("userPost");
    DatabaseReference mUserRef = mRootRef.child("userFriend");
    DatabaseReference mUser = mRootRef.child("user");
    private final String TAG = "tag:EditProfileActivity";
    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;
    private TextView userName;
    private EditText birthdayProfile;
    private TextView email,firstName,lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_icon_logo); //keep image size of 48*48 for app icon
        getSupportActionBar().setTitle("  My Social App");

        auth = FirebaseAuth.getInstance();
        userName = (TextView)findViewById(R.id.userName);
        userName.setText(auth.getCurrentUser().getDisplayName());
        ImageView homeButton = (ImageView)findViewById(R.id.imageViewHome);

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        firstName = (TextView)findViewById(R.id.editTextFirstname);
        lastName = (TextView)findViewById(R.id.editTextLastname);
        email = (TextView)findViewById(R.id.editTextEmail);
        email.setEnabled(false);
        birthdayProfile = (EditText) findViewById(R.id.birthday);
        final int day = 0, month = 0, year = 0;
        final String[] birthday = {""};


        User user = loadList();


        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();



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
                            Toast.makeText(EditProfileActivity.this, "Age should be greater than 13", Toast.LENGTH_SHORT).show();
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
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() ) {
                    writeNewUser(auth.getUid(), email.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),birthdayProfile.getText().toString());
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(firstName.getText().toString()+" "+lastName.getText().toString()).build();
                    firebaseUser.updateProfile(profileUpdates);
                    userName.setText(firstName.getText().toString()+" "+lastName.getText().toString());
                    Toast.makeText(EditProfileActivity.this,"Profile Updated Successfully",Toast.LENGTH_LONG).show();
                } else {
                    if (firstName.getText().toString().isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (lastName.getText().toString().isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                    }else if (birthdayProfile.getText().toString().isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, "Date of Birth cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void writeNewUser(String userId,String email, String firstName,String lastName,String dob) {
        User user = new User(firstName,lastName, email,dob);
        mUser.child(userId).setValue(user);
    }
    void setData(User user){
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        birthdayProfile.setText(user.getDob());
        email.setText(user.getEmail());
    }
    User loadList() {
        final ArrayList<String> userList = new ArrayList<String>();
        final User[] user = new User[1];
        mUser.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                    //userList.add(ds.getKey());
                    user[0] = new User();
                    user[0].setFirstName(ds.child("firstName").getValue(String.class));
                    user[0].setLastName(ds.child("lastName").getValue(String.class));
                    user[0].setDob(ds.child("dob").getValue(String.class));
                    user[0].setEmail(ds.child("email").getValue(String.class));
                    user[0].setUserId(ds.getKey());
                    setData(user[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user[0];
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            signOut();
            auth.signOut();
            startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
