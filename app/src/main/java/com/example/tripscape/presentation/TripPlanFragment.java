package com.example.tripscape.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Trip;

public class TripPlanFragment extends Fragment {
Context context;
LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.trip_plan,
                container, false);
        context = container.getContext();
        linearLayout = vista.findViewById(R.id.page_trip_plan);

        initTripPlan();
        return vista;
    }

    private void initTripPlan() {
        //Create new image view and a button
        ImageView imageView = new ImageView(context);

        imageView.setImageResource(FirestoreData.getDrawableFromLocation(Enums.Location.Berlin));
        imageView.setMinimumHeight((int) getResources().getDimension(R.dimen.imageview_height));
        imageView.setMinimumWidth((int) getResources().getDimension(R.dimen.imageview_width));
        linearLayout.addView(imageView);

        TextView tx = new TextView(context);
        tx.setText("TEST ME");
        linearLayout.addView(tx);
    }
}
