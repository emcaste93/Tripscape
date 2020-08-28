package com.example.tripscape.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;


import androidx.fragment.app.Fragment;

import com.example.tripscape.R;
import com.example.tripscape.model.Trip;

public class ChooseDestinationFragment extends Fragment {
    Context context;
    TableLayout tableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.choose_destination,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.chooseDestinationPage);

        //Get saved data from the trip
       /* Bundle bundle = getArguments();
          if(bundle != null) {
            trip = (Trip) bundle.getSerializable("trip");
        }*/

        addDestination();
        addDestination();
        addDestination();
        addDestination();
        return vista;
    }

    private void addDestination() {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        //Create new image view and a button
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(R.drawable.berlin);
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
        tableRow.addView(imageView);

        Button button = new Button(context);
        button.setText("Munich");
        tableRow.addView(button);

        //Add the new row to the table
        tableLayout.addView(tableRow);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
