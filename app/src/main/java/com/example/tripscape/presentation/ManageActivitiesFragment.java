package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransitionImpl;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class ManageActivitiesFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    TextView priceView;

    /** General constructor called when the view is created */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.manage_activities,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.manage_activities_page);
        priceView = vista.findViewById(R.id.priceTextView);
        Trip.getInstance().initialiseSelectedAttractions();
        displayAttractions();
        return vista;
    }

    /** Used to generate the Main Menu Layout which displays all selected attractions */
    private void displayAttractions() {
        for (Attraction attraction: Trip.getInstance().getSelectedAttractions()) {
            addAttractionToMenu(attraction);
        }
        updatePrice();
    }

    /** Adds a new attraction to the Main Menu */
    private void addAttractionToMenu(final Attraction attraction) {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        final TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        //Create new image view
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(getDrawableFromActivity(attraction.getActivity()));
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
        tableRow.addView(imageView);

        final Button b = new Button(context);
        if(attraction != null) {
            String s = attraction.getTitle();
            s += "\n" + "Price: " + attraction.getPrice() + ", Duration: " + attraction.getDuration() ;
            b.setText(s);
        }

        rowParams.setMargins(20,(int) getResources().getDimension(R.dimen.top_margin),(int) getResources().getDimension(R.dimen.right_margin),0);
        b.setLayoutParams(rowParams);
        b.setTextSize(10);

        b.setOnLongClickListener(new View.OnLongClickListener() {
                                     @Override
                                     public boolean onLongClick(View v) {
                                         AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                         builder.setTitle("Manage attraction: " + attraction.getActivity() + " (" + attraction.getLocation() + ")");
                                         builder.setItems(new CharSequence[]
                                                         {"Add new", "Delete selected", "Share selected"},
                                                 new DialogInterface.OnClickListener() {
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         // The 'which' argument contains the index position
                                                         // of the selected item
                                                         switch (which) {
                                                             case 0:
                                                                 displayAddAttractionDialog();
                                                                 break;
                                                             case 1:
                                                                 tableLayout.removeView(tableRow);
                                                                 Trip.getInstance().removeSelectedAttraction(attraction);
                                                                 updatePrice();
                                                                 break;
                                                             case 2:
                                                                 Toast.makeText(context, "Share clicked!", Toast.LENGTH_SHORT).show();
                                                                 break;
                                                         }
                                                     }
                                                 });
                                         builder.create().show();
                                         return true;
                                     }
                                 });
        tableRow.addView(b);
        //Add the new row to the table
        tableLayout.addView(tableRow);
    }

    /** Displays all attractions that are available for that location and are not part of the selected */
    private ArrayList<Attraction> getNonSelectedAttractions() {
        ArrayList<Attraction> nonSelectedAttractions = new ArrayList<>();
        ArrayList<Attraction> selectedAttractions = Trip.getInstance().getSelectedAttractions();
        ArrayList<Attraction> availableAttractionsList = FirestoreData.getAttractionsForLocation(Trip.getInstance().getDestination());
        for (Attraction attraction: availableAttractionsList) {
            if(!selectedAttractions.contains(attraction)) {
                nonSelectedAttractions.add(attraction);
            }
        }
        return  nonSelectedAttractions;
    }

    private void displayAddAttractionDialog() {
        ArrayList<String> nonSelectedAttractions = new ArrayList<>();
        for(Attraction attraction: getNonSelectedAttractions() ) {
            nonSelectedAttractions.add(attraction.getActivity().toString());
        }

        if(nonSelectedAttractions.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Add attraction");
            builder.setItems(nonSelectedAttractions.toArray(new CharSequence[nonSelectedAttractions.size()]),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            Attraction newSelectedAttraction = getNonSelectedAttractions().get(which);
                            Trip.getInstance().addSelectedAttraction(newSelectedAttraction);
                            addAttractionToMenu(newSelectedAttraction);
                            updatePrice();
                        }
                    });
            builder.create().show();
        }
        else {
            Toast.makeText(context, "No more available attractions", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePrice() {
        priceView.setText("Price:\n" + Trip.getInstance().getTotalPrice() + " €");
    }

    /** Helper method used to display the images for each activity */
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
