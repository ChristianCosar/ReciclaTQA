package com.kodevian.reciclapp.views.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.CompanyEntity;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 20/03/16.
 */
public class MapActivity extends AppCompatActivity {


    @Bind(R.id.btn_close)
    ImageButton btnClose;
    @Bind(R.id.btn_car)
    ImageButton btnCar;
    private GoogleMap googleMap;
    private CompanyEntity companyEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        ButterKnife.bind(this);
        try {
            String event_str = getIntent().getStringExtra("company");
            companyEntity = new Gson().fromJson(event_str, CompanyEntity.class);
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_car)
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setItems(R.array.map_options, mDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.btn_close)
    public void closeActivity(){
        MapActivity.this.finish();
    }

    private void initilizeMap() {
        if (googleMap == null) {

            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(false);

            googleMap.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(companyEntity.getLocation().getLatitude(),
                                    companyEntity.getLocation().getLongitude()))
                    .title(companyEntity.getAddress())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            CameraPosition camPos = new CameraPosition.Builder()
                    .target(new LatLng(companyEntity.getLocation().getLatitude(),
                           companyEntity.getLocation().getLongitude()))
                    .zoom(16)
                    .build();

            CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
            googleMap.moveCamera(camUpd3);

            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    DialogInterface.OnClickListener mDialogListener = new Dialog.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent;

            switch (which) {
                case 0:
                    intent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" +
                                    companyEntity.getLocation().getLongitude() + "," +
                                    companyEntity.getLocation().getLatitude() +
                                    "&mode=driving"));
                    startActivity(intent);
                    break;
                default:
                    try {
                        String url = "waze://?ll=" +
                                companyEntity.getLocation().getLongitude() +
                                "," +
                               companyEntity.getLocation().getLatitude() +
                                "&navigate=yes";
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception ex) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
}
