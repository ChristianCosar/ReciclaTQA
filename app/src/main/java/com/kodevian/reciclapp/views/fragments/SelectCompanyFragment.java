package com.kodevian.reciclapp.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.CompanyJuridicEntity;
import com.kodevian.reciclapp.model.CompanyReciclappEntity;
import com.kodevian.reciclapp.util.Util_Fonts;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 20/03/16.
 */
public class SelectCompanyFragment extends BaseFragment {


    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.tv_company_reciclapp)

    TextView tvCompanyReciclapp;
    @Bind(R.id.tv_detail_company_reciclapp)
    TextView tvDetailCompanyReciclapp;
    @Bind(R.id.action_juridic_company)
    CardView actionJuridicCompany;
    @Bind(R.id.tv_type_company_legal)
    TextView tvTypeCompanyLegal;
    @Bind(R.id.tv_detail_company)
    TextView tvDetailCompany;
    @Bind(R.id.action_reciclapp_company)
    CardView actionReciclappCompany;
    @Bind(R.id.tv_error)
    TextView tvError;


    private CompanyEntity companyEntity;

    public SelectCompanyFragment() {
        setIdLayout(R.layout.layout_select_company);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static SelectCompanyFragment newInstance() {
        SelectCompanyFragment selectCompanyFragment = new SelectCompanyFragment();
        return selectCompanyFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initActionBar() {
        actionMiddle.setText("Opciones de puntos de reciclaje");
        actionRight.setVisibility(View.GONE);
        actionLeft.setImageResource(R.drawable.ic_action_cancel);
    }

    @Override
    protected void onBackActions() {
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {
        tvError.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvCompanyReciclapp.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvTypeCompanyLegal.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvDetailCompany.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvDetailCompanyReciclapp.setTypeface(Util_Fonts.setFontLight(getMainActivity()));

    }

    @Override
    protected void createEntities() {

    }

    @OnClick(R.id.action_left)
    @SuppressWarnings("unused")
    public void back() {
        deleteFragment();

    }

    @Override
    protected void requestServices() {

    }

    @Override
    public void deleteFragment() {
        getMainActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.action_reciclapp_company)
    @SuppressWarnings("unused")
    public void goRegisterCompanyReciclapp() {
        if (!isLoading && active) {
            Gson gson = new Gson();
            companyEntity = new CompanyEntity();
            String str = gson.toJson(companyEntity);
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, CreateCompanyFragment.newInstance(SelectCompanyFragment.class.getName(), str, "Reciclapp"), CreateCompanyFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active = false;
        }

    }

    @OnClick(R.id.action_juridic_company)
    @SuppressWarnings("unused")
    public void goRegisterCompanyJuridic() {
        if (!isLoading && active) {
            Gson gson = new Gson();
            companyEntity = new CompanyEntity();
            String str = gson.toJson(companyEntity);
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, CreateCompanyFragment.newInstance(SelectCompanyFragment.class.getName(), str, "Juridic"), CreateCompanyFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active = false;
        }


    }
}
