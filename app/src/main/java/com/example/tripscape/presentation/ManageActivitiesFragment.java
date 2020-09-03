package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        if(attraction == null ) {
            return;
        }

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,  0.6f);
        final TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);
        tableRow.setGravity(Gravity.CENTER);

        //Create new image view
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(FirestoreData.getDrawableFromActivity(attraction.getActivity()));
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height_large);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width_large);
        tableRow.addView(imageView);

        //Info:
        RelativeLayout rl = new RelativeLayout(context);
        rowParams.setMargins(100,20,100,20);
        rl.setLayoutParams(rowParams);

        //Title
        TextView txtVwTitle = new TextView(context);
        txtVwTitle.setId(View.generateViewId());
        txtVwTitle.setText(attraction.getTitle());
        txtVwTitle.setTextSize(16);
        txtVwTitle.setLayoutParams(rowParams);
        txtVwTitle.setPadding(20,10,0,0);
        txtVwTitle.setTypeface(Typeface.DEFAULT_BOLD);
        txtVwTitle.setTextColor(getResources().getColor(R.color.colorBlack));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addView(txtVwTitle,lp);

        //More info
        TextView txtVwInfo = new TextView(context);
        txtVwInfo.setText(attraction.getStartLocation());
        txtVwInfo.setLayoutParams(rowParams);
        txtVwInfo.setTextSize(10);
        txtVwInfo.setId(View.generateViewId());
        txtVwInfo.setPadding(20,20,0,0);
        txtVwInfo.setTextColor(getResources().getColor(R.color.colorBlack));
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.BELOW, txtVwTitle.getId());
        rl.addView(txtVwInfo, params2);

        //Price
        final TextView txtVwPrice = new TextView(context);
        txtVwPrice.setId(View.generateViewId());
        txtVwPrice.setText(attraction.getPrice() + "€");
        txtVwPrice.setTextSize(12);
        txtVwPrice.setLayoutParams(rowParams);
        txtVwPrice.setPadding(20,20,0,20);
        txtVwPrice.setTypeface(Typeface.DEFAULT_BOLD);
        txtVwPrice.setTextColor(getResources().getColor(R.color.colorGreen));
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.BELOW, txtVwInfo.getId());

        rl.addView(txtVwPrice, lp1);

        //Event Listeners
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).changeFragment(new AttractionDetailsFragment(), attraction);
            }
        });

        rl.setOnLongClickListener(new View.OnLongClickListener() {
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
                                        if(Trip.getInstance().getSelectedAttractions().size() > 1) {
                                            tableLayout.removeView(tableRow);
                                            Trip.getInstance().removeSelectedAttraction(attraction);
                                            updatePrice();
                                        }
                                        else {
                                            Toast.makeText(context, "You must add more attractions to remove one", Toast.LENGTH_SHORT).show();
                                        }
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
        //tableRow.setBackground(getResources().getDrawable(R.drawable.border));
        rl.setBackground(getResources().getDrawable(R.drawable.border));
        tableRow.addView(rl);
        //Add the new row to the table
        tableLayout.addView(tableRow);
    }

    /** Displays all attractions that are available for that location and are not part of the selected */
    private ArrayList<Attraction> getNonSelectedAttractions() {
        ArrayList<Attraction> nonSelectedAttractions = new ArrayList<>();
        ArrayList<Attraction> selectedAttractions = Trip.getInstance().getSelectedAttractions();
        ArrayList<Attraction> availableAttractionsList = FirestoreData.getAttractionsForLocation(Trip.getInstance().getDestination(), Trip.getInstance().getStartDate());
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
}
