package com.example.tripscape.presentation;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;


import androidx.fragment.app.Fragment;
import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Enums.Location;
import com.example.tripscape.model.Trip;
import com.google.android.material.button.MaterialButton;


import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class ChooseDestinationFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    List<Location> locationList;
    Button lastPressedButton;
    Location destination;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.choose_destination,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.chooseDestinationPage);

        //TODO: Change this in order to get only the attractions for the Trip
        locationList = FirestoreData.getTripLocations();
        destination = Trip.getInstance().getDestination();

        for (Location location: locationList) {
            addDestination(location);
        }

        return vista;
    }

    private void addDestination(Location location) {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        //Create new image view and a button
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(getDrawableFromLocation(location));
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
        tableRow.addView(imageView);

        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(5, getResources().getColor(R.color.colorBlue));
        gradientDrawable.setColor(getResources().getColor(R.color.colorWhite));

        final Button button = new Button(context);
        button.setTextColor(getResources().getColor(R.color.colorBlue));
        button.setText(location.toString() + System.getProperty("line.separator") + getLocationMatchString(location));
        button.setBackground(gradientDrawable);


        rowParams.setMargins(0,20,100,0);
        button.setLayoutParams(rowParams);

        if(destination == location) {
            button.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
            lastPressedButton = button;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setStroke(5, getResources().getColor(R.color.colorBlue));

                if (lastPressedButton != null) {
                    gradientDrawable.setColor(getResources().getColor(R.color.colorWhite));
                    lastPressedButton.setBackground(gradientDrawable);
                }
                final GradientDrawable gradientDrawableActive = new GradientDrawable();
                gradientDrawableActive.setShape(GradientDrawable.RECTANGLE);
                gradientDrawableActive.setStroke(5, getResources().getColor(R.color.colorBlue));
                gradientDrawableActive.setColor(getResources().getColor(R.color.colorLightGreen));
                button.setBackground(gradientDrawableActive);
                int firstLineSeparator = button.getText().toString().indexOf('\n');
                String txt = button.getText().toString().substring(0,firstLineSeparator);
                Trip.getInstance().setDestination(Location.valueOf(txt));
                lastPressedButton = button;
            }
        });

        tableRow.addView(button);

        //Add the new row to the table
        tableLayout.addView(tableRow);
    }

    private int getDrawableFromLocation(Location location) {
        switch (location) {
            case Munich:
                 return R.drawable.munich_3;
            case Berlin:
                return R.drawable.berlin;
            case Hamburg:
                return R.drawable.hamburg;
            case Black_Forest:
                return R.drawable.black_forest;
            default:
                return  R.drawable.munich_4;
        }
    }

    private String getLocationMatchString(Location location) {
        String res = "";
        List<Activity> activitiesForLocation = FirestoreData.getActivitiesForLocation(location);
        List<Activity> tripActivities = Trip.getInstance().getActivities();
        int desiredAcivities = tripActivities.size();
        int count = 0;
        for(Activity tripActivity: tripActivities) {
            if(activitiesForLocation.contains(tripActivity)) {
                count ++;
            }
        }
        int resPercentage = Math.round((count*100)/desiredAcivities);
        res = resPercentage + "% Match";

        return  res;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
