package com.kodevian.reciclapp.views.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.presenter.CompanyExploredPresenter;
import com.kodevian.reciclapp.services.GPSTracker;
import com.kodevian.reciclapp.views.activities.DetailCompanyActivity;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.activities.MapActivityGPS;
import com.kodevian.reciclapp.views.adapters.CategoriesSearchAdapter;
import com.kodevian.reciclapp.views.adapters.MyCompaniesAdapter;
import com.kodevian.reciclapp.views.commons.CompanyExploredView;
import com.kodevian.reciclapp.views.fragments.dialog.SearchDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by junior on 08/04/16.
 */
public class ExploreFragment extends BaseFragment implements CompanyExploredView
        , EasyPermissions.PermissionCallbacks {
    private static final int PERMISSION_LOCATION = 90;

    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.list_items)
    RecyclerView listItems;
    @Bind(R.id.action_rigth_home)
    ImageButton actionRigthHome;
    @Bind(R.id.noComaniesText)
    TextView noComaniesText;
    @Bind(R.id.noCompanies)
    LinearLayout noCompanies;


    private CompanyExploredPresenter companyExploredPresenter;
    private MyCompaniesAdapter myCompaniesAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isSearchDialog = false;
    private SearchDialog searchDialog;

    /**
     * Constructor
     */
    public ExploreFragment() {
        setIdLayout(R.layout.layout_explore);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static ExploreFragment newInstance() {
        ExploreFragment myCompaniesFragment = new ExploreFragment();
        return myCompaniesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initActionBar() {
        actionLeft.setImageResource(R.drawable.ic_menu);
        actionRigthOff.setVisibility(View.VISIBLE);
        actionMiddle.setText("Explorar");
        actionRigthHome.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onBackActions() {
        isLoading = false;
        active = true;
        activeActionsBack();
    }

    @Override
    protected void initView() {

        listItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && totalItemCount > 0) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = true;
                            companyExploredPresenter.getMore();
                            //Do pagination.. i.e. fetch new data
                        }
                    }

                }
            }
        });


    }

    @Override
    protected void createEntities() {
        companyExploredPresenter = new CompanyExploredPresenter(getMainActivity(), this);
        listItems.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItems.setLayoutManager(linearLayoutManager);
        myCompaniesAdapter = new MyCompaniesAdapter(getMainActivity(), new ArrayList<CompanyEntity>(), companyExploredPresenter);
        listItems.setAdapter(myCompaniesAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        isSearchDialog = false;
    }

    @Override
    protected void requestServices() {
        noCompanies.setVisibility(View.GONE);
        if (!isLoading) {
            companyExploredPresenter.getCompanies();
            isLoading = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void populate(ArrayList<CompanyEntity> arrayList) {
        myCompaniesAdapter = new MyCompaniesAdapter(getMainActivity(), arrayList, companyExploredPresenter);
        if (listItems != null) {

            listItems.setAdapter(myCompaniesAdapter);
        }else{
            noCompanies.setVisibility(View.VISIBLE);
        }
        isLoading = false;
    }

    @Override
    public void setMore(ArrayList<CompanyEntity> arrayList) {

        for (int i = 0; i < arrayList.size(); i++) {
            myCompaniesAdapter.addCompany(arrayList.get(i));
        }
        isLoading = false;
        active = true;
    }

    @Override
    public void detailCompany(CompanyEntity companyEntity) {
       /* if (!isLoading && active) {
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, DetailCompanyFragment.newInstance(ExploreFragment.class.getName()), DetailCompanyFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active = false;
        }*/


        Gson gson = new Gson();
        String str = gson.toJson(companyEntity);
        Intent intent = new Intent(getMainActivity().getBaseContext(), DetailCompanyActivity.class);
        intent.putExtra("company", str);
        startActivity(intent);
    }

    @Override
    public void getCategories(ArrayList<RecycleItemEntity> arrayList) {
        if (searchDialog != null) {
            if (arrayList != null) {
                searchDialog.categoriesListAdapter = new CategoriesSearchAdapter(getMainActivity(), arrayList);
                searchDialog.gridItems.setAdapter(searchDialog.categoriesListAdapter);

            }

        }

    }

    @Override
    public void searchCompanies(String idCategory, boolean reciclapp, boolean juridic, boolean verify, Location locationGPS) {
        companyExploredPresenter.searchCompanies(idCategory, reciclapp, juridic, verify, locationGPS);
    }

    @Override
    public void searchCompanies(String idCategory, boolean reciclapp, boolean juridic, boolean verify) {
        companyExploredPresenter.searchCompanies(idCategory, reciclapp, juridic, verify);
    }


    @Override
    public void foundCompanies() {

    }

    @Override
    public void populateDialogSearch(RecyclerView recyclerView) {

        companyExploredPresenter.getCategories();

    }

    @Override
    public void requiredPermissonLocation() {
        isSearchDialog = false;
    }

    @Override
    public void showLoad() {
        if (viewMessage != null) {
            viewMessage.showMessage(Messages.LOAD);
        }
    }

    @Override
    public void hideLoad() {
        if (viewMessage != null) {
            viewMessage.hideMessage();
        }
        isLoading = false;
        active = true;
    }

    @Override
    public void deleteFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @OnClick(R.id.action_left)
    @SuppressWarnings("unused")
    public void back() {
        deleteFragment();
    }

    @Override
    public void setError(String msg) {
        getMainActivity().showMessage(msg);
    }

    @OnClick(R.id.action_left)
    public void onClick() {
        ((MainActivity) getMainActivity()).openMenu();
    }

    @OnClick({R.id.action_rigth_off, R.id.action_right, R.id.action_rigth_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_rigth_off:

                LocationManager locationManager = (LocationManager) getContext().
                        getSystemService(getContext().LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    methodRequiresPermissionLocation();
                } else {
                    if (searchDialog == null) {
                        searchDialog = new SearchDialog(getContext(), this);
                        searchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(final DialogInterface arg0) {
                                isSearchDialog = false;
                            }
                        });
                        searchDialog.show();
                        isSearchDialog = true;
                    } else {
                        searchDialog.show();
                        isSearchDialog = true;
                    }


                }

                break;
            case R.id.action_right:
                Intent intent = new Intent(getMainActivity().getBaseContext(), MapActivityGPS.class);
                startActivity(intent);
                break;

            case R.id.action_rigth_home:
                if (!isLoading) {
                    companyExploredPresenter.getCompanies();
                    isLoading = true;
                }
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        if (requestCode == PERMISSION_LOCATION) {
            if (!isSearchDialog) {
                GPSTracker gpsTracker = new GPSTracker(getContext());
                if (searchDialog == null) {
                    searchDialog = new SearchDialog(getContext(), this, gpsTracker.getLocation());
                    searchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(final DialogInterface arg0) {
                            isSearchDialog = false;
                        }
                    });
                    searchDialog.show();
                    isSearchDialog = true;
                } else {
                    searchDialog.setCurrentLocation(gpsTracker.getLocation());
                    searchDialog.show();
                    isSearchDialog = true;
                }


            }
        }

    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, getString(R.string.accept),
                R.string.action_settings, R.string.cancel, perms);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(PERMISSION_LOCATION)
    private void methodRequiresPermissionLocation() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            if (!isSearchDialog) {
                GPSTracker gpsTracker = new GPSTracker(getContext());
                if (searchDialog == null) {
                    searchDialog = new SearchDialog(getContext(), this, gpsTracker.getLocation());
                    searchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(final DialogInterface arg0) {
                            isSearchDialog = false;
                        }

                    });
                    searchDialog.show();
                    isSearchDialog = true;
                } else {
                    searchDialog.setCurrentLocation(gpsTracker.getLocation());
                    searchDialog.show();
                    isSearchDialog = true;
                }


            }
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_location),
                    PERMISSION_LOCATION, perms);
        }
    }

}
