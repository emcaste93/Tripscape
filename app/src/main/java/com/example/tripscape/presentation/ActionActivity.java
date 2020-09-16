package com.example.tripscape.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripscape.R;

public class ActionActivity extends AppCompatActivity {
Button buttonSearch, buttonMyTrips;

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

        buttonSearch.setOnClickListener( v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //TODO: Start activity MyTrips that displays all your last saved trips
        buttonMyTrips.setOnClickListener( v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

}
