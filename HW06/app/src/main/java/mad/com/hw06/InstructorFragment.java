package mad.com.hw06;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import mad.com.hw06.Adapters.CourseListAdapter;
import mad.com.hw06.Adapters.InstructorListAdapter;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InstructorFragment.OnInstructorFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InstructorFragment extends Fragment {

    private OnInstructorFragmentInteractionListener mListener;
    Bitmap imageCapture;
    ImageView imageView;
    DatabaseManager databaseManager;

    public InstructorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Add Instructor");
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
                                            startActivityForResult(action, Register.IMAGE_CAPTURE);
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
                                            startActivityForResult(photoPickerIntent, Register.IMAGE_GALLERY);
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
        final EditText editTextFirstName = (EditText) getActivity().findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = (EditText) getActivity().findViewById(R.id.editTextLastName);
        final EditText editTextEmail = (EditText) getActivity().findViewById(R.id.editTextEmail);
        final EditText editTextWebsite = (EditText) getActivity().findViewById(R.id.editTextWebsite);


        getActivity().findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String firstName = editTextFirstName.getText().toString();
               String lastName = editTextLastName.getText().toString();
               String email = editTextEmail.getText().toString();
               String personalWebsite = editTextWebsite.getText().toString();

               if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() &&validateEmail(email) && !personalWebsite.isEmpty() && (imageCapture != null)) {
                   Instructor instructor = new Instructor();
                   Instructor existingInstructor = databaseManager.getInstructor(MainActivity.username_loggedin_user, email);
                   if (existingInstructor == null ) {
                       instructor.setUsername(MainActivity.username_loggedin_user);
                       instructor.setInstructorFirstName(firstName);
                       instructor.setInstructorLastName(lastName);
                       instructor.setInstructorEmailId(email);
                       instructor.setInstructorWebsite(personalWebsite);
                       instructor.setInstructorImage(imageCapture);
                       databaseManager.saveInstructor(instructor);
                        Toast.makeText(getActivity(), "Instructor Added Successfully", Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(getActivity(), "Instructor with same email-id already exists", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   if (firstName.isEmpty()) {
                       Toast.makeText(getActivity(), "First name cannot be empty", Toast.LENGTH_SHORT).show();
                   } else if (lastName.isEmpty()) {
                       Toast.makeText(getActivity(), "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                   } else if (email.isEmpty()) {
                       Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                   } else if (personalWebsite.isEmpty()) {
                       Toast.makeText(getActivity(), "Personal Website cannot be empty", Toast.LENGTH_SHORT).show();
                   } else if (imageCapture == null) {
                       Toast.makeText(getActivity(), "Please provide an image", Toast.LENGTH_SHORT).show();
                   } else if(!validateEmail(email)){
                       Toast.makeText(getActivity(), "Please provide an proper email address", Toast.LENGTH_SHORT).show();
                   }
               }

           }
       });

       getActivity().findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setMessage("Do you really want to reset?")
                       .setCancelable(true)
                       .setPositiveButton("Yes",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       editTextFirstName.setText("");
                                       editTextLastName.setText("");
                                       editTextEmail.setText("");
                                       editTextWebsite.setText("");
                                       imageCapture = null;
                                       imageView.setImageDrawable(getResources().getDrawable(R.drawable.add_photo));
                                   }
                               })
                       .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       dialog.cancel();
                                   }
                               });
               AlertDialog alert = builder.create();
               alert.show();

           }
       });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String msg) {
        if (mListener != null) {
            mListener.onInstructorFragmentInteraction(msg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstructorFragmentInteractionListener) {
            mListener = (OnInstructorFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInstructorFragmentInteractionListener {
        // TODO: Update argument type and name
        void onInstructorFragmentInteraction(String msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Register.IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    imageCapture = (Bitmap)extras.getParcelable("data");
                    imageView.setImageBitmap(imageCapture);
                }
                break;
            case Register.IMAGE_GALLERY:
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

    boolean validateEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    }
