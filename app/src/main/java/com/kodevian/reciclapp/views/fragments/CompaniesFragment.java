package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.CreateCompanyActivity;
import com.kodevian.reciclapp.views.activities.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 08/04/16.
 */
public class CompaniesFragment extends BaseFragment {


    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.tv_title_info_company)
    TextView tvTitleInfoCompany;
    @Bind(R.id.tv_info_company)
    TextView tvInfoCompany;
    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;

    /**
     * Constructor
     */
    public CompaniesFragment() {
        setIdLayout(R.layout.layout_companies);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static CompaniesFragment newInstance() {
        CompaniesFragment landingFragment = new CompaniesFragment();
        return landingFragment;
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
        actionMiddle.setText("Empresas");
        actionRight.setVisibility(View.GONE);
        actionLeft.setImageResource(R.drawable.ic_menu);
    }

    @Override
    protected void onBackActions() {
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {
        tvTitleInfoCompany.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvInfoCompany.setTypeface(Util_Fonts.setFontLight(getMainActivity()));


    }

    @Override
    protected void createEntities() {

    }

    @Override
    protected void requestServices() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void deleteFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }



    @OnClick(R.id.action_left)
    public void onClick() {
        ((MainActivity) getMainActivity()).openMenu();
    }

    @OnClick(R.id.btn_create_account)
    public void registerCompany() {
        Intent i = new Intent(getMainActivity().getBaseContext(), CreateCompanyActivity.class);
        startActivity(i);
    }
}
