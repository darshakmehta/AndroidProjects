package mad.com.hw06;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Register extends AppCompatActivity {

    Bitmap imageCapture;
    ImageView imageView;
    DatabaseManager databaseManager;
    final public static int IMAGE_CAPTURE = 100;
    final public static int IMAGE_GALLERY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseManager = new DatabaseManager(this);

        imageView = (ImageView) findViewById(R.id.imageViewCamera);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setMessage("Choose the app to be used?")
                        .setCancelable(true)
                        .setPositiveButton("Camera",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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
                                            ActivityCompat.requestPermissions((Activity) Register.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                                            Intent photoPickerIntent = new Intent(
                                                    Intent.ACTION_GET_CONTENT);
                                            photoPickerIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                            photoPickerIntent.setType("image/*");
                                            startActivityForResult(photoPickerIntent, IMAGE_GALLERY);
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
        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstNameEditText = (EditText) findViewById(R.id.editTextFirstName);
                EditText lastNameEditText = (EditText) findViewById(R.id.editTextLastName);
                EditText userNameEditText = (EditText) findViewById(R.id.editTextUsername);
                EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty() && !password.isEmpty() && (imageCapture != null)) {
                    Users user = new Users();
                    Users existingUser = databaseManager.getUser(username);
                    if (existingUser == null ) {
                        if (password.length() < 8) {
                            Toast.makeText(Register.this, "Password should be minimum 8 characters", Toast.LENGTH_SHORT).show();
                        } else{
                            user.setFirst_Name(firstName);
                            user.setLast_Name(lastName);
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setImage(imageCapture);
                            databaseManager.saveUsers(user);
                            MainActivity.username_loggedin_user = username;
                            Intent intent = new Intent(Register.this, CourseManagerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Toast.makeText(Register.this, "Welcome " + firstName, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(Register.this, "Username Already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (firstName.isEmpty()) {
                        Toast.makeText(Register.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (lastName.isEmpty()) {
                        Toast.makeText(Register.this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (username.isEmpty()) {
                        Toast.makeText(Register.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(Register.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (imageCapture == null) {
                        Toast.makeText(Register.this, "Please provide an image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    imageCapture = (Bitmap)extras.getParcelable("data");
                    imageView.setImageBitmap(imageCapture);
                }
                break;
            case IMAGE_GALLERY:
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
                break;
            default:
                imageCapture = null;
                Toast.makeText(Register.this, "You didn't choose and image", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_list_items, menu);
        menu.getItem(3).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "Login/SignUp to proceed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.instructors:
                Toast.makeText(this, "Login/SignUp to proceed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_instructor:
                Toast.makeText(this, "Login/SignUp to proceed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                finishAffinity();
                break;
        }
        return true;
    }
}
