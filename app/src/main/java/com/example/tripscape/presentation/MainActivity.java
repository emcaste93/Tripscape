package com.example.tripscape.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripscape.R;
import com.example.tripscape.data.AttractionList;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonNext, buttonBack;
    ChooseDestinationFragment chooseDestinationFragment;
    EnterDataFragment enterDataFragment;
    ManageActivitiesFragment manageActivitiesFragment;
    TripPlanFragment tripPlanFragment;
    int pageNum = 0;
    ImageView c1, c2, c3, c4;
    Toolbar toolbar;

    ArrayList<ImageView> circleList = new ArrayList<>();
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList;
    boolean detailsFragmentActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //initialise variables
        init();

        //Add action listeners for buttons
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNextPageOverview(true, view);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNextPageOverview(false, view);
            }
        });

        FirestoreData firestoreData = new FirestoreData();

        //generate test data locally
        //  FirestoreData.generateAttractionsData();

        //Generate Data into Firestore
       /* AttractionList attractionList = new AttractionList();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for(int id = 0; id < attractionList.getSize(); id++) {
            db.collection("attractions").add(attractionList.getElementAt(id));
        }*/
    }

    private void init() {
        chooseDestinationFragment = new ChooseDestinationFragment();
        enterDataFragment = new EnterDataFragment();
        manageActivitiesFragment = new ManageActivitiesFragment();
        tripPlanFragment = new TripPlanFragment();
        changeFragment(enterDataFragment, null);
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
        fragmentList.add(manageActivitiesFragment);
        fragmentList.add(tripPlanFragment);
        buttonNext = findViewById(R.id.nextButton);
        buttonBack = findViewById(R.id.backButton);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.action_about)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(R.string.about);
                    alertDialog.setMessage(getResources().getString(R.string.app_description));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else{
                    // do something
                }

                return false;
            }
        });

        titleList = Arrays.asList(getString(R.string.enterDataTitle), getString(R.string.chooseDestinationTitle),
                getString(R.string.manageActivitesTitle), getString(R.string.tripPlanTitle));
        toolbar.inflateMenu(R.menu.menu_manageactivities);
    }

    public void changeFragment(Fragment fragment, Attraction attraction) {
        if (AttractionDetailsFragment.class.isInstance(fragment)) {
            detailsFragmentActive = true;
            Bundle bundle = new Bundle();
            bundle.putSerializable("attraction", attraction);
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void updateNextPageOverview(boolean goNextPage, View view) {
        if (detailsFragmentActive) {
            Fragment f = fragmentList.get(pageNum);
            changeFragment(f, null);
            detailsFragmentActive = false;
            return;
        }
        //Safety check OutOfBound
        if (goNextPage && pageNum >= 3) {
            Snackbar.make(view, R.string.nextPressedError, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        if (!goNextPage && pageNum == 0) {
            Snackbar.make(view, R.string.backPressedError, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        //Reset old Page
        ImageView oldPage, newPage;
        oldPage = circleList.get(pageNum);
        oldPage.setImageResource(R.drawable.circle_empty);

        //set new Page
        pageNum = goNextPage == true ? pageNum + 1 : pageNum - 1;
        Fragment f = fragmentList.get(pageNum);
        changeFragment(f, null);
        TextView txtView = findViewById(R.id.title);
        txtView.setText(titleList.get(pageNum));
        newPage = circleList.get(pageNum);
        newPage.setImageResource(R.drawable.circle_full);
    }
}
