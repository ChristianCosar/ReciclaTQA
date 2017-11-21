package com.kodevian.reciclapp.views.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.util.HeaderView;
import com.kodevian.reciclapp.views.fragments.DetailCompanyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 31/03/16.
 */

public class DetailCompanyActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private TextView textView;
    private ImageView imageView;
    private CompanyEntity companyEntity;
    private ProgressDialog mProgressDialog;

    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.toolbar_header_view)
    HeaderView toolbarHeaderView;

    @Bind(R.id.float_header_view)
    HeaderView floatHeaderView;
    @Bind(R.id.fb_location)
    FloatingActionButton fbLocation;

    private boolean isHideToolbarView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_company);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Cargando...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        String event_str = getIntent().getStringExtra("company");
        companyEntity = new Gson().fromJson(event_str, CompanyEntity.class);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        imageView = (ImageView) findViewById(R.id.img_header);



        if (companyEntity.getPhoto() != null) {


            Glide.with(this)

                    .load(companyEntity.getPhoto())

                    .into(new GlideDrawableImageViewTarget(imageView));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout.setTitle(" ");

        toolbarHeaderView.bindTo(companyEntity.getName(), companyEntity.getAddress());
        floatHeaderView.bindTo(companyEntity.getName(), companyEntity.getAddress());

        appBarLayout.addOnOffsetChangedListener(this);

        //getSupportActionBar().setCustomView(textView);


        if (savedInstanceState == null)
            initialProcess();
    }

    private void initialProcess() {
        Gson gson = new Gson();
        String str = gson.toJson(companyEntity);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                DetailCompanyFragment.newInstance(str),
                DetailCompanyFragment.class.getName()).commit();
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @OnClick(R.id.fb_location)
    public void onClick() {
        Gson gson = new Gson();

        String str = gson.toJson(companyEntity);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("company", str);
        startActivity(intent);
    }
}
