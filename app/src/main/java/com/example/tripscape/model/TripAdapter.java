package com.example.tripscape.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreDataManager;
import com.example.tripscape.presentation.TripCodeActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Trip> tripList;
    private Context context;

    public TripAdapter(Context context, List<Trip> tripList) {
        this.tripList = tripList;
        this.context = context;
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
        holder.icon.setImageResource(FirestoreDataManager.getDrawableFromLocation(trip.getDestination()));
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, subtitle;
        public ImageView icon;
        public Button removeButton;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tripTitle);
            subtitle = itemView.findViewById(R.id.tripSubtitle);
            icon = itemView.findViewById(R.id.tripIcon);
            removeButton = itemView.findViewById(R.id.btnRemoveTrip);

            removeButton.setOnClickListener(view -> displayDialog());
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

        private void removeItem() {
            //Remove item
            int position = getAdapterPosition();
            Trip selectedTrip = tripList.get(position);
            FirestoreDataAdapterImpl.getInstance().deleteTrip(selectedTrip);
            tripList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tripList.size());
        }

        private void displayDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.alertDialog);
            builder.setCancelable(false);
            builder.setTitle(R.string.remove);
            builder.setMessage(context.getResources().getString(R.string.removeTrip_confirmation_question));
            builder.setPositiveButton(context.getResources().getString(R.string.remove),
                    (dialog, which) -> {
                        removeItem();
                        dialog.dismiss();
                    });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Button bPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button bNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if(bPositive != null) {
                bPositive.setTextSize(14);
                bPositive.setTypeface(Typeface.DEFAULT_BOLD);
                bPositive.setTextColor(context.getResources().getColor(R.color.colorGreen));
            }
            if(bNegative != null) {
                bNegative.setTextSize(14);
                bNegative.setTypeface(Typeface.DEFAULT_BOLD);
                bNegative.setTextColor(context.getResources().getColor(R.color.colorRed));
            }
            int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
            TextView tv = alertDialog.findViewById(textViewId);
            tv.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }
}
