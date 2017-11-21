package com.kodevian.reciclapp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.LoginPresenter;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.commons.LoginView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 19/03/16.
 */
public class LandingFragment extends BaseFragment implements LoginView {


    @Bind(R.id.login_facebook)
    Button loginFacebook;
    @Bind(R.id.login_normal)
    Button loginNormal;
    @Bind(R.id.login_business)
    Button loginBusiness;
    @Bind(R.id.tv_error)
    TextView tvError;

    private CallbackManager callbackManager;
    LoginPresenter loginPresenter;

    /**
     * Constructor
     */
    public LandingFragment() {
        setIdLayout(R.layout.layout_landing);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static LandingFragment newInstance() {
        LandingFragment landingFragment = new LandingFragment();
        return landingFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getMainActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String access_token_facebook = loginResult.getAccessToken().getToken();
                if (access_token_facebook != null || !access_token_facebook.equals("")) {
                    loginPresenter.loginUserFacebook(access_token_facebook);
                    AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                }else {
                    getMainActivity().showMessage("Algo sucedió mal al intentar loguearse");
                }

            }

            @Override
            public void onCancel() {
                getMainActivity().showMessage("El login a facebook se a cancelado, intente más tarde");
            }

            @Override
            public void onError(FacebookException error) {
                getMainActivity().showMessage("Error al intentar loguearse");
            }
        });
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
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {
        loginBusiness.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        loginFacebook.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        loginNormal.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));

    }

    @Override
    protected void createEntities() {
        loginPresenter= new LoginPresenter(getMainActivity(),this);
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
        getMainActivity().finish();
    }

    @OnClick(R.id.login_normal)
    @SuppressWarnings("unused")
    public void goLoginFragment() {
        if(!isLoading && active){
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, LoginFragment.newInstance(LandingFragment.class.getName()), LoginFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active=false;

        }



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    @OnClick(R.id.login_business)
    @SuppressWarnings("unused")
    public void goRegisterCompany() {
        if(!isLoading && active){
            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container, MeCompanyFragment.newInstance(LandingFragment.class.getName()), MeCompanyFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active=false;

        }



    }

    @Override
    public void errorLogin(String error) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(error);
        isLoading = false;
    }

    @Override
    public void succesLogin(UserEntity userEntity) {
        tvError.setVisibility(View.GONE);
        Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
                |Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i);
        getMainActivity().finish();

    }

    @Override
    public void showLoad() {
        this.viewMessage.showMessage(Messages.LOAD);
    }

    @Override
    public void hideLoad() {
        this.viewMessage.hideMessage();

    }

    @Override
    public void setError(String msg) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
        isLoading = false;
    }

    @OnClick(R.id.login_facebook)
    public void onClick() {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));


    }
}
