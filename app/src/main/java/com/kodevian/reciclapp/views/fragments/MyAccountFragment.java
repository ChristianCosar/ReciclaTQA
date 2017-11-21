package com.kodevian.reciclapp.views.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.util.CircleTransform;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 04/04/16.
 */
public class MyAccountFragment extends BaseFragment {


    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.tv_name_user)
    TextView tvNameUser;
    @Bind(R.id.tv_email_user)
    TextView tvEmailUser;
    @Bind(R.id.tv_tittle_email)
    TextView tvTittleEmail;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_phone_title)
    TextView tvPhoneTitle;
    @Bind(R.id.tv_birthdate)
    TextView tvBirthdate;
    @Bind(R.id.tv_birthdate_tittle)
    TextView tvBirthdateTittle;
    @Bind(R.id.btn_my_companies)
    Button btnMyCompanies;
    @Bind(R.id.image_fb)
    ImageView imageFb;

    /**
     * Constructor
     */

    private UserEntity userEntity;
    public MyAccountFragment() {
        setIdLayout(R.layout.layout_account);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static MyAccountFragment newInstance() {
        MyAccountFragment myAccountFragment = new MyAccountFragment();
        return myAccountFragment;
    }

    @Override
    protected void initActionBar() {
        actionMiddle.setText("Mi cuenta");
        actionRight.setImageResource(R.drawable.ic_action_edit);
        actionLeft.setImageResource(R.drawable.ic_menu);
        actionRigthOff.setImageResource(R.drawable.ic_action_recycling);
        actionRigthOff.setVisibility(View.GONE);
    }

    @Override
    protected void onBackActions() {
        isLoading = false;
        active = true;
        activeActionsBack();
    }


    @Override
    public void onResume() {
        super.onResume();
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        try {

            if(AccessToken.getCurrentAccessToken()!=null) {
                imageFb.setVisibility(View.VISIBLE);
                Glide.with(this).load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId()
                        + "/picture?type=normal").bitmapTransform(new CircleTransform(getMainActivity())).into(imageFb);
            }else{
                imageFb.setVisibility(View.GONE);
            }
            userEntity=sessionManager.getUserEntity();

            tvNameUser.setText(userEntity.getFirst_name() + " " + userEntity.getLast_name());
            tvEmailUser.setText(userEntity.getEmail());

            if (userEntity.getBirthdate() == null)
                tvBirthdate.setText("");
            else
                tvBirthdate.setText(userEntity.getBirthdate());

            if (userEntity.getCelphone() == null)
                tvPhone.setText("");
            else
                tvPhone.setText(userEntity.getCelphone());




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.action_left)
    public void openMenu() {
        ((MainActivity) getMainActivity()).openMenu();

    }

    @OnClick(R.id.action_right)
    public void editUser() {
        if (!isLoading && active) {
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, EditAccountFragment.newInstance(MyAccountFragment.class.getName()), EditAccountFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active = false;
        }


    }

    @Override
    protected void initView() {
        tvBirthdate.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvNameUser.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvEmailUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvPhone.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvTittleEmail.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvPhoneTitle.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvBirthdateTittle.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvBirthdate.setInputType(InputType.TYPE_NULL);
        tvBirthdate.requestFocus();
    }

    @Override
    public void deleteFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @Override
    protected void createEntities() {

    }

    @Override
    protected void requestServices() {

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

    @OnClick(R.id.action_rigth_off)
    public void goToSelectFragmentCategories() {

        if (!isLoading &&active) {
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, MeRecycleFragment.newInstance(MyAccountFragment.class.getName()), MeRecycleFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active = false;
        }

    }

    @OnClick(R.id.btn_my_companies)
    public void onClick() {
        if (!isLoading && active) {
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, MyCompaniesFragment.newInstance(MyAccountFragment.class.getName()), MyCompaniesFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active=false;

        }

    }


}
