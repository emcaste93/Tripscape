package com.example.tripscape.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;

import java.text.SimpleDateFormat;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Trip> tripList;

    public TripsAdapter(Context context, List<Trip> tripList) {
        this.tripList = tripList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = inflater.inflate(R.layout.trip_element, parent, false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.title.setText(trip.getDestination().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.subtitle.setText(formatter.format(trip.getStartDate().getTime()) + " - " +  formatter.format(trip.getEndDate().getTime()));
        holder.icon.setImageResource(FirestoreData.getDrawableFromLocation(trip.getDestination()));
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tripTitle);
            subtitle = itemView.findViewById(R.id.tripSubtitle);
            icon = itemView.findViewById(R.id.tripIcon);
        }
    }
}
