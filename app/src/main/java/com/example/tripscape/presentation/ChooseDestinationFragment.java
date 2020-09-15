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
import com.example.tripscape.model.FirestoreDataAdapter;
import com.example.tripscape.model.FirestoreDataAdapterImpl;
import com.example.tripscape.model.Trip;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.tripscape.model.Enums.*;

public class ChooseDestinationFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    HashMap<Location, Integer> locationMap;
    LinkedHashMap<Location,Integer> sortedLocationMap;
    Button lastPressedButton;
    Location destination;
    ArrayList<Button> buttons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.choose_destination,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.chooseDestinationPage);
        sortedLocationMap = new LinkedHashMap<>();
        locationMap = new HashMap<>();
        buttons = new ArrayList<>();

        initLocationMap(FirestoreDataAdapterImpl.getInstance().getTripLocations());
        destination = Trip.getInstance().getDestination();
        orderLocationList();


        for (Location location: sortedLocationMap.keySet()) {
            addDestination(location, sortedLocationMap.get(location));
        }

        customizeSelectedButton(buttons.get(0));

        return vista;
    }

    private void initLocationMap(List<Location> locationList) {
        for(Location loc: locationList) {
            locationMap.put(loc, getLocationMatch(loc));
        }
    }

    private void orderLocationList() {
        List<Location> mapKeys = new ArrayList<>(locationMap.keySet());
        List<Integer> mapValues = new ArrayList<>(locationMap.values());

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<Location> keyIt = mapKeys.iterator();
            Location key = null;

            while (keyIt.hasNext()) {
                Location keyTmp = keyIt.next();
                int valTmp = locationMap.get(keyTmp);
                if(valTmp >= val || mapKeys.size() == 1) {
                    val = valTmp;
                    key = keyTmp;
                }
            }
            if(key != null) {
                sortedLocationMap.put(key, val);
                mapKeys.remove(key);
            }
        }
    }

    /** Adds a row for the destination location, with an image and a button */
    private void addDestination(Location location, int match) {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        //Create new image view and a button
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(FirestoreData.getDrawableFromLocation(location));
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
        tableRow.addView(imageView);

        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(5, getResources().getColor(R.color.colorBlue));
        gradientDrawable.setColor(getResources().getColor(R.color.colorWhite));

        final Button button = new Button(context);
        button.setTextColor(getResources().getColor(R.color.colorBlue));
        button.setText(location.toString() + System.getProperty("line.separator") + match + "% MATCH" );
        button.setBackground(gradientDrawable);
        button.setTextSize(10);

        rowParams.setMargins((int) getResources().getDimension(R.dimen.left_margin),(int) getResources().getDimension(R.dimen.inner_margin),(int) getResources().getDimension(R.dimen.right_margin),0);
        button.setLayoutParams(rowParams);

        if(destination == location) {
            button.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
            lastPressedButton = button;
        }

        button.setOnClickListener(view -> {
            customizeSelectedButton(button);
        });

        tableRow.addView(button);

        //Add the new row to the table
        tableLayout.addView(tableRow);
        buttons.add(button);
    }

    private void customizeSelectedButton(Button button) {
        final GradientDrawable gradientDrawable1 = new GradientDrawable();
        gradientDrawable1.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable1.setStroke(5, getResources().getColor(R.color.colorBlue));

        if (lastPressedButton != null) {
            gradientDrawable1.setColor(getResources().getColor(R.color.colorWhite));
            lastPressedButton.setBackground(gradientDrawable1);
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

    /** Calculates the 'match percentage' between your selected activities and the available for the location given as parameter */
    private int getLocationMatch(Location location) {
        List<Activity> activitiesForLocation = FirestoreDataAdapterImpl.getInstance().getActivitiesForLocation(location, Trip.getInstance().getStartDate());
        List<Activity> tripActivities = Trip.getInstance().getDesiredActivities();
        int desiredAcivities = tripActivities.size();
        int count = 0;
        for(Activity tripActivity: tripActivities) {
            if(activitiesForLocation.contains(tripActivity)) {
                count ++;
            }
        }
        int match = Math.round((count*100)/desiredAcivities);
        return  match;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
