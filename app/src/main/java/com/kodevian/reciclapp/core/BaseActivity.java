package com.kodevian.reciclapp.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.kodevian.reciclapp.ReciclappAplication;
import com.kodevian.reciclapp.model.Location;


/**
 * Created by manu on 06/12/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ReciclappAplication application;
    private boolean flag = false;
    //GPSTracker gps;

    // abstract methods

    protected abstract void onCreateElements();
    protected abstract void initFragment();
    protected abstract void initDrawer();


    //override methods
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeKeyboard();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ReciclappAplication) getApplication();
       // gps = new GPSTracker(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        application = (ReciclappAplication) getApplication();
    }

    @Override
    protected void onDestroy() {
      //  gps.onDestroy();
        super.onDestroy();
    }

    // our methods

    public void initialProcess() {
        onCreateElements();
        initDrawer();
        initFragment();
    }

    /**
    * Reestart Activity
    */
    public void restart() {
        Intent intent = getIntent();
        intent.putExtra("flag", -1);
        finish();
        startActivity(intent);
    }

    /**
    Clean history to fragments
     */
    public void clearHistory() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    /**
    Close keyboard
     */
    public void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager input = (InputMethodManager) this
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
    Check if it is activate locations and return the point
    */
    public Location getChangedLocation() {
        /*if(gps.canGetLocation()){
            String latitude = String.valueOf(gps.getLatitude());
            String longitude = String.valueOf(gps.getLongitude());
            return new Location(latitude,longitude);
        }else{
            return null;
        }*/
        return null;
    }


    /**
     Show a message
     @param message
     */
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //getters and Setters

    public ReciclappAplication getApplicationMaven() {
        return application;
    }

    public void setApplication(ReciclappAplication application) {
        this.application = application;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
