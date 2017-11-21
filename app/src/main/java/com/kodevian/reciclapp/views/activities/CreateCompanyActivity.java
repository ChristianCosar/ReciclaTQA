package com.kodevian.reciclapp.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseActivity;
import com.kodevian.reciclapp.views.fragments.CreateAccountFragment;
import com.kodevian.reciclapp.views.fragments.CreateCompanyFragment;
import com.kodevian.reciclapp.views.fragments.SelectCompanyFragment;

import butterknife.ButterKnife;

/**
 * Created by junior on 20/03/16.
 */
public class CreateCompanyActivity extends BaseActivity {


    @Override
    protected void onCreateElements() {

    }

    @Override
    protected void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                SelectCompanyFragment.newInstance(),
                SelectCompanyFragment.class.getName()).commit();
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


    public void finishActivity(){
        finish();
    }
}
