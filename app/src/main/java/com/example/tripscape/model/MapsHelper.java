package com.example.tripscape.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

public class MapsHelper implements LocationListener{
    private static final String TAG = "MapsHelper";
    private Activity activity;
    private int permissionCode;

    public MapsHelper(Activity activity, int permissionCode) {
        this.activity = activity;
        this.permissionCode = permissionCode;
    }

    public Location getCurrentLocation(Context mContext) {
        int MIN_TIME_BW_UPDATES = 1000;
        int MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
        Location loc = null;
        Double latitude, longitude;

        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        Boolean checkGPS = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        Boolean checkNetwork = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isLocationPermissionActive()) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                    "Location service is necessary to display on the map the attractions", permissionCode, activity);
        }
        else {

            //this.canGetLocation = true;
            // First get location from Network Provider
            if (checkNetwork) {
                Toast.makeText(mContext, "Network", Toast.LENGTH_SHORT).show();
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }

                    if (loc != null) {
                        Log.d(TAG, "getCurrentLocation: " + loc.getLatitude() + ", " + loc.getLongitude());
                        return loc;
                    }
                } catch (SecurityException e) {

                }
            }
        }
        // if GPS Enabled get lat/long using GPS Services
        if (checkGPS) {
          //  Toast.makeText(mContext, "GPS", Toast.LENGTH_SHORT).show();
            if (loc == null) {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                           // Toast.makeText(mContext, "LATITUDE: " + latitude + ", LONGITUDE: " + longitude, Toast.LENGTH_SHORT).show();
                            return loc;
                        }
                        else {
                            Toast.makeText(mContext, "Loc == null (1)", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (SecurityException e) {
                    Log.d(TAG, "EXCEPTION: " + e.getMessage());
                }
            }
            else {
                Toast.makeText(mContext, "Loc == null (2)", Toast.LENGTH_SHORT).show();
            }
        }
        Location locErr = null;
        return locErr;
    }

    private boolean isLocationPermissionActive() {
        return ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Permission request")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode); }}).show();
        }
        else {
            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
