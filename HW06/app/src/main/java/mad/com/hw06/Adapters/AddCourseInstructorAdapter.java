package mad.com.hw06.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

import mad.com.hw06.Instructor;
import mad.com.hw06.R;

public class AddCourseInstructorAdapter extends RecyclerView.Adapter<AddCourseInstructorAdapter.ViewHolder> {

    ArrayList<Instructor> instructors;
    Activity context;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    public AddCourseInstructorAdapter(ArrayList<Instructor> instructors, Activity context) {
        this.instructors = instructors;
        this.context = context;
    }
    @Override
    public AddCourseInstructorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructor_row_activity, parent, false);
        AddCourseInstructorAdapter.ViewHolder viewHolder =  new AddCourseInstructorAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddCourseInstructorAdapter.ViewHolder holder, int position) {
        String instructorName = instructors.get(position).getInstructorFirstName()+" "+instructors.get(position).getInstructorLastName();
        holder.instructorName.setText(instructorName);
        holder.instructorImage.setImageBitmap(instructors.get(position).getInstructorImage());
        holder.radioButton.setChecked(instructors.get(position).isSelected());
        holder.radioButton.setTag(new Integer(position));

        //for default check in first item
        if(position == 0 && instructors.get(0).isSelected() && holder.radioButton.isChecked())
        {
            lastChecked = holder.radioButton;
            lastCheckedPos = 0;
        }


        holder.radioButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioButton cb = (RadioButton) v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        instructors.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                instructors.get(clickedPos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instructorName;
        RadioButton radioButton;
        ImageView instructorImage;



        public ViewHolder(View itemView) {
            super(itemView);
            instructorName = (TextView) itemView.findViewById(R.id.instructorName);
            radioButton = (RadioButton) itemView.findViewById(R.id.instructorRadio);
            instructorImage = (ImageView) itemView.findViewById(R.id.instructorImage);
        }
    }

    public interface OnInstructorItemClickListener {
        public void onCourseItemClicked(int position);
    }

    public interface OnInstructorItemLongClickListener {
        public boolean onCourseItemLongClicked(int position);
    }
}
