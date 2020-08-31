package com.example.tripscape.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Attraction;

public class AttractionDetailsFragment  extends Fragment {
    TextView tvStartLocation, tvStartTime, tvPrice, tvTransportation, tvLink;
    ImageView imageView;
    Attraction attraction;
    Context context;

    /** General constructor called when the view is created */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.attraction_details,
                container, false);
        context = container.getContext();
        tvStartLocation = vista.findViewById(R.id.txtViewStartLocation);
        tvStartTime = vista.findViewById(R.id.txtViewStartTime);
        tvPrice = vista.findViewById(R.id.txtViewPrice);
        tvTransportation = vista.findViewById(R.id.txtViewTransportation);
        tvLink = vista.findViewById(R.id.txtViewLink);
        imageView = vista.findViewById(R.id.imgVwDetails);
        //Get data from the selected the attraction
        Bundle bundle = getArguments();
        if(bundle != null) {
            attraction = (Attraction) bundle.getSerializable("attraction");
        }
        displayData();
        return vista;
    }

    private void displayData() {
        if(attraction != null) {
            tvStartLocation.setText(attraction.getStartLocation());
            tvStartTime.setText(attraction.getStartTime());
            tvPrice.setText( attraction.getPrice() + "€");
            String transportationText = attraction.getTransportation() ? "Included" : "Not included";
            tvTransportation.setText(transportationText);
            tvLink.setText(attraction.getLink());
            imageView.setImageResource(FirestoreData.getDrawableFromActivity(attraction.getActivity()));
        }
    }
}