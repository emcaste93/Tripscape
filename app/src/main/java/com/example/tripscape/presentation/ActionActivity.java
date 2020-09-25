package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripscape.R;
import com.example.tripscape.model.Trip;

public class ActionActivity extends AppCompatActivity {
    Button buttonSearch, buttonMyTrips;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_layout);

        //initialise variables
        init();
    }

    private void init() {
        buttonSearch = findViewById(R.id.button_search);
        buttonMyTrips = findViewById(R.id.button_mytrips);
        logo = findViewById(R.id.logo);

        logo.setOnClickListener( v -> displayAboutDialog());

        buttonSearch.setOnClickListener( v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });

        //TODO: Start activity MyTrips that displays all your last saved trips
        buttonMyTrips.setOnClickListener( v -> {
            Intent intent = new Intent(this, MyTripsActivity.class);
            startActivity(intent);
        });
    }

    private void displayAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.alertDialog);
        builder.setCancelable(false);
        builder.setTitle(R.string.about);
        builder.setMessage(getResources().getString(R.string.app_description));
        builder.setPositiveButton("OK",
                (dialog, which) -> dialog.dismiss());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if(b != null) {
            b.setTextSize(24);
            b.setTypeface(Typeface.DEFAULT_BOLD);
            b.setTextColor(getResources().getColor(R.color.colorBlack));
        }
        int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = alertDialog.findViewById(textViewId);
        tv.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    public void loginButtonActionActivityOnClick(View view) {
        //TODO Add confirm dialog(Log out)
        Trip.getInstance().setUserId("");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
