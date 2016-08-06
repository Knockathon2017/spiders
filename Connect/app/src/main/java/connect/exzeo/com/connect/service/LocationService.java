package connect.exzeo.com.connect.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import connect.exzeo.com.connect.dao.DatabaseHelper;
import connect.exzeo.com.connect.dao.DatabaseUtility;
import connect.exzeo.com.connect.event.EventNotifier;
import connect.exzeo.com.connect.modal.CurrentLocation;

/**
 * Created by subodh on 6/8/16.
 */
public class LocationService extends Service implements Runnable{

    private static final String TAG = "LocationService";
    LocationManager locationManager = null;
    LocationListener locationListener = null;
    Thread thread = null;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void onDestroy() {
        thread.interrupt();
    }


    @Override
    public void run() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                if(location != null){
                    CurrentLocation currentLocation = new CurrentLocation();

                    currentLocation.setLongitude(String.valueOf(location.getLongitude()));
                    currentLocation.setLattitude(String.valueOf(location.getLatitude()));
//                    EventNotifier event = new EventNotifier();
//                    event.notifyEvent();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }


            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {}
        };
        for(; ; ){
            handler.sendEmptyMessage(1);
            try {
                Thread.currentThread();
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    };

}
