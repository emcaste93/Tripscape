package com.example.tripscape.presentation;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.tripscape.R;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.MapsHelper;
import com.example.tripscape.model.Trip;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import static android.content.ContentValues.TAG;


public class TripPlanFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    MapView mapView;
    View mView;

    public TripPlanFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.trip_plan, container, false);
       return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        mapView = (MapView) mView.findViewById(R.id.mapview);
        if(mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    private void addMarkers() {
        for (Attraction attraction : Trip.getInstance().getSelectedAttractions()) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(attraction.getCoordinates().getLatitude(), attraction.getCoordinates().getLongitude()))
                    .title(attraction.getTitle())
                    .snippet("THIS IS A SNIPPET"));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        addMarkers();

        MapsHelper gpsHelper = new MapsHelper();
        Location loc = gpsHelper.getCurrentLocation(getContext());
        if (loc!=null){
            Log.d(TAG, "onMapReady: " + loc.getLatitude() + ", " + loc.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(),loc.getLongitude()), 15.0f));
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}