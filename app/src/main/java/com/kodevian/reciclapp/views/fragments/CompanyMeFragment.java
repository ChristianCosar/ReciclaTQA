package com.kodevian.reciclapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.util.Util_Fonts;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 20/03/16.
 */
public class CompanyMeFragment extends BaseFragment {

    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.frame_menu)
    FrameLayout frameMenu;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;
    @Bind(R.id.btn_close)
    ImageButton btnClose;

    public CompanyMeFragment() {
        setIdLayout(R.layout.layout_me_company);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static CompanyMeFragment newInstance(String tag) {
        CompanyMeFragment companyMeFragment = new CompanyMeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        companyMeFragment.setArguments(bundle);
        return companyMeFragment;
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

        actionMiddle.setVisibility(View.GONE);
        actionLeft.setVisibility(View.GONE);
        actionRight.setImageResource(R.drawable.ic_action_cancel);
        actionRight.setBackgroundResource(R.drawable.button_circle);

    }

    @Override
    protected void onBackActions() {
        activeActionsBack();
    }


    @Override
    protected void initView() {
        actionMiddle.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvError.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
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

    @OnClick(R.id.action_left)
    @SuppressWarnings("unused")
    public void back() {
        deleteFragment();
    }

    @OnClick(R.id.action_right)
    @SuppressWarnings("unused")
    public void goToAppForNewUser() {
       /* getMainActivity().getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                add(R.id.container, CreateAccountFragment.newInstance(LoginFragment.class.getName()), CreateAccountFragment.class.getName()).
                addToBackStack(null)
                .commit();*/
    }

}
