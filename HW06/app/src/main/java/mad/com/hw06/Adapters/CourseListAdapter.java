package mad.com.hw06.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mad.com.hw06.Course;
import mad.com.hw06.DatabaseManager;
import mad.com.hw06.DisplayCourseFragment;
import mad.com.hw06.Instructor;
import mad.com.hw06.MainActivity;
import mad.com.hw06.R;
import mad.com.hw06.Register;
import mad.com.hw06.RegisterFragment;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    ArrayList<Course> courses;
    Context context;
    DatabaseManager databaseManager;
    OnCourseItemClickListener onCourseItemClickListener;

    public CourseListAdapter(ArrayList<Course> courses, Context context, DatabaseManager databaseManager) {
        this.courses = courses;
        this.context = context;
        this.databaseManager = databaseManager;
    }

    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CourseListAdapter.ViewHolder holder, final int position) {
        // TODO Get Instructor Details from Database using username and instructor email id
        holder.title.setText(courses.get(position).getCourseTitle());
        String courseInstructorEmailId = courses.get(position).getInstructorEmailId();
        Instructor courseInstructor = databaseManager.getInstructor(MainActivity.username_loggedin_user, courseInstructorEmailId);
        holder.instructorName.setText(courseInstructor.getInstructorFirstName() + " " + courseInstructor.getInstructorLastName());
        String schedule = courses.get(position).getScheduleDay() +" "+courses.get(position).getScheduleTimeHours()+":"+courses.get(position).getScheduleTimeMinutes()+" "+courses.get(position).getScheduleTimePeriod();
        holder.schedule.setText(schedule);
        holder.instructorImage.setImageBitmap(courseInstructor.getInstructorImage());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) holder.view.getContext();
                DisplayCourseFragment displayCourseFragment = new DisplayCourseFragment();
                Bundle args = new Bundle();
                args.putParcelable("course", courses.get(position));
                displayCourseFragment.setArguments(args);
                activity.getFragmentManager().beginTransaction().replace(R.id.loginContainer, displayCourseFragment, "displayCourseFragment").addToBackStack(null).commit();
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to delete?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        databaseManager.delete(courses.get(position));
                                        courses.remove(courses.get(position));
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView instructorName;
        TextView schedule;
        ImageView instructorImage;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            instructorName = (TextView) itemView.findViewById(R.id.textViewInstructor);
            schedule = (TextView) itemView.findViewById(R.id.textViewDay);
            instructorImage = (ImageView) itemView.findViewById(R.id.imageViewInstructor);
        }
    }

    public interface OnCourseItemClickListener {
        public void onCourseItemClicked(int position);
    }

    public interface OnCourseItemLongClickListener {
        public boolean onCourseItemLongClicked(int position);
    }
}
