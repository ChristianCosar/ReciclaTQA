package com.kodevian.reciclapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseActivity;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.fragments.LandingFragment;
import com.kodevian.reciclapp.views.fragments.LoginFragment;

import butterknife.Bind;


public class LoginActivity extends BaseActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreateElements() {

    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void initDrawer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        sessionManager=getApplicationMaven().getSessionManager();
        if (savedInstanceState == null)
            initialProcess();

    }

    @Override
    public void initialProcess() {
        if(!getApplicationMaven().getSessionManager().isLogin()){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    LandingFragment.newInstance(),
                    LandingFragment.class.getName()).commit();
        }else {
                gotoApp();
        }
    }

    public void gotoApp(){
        Intent i = new Intent(getBaseContext(),MainActivity.class);
        startActivity(i);
       finish();
    }





}
