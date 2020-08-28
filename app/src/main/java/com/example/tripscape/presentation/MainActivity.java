package com.example.tripscape.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripscape.R;
import com.example.tripscape.model.Trip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonNext, buttonBack;
    ChooseDestinationFragment chooseDestinationFragment;
    EnterDataFragment enterDataFragment;
    int pageNum = 0;
    ImageView c1, c2, c3, c4;

    ArrayList<ImageView> circleList = new ArrayList<>();
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //initialise variables
        init();

        //Add action listeners for buttons
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateNextPageOverview(true, view);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateNextPageOverview(false, view);
            }
        });
    }

    private void init() {
        chooseDestinationFragment = new ChooseDestinationFragment();
        enterDataFragment = new EnterDataFragment();
        trip = new Trip();
        changeFragment(enterDataFragment);
        c1 = findViewById(R.id.circle1);
        c2 = findViewById(R.id.circle2);
        c3 = findViewById(R.id.circle3);
        c4 = findViewById(R.id.circle4);
        circleList.add(c1);
        circleList.add(c2);
        circleList.add(c3);
        circleList.add(c4);
        fragmentList.add(enterDataFragment);
        fragmentList.add(chooseDestinationFragment);
        buttonNext =  findViewById(R.id.nextButton);
        buttonBack =  findViewById(R.id.backButton);
        titleList =  Arrays.asList(getString(R.string.enterDataTitle), getString(R.string.chooseDestinationTitle),
                getString(R.string.manageActivitesTitle),getString(R.string.tripPlanTitle));
    }

    private void changeFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("trip", trip);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void updateNextPageOverview(boolean goNextPage, View view) {
        //Safety check OutOfBound
        if(goNextPage && pageNum >=3) {
            Snackbar.make(view, R.string.nextPressedError, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return ;
        }
        if(!goNextPage && pageNum == 0) {
            Snackbar.make(view, R.string.backPressedError, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return ;
        }

        //Reset old Page
        ImageView oldPage, newPage;
        oldPage = circleList.get(pageNum);
        oldPage.setImageResource(R.drawable.circle_empty);

        //set new Page
        pageNum = goNextPage == true ? pageNum + 1 : pageNum - 1;
        Fragment f = fragmentList.get(pageNum);
        changeFragment(f);
        TextView txtView = findViewById(R.id.title);
        txtView.setText(titleList.get(pageNum));
        newPage = circleList.get(pageNum);
        newPage.setImageResource(R.drawable.circle_full);
    }

    public void updateTrip(Trip trip){
        this.trip = trip;
    }
}
