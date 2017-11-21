package com.kodevian.reciclapp.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseActivity;
import com.kodevian.reciclapp.util.CircleTransform;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.fragments.CompaniesFragment;
import com.kodevian.reciclapp.views.fragments.ExploreFragment;
import com.kodevian.reciclapp.views.fragments.MyAccountFragment;
import com.kodevian.reciclapp.views.fragments.UsFragment;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by junior on 20/03/16.
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer)
    DrawerLayout drawer;
    ProgressDialog mProgressDialog;

    TextView tv_email;
    TextView tv_username;
    ImageView profile_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        if (savedInstanceState == null)
            initialProcess();

    }



    @Override
    public void initialProcess() {
        onCreateElements();
        initDrawer();
        initFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        initDrawer();
    }



    @Override
    protected void onCreateElements() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Cargando...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container,
                ExploreFragment.newInstance(), ExploreFragment.class.getName())
                .commit();
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.menu_explored);
        menuItem.setChecked(true);


    }


    @Override
    protected void initDrawer() {

        View header=navigationView.getHeaderView(0);
        tv_email=(TextView)header.findViewById(R.id.tv_email);
        tv_username=(TextView)header.findViewById(R.id.tv_username);
        profile_image=(ImageView) header.findViewById(R.id.profile_image);


        SessionManager sessionManager = this.getApplicationMaven().getSessionManager();
        try{
        tv_email.setText(sessionManager.getUserEntity().getEmail());
           tv_username.setText(sessionManager.getUserEntity().getFirst_name() + " " + sessionManager.getUserEntity().getLast_name());
            if(AccessToken.getCurrentAccessToken()!=null){

                profile_image.setVisibility(View.VISIBLE);
                Glide.with(this).load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId()
                        + "/picture?type=normal").bitmapTransform(new CircleTransform(this)).into(profile_image);
            }else{
                profile_image.setVisibility(View.VISIBLE);
            }
        }catch(JSONException e){
            Log.e("Error",e.getMessage());
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawer.closeDrawers();


                switch (menuItem.getItemId()) {

                    case R.id.menu_explored:
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.container,
                                ExploreFragment.newInstance(), ExploreFragment.class.getName())
                                .commit();
                        return true;
                    case R.id.menu_my_account:
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.container,
                                MyAccountFragment.newInstance(), MyAccountFragment.class.getName())
                                .commit();
                        return true;

                    case R.id.menu_information:
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.container,
                                UsFragment.newInstance(), UsFragment.class.getName())
                                .commit();
                        return true;
                    case R.id.menu_close_session:
                        SessionManager sessionManager = getApplicationMaven().getSessionManager();
                        sessionManager.closeSession();
                        AccessToken.setCurrentAccessToken(null);
                        Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    default:
                        return true;
                }
            }
        });
    }
    public void openMenu() {
        if (drawer.isDrawerOpen(navigationView))
            drawer.closeDrawer(navigationView);
        else
            drawer.openDrawer(navigationView);
    }

    @Override
    public void onBackPressed() {


        if(drawer.isDrawerOpen(navigationView)){
            drawer.closeDrawer(navigationView);
        }

    }
}
