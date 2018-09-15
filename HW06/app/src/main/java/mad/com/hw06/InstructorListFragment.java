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

import java.util.ArrayList;

import mad.com.hw06.Adapters.AddCourseInstructorAdapter;
import mad.com.hw06.Adapters.CourseListAdapter;
import mad.com.hw06.Adapters.InstructorListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InstructorListFragment.OnInstructorListFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InstructorListFragment extends Fragment implements AddCourseInstructorAdapter.OnInstructorItemClickListener, AddCourseInstructorAdapter.OnInstructorItemLongClickListener  {

    private OnInstructorListFragmentInteractionListener mListener;
    DatabaseManager databaseManager;
    public InstructorListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseManager = new DatabaseManager(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Instructor");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        RecyclerView instructorRecylcerView = (RecyclerView) getActivity().findViewById(R.id.instructorRecyclerView);
        instructorRecylcerView.setLayoutManager(mLayoutManager);
        ArrayList<Instructor> instructors = (ArrayList<Instructor>) databaseManager.getAllInstructor(MainActivity.username_loggedin_user);
        InstructorListAdapter instructorListAdapter = new InstructorListAdapter(instructors, getActivity(), databaseManager);
        instructorRecylcerView.setAdapter(instructorListAdapter);

        getActivity().findViewById(R.id.imageViewInstructorAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.loginContainer, new InstructorFragment(), "addInstructorFragment").addToBackStack(null).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onInstructorListFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstructorListFragmentInteractionListener) {
            mListener = (OnInstructorListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInstructorListFragmentInteractionListener");
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
    public interface OnInstructorListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onInstructorListFragmentInteraction(Uri uri);
    }


}
