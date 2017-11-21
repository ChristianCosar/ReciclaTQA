package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.CreateAccountActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 26/03/16.
 */
public class MeCompanyFragment extends BaseFragment {


    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;
    @Bind(R.id.btn_close)
    ImageButton btnClose;
    @Bind(R.id.tv_info)
    TextView tvInfo;

    /**
     * Constructor
     */
    public MeCompanyFragment() {
        setIdLayout(R.layout.layout_me_company);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static MeCompanyFragment newInstance(String tag) {
        MeCompanyFragment meCompanyFragment = new MeCompanyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        meCompanyFragment.setArguments(bundle);
        return meCompanyFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        lastTag = arg.getString("tag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected void onBackActions() {
        activeActionsBack();
    }


    @Override
    protected void initView() {
        tvInfo.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
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


    @OnClick(R.id.btn_create_account)
    @SuppressWarnings("unused")
    public void goLoginFragment() {

       /* getMainActivity().getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                add(R.id.container, CreateAccountFragment.newInstance(MeCompanyFragment.class.getName()), CreateAccountFragment.class.getName()).
                addToBackStack(null)
                .commit();*/
        Intent i = new Intent(getMainActivity().getBaseContext(), CreateAccountActivity.class);
        startActivity(i);
        // getMainActivity().finish();

    }

    @OnClick(R.id.btn_close)
    @SuppressWarnings("unused")
    public void onClick() {
        deleteFragment();
    }
}
