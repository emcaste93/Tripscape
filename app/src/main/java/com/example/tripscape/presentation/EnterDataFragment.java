package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tripscape.R;
import com.example.tripscape.model.FirestoreDataAdapterImpl;
import com.example.tripscape.model.Trip;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.tripscape.model.Enums.*;

public class EnterDataFragment extends Fragment {
    Button buttonStartDate, buttonEndDate, buttonAttractions, buttonPlus, buttonMinus;
    Context context;
    DatePickerDialog  startDate, endDate;
    Map<String, Boolean> mapAttractions  = new HashMap<>();
    TextView txtViewNumPersons;
    Spinner spinnerActivities;
    ProgressDialog dialog;

    /** Main constructor of the Enter Data page */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.enter_data,
                container, false);
        context = container.getContext();
        buttonStartDate = vista.findViewById(R.id.buttonStartDate);
        buttonEndDate = vista.findViewById(R.id.buttonEndDate);
        buttonAttractions = vista.findViewById(R.id.buttonAttractions);
        buttonPlus = vista.findViewById(R.id.buttonMorePersons);
        buttonMinus = vista.findViewById(R.id.buttonLessPersons);
        txtViewNumPersons = vista.findViewById(R.id.txtViewPersons);
        spinnerActivities = vista.findViewById(R.id.spinner1);

        //init variables
        init();

        buttonStartDate.setOnClickListener(v -> startDate.show());

        buttonEndDate.setOnClickListener(v -> endDate.show());

        buttonAttractions.setOnClickListener(v -> {
            if(mapAttractions.size() == 0) {
                initMapAttractions();
            }
            final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.alertDialog);
            builder.setTitle("Choose attractions");
            final String[] listAttractions = getAllAttractionsKeys();

            builder.setMultiChoiceItems(listAttractions, getAllAttractionsValues(), (dialog, pos, isChecked) -> mapAttractions.put(listAttractions[pos], isChecked));

            builder.setPositiveButton("Done", (dialog, which) -> {
                buttonAttractions.setText(getSelectedAttractionsText());
                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        buttonPlus.setOnClickListener(v -> {
           Trip.getInstance().addPerson();
           txtViewNumPersons.setText(Integer.toString(getTripNumPersons()));
        });

        buttonMinus.setOnClickListener(v -> {
            if(getTripNumPersons() == 1) {
                return ;
            }
            Trip.getInstance().removePerson();
            txtViewNumPersons.setText(Integer.toString(getTripNumPersons()));
        });

        return vista;
    }

    private int getTripNumPersons() {
        return Trip.getInstance().getNumPersons();
    }

    private ArrayList<Activity> getTripDesiredActivities() {
        return Trip.getInstance().getDesiredActivities();
    }

    private Date getTripStartDate() {
        return Trip.getInstance().getStartDate();
    }

    private Date getTripEndDate() {
        return Trip.getInstance().getEndDate();
    }

    private void init() {
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading");
        dialog.setTitle("Please wait");
        initForm();
        initCalendars();
    }

    private void initForm() {
       txtViewNumPersons.setText(String.valueOf(getTripNumPersons()));
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
       buttonStartDate.setText(simpleDateFormat.format(getTripStartDate().getTime()));
       buttonEndDate.setText(simpleDateFormat.format(getTripEndDate().getTime()));
       buttonAttractions.setText(getSelectedAttractionsText());
       spinnerActivities.setSelection(3);
       spinnerActivities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String [] budgetArray = getResources().getStringArray(R.array.spinnerItems);
               int newBudget;
               switch (i) {
                   case 0:
                       newBudget = Integer.parseInt(budgetArray[0].substring(1,4));
                       break;
                   case 1:
                       newBudget = Integer.parseInt(budgetArray[1].substring(1,4));
                       break;
                   case 2:
                       newBudget = Integer.parseInt(budgetArray[2].substring(1,4));
                       break;
                   default:
                       newBudget = 9999;
                       break;
               }
               Trip.getInstance().setBudget(newBudget);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    }

    public void initMapAttractions() {
        ArrayList<Activity> allActivitiesArrayList;
        ArrayList<Activity> tripDesiredActivities = getTripDesiredActivities();
        Log.d(TAG, "Getting data for Attractions");
        allActivitiesArrayList =  FirestoreDataAdapterImpl.getInstance().getAllActivities();
        for (Activity activity: allActivitiesArrayList) {
            mapAttractions.put(activity.toString(),tripDesiredActivities.contains(activity));
        }
        buttonAttractions.setText(getSelectedAttractionsText());
    }

    private ArrayList<Activity> getAllActivities() {
        return FirestoreDataAdapterImpl.getInstance().getAllActivities();
    }

    private String[] getAllAttractionsKeys() {
        return mapAttractions.keySet().toArray(new String[0]);
    }

    /** Returns All possible attractions */
    private boolean[] getAllAttractionsValues() {
        Boolean[] values = mapAttractions.values().toArray(new Boolean[0]);
        boolean[] res = new boolean[values.length];
        for(int i=0; i<values.length; i++) {
            res[i] = values[i];
        }
        return res;
    }

    /** Used to display in a text-form all selected attractions, in a comma-separated way */
    private String getSelectedAttractionsText() {
        String attractionsString = "";
        boolean first = true;
        ArrayList<Activity> selectedActivities = new ArrayList<>();
        for(String key: mapAttractions.keySet()) {
            if(mapAttractions.get(key)) {
                selectedActivities.add(Activity.valueOf(key));
                if(first) {
                    first = false;
                }
                else {
                    attractionsString += ", ";
                }
                attractionsString += key;
            }
        }
        Trip.getInstance().setDesiredActivities(selectedActivities);
        return attractionsString;
    }

    /** Initialises the calendars with an initial date of today and handles it's interaction */
    private void initCalendars(){
        final Calendar startCalendar = Calendar.getInstance();
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(Trip.getInstance().getEndDate());
        startDate = new DatePickerDialog(context, R.style.datepicker, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            //Start date valid if it is today or later, and it is earlier than the end date.
            boolean b1 = newDate.getTime().compareTo((EnterDataFragment.this.getTripEndDate())) > 0;
            boolean b2 = newDate.getTime().compareTo(Calendar.getInstance().getTime()) >= 0;
            if (b2) {
                Trip.getInstance().setStartDate(newDate.getTime());
                buttonStartDate.setText(simpleDateFormat.format(EnterDataFragment.this.getTripStartDate().getTime()));
                startCalendar.setTime(newDate.getTime());
                Trip.getInstance().setStartDate(newDate.getTime());
                if (b1) {
                    //If start date is later than end date then set end date = start date
                    buttonEndDate.setText(simpleDateFormat.format(EnterDataFragment.this.getTripStartDate().getTime()));
                    endCalendar.setTime(newDate.getTime());
                    endDate.updateDate(year, monthOfYear, dayOfMonth);
                    Trip.getInstance().setEndDate(newDate.getTime());
                }
            } else {
                Snackbar.make(view, R.string.startDateError, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Cancel click event and set calendar date to old start date
                Date savedDate = Trip.getInstance().getStartDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(savedDate);
                startDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

        endDate = new DatePickerDialog(context, R.style.datepicker, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            if(newDate.getTime().compareTo(getTripStartDate()) >= 0 ) {
                Trip.getInstance().setEndDate(newDate.getTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                buttonEndDate.setText(simpleDateFormat.format(getTripEndDate().getTime()));
                Trip.getInstance().setEndDate(newDate.getTime());
            }
            else {
                Snackbar.make(view, R.string.endDateError, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Date savedDate = Trip.getInstance().getEndDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(savedDate);
                endDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
