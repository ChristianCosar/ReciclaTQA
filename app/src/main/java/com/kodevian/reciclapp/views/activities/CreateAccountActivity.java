package com.kodevian.reciclapp.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseActivity;
import com.kodevian.reciclapp.views.fragments.CreateAccountFragment;
import com.kodevian.reciclapp.views.fragments.CreateCompanyFragment;
import com.kodevian.reciclapp.views.fragments.LandingFragment;
import com.kodevian.reciclapp.views.fragments.SelectCompanyFragment;

/**
 * Created by junior on 05/04/16.
 */
public class CreateAccountActivity extends BaseActivity {



    @Override
    protected void onCreateElements() {

    }

    @Override
    protected void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                CreateAccountFragment.newInstance(),
                CreateAccountFragment.class.getName()).commit();
    }

    @Override
    protected void initDrawer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        initFragment();
    }


    public void gotoApp(){
        Intent i = new Intent(getBaseContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

}
