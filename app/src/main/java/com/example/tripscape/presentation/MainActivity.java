package com.example.tripscape.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripscape.R;
import com.example.tripscape.data.AttractionList;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.FirestoreDataAdapterImpl;
import com.example.tripscape.model.Trip;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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
    FirestoreDataAdapterImpl adapter;

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
    }

    private void init() {
      //  generateFirebaseData(); // USAGE:UNCOMMENT TO GENERATE NEW TEST DATA TO CLOUD FIRESTORE(Will add duplicates if not manual double check it is empty)

        chooseDestinationFragment = new ChooseDestinationFragment();
        enterDataFragment = new EnterDataFragment();
        manageActivitiesFragment = new ManageActivitiesFragment();
        tripPlanFragment = new TripPlanFragment();
        adapter = new FirestoreDataAdapterImpl();
        readFirestoreData();

        changeFragment(enterDataFragment, null);

        c1 = findViewById(R.id.circle1);
        c2 = findViewById(R.id.circle2);
        c3 = findViewById(R.id.circle3);
        c4 = findViewById(R.id.circle4);
        buttonNext = findViewById(R.id.nextButton);
        buttonBack = findViewById(R.id.backButton);
        toolbar = findViewById(R.id.toolbar);

        circleList.add(c1);
        circleList.add(c2);
        circleList.add(c3);
        circleList.add(c4);
        fragmentList.add(enterDataFragment);
        fragmentList.add(chooseDestinationFragment);
        fragmentList.add(manageActivitiesFragment);
        fragmentList.add(tripPlanFragment);

        toolbar.setOnMenuItemClickListener(item -> {

            if(item.getItemId() == R.id.action_about)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.alertDialog);
                builder.setCancelable(false);
                builder.setTitle(R.string.about);
                builder.setMessage(getResources().getString(R.string.app_description));
                builder.setPositiveButton("OK",
                        (dialog, which) -> dialog.dismiss());
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button b = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if(b != null) {
                   b.setTextSize(14);
                   b.setTypeface(Typeface.DEFAULT_BOLD);
                   b.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                TextView tv = alertDialog.findViewById(textViewId);
                tv.setTextColor(getResources().getColor(R.color.colorBlack));
            }
            else if(item.getItemId() == R.id.action_share) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.alertDialog);
                builder.setCancelable(false);
                builder.setTitle(R.string.share);
                builder.setMessage(getResources().getString(R.string.share_confirmation_question));
                builder.setPositiveButton(getResources().getString(R.string.share),
                        (dialog, which) -> {
                            if(Trip.getInstance().getSelectedAttractions().size() == 0) {
                                Toast.makeText(MainActivity.this, "You must complete the search of the attractions of the trip before sharing!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Send info per email
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                                        Uri.fromParts("mailto", "emcaste93@gmail.com", null)) ;
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tripescape - Trip data");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, Trip.getInstance().getTripData());
                                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(Intent.createChooser(emailIntent, "Send email ..."));
                                }
                            }
                            dialog.dismiss();
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button bPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button bNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(bPositive != null) {
                    bPositive.setTextSize(14);
                    bPositive.setTypeface(Typeface.DEFAULT_BOLD);
                    bPositive.setTextColor(getResources().getColor(R.color.colorGreen));
                }
                if(bNegative != null) {
                    bNegative.setTextSize(14);
                    bNegative.setTypeface(Typeface.DEFAULT_BOLD);
                    bNegative.setTextColor(getResources().getColor(R.color.colorRed));
                }
                int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                TextView tv = alertDialog.findViewById(textViewId);
                tv.setTextColor(getResources().getColor(R.color.colorBlack));
            }
            else{
                // do something
            }

            return false;
        });

        titleList = Arrays.asList(getString(R.string.enterDataTitle), getString(R.string.chooseDestinationTitle),
                getString(R.string.manageActivitesTitle), getString(R.string.tripPlanTitle));
        toolbar.inflateMenu(R.menu.menu_manageactivities);

        //Add action listeners for buttons
        buttonNext.setOnClickListener(view -> updateNextPageOverview(true, view));

        buttonBack.setOnClickListener(view -> updateNextPageOverview(false, view));
    }

    private void generateFirebaseData() {
        FirestoreDataAdapterImpl.getInstance().generateFirestoreData();
    }

    private void generateLocalData() {
        //generate test data locally
        //  FirestoreData.generateAttractionsData();

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
            displayExitDialog();
            return;
        }
        if (!goNextPage && pageNum == 0) {
            Intent intent = new Intent(this, ActionActivity.class);
            startActivity(intent);
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

    public void readFirestoreData () {
        Query query = FirebaseFirestore.getInstance()
                .collection("attractionsGermany")
                .limit(50);
        ProgressDialog loadingDialog = ProgressDialog.show(MainActivity.this, "", "Loading data. Please wait...", true);

        query.get().
                addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        //   Log.d(TAG, "onSuccess: LIST EMPTY");
                        return;
                    } else {
                        //Save data into Attraction class and initialise Selected Activities to All activities
                        List<Attraction> attractions = documentSnapshots.toObjects(Attraction.class);
                        adapter.getInstance().setFirestoreDataAttractionList(attractions);
                        Trip.getInstance().setDesiredActivities(adapter.getInstance().getAllActivities());
                        enterDataFragment.initMapAttractions();

                    }
                    loadingDialog.dismiss();
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show());
    }

    private void displayExitDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(R.string.exit);

        TextView text = dialog.findViewById(R.id.dialogText);
        text.setText(R.string.exit_text);

        ImageButton btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> dialog.dismiss());

        Button btnExitAndSave = dialog.findViewById(R.id.btnExitAndSave);
        btnExitAndSave.setOnClickListener(view -> {
            dialog.dismiss();
            FirestoreDataAdapterImpl.getInstance().saveTripData();
            Intent intent = new Intent(this, MyTripsActivity.class);
            startActivity(intent);
        });

        Button btnExit = dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(this, ActionActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }
}
