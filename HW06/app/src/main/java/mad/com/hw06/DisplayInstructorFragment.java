package mad.com.hw06;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayInstructorFragment.OnDisplayInstructorFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DisplayInstructorFragment extends Fragment {

    private OnDisplayInstructorFragmentInteractionListener mListener;
    Bundle bundle;
    Instructor instructor;
    DatabaseManager databaseManager;
    public DisplayInstructorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseManager =  new DatabaseManager(getActivity());
        TextView displayInstructorUser = (TextView) getActivity().findViewById(R.id.displayInstructorUsername);
        TextView displayInstructorName = (TextView) getActivity().findViewById(R.id.displayInstructorName);
        TextView displayInstructorEmailId = (TextView) getActivity().findViewById(R.id.displayInstructorEmailId);
        final TextView displayInstructorWebsite = (TextView) getActivity().findViewById(R.id.displayInstructorWebsite);
        ImageView displayInstructorImage  = (ImageView) getActivity().findViewById(R.id.displayInstructorImage);

        displayInstructorUser.setText("Welcome" + " " + instructor.getUsername());
        displayInstructorName.setText(displayInstructorName.getText().toString() + " " + instructor.getInstructorFirstName() + " " + instructor.getInstructorLastName());
        displayInstructorEmailId.setText(displayInstructorEmailId.getText().toString() + " " + instructor.getInstructorEmailId());
        displayInstructorWebsite.setText(instructor.getInstructorWebsite());
        displayInstructorImage.setImageBitmap(instructor.getInstructorImage());

       /* displayInstructorWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(instructor.getInstructorWebsite()));
                //((Activity)v.getContext()).startActivity(intent);
                //v.getContext().startActivity(intent);
                getActivity().startActivity(intent);
            }
        });*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();
        if (bundle != null) {
            instructor = bundle.getParcelable("instructor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_instructor, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDisplayInstructorFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayInstructorFragmentInteractionListener) {
            mListener = (OnDisplayInstructorFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDisplayInstructorFragmentInteractionListener");
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
    public interface OnDisplayInstructorFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDisplayInstructorFragmentInteraction(Uri uri);
    }
}
