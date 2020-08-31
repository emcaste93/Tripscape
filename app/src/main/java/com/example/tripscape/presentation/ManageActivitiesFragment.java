package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tripscape.R;
import com.example.tripscape.data.FirestoreData;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Trip;

import java.util.Arrays;
import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class ManageActivitiesFragment extends Fragment {
    Context context;
    TableLayout tableLayout;
    List<Attraction> attractionsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.manage_activities,
                container, false);
        context = container.getContext();
        tableLayout = vista.findViewById(R.id.manage_activities_page);
        attractionsList = FirestoreData.getAttractionsForLocation(Trip.getInstance().getDestination());

        for (Attraction attraction: attractionsList) {
            addAttraction(attraction);
        }

        return vista;
    }

    private void addAttraction(final Attraction attraction) {
        //TODO:Delete, test
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        //Create new row
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        //Create new image view
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(rowParams);

        imageView.setImageResource(getDrawableFromActivity(attraction.getActivity()));
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
        tableRow.addView(imageView);

        final Button b = new Button(context);
        if(attraction != null) {
            String s = attraction.getTitle();
            s += "\n" + "Price: " + attraction.getPrice() + ", Duration: " + attraction.getDuration() ;
            b.setText(s);
        }

        rowParams.setMargins(20,50,20,0);
        b.setLayoutParams(rowParams);
        b.setTextSize(10);

        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), attraction.getTitle() + "was clicked!",
                        Toast.LENGTH_LONG).show();
                final AlertDialog.Builder ad = new AlertDialog.Builder(context,R.style.alertDialog);
                List<String> options = Arrays.asList("Compartir", "Borrar ", "Insertar");
                ad.show();
                return true;
            }
        });
            /*    menu.setItems(opciones) { dialog, opcion ->
                        when (opcion) {
                    0 //Compartir
                    -> {
                        val (titulo, _, _, urlAudio) = listaLibros!![id]
                        val anim = AnimationUtils.loadAnimation(actividad, R.anim.anim_compartir)
                        anim.setAnimationListener(this@SelectorFragment)
                        v.startAnimation(anim)
                        val i = Intent(Intent.ACTION_SEND)
                        i.type = "text/plain"
                        i.putExtra(Intent.EXTRA_SUBJECT, titulo)
                        i.putExtra(Intent.EXTRA_TEXT, urlAudio)
                        startActivity(Intent.createChooser(i, "Compartir"))
                    }
                    1 //Borrar
                    -> {
                        adaptador!!.borrar(id)
                        adaptador!!.notifyDataSetChanged()
                        Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG)
                                .setAction("SI") {
                            val anim = AnimationUtils.loadAnimation(actividad, R.anim.menguar)
                            anim.setAnimationListener(this@SelectorFragment)
                            v.startAnimation(anim)
                            listaLibros?.removeAt(id)
                            adaptador?.notifyDataSetChanged()
                        }
                                .show()
                    }
                    2 //Insertar
                    -> {
                        val posicion = recyclerView!!.getChildLayoutPosition(v)
                        adaptador?.insertar(adaptador!!.getItem(posicion))
                        //adaptador.notifyDataSetChanged();
                        adaptador?.notifyItemInserted(0)
                        Snackbar.make(v, "Libro insertado", Snackbar.LENGTH_INDEFINITE)
                                .setAction("OK") { }
                                .show()
                    }
                }
                }
                menu.create().show()
            }
        });*/


        tableRow.addView(b);

    /*    Button buttonLess = new Button(context);
        buttonLess.setMaxWidth(20);
        buttonLess.setText("-");
        rowParams.setMargins(0,50,20,0);
        buttonLess.setLayoutParams(rowParams);
        buttonLess.setWidth(30);
        buttonLess.setHeight(30);
        tableRow.addView(buttonLess);*/

        //Add the new row to the table
        tableLayout.addView(tableRow);
    }


    private int getDrawableFromActivity(Activity activity) {
        switch (activity) {
            case Hiking:
                return R.drawable.mountain;
            case Skiing:
                return R.drawable.skiing;
            case Wine_Tasting:
                return R.drawable.wine;
            case Canoeing:
                return R.drawable.kayak;
            case Sailing:
                return R.drawable.boat;
            case Sightseeing:
                return R.drawable.camera;
            default:
                return  R.drawable.munich_4;
        }
    }

}
