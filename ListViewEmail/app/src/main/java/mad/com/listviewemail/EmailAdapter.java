package mad.com.listviewemail;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static mad.com.listviewemail.R.id.textViewSummary;

public class EmailAdapter extends ArrayAdapter<Email> {

    public EmailAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Email> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Email email = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.email_item, parent, false);
            holder.sender = (TextView) convertView.findViewById(R.id.textViewEmail);
            holder.subject = (TextView) convertView.findViewById(R.id.textViewSubject);
            holder.summary = (TextView) convertView.findViewById(R.id.textViewSummary);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sender.setText(email.sender);
        holder.summary.setText(email.summary);
        holder.subject.setText(email.subject);
        return convertView;
    }

    private static class ViewHolder {
        TextView subject, sender, summary;
    }
}
