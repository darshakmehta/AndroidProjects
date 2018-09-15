package mad.com.hw06;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    private OnLoginFragmentInteractionListener mListener;
    DatabaseManager databaseManager;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Course Manager");
        databaseManager = new DatabaseManager(getActivity());
        TextView textView = (TextView) getActivity().findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.loginContainer, new RegisterFragment(), "registerFragment").addToBackStack(null).commit();
            }
        });

        getActivity().findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userfield = (EditText) getActivity().findViewById(R.id.editTextUsername);
                String username = userfield.getText().toString();
                EditText passwordfield = (EditText) getActivity().findViewById(R.id.editTextPassword);
                String password = passwordfield.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){
                    Users user = databaseManager.getUser(username);
                    if(user != null){
                        if(password.equals(user.getPassword())){
                            Toast.makeText(getActivity(), "Welcome " + user.getFirst_Name(), Toast.LENGTH_SHORT).show();
                            MainActivity.username_loggedin_user = username;
                            FragmentManager fm = getFragmentManager(); // or 'getSupportFragmentManager();'
                            int count = fm.getBackStackEntryCount();
                            for(int i = 0; i < count; ++i) {
                                fm.popBackStack();
                            }
                            MainActivity.menu.getItem(3).setVisible(true);
                            getFragmentManager().beginTransaction().replace(R.id.loginContainer, new CourseFragment(), "courseListFragment").addToBackStack(null).commit();
                        }else{
                            Toast.makeText(getActivity(), "Username or password does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(username.isEmpty() && password.isEmpty()){
                        Toast.makeText(getActivity(), "Username and password should not be empty", Toast.LENGTH_SHORT).show();
                    } else if(username.isEmpty()){
                        Toast.makeText(getActivity(), "Username should not be empty", Toast.LENGTH_SHORT).show();
                    }else if(password.isEmpty()){
                        Toast.makeText(getActivity(), "Password should not be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLoginFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnLoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLoginFragmentInteraction(Uri uri);
    }
}
