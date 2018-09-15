package mad.com.hw06;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayCourseFragment.OnDisplayCourseFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DisplayCourseFragment extends Fragment {

    private OnDisplayCourseFragmentInteractionListener mListener;
    Bundle bundle;
    Course course;
    DatabaseManager databaseManager;
    public DisplayCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Course Manager");
        databaseManager =  new DatabaseManager(getActivity());
        TextView displayCourseUser = (TextView) getActivity().findViewById(R.id.displayInstructorUsername);
        TextView displayCourseTitle = (TextView) getActivity().findViewById(R.id.displayCourseTitle);
        TextView displayCourseInstructorName = (TextView) getActivity().findViewById(R.id.displayCourseInstructorName);
        TextView displayCourseEmailId = (TextView) getActivity().findViewById(R.id.displayCourseEmailId);
        TextView displayCourseSchedule = (TextView) getActivity().findViewById(R.id.displayCourseSchedule);
        TextView displayCourseCreditHours = (TextView) getActivity().findViewById(R.id.displayCourseCreditHours);
        TextView displayCourseSemester = (TextView) getActivity().findViewById(R.id.displayCourseSemester);
        Instructor instructor = databaseManager.getInstructor(MainActivity.username_loggedin_user, course.getInstructorEmailId());
        displayCourseUser.setText("Welcome" + " " + course.getUsername());
        displayCourseTitle.setText(displayCourseTitle.getText().toString() + " " + course.getCourseTitle());
        displayCourseInstructorName.setText(displayCourseInstructorName.getText().toString() + " " + instructor.getInstructorFirstName() + " " + instructor.getInstructorLastName());
        displayCourseEmailId.setText(displayCourseEmailId.getText().toString() + " " + course.getInstructorEmailId());
        displayCourseSchedule.setText(displayCourseSchedule.getText().toString() + " " + course.getScheduleDay() + " " + course.getScheduleTimeHours() + ":" + course.getScheduleTimeMinutes() + " " + course.getScheduleTimePeriod());
        displayCourseCreditHours.setText(displayCourseCreditHours.getText().toString() + " " + course.getCreditHours());
        displayCourseSemester.setText(displayCourseSemester.getText().toString() + " " + course.getSemester());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();
        if (bundle != null) {
            course = bundle.getParcelable("course");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_course, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDisplayCourseFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayCourseFragmentInteractionListener) {
            mListener = (OnDisplayCourseFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDisplayCourseFragmentInteractionListener");
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
    public interface OnDisplayCourseFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDisplayCourseFragmentInteraction(Uri uri);
    }
}
