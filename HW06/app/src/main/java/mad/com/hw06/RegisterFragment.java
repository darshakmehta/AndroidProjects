package mad.com.hw06;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RegisterFragment extends Fragment {

    private OnRegisterFragmentInteractionListener mListener;
    Bitmap imageCapture;
    ImageView imageView;
    DatabaseManager databaseManager;
    final public static int IMAGE_CAPTURE = 100;
    final public static int IMAGE_GALLERY = 101;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Register");
        databaseManager = new DatabaseManager(getActivity());

        imageView = (ImageView) getActivity().findViewById(R.id.imageViewCamera);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
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
        getActivity().findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstNameEditText = (EditText) getActivity().findViewById(R.id.editTextFirstName);
                EditText lastNameEditText = (EditText) getActivity().findViewById(R.id.editTextLastName);
                EditText userNameEditText = (EditText) getActivity().findViewById(R.id.editTextUsername);
                EditText passwordEditText = (EditText) getActivity().findViewById(R.id.editTextPassword);
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty() && !password.isEmpty() && (imageCapture != null)) {
                    Users user = new Users();
                    Users existingUser = databaseManager.getUser(username);
                    if (existingUser == null ) {
                        if (password.length() < 8) {
                            Toast.makeText(getActivity(), "Password should be minimum 8 characters", Toast.LENGTH_SHORT).show();
                        } else{
                            user.setFirst_Name(firstName);
                            user.setLast_Name(lastName);
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setImage(imageCapture);
                            databaseManager.saveUsers(user);
                            MainActivity.username_loggedin_user = username;
                            MainActivity.menu.getItem(3).setVisible(true);
                            Toast.makeText(getActivity(), "Welcome " + firstName, Toast.LENGTH_SHORT).show();
                            FragmentManager fm = getFragmentManager(); // or 'getSupportFragmentManager();'
                            int count = fm.getBackStackEntryCount();
                            for(int i = 0; i < count; ++i) {
                                fm.popBackStack();
                            }
                            getFragmentManager().beginTransaction().replace(R.id.loginContainer, new CourseFragment(), "courseListFragment").addToBackStack(null).commit();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Username Already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (firstName.isEmpty()) {
                        Toast.makeText(getActivity(), "First name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (lastName.isEmpty()) {
                        Toast.makeText(getActivity(), "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (username.isEmpty()) {
                        Toast.makeText(getActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (imageCapture == null) {
                        Toast.makeText(getActivity(), "Please provide an image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRegisterFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegisterFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRegisterFragmentInteraction(Uri uri);
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
                        Bitmap photoBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
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
                Toast.makeText(getActivity(), "You didn't choose and image", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
