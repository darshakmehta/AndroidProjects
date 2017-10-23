package mad.com.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    ArrayList<Email> mData;

    public EmailAdapter(ArrayList<Email> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, parent, false);
        ViewHolder viewHolder =  new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Email email = mData.get(position);
        holder.textViewSubject.setText(email.subject);
        holder.textViewEmail.setText(email.sender);
        holder.textViewSummary.setText(email.summary);
        holder.email = email;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSubject, textViewEmail, textViewSummary;
        Email email;

        public ViewHolder(View itemView) {
            super(itemView);
            this.email = email;
            textViewSubject = (TextView) itemView.findViewById(R.id.textViewSubject);
            textViewSummary = (TextView) itemView.findViewById(R.id.textViewSummary);
            textViewEmail = (TextView) itemView.findViewById(R.id.textViewEmail);

            itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "clicked the button " + email.sender);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","Clicked " + email.sender);
                }
            });
        }
}


}
