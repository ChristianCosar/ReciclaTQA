package com.kodevian.reciclapp.views.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.services.GPSTracker;
import com.kodevian.reciclapp.util.SessionManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by junior on 13/04/16.
 */
public class MapChoisePointActivity extends AppCompatActivity implements LocationListener, LocationSource,
        EasyPermissions.PermissionCallbacks {


    private static final int PERMISSION_LOCATION = 90;
    String tag;
    GoogleMap googleMap;
    @Bind(R.id.btn_close)
    ImageButton btnClose;
    @Bind(R.id.btn_car)
    ImageButton btnCar;
    @Bind(R.id.btn_define_point)
    Button clickMe;
    private LocationManager locationManager;
    private String provider;
    private LatLng posInicial;
    private com.kodevian.reciclapp.model.Location point_choice = null;
    private String streetName;
    private List<Address> addressList;
    private Geocoder geocoder;
    private Location location;
    private ProgressDialog mProgressDialog;
    private GPSTracker gpsTracker;
    private boolean isOpenDialog = false;

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == PERMISSION_LOCATION) {
            try {
                initilizeMap();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void enabledMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.setMyLocationEnabled(true);
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
            try {
                initilizeMap();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsTracker = new GPSTracker(this);
        String locationCompany = getIntent().getStringExtra("location");
        point_choice = new Gson().fromJson(locationCompany, com.kodevian.reciclapp.model.Location.class);
        setContentView(R.layout.layout_map_choose_point);
        ButterKnife.bind(this);
        methodRequiresPermissionLocation();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isOpenDialog = false;
    }

    @OnClick(R.id.btn_close)
    public void closeActivity() {
        MapChoisePointActivity.this.finish();
    }

    @OnClick(R.id.btn_define_point)
    public void click() {

    }

    public String getAddres(double lat, double lon) throws IOException {
        if (addressList != null) {
            if (addressList.size() != 0) {
                addressList = geocoder.getFromLocation(lat, lon, 1);
                return addressList.get(0).getAddressLine(0);
            } else {
                return null;
            }

        } else {
            return null;
        }


    }

    public void initilizeMap() throws IOException {

        if (googleMap == null) {
            location = gpsTracker.getLocation();
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    posInicial = new LatLng(latLng.latitude, latLng.longitude);
                    googleMap.clear();
                    // streetName = getAddres(latLng.latitude, latLng.longitude);
                    point_choice = new com.kodevian.reciclapp.model.Location(latLng.latitude, latLng.longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLng.latitude, latLng.longitude))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
            });

            if (point_choice != null) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point_choice.getLatitude(),
                                point_choice.getLongitude())).title(streetName)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(false);


                CameraPosition camPos = new CameraPosition.Builder()
                        .target(new LatLng(point_choice.getLatitude(), point_choice.getLongitude()))
                        .zoom(17)
                        .build();

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
            } else {

                if (location != null) {
                    isOpenDialog = false;
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(location.getLatitude(), location.getLongitude()), 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
               /*android.support.v7.app.AlertDialog alertDialog= AskOption();
                    alertDialog.show();*/

                    AskOption().show();
                } else {

                    if (!isOpenDialog) {
                        gpsTracker.showSettingsAlert();
                        isOpenDialog = true;
                    }
                    enabledMyLocation();
                    point_choice = null;

                }
            }

            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }


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

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    private android.support.v7.app.AlertDialog AskOption() {
        android.support.v7.app.AlertDialog myQuittingDialogBox = new android.support.v7.app.AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Indicar ubicación de empresa")
                .setMessage("Indique dónde se encuentra su empresa tocando en el mapa el punto que desee.")
                .setIcon(R.drawable.ic_action_location_green)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();

                    }

                })

                .create();

        return myQuittingDialogBox;

    }

    @OnClick(R.id.btn_define_point)
    public void onClick() {

        if (point_choice != null) {
            Intent i = getIntent();
            Gson gson = new Gson();

            String str = gson.toJson(point_choice);
            i.putExtra("point_choice", str);
            setResult(RESULT_OK, i);
            finish();
        }

    }
}
