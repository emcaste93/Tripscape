package com.example.tripscape.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripscape.R;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Trip;

public class TripCodeActivity extends AppCompatActivity {
    TextView tripCodeTitle, tripCodeSubtitle;
    String title, subtitle, destination;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_code);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            destination = bundle.getString("destination");
            String startDate = bundle.getString("startDate");
            String endDate = bundle.getString("endDate");
            title = destination;
            subtitle = startDate + " - " + endDate;
        }

        //initialise variables
        init();
    }

    private void init() {
        tripCodeTitle = findViewById(R.id.tripCodeTitle);
        tripCodeSubtitle = findViewById(R.id.tripCodeSubtitle);
        button = findViewById(R.id.tripCodeBack);

        tripCodeTitle.setText(title);
        tripCodeSubtitle.setText(subtitle);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyTripsActivity.class);
            startActivity(intent);
        });
    }

    public void loginButtonTripCodeOnClick(View view) {
        //TODO Add confirm dialog(Log out)
        Trip.getInstance().setUserId("");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
