package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tripscape.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EnterDataFragment extends Fragment {
    Button buttonStartDate, buttonEndDate, buttonAttractions;
    Context context;
    DatePickerDialog  startDate, endDate;
    Map<String, Boolean> mapAttractions  = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.enter_data,
                container, false);
        context = container.getContext();
        buttonStartDate = vista.findViewById(R.id.buttonStartDate);
        buttonEndDate = vista.findViewById(R.id.buttonEndDate);
        buttonAttractions = vista.findViewById(R.id.buttonAttractions);

        initMapAttractions();
        setCalendars();

        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate.show();
            }
        });

        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDate.show();
            }
        });

        buttonAttractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.alertDialog);
                builder.setTitle("Choose attractions");
                final String[] listAttractions = getAllAttractionsKeys();

                builder.setMultiChoiceItems(listAttractions, getAllAttractionsValues(), new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos, boolean isChecked) {
                        mapAttractions.put(listAttractions[pos], isChecked);
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buttonAttractions.setText(getSelectedAttractionsText());
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return vista;
    }

    private void initMapAttractions() {
        mapAttractions.put("Hiking",false);
        mapAttractions.put("Snorkeling",true);
        mapAttractions.put("Rafting",false);
        mapAttractions.put("City",false);
        mapAttractions.put("Other",false);
    }

    private String[] getAllAttractionsKeys() {
        return mapAttractions.keySet().toArray(new String[0]);
    }

    private boolean[] getAllAttractionsValues() {
        Boolean[] values = mapAttractions.values().toArray(new Boolean[0]);
        boolean[] res = new boolean[values.length];
        for(int i=0; i<values.length; i++) {
            res[i] = values[i];
        }
        return res;
    }

    private String getSelectedAttractionsText() {
        String attractionsString = "";
        boolean first = true;
        for(String key: mapAttractions.keySet()) {
            if(mapAttractions.get(key)) {
                if(first) {
                    first = false;
                }
                else {
                    attractionsString += ", ";
                }
                attractionsString += key;
            }
        }
        return attractionsString;
    }


    private void setCalendars(){
        final Calendar startCalendar = Calendar.getInstance();
        startDate = new DatePickerDialog(context, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                buttonStartDate.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

        final Calendar endCalendar = Calendar.getInstance();
        endDate = new DatePickerDialog(context, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                buttonEndDate.setText(simpleDateFormat.format(newDate.getTime()));
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
