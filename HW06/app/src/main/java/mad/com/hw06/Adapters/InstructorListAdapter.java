package mad.com.hw06.Adapters;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
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
import java.util.List;

import mad.com.hw06.Course;
import mad.com.hw06.DatabaseManager;
import mad.com.hw06.DisplayCourseFragment;
import mad.com.hw06.DisplayInstructorFragment;
import mad.com.hw06.Instructor;
import mad.com.hw06.R;

public class InstructorListAdapter extends RecyclerView.Adapter<InstructorListAdapter.ViewHolder> {

    ArrayList<Instructor> instructors;
    Activity context;
    DatabaseManager databaseManager;

    public InstructorListAdapter(ArrayList<Instructor> instructors, Activity context, DatabaseManager databaseManager) {
        this.instructors = instructors;
        this.context = context;
        this.databaseManager = databaseManager;
    }

    @Override
    public InstructorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructormenuitem, parent, false);
        InstructorListAdapter.ViewHolder viewHolder =  new InstructorListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InstructorListAdapter.ViewHolder holder, final int position) {
        String instructorName = instructors.get(position).getInstructorFirstName()+" "+instructors.get(position).getInstructorLastName();
        holder.instructorName.setText(instructorName);
        holder.instructorEmailId.setText(instructors.get(position).getInstructorEmailId());
        holder.instructorImage.setImageBitmap(instructors.get(position).getInstructorImage());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) holder.view.getContext();
                DisplayInstructorFragment displayInstructorFragment = new DisplayInstructorFragment();
                Bundle args = new Bundle();
                args.putParcelable("instructor", instructors.get(position));
                displayInstructorFragment.setArguments(args);
                activity.getFragmentManager().beginTransaction().replace(R.id.loginContainer, displayInstructorFragment, "displayInstructorFragment").addToBackStack(null).commit();
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
                                        List<Course> courses = databaseManager.getCourseByInstructorByEmailId(instructors.get(position).getInstructorEmailId());
                                        if(courses.size()>0){
                                            Toast.makeText(context, "There are existing courses associated with the instructor and you cannot delete. ", Toast.LENGTH_LONG).show();
                                        }else{
                                        databaseManager.delete(instructors.get(position));
                                        instructors.remove(instructors.get(position));
                                        notifyDataSetChanged();
                                        }
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
        return instructors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instructorName;
        TextView instructorEmailId;
        ImageView instructorImage;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            instructorName = (TextView) itemView.findViewById(R.id.instructorName);
            instructorEmailId = (TextView) itemView.findViewById(R.id.instructorEmail);
            instructorImage = (ImageView) itemView.findViewById(R.id.imageViewInstructorImage);
        }
    }

    public interface OnInstructorItemClickListener {
        public void onCourseItemClicked(int position);
    }

    public interface OnInstructorItemLongClickListener {
        public boolean onCourseItemLongClicked(int position);
    }
}
