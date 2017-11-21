package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.CompanyReciclappEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.presenter.MyCompaniesPresenter;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.views.activities.CreateCompanyActivity;
import com.kodevian.reciclapp.views.adapters.MyCompaniesAdapter;
import com.kodevian.reciclapp.views.commons.MyCompaniesViews;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 08/04/16.
 */
public class MyCompaniesFragment extends BaseFragment implements MyCompaniesViews {


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
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private static final int CREATE_COMPANY = 1;
    private MyCompaniesPresenter myCompaniesPresenter;
    private MyCompaniesAdapter myCompaniesAdapter;
    private LinearLayoutManager linearLayoutManager;

    /**
     * Constructor
     */
    public MyCompaniesFragment() {
        setIdLayout(R.layout.layout_items);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static MyCompaniesFragment newInstance(String tag) {
        MyCompaniesFragment myCompaniesFragment = new MyCompaniesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        myCompaniesFragment.setArguments(bundle);
        return myCompaniesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        lastTag = arg.getString("tag");

    }
    @Override
    protected void initActionBar() {
        actionLeft.setImageResource(R.drawable.ic_action_arrow_left);
        actionRight.setVisibility(View.GONE);
        actionMiddle.setText("Mis Empresas");
    }

    @Override
    protected void onBackActions() {
        isLoading = false;
        active = true;
        activeActionsBack();

        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        myCompaniesPresenter.getCompanies(sessionManager.getUserToken());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createEntities() {
        myCompaniesPresenter = new MyCompaniesPresenter(getMainActivity(), this);
        listItems.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItems.setLayoutManager(linearLayoutManager);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary)));

        myCompaniesAdapter = new MyCompaniesAdapter(getMainActivity(), new ArrayList<CompanyEntity>(), myCompaniesPresenter);
        listItems.setAdapter(myCompaniesAdapter);
    }



    @Override
    protected void requestServices() {
        if (!isLoading){
            SessionManager sessionManager= getMainActivity().getApplicationMaven().getSessionManager();
            myCompaniesPresenter.getCompanies(sessionManager.getUserToken());
            isLoading=true;
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
        myCompaniesAdapter = new MyCompaniesAdapter(getMainActivity(), arrayList, myCompaniesPresenter);
        if(listItems!=null)
            listItems.setAdapter(myCompaniesAdapter);
        isLoading=false;
    }

    @Override
    public void setMore(ArrayList<CompanyEntity> arrayList) {
        for ( int i=0; i< arrayList.size() ; i++){
            myCompaniesAdapter.addCompany(arrayList.get(i));
        }
        isLoading=false;
        active=true;
    }

    @Override
    public void detailCompany(CompanyEntity companyEntity) {

        if(companyEntity instanceof CompanyReciclappEntity){
            if(!isLoading&&active) {

                Gson gson = new Gson();
                String str = gson.toJson(companyEntity);
                getMainActivity().getSupportFragmentManager().beginTransaction().
                        setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                        add(R.id.container, CompanyEditFragment.newInstance(MyCompaniesFragment.class.getName(),str,"Reciclapp"), CompanyEditFragment.class.getName()).
                        addToBackStack(null)
                        .commit();
                active=false;
            }
        }else{

            if(!isLoading&&active) {
            Gson gson = new Gson();
            String str = gson.toJson(companyEntity);
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, CompanyEditFragment.newInstance(MyCompaniesFragment.class.getName(),str,"Juridic"), CompanyEditFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active=false;
            }
        }


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

    @OnClick(R.id.action_left)
    @SuppressWarnings("unused")
    public void back() {
        deleteFragment();
    }

    @Override
    public void setError(String msg) {
        getMainActivity().showMessage(msg);
    }

    @OnClick(R.id.fab)
    public void createNewCompany() {
        Intent i = new Intent(getMainActivity().getBaseContext(), CreateCompanyActivity.class);
        startActivityForResult(i, CREATE_COMPANY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        active = true;
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CREATE_COMPANY:
                    if (data != null) {

                       String resultado = data.getExtras().getString("company");
                        CompanyEntity companyEntity = new Gson().fromJson(resultado, CompanyEntity.class);
                        myCompaniesAdapter.addCompany(companyEntity);

                      /*  SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
                        myCompaniesPresenter.getCompanies(sessionManager.getUserToken());*/
                    } else {
                        isLoading = false;
                    }
                    break;
            }

        } else {
            isLoading = false;

        }

    }

}
