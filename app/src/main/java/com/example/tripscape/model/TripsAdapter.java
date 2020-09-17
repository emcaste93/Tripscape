package com.example.tripscape.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.presentation.MyTripsActivity;
import com.example.tripscape.presentation.TripCodeActivity;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, subtitle;
        public ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tripTitle);
            subtitle = itemView.findViewById(R.id.tripSubtitle);
            icon = itemView.findViewById(R.id.tripIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Start activity for that trip
            Trip selectedTrip = tripList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), TripCodeActivity.class);
            intent.putExtra("destination", selectedTrip.getDestination().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            intent.putExtra("startDate", sdf.format(selectedTrip.getStartDate()));
            intent.putExtra("endDate", sdf.format(selectedTrip.getEndDate()));
            v.getContext().startActivity(intent);
        }
    }
}
