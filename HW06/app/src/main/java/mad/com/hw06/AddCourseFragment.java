package mad.com.hw06;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mad.com.hw06.Adapters.AddCourseInstructorAdapter;
import mad.com.hw06.Adapters.CourseListAdapter;

public class AddCourseFragment extends Fragment {
    public static ArrayList<Instructor> instructors;
    public Instructor selectedInstructor;
    private OnAddCourseFragmentInteractionListener mListener;
    public DatabaseManager databaseManager;
    RecyclerView addCourseRecyclerView;
    String creditHours = null;
    AddCourseInstructorAdapter addCourseInstructorAdapter;
    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Create Course");
        databaseManager = new DatabaseManager(getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final TextView noInstructor = (TextView)getActivity().findViewById(R.id.textViewNoInstructor);
        final Button createCourse = (Button)getActivity().findViewById(R.id.addCourseCreate);
        addCourseRecyclerView = (RecyclerView) getActivity().findViewById(R.id.addCourseRecyclerView);
        addCourseRecyclerView.setLayoutManager(mLayoutManager);
        instructors = (ArrayList<Instructor>) databaseManager.getAllInstructor(MainActivity.username_loggedin_user);
        if(instructors.size() < 1){
            noInstructor.setVisibility(View.VISIBLE);
            createCourse.setEnabled(false);
        }
        addCourseInstructorAdapter = new AddCourseInstructorAdapter(instructors, getActivity());
        addCourseRecyclerView.setAdapter(addCourseInstructorAdapter);
        final EditText addCourseTitle = (EditText) getActivity().findViewById(R.id.addCourseTitle);
        final EditText addCourseTimeInHours = (EditText) getActivity().findViewById(R.id.addCourseTimeInHours);
        final EditText addCourseTimeInMinutes = (EditText) getActivity().findViewById(R.id.addCourseTimeInMinutes);
        final Spinner courseDay = (Spinner) getActivity().findViewById(R.id.addCourseDay);
        final Spinner courseTime = (Spinner) getActivity().findViewById(R.id.addCourseTimeSpinner);
        final Spinner courseSemester = (Spinner) getActivity().findViewById(R.id.addCourseSemester);
        final RadioGroup radioGroupCreditHours = (RadioGroup) getActivity().findViewById(R.id.radioGroupCreditHours);
        getActivity().findViewById(R.id.addCourseCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseTitle = addCourseTitle.getText().toString();
                String courseTimeInHours = addCourseTimeInHours.getText().toString();
                String courseTimeInMinutes = addCourseTimeInMinutes.getText().toString();
                int checkedRadioButtonId = radioGroupCreditHours.getCheckedRadioButtonId();
                RadioButton creditHoursRadioButton = (RadioButton) getActivity().findViewById(checkedRadioButtonId);
                if(checkedRadioButtonId != -1){
                    creditHours = creditHoursRadioButton.getText().toString();
                }
                int hours = -1;
                int minutes = -1;
                if (!courseTimeInHours.isEmpty()) {
                    hours = Integer.parseInt(courseTimeInHours);
                }
                if (!courseTimeInMinutes.isEmpty()) {
                    minutes = Integer.parseInt(courseTimeInMinutes);
                }

                for (int i = 0; i < instructors.size(); i++) {
                    if (instructors.get(i).isSelected())
                        selectedInstructor = instructors.get(i);
                }

                if (!courseTitle.isEmpty() && !courseTimeInHours.isEmpty() && !courseTimeInMinutes.isEmpty() && selectedInstructor != null && (hours >= 1 && hours <= 12) && (minutes >= 0 && minutes < 60) && (!courseDay.getSelectedItem().toString().equals("Select")) && (!courseSemester.getSelectedItem().toString().equals("Select semester")) && (!(radioGroupCreditHours.getCheckedRadioButtonId() == -1))) {
                    Course course = new Course();
                    Course existingCourse = databaseManager.getCourse(MainActivity.username_loggedin_user, courseTitle);
                    if(existingCourse == null){
                        course.setUsername(MainActivity.username_loggedin_user);
                        course.setCourseTitle(courseTitle);
                        course.setScheduleDay(courseDay.getSelectedItem().toString());
                        course.setScheduleTimePeriod(courseTime.getSelectedItem().toString());
                        course.setSemester(courseSemester.getSelectedItem().toString());
                        course.setCreditHours(creditHours);
                        course.setScheduleTimeHours(courseTimeInHours);
                        course.setScheduleTimeMinutes(courseTimeInMinutes);
                        course.setInstructorEmailId(selectedInstructor.getInstructorEmailId());
                        databaseManager.saveCourse(course);
                        Toast.makeText(getActivity(), "Course added successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        if((existingCourse.getUsername().equals(MainActivity.username_loggedin_user)) && (existingCourse.getCourseTitle().equalsIgnoreCase(courseTitle))){
                            Toast.makeText(getActivity(), "Course with this tilte already exists", Toast.LENGTH_SHORT).show();
                        }else{
                            course.setUsername(MainActivity.username_loggedin_user);
                            course.setCourseTitle(courseTitle);
                            course.setScheduleDay(courseDay.getSelectedItem().toString());
                            course.setScheduleTimePeriod(courseTime.getSelectedItem().toString());
                            course.setSemester(courseSemester.getSelectedItem().toString());
                            course.setCreditHours(creditHours);
                            course.setScheduleTimeHours(courseTimeInHours);
                            course.setScheduleTimeMinutes(courseTimeInMinutes);
                            course.setInstructorEmailId(selectedInstructor.getInstructorEmailId());
                            databaseManager.saveCourse(course);
                            Toast.makeText(getActivity(), "Course added successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (courseTitle.isEmpty()) {
                        Toast.makeText(getActivity(), "Please provide Course Title", Toast.LENGTH_SHORT).show();
                    } else if (courseTimeInHours.isEmpty()) {
                        Toast.makeText(getActivity(), "Please provide Course Time in Hours", Toast.LENGTH_SHORT).show();
                    } else if (courseTimeInMinutes.isEmpty()) {
                        Toast.makeText(getActivity(), "Please provide Course Time in Minutes", Toast.LENGTH_SHORT).show();
                    } else if (selectedInstructor == null) {
                        Toast.makeText(getActivity(), "Please select one Instructor", Toast.LENGTH_SHORT).show();
                    } else if (!(hours >= 1 && hours <= 12)) {
                        Toast.makeText(getActivity(), "Please provides hours between 1 and 12", Toast.LENGTH_SHORT).show();
                    } else if (!(minutes >= 1 && minutes <= 59)) {
                        Toast.makeText(getActivity(), "Please provides minutes between 1 and 59", Toast.LENGTH_SHORT).show();
                    } else if(courseDay.getSelectedItem().toString().equals("Select")){
                        Toast.makeText(getActivity(), "Please select Day", Toast.LENGTH_SHORT).show();
                    } else if(radioGroupCreditHours.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getActivity(), "Please select Credit Hours", Toast.LENGTH_SHORT).show();
                    } else if(courseSemester.getSelectedItem().toString().equals("Select semester")){
                        Toast.makeText(getActivity(), "Please select Semester", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        getActivity().findViewById(R.id.addCourseButtonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you really want to reset?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        addCourseTitle.setText("");
                                        addCourseTimeInHours.setText("");
                                        addCourseTimeInMinutes.setText("");
                                        if(instructors.size() > 0)
                                        instructors.get(0).setSelected(true);
                                        courseDay.setSelection(0);
                                        courseTime.setSelection(0);
                                        courseSemester.setSelection(0);
                                        radioGroupCreditHours.clearCheck();
                                        for (int i = 0; i < instructors.size(); i++) {
                                            if (instructors.get(i).isSelected())
                                                instructors.get(i).setSelected(false);
                                        }
                                        addCourseInstructorAdapter = new AddCourseInstructorAdapter(instructors, getActivity());
                                        addCourseRecyclerView.setAdapter(addCourseInstructorAdapter);

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
        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddCourseFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddCourseFragmentInteractionListener) {
            mListener = (OnAddCourseFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddCourseFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAddCourseFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddCourseFragmentInteraction(Uri uri);
    }
}
