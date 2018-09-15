package mad.com.hw06;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import mad.com.hw06.Adapters.CourseListAdapter;

public class CourseFragment extends Fragment implements CourseListAdapter.OnCourseItemClickListener, CourseListAdapter.OnCourseItemLongClickListener {

    private CoursesFragmentOnFragmentInteractionListener mListener;
    DatabaseManager databaseManager;
    public CourseListAdapter courseListAdapter;
    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Course Manager");
        databaseManager = new DatabaseManager(getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        RecyclerView courseRecyclerView = (RecyclerView) getActivity().findViewById(R.id.courseRecyclerView);
        courseRecyclerView.setHasFixedSize(true);
        courseRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<Course> courses = (ArrayList<Course>) databaseManager.getAllCourses(MainActivity.username_loggedin_user);
        courseListAdapter = new CourseListAdapter(courses, getActivity(), databaseManager);
        courseRecyclerView.setAdapter(courseListAdapter);
        ImageView addCourseButton = (ImageView) getActivity().findViewById(R.id.imageViewAddCourse);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.loginContainer, new AddCourseFragment() , "newCourseFragment").addToBackStack(null).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    public void onButtonPressed(String msg) {
        if (mListener != null) {
            mListener.onCourseFragmentInteraction(msg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CoursesFragmentOnFragmentInteractionListener) {
            mListener = (CoursesFragmentOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onCourseFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCourseItemClicked(int position) {
    }

    @Override
    public boolean onCourseItemLongClicked(int position) {
        return false;
    }


    public interface CoursesFragmentOnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCourseFragmentInteraction(String message);
    }
}
