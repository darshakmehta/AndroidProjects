package com.uncc.inclass11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private TextView btnSignUp, email, password;
    private Button btnLogin;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private final String TAG = "tag";
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_icon_logo); //keep image size of 48*48 for app icon
        getSupportActionBar().setTitle("  My Social App");*/

        SignInButton signInGoogleButton = (SignInButton) findViewById(R.id.sign_in_button);
        btnSignUp = (TextView) findViewById(R.id.buttonSignUp);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        email = (TextView) findViewById(R.id.editTextEmail);
        password = (TextView) findViewById(R.id.editTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();

       /* if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
*/

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
        mGoogleApiClient.connect();

        signInGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login failed! Invalid email or password,\nPlease try again.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Logged in successfully.", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                        finish();
                                    }
                                }
                            });
                } else {
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
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            FirebaseUserAuth(account);
        }
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        Log.d("token", googleSignInAccount.getIdToken());
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {
                        if (AuthResultTask.isSuccessful()) {
                            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Logged in successfully.", Toast.LENGTH_LONG).show();
                            mRootRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue(String.class) != null) {
                                        Log.d(TAG, "don't need to signup user");
                                    } else {
                                        Log.d(TAG, "signup user");
                                        writeNewUser(firebaseUser.getUid(), firebaseUser.getEmail(), firebaseUser.getDisplayName(), firebaseUser.getDisplayName());
                                    }
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void writeNewUser(String userId, String email, String firstName, String lastName) {
        User user = new User(firstName, lastName, email);
        mUserRef.child(userId).setValue(user);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
