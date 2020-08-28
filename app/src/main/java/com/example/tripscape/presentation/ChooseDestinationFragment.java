package com.example.tripscape.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
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
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Enums.Location;


import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class ChooseDestinationFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    List<Location> locationList;
    Button lastPressedButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.choose_destination,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.chooseDestinationPage);

        //TODO: Change this in order to get only the attractions for the Trip
        locationList = FirestoreData.getTripLocations();

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

        //int buttonStyle = R.style.button_style;
        //(new ContextThemeWrapper(context, buttonStyle), null, buttonStyle);
        final Button button = new Button(context);
        button.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        button.setText(location.toString());
        rowParams.setMargins(0,20,100,0);
        button.setLayoutParams(rowParams);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPressedButton != null) {
                    lastPressedButton.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                }
                button.setBackgroundColor(getResources().getColor(R.color.colorGreen));
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

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
