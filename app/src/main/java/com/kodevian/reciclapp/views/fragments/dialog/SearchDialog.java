package com.kodevian.reciclapp.views.fragments.dialog;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.services.GPSTracker;
import com.kodevian.reciclapp.views.adapters.CategoriesListAdapter;
import com.kodevian.reciclapp.views.adapters.CategoriesSearchAdapter;
import com.kodevian.reciclapp.views.adapters.RecycleItemsAdapter;
import com.kodevian.reciclapp.views.commons.CompanyExploredView;

import java.util.ArrayList;


/**
 * Created by junior on 24/04/16.
 */
public class SearchDialog extends AlertDialog {

    private CompanyExploredView companyExploredView;
    public RecyclerView gridItems;
    private GridLayoutManager gridLayoutManager;
    private ImageView closeButon;
    private Button buttonSearch;
    public CategoriesSearchAdapter categoriesListAdapter;
    private CheckBox checkBoxJuridic;
    private CheckBox checkBoxReciclapp;
    private CheckBox checkBoxVerify;
    public Location currentLocation;
    private static final int NUM_OF_COLUMNS = 4;

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public SearchDialog(Context context, CompanyExploredView companyExploredView) {
        super(context);
        this.companyExploredView = companyExploredView;
        this.currentLocation = null;

        initDialog();
    }
    public SearchDialog(Context context, CompanyExploredView companyExploredView,Location location) {
        super(context);
        this.companyExploredView = companyExploredView;
        this.currentLocation=location;

        initDialog();
    }

    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_search, null);
        setView(view);
        gridItems = (RecyclerView) view.findViewById(R.id.grid_items);
        closeButon = (ImageView) view.findViewById(R.id.btn_close);
        buttonSearch = (Button) view.findViewById(R.id.btn_search);
        checkBoxJuridic = (CheckBox) view.findViewById(R.id.check_juridic);
        checkBoxReciclapp = (CheckBox) view.findViewById(R.id.check_reciclapp);
        checkBoxVerify = (CheckBox) view.findViewById(R.id.check_verify);
        categoriesListAdapter = new CategoriesSearchAdapter(getContext(), new ArrayList<RecycleItemEntity>());
        gridLayoutManager = new GridLayoutManager(getContext(), NUM_OF_COLUMNS);
        gridItems.setLayoutManager(gridLayoutManager);
        gridItems.setHasFixedSize(true);
        gridItems.setAdapter(categoriesListAdapter);
        companyExploredView.populateDialogSearch(gridItems);

        closeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyExploredView.requiredPermissonLocation();
                dismiss();

            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoriesListAdapter.getSelectedItems().size()!=0){

                    if(currentLocation!=null){
                        com.kodevian.reciclapp.model.Location location=
                                new com.kodevian.reciclapp.model.Location
                                        (currentLocation.getLatitude(),currentLocation.getLongitude());
                        companyExploredView.searchCompanies(categoriesListAdapter.getSelectItem().getId(), checkBoxReciclapp.isChecked(),
                                checkBoxJuridic.isChecked(), checkBoxVerify.isChecked(),location);
                    }else{
                        companyExploredView.searchCompanies(categoriesListAdapter.getSelectItem().getId(), checkBoxReciclapp.isChecked(),
                                checkBoxJuridic.isChecked(), checkBoxVerify.isChecked());
                    }

                    companyExploredView.requiredPermissonLocation();
                    dismiss();
                }else{
                    Toast.makeText(getContext(), "Debe selecionar una categoría", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}