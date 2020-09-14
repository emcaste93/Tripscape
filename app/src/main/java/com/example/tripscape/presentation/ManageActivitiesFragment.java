package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.example.tripscape.model.FirestoreDataAdapterImpl;
import com.example.tripscape.model.Trip;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class ManageActivitiesFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    TextView priceView, plusView;
    LinearLayout ll;

    /** General constructor called when the view is created */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.manage_activities,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.manage_activities_page);
        ll = vista.findViewById(R.id.manage_activities_layout);
        priceView = null;
        plusView = null;
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
        tableParams.setMargins(20,0,40, 0);
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
        rl.setGravity(Gravity.CENTER);
        rowParams.setMargins(50,10,0,20);
        rl.setLayoutParams(rowParams);

        //Title
        TextView txtVwTitle = new TextView(context);
        txtVwTitle.setId(View.generateViewId());
        txtVwTitle.setText(attraction.getTitle());
        txtVwTitle.setTextSize(16);
        txtVwTitle.setLayoutParams(rowParams);
        txtVwTitle.setPadding(20,0,0,0);
        txtVwTitle.setTypeface(Typeface.DEFAULT_BOLD);
        txtVwTitle.setTextColor(getResources().getColor(R.color.colorBlack));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addView(txtVwTitle,lp);

        //Start location
        TextView txtViewAddress = new TextView(context);
        txtViewAddress.setText(attraction.getStartLocation());
        txtViewAddress.setLayoutParams(rowParams);
        txtViewAddress.setTextSize(10);
        txtViewAddress.setId(View.generateViewId());
        txtViewAddress.setPadding(20,20,0,0);
        txtViewAddress.setTextColor(getResources().getColor(R.color.colorBlack));
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.BELOW, txtVwTitle.getId());
        rl.addView(txtViewAddress, params2);

        //Date and price

        LinearLayout dateAndPriceLayout  = new LinearLayout(context);
        dateAndPriceLayout.setOrientation(LinearLayout.HORIZONTAL);
        dateAndPriceLayout.setLayoutParams(rowParams);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.BELOW, txtViewAddress.getId());


        //Price
        final TextView txtVwPrice = new TextView(context);
        txtVwPrice.setId(View.generateViewId());
        txtVwPrice.setText(attraction.getStartTime());
        txtVwPrice.setTextSize(12);
        rowParams.setMargins(20,0,0,0);
        txtVwPrice.setLayoutParams(rowParams);
        txtVwPrice.setPadding(0,0,0,20);
        txtVwPrice.setTypeface(Typeface.DEFAULT_BOLD);
        txtVwPrice.setTextColor(getResources().getColor(R.color.colorBlack));
        dateAndPriceLayout.addView(txtVwPrice);

        final TextView txtVwPrice2 = new TextView(context);
        txtVwPrice2.setId(View.generateViewId());
        txtVwPrice2.setText(attraction.getPrice() * Trip.getInstance().getNumPersons() + " €");
        txtVwPrice2.setTextSize(12);
       // txtVwPrice2.setLayoutParams(rowParams);
        //txtVwPrice2.setPadding(20,0,0,20);
        txtVwPrice2.setTypeface(Typeface.DEFAULT_BOLD);
        txtVwPrice2.setTextColor(getColorForPrice(attraction.getPrice()));
        dateAndPriceLayout.addView(txtVwPrice2);

        rl.addView(dateAndPriceLayout, lp2);
        //Event Listeners

        tableRow.setOnLongClickListener(new View.OnLongClickListener() {
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
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).changeFragment(new AttractionDetailsFragment(), attraction);
            }
        });
        //Add relative layout to row
        tableRow.addView(rl);

        //Button remove attraction
        Button b = new Button(context);
        b.setText("X");
        b.setTextSize(14);
        b.setGravity(Gravity.CENTER);
        b.setBackgroundColor(Color.TRANSPARENT);
        b.setLayoutParams(rowParams);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.removeView(tableRow);
                Trip.getInstance().removeSelectedAttraction(attraction);
                updatePrice();
            }
        });

        tableRow.addView(b);
        tableRow.setBackground(getResources().getDrawable(R.drawable.border));
        //Add the new row to the table
        tableLayout.addView(tableRow);
    }

    /** Displays all attractions that are available for that location and are not part of the selected */
    private ArrayList<Attraction> getNonSelectedAttractions() {
        ArrayList<Attraction> nonSelectedAttractions = new ArrayList<>();
        ArrayList<Attraction> selectedAttractions = Trip.getInstance().getSelectedAttractions();
        ArrayList<Attraction> availableAttractionsList = FirestoreDataAdapterImpl.getInstance().getAttractionsForLocation(Trip.getInstance().getDestination(), Trip.getInstance().getStartDate());
        for (Attraction attraction: availableAttractionsList) {
            if(!selectedAttractions.contains(attraction)) {
                nonSelectedAttractions.add(attraction);
            }
        }
        return  nonSelectedAttractions;
    }

    /** Displays a dialog that allows you to add non-selected attractions to your Trip plan */
    private void displayAddAttractionDialog() {
        ArrayList<String> nonSelectedAttractions = new ArrayList<>();
        for(Attraction attraction: getNonSelectedAttractions() ) {
            nonSelectedAttractions.add(attraction.getTitle() + " - " + attraction.getActivity().toString());
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

    /** Updates the label that displays the total price of the Trip */
    private void updatePrice() {
        //Init text view or remove old(to display it at the bottom)
        if(priceView == null) {
            priceView = new TextView(context);
            plusView = new TextView(context);
        }
        else {
            tableLayout.removeView(priceView);
            tableLayout.removeView(plusView);
        }

        //Plus View button info
        plusView.setGravity(Gravity.RIGHT);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(800,20,100,10);
        plusView.setLayoutParams(lp);
        plusView.setPadding(20,20,20,20);
        plusView.setBackgroundResource(R.drawable.plus);

        plusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAddAttractionDialog();
            }
        });

        tableLayout.addView(plusView);


        //Set layout and values to Text View Price
        priceView.setTextColor(getResources().getColor(R.color.colorBlue));
        priceView.setGravity(Gravity.RIGHT);
        TableLayout.LayoutParams tp = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tp.setMargins(10,20,100,10);
        priceView.setLayoutParams(tp);
        priceView.setPadding(5,5,5,5);
        priceView.setText("Price:\n" + Trip.getInstance().getTotalPrice() + " €");

        //Add Click Listener
        priceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String dialogTitle = getResources().getString(R.string.total_price_summary);
                int numPersons = Trip.getInstance().getNumPersons();
                if(numPersons == 1) {
                    dialogTitle += " (1 Person)";
                }
                else {
                    dialogTitle += " (" + Trip.getInstance().getNumPersons() + " Persons)";
                }
                builder.setTitle(dialogTitle);

                TableLayout tableLayout = new TableLayout(context);
                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,  0.6f);
                layoutParams.setMargins(10,10,10,10);
                tableLayout.setLayoutParams(layoutParams);

                //Insert a row per selected attraction
               for (Attraction attraction: Trip.getInstance().getSelectedAttractions()) {
                    TableRow tr = new TableRow(context);
                    layoutParams.setMargins(100,0,0,0);
                    tr.setLayoutParams(layoutParams);
                    TextView tx = new TextView(context);
                    tx.setText(attraction.getTitle() + " - " + attraction.getActivity().toString() + "\t\t\t");
                    tx.setTextColor(getResources().getColor(R.color.colorBlack));
                    tr.addView(tx);

                    TextView txPrice = new TextView(context);
                    txPrice.setText(attraction.getPrice() * Trip.getInstance().getNumPersons() + " €");
                    txPrice.setTextColor(getColorForPrice(attraction.getPrice()));
                    txPrice.setTypeface(Typeface.DEFAULT_BOLD);
                    txPrice.setGravity(Gravity.RIGHT);
                    tr.addView(txPrice);

                    tableLayout.addView(tr);
                }

                //Add separation Row
                TableRow tr1 = new TableRow(context);
                layoutParams.setMargins(100,0,0,0);
                tr1.setLayoutParams(layoutParams);

                TextView txLine = new TextView(context);
                txLine.setText("___________________");
                txLine.setTextColor(getResources().getColor(R.color.colorBlack));
                txLine.setTypeface(Typeface.DEFAULT_BOLD);
                tr1.addView(txLine);

                TextView txLinePrice = new TextView(context);
                txLinePrice.setText("______");
                txLinePrice.setTextColor(getResources().getColor(R.color.colorBlack));
                txLinePrice.setTypeface(Typeface.DEFAULT_BOLD);
                txLinePrice.setGravity(Gravity.RIGHT);
                tr1.addView(txLinePrice);
                tableLayout.addView(tr1);

                //Add last row with the total price
                TableRow tr = new TableRow(context);
                layoutParams.setMargins(100,0,0,0);
                tr.setLayoutParams(layoutParams);

                TextView txTotalPrice = new TextView(context);
                txTotalPrice.setText(R.string.total_price);
                txTotalPrice.setTextColor(getResources().getColor(R.color.colorBlack));
                txTotalPrice.setTypeface(Typeface.DEFAULT_BOLD);
                tr.addView(txTotalPrice);

                TextView txTotalPriceVal = new TextView(context);
                txTotalPriceVal.setText(Trip.getInstance().getTotalPrice() + " €");
                txTotalPriceVal.setTextColor(getResources().getColor(R.color.colorBlack));
                txTotalPriceVal.setTypeface(Typeface.DEFAULT_BOLD);
                txTotalPriceVal.setGravity(Gravity.RIGHT);
                tr.addView(txTotalPriceVal);
                tableLayout.addView(tr);

                builder.setView(tableLayout);

                //Add Dialog button that will just close the Dialog
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        tableLayout.addView(priceView);
    }

    /** Returns the color for the text from a price per person */
    private int getColorForPrice(double price) {
        int resColor = getResources().getColor(R.color.colorOrange);
        if(price <= 50) {
            resColor = getResources().getColor(R.color.colorGreen);
        }
        return resColor;
    }
}
