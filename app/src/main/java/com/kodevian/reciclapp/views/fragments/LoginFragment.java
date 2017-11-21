package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.LoginPresenter;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.CreateAccountActivity;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.commons.LoginView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 19/03/16.
 */
public class LoginFragment extends BaseFragment implements LoginView, Validator.ValidationListener {

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Email(message = "Email inválido")
    @Bind(R.id.etEmail)
    EditText etEmail;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;
    @Bind(R.id.btn_close)
    ImageButton btnClose;
    @Bind(R.id.tv_info)
    TextView tvInfo;
    @Bind(R.id.input_pass)
    TextInputLayout inputPass;
    @Bind(R.id.input_mail)
    TextInputLayout inputMail;


    private Validator validator;
    private LoginPresenter loginPresenter;

    /**
     * Constructor
     */
    public LoginFragment() {
        setIdLayout(R.layout.layout_login_normal);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static LoginFragment newInstance(String tag) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        loginFragment.setArguments(bundle);
        return loginFragment;
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
        isLoading = false;
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {
        etEmail.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        etPassword.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        btnLogin.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvError.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvInfo.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        inputPass.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputMail.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
    }

    @Override
    protected void createEntities() {
        searchEditTextAndImplementsKey(getLayoutLoader());
        validator = new Validator(this);
        validator.setValidationListener(this);
        loginPresenter = new LoginPresenter(getMainActivity(), this);

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
    public void goCreateAccount() {
      /* getMainActivity().getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                add(R.id.container, CreateAccountFragment.newInstance(LoginFragment.class.getName()), CreateAccountFragment.class.getName()).
                addToBackStack(null)
                .commit();*/
        if(!isLoading && active){
            Intent i = new Intent(getMainActivity().getBaseContext(), CreateAccountActivity.class);
            startActivity(i);
            active=false;
        }


    }

    @Override
    public void errorLogin(String error) {
        btnLogin.setEnabled(true);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(error);
        isLoading = false;
    }

    @Override
    public void succesLogin(UserEntity userEntity) {
        tvError.setVisibility(View.GONE);
        Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY
        |Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i);
        getMainActivity().finish();

    }

    @Override
    public void showLoad() {
        btnLogin.setText("Cargando...");
        this.viewMessage.showMessage(Messages.LOAD);
    }

    @Override
    public void hideLoad() {
        btnLogin.setEnabled(true);
        btnLogin.setText("Ingresar");
        this.viewMessage.hideMessage();

    }

    @Override
    public void setError(String msg) {
        btnLogin.setEnabled(true);
        tvError.setVisibility(View.GONE);
        tvError.setText(msg);
        isLoading = false;
        btnLogin.setText("Ingresar");
    }

    @Override
    public void onValidationSucceeded() {
        sendUser(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getMainActivity());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                tvError.setText(message);
            }
        }
        btnLogin.setEnabled(true);
    }

    private void sendUser(String email, String password) {
        if (!isLoading) {

                tvError.setVisibility(View.GONE);
            isLoading = true;
            loginPresenter.loginUser(email, password);
        }
    }

    @OnClick(R.id.btn_login)
    public void goToPresenter() {

            btnLogin.setEnabled(false);
            validator.validate();

     /*   Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
        startActivity(i);
        getMainActivity().finish();*/
    }

    @OnClick(R.id.btn_close)
    public void onClick() {
        deleteFragment();
    }
}
