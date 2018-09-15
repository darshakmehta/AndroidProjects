package mad.com.inclass13test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    ArrayList<Place> mPlace;
    Context mContext;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    public PlacesAdapter(ArrayList<Place> mPlace, Context mContext) {
        this.mPlace = mPlace;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity_places, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cost.setText(mPlace.get(position).getCost());
        holder.duration.setText(mPlace.get(position).getDuration());
        holder.place.setText(mPlace.get(position).getPlace());

    }

    @Override
    public int getItemCount() {
        return mPlace.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cost, duration, place;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            place =(TextView) itemView.findViewById(R.id.textViewTripName);
            duration =(TextView) itemView.findViewById(R.id.textViewTripDuration);
            cost =(TextView) itemView.findViewById(R.id.textViewTripCostValue);
        }
    }
}
