package com.kodevian.reciclapp.views.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.presenter.GPSCompaniesPresenter;
import com.kodevian.reciclapp.services.GPSTracker;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.util.UtilityClass;
import com.kodevian.reciclapp.views.commons.GPSCompaniesView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by junior on 20/03/16.
 */
public class MapActivityGPS extends AppCompatActivity implements LocationListener, GPSCompaniesView
        , EasyPermissions.PermissionCallbacks {

    private static final int PERMISSION_LOCATION = 90;
    @Bind(R.id.btn_close)
    ImageButton btnClose;
    @Bind(R.id.btn_car)
    ImageButton btnCar;
    private GoogleMap googleMap;
    private CompanyEntity companyEntity;
    private Location location;
    private GPSTracker gpsTracker;
    private GPSCompaniesPresenter gpsCompaniesPresenter;
    private com.kodevian.reciclapp.model.Location GPSLocation;
    private ArrayList<CompanyEntity> arrayList;


    private void enabledMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.setMyLocationEnabled(true);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsTracker = new GPSTracker(this);
        gpsCompaniesPresenter = new GPSCompaniesPresenter(this);
        setContentView(R.layout.layout_map);
        ButterKnife.bind(this);
        btnCar.setVisibility(View.GONE);
        try {
            String event_str = getIntent().getStringExtra("company");
            companyEntity = new Gson().fromJson(event_str, CompanyEntity.class);
            methodRequiresPermissionLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btn_close)
    public void closeActivity() {
        MapActivityGPS.this.finish();
    }

    private void initilizeMap() {
        if (googleMap == null) {
            location = gpsTracker.getLocation();
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            enabledMyLocation();
            googleMap.setOnCameraChangeListener(getCameraChangeListener());
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    arg0.showInfoWindow();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getName().equals(arg0.getTitle())) {
                            Gson gson = new Gson();
                            String str = gson.toJson(arrayList.get(i));
                            Intent intent = new Intent(getBaseContext(), DetailCompanyActivity.class);
                            intent.putExtra("company", str);
                            startActivity(intent);
                        }
                    }
                    return true;
                }
            });

            if (location != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                gpsTracker.showSettingsAlert();
                location = gpsTracker.getLocation();
                enabledMyLocation();
                Toast.makeText(MapActivityGPS.this, "No se pudo cargar su ubicación actual", Toast.LENGTH_SHORT).show();
            }
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            enabledMyLocation();
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GoogleMap.OnCameraChangeListener getCameraChangeListener() {
        return new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {

                if (GPSLocation != null) {
                    googleMap.clear();
                    GPSLocation.setLatitude(position.target.latitude);
                    GPSLocation.setLongitude(position.target.longitude);
                    gpsCompaniesPresenter.getCompaniesGPS(GPSLocation);
                } else {
                    if (location != null) {
                        GPSLocation = new com.kodevian.reciclapp.model.Location(location.getLatitude(), location.getLongitude());
                        gpsCompaniesPresenter.getCompaniesGPS(GPSLocation);
                    } else {
                        location = gpsTracker.getLocation();
                    }
                }
            }
        };
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == PERMISSION_LOCATION) {
            initilizeMap();
            enabledMyLocation();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, getString(R.string.accept),
                R.string.action_settings, R.string.cancel, perms);
    }

    @AfterPermissionGranted(PERMISSION_LOCATION)
    private void methodRequiresPermissionLocation() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getBaseContext(), perms)) {
            initilizeMap();

        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_location),
                    PERMISSION_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void populate(ArrayList<CompanyEntity> companyEntities) {
        arrayList = companyEntities;

        if (arrayList != null)
            for (int i = 0; i < arrayList.size(); i++) {
               /* if(arrayList.get(i).getC_type().equals("R"))*/
                googleMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(arrayList.get(i).getLocation().getLatitude(),
                                        arrayList.get(i).getLocation().getLongitude()))
                        .title(arrayList.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
               /* else
                    googleMap.addMarker(new MarkerOptions()
                            .position(
                                    new LatLng(arrayList.get(i).getLocation().getLatitude(),
                                            arrayList.get(i).getLocation().getLongitude()))
                            .title(arrayList.get(i).getAddress())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/
            }

    }

    @Override
    public void setError(String error) {
        Toast.makeText(MapActivityGPS.this, error, Toast.LENGTH_SHORT).show();
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
