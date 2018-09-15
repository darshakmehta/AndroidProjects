package com.uncc.inclass11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditUsername extends AppCompatActivity {
    private TextView email,password,firstName,lastName,confirmPassword;
    private Button btnSignUp,btnCancel;
    private EditText birthdayProfile;
    private FirebaseAuth auth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("user");
    Bitmap imageCapture;
    ImageView imageView;
    final public static int IMAGE_CAPTURE = 100;
    final public static int IMAGE_GALLERY = 101;
    private FirebaseStorage storageUpload = FirebaseStorage.getInstance();
    private StorageReference storage = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_username);
        auth = FirebaseAuth.getInstance();
        imageView =(ImageView) findViewById(R.id.imageView);
        final String phone = (String) getIntent().getStringExtra("phone");

        firstName = (TextView)findViewById(R.id.editTextFirstname);
        lastName = (TextView)findViewById(R.id.editTextLastname);
        email = (TextView)findViewById(R.id.editTextEmail);
        password = (TextView)findViewById(R.id.editTextPassword);
        confirmPassword = (TextView)findViewById(R.id.editTextConfirmPassword);
        btnSignUp = (Button)findViewById(R.id.save);

        final User[] contact = new User[1];
        mRootRef.child("user").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                contact[0] = new User();
                contact[0].setFirstName(ds.child("firstName").getValue(String.class));
                contact[0].setLastName(ds.child("lastName").getValue(String.class));
                contact[0].setEmail(ds.child("email").getValue(String.class));
                firstName.setText(contact[0].getFirstName());
                lastName.setText(contact[0].getLastName());
                email.setText(contact[0].getEmail());
                email.setEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditUsername.this);
                builder.setMessage("Choose the app to be used?")
                        .setCancelable(true)
                        .setPositiveButton("Camera",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        try {
                                            Intent action = new Intent(
                                                    "android.media.action.IMAGE_CAPTURE");
                                            //action.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                                            startActivityForResult(action, IMAGE_CAPTURE);
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                })
                        .setNegativeButton("Gallery",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                        try {
                                           /* ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                                            Intent photoPickerIntent = new Intent(
                                                    Intent.ACTION_GET_CONTENT);
                                            photoPickerIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                            photoPickerIntent.setType("image*//*");
                                            startActivityForResult(photoPickerIntent, IMAGE_GALLERY);*/
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() ) {

                        uploadImage();
                        writeNewUser(auth.getUid(), email.getText().toString(), firstName.getText().toString(), lastName.getText().toString());
                        Toast.makeText(EditUsername.this, "User registered successfully:", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditUsername.this, HomeActivity.class));
                                           /* auth.signOut();*/
                        finish();

                } else {
                    if (firstName.getText().toString().isEmpty()) {
                        Toast.makeText(EditUsername.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (lastName.getText().toString().isEmpty()) {
                        Toast.makeText(EditUsername.this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().toString().isEmpty()) {
                        Toast.makeText(EditUsername.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    void uploadImage(){

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        String path = "users/"+ email.getText().toString()+".png";
        StorageReference firememeref = storageUpload.getReference(path);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("text",firstName.getText().toString())
                .build();
        UploadTask uploadTask = firememeref.putBytes(data,metadata);

        uploadTask.addOnSuccessListener(EditUsername.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                Toast.makeText(EditUsername.this, "Image uploaded",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                int errorCode = ((StorageException) exception).getErrorCode();
                String errorMessage = exception.getMessage();

            }
        });
    }

    private void writeNewUser(String userId,String email, String firstName,String lastName) {
        User user = new User(firstName,lastName, email);
        mUserRef.child(userId).setValue(user);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CAPTURE:
                if(resultCode == MainActivity.RESULT_OK){
                    Bundle extras = data.getExtras();
                    imageCapture = (Bitmap)extras.getParcelable("data");
                    imageView.setImageBitmap(imageCapture);
                }
                break;
            case IMAGE_GALLERY:
                if(data!=null) {
                    imageCapture = null;
                    int flags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    imageView.setImageBitmap(imageCapture);
                    Uri uri = data.getData();
                    try {
                        Bitmap photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        imageCapture = photoBitmap;
                        imageView.setImageBitmap(photoBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                imageCapture = null;
                Toast.makeText(EditUsername.this, "You didn't choose and image", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
