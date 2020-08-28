package com.example.tripscape.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Trip;

import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class ManageActivitiesFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    List<Attraction> attractionsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.manage_activities,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.manage_activities_page);
        attractionsList = FirestoreData.getAttractionsForLocation(Trip.getInstance().getDestination());

        for (Attraction attraction: attractionsList) {
            addAttraction(attraction);
        }

        return vista;
    }

    private void addAttraction(Attraction attraction) {
        //TODO:Delete, test
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        //Create new image view
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(getDrawableFromActivity(attraction.getActivity()));
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
        tableRow.addView(imageView);

        Button b = new Button(context);
        String s = attraction != null ? attraction.getActivity().toString() : "Error";
        b.setText(s);
        rowParams.setMargins(0,20,100,0);
        b.setLayoutParams(rowParams);
        tableRow.addView(b);

        //Add the new row to the table
        tableLayout.addView(tableRow);
    }


    private int getDrawableFromActivity(Activity activity) {
        switch (activity) {
            case Hiking:
                return R.drawable.mountain;
            case Skiing:
                return R.drawable.skiing;
            case Wine_Tasting:
                return R.drawable.wine;
            case Canoeing:
                return R.drawable.kayak;
            case Sailing:
                return R.drawable.boat;
            case Sightseeing:
                return R.drawable.camera;
            default:
                return  R.drawable.munich_4;
        }
    }

}
