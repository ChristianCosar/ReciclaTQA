package com.kodevian.reciclapp.views.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.UserPresenter;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.commons.UserView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Past;

import org.json.JSONException;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 19/03/16.
 */
public class EditAccountFragment extends BaseFragment implements UserView, Validator.ValidationListener {

    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private static final int RESULT_LOAD_IMAGE = 100;

    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.tv_icon_perfil)
    ImageView tvIconPerfil;


    @NotEmpty(message = "Este campo no puede ser vacío")
    @Email(message = "Email inválido")
    @Bind(R.id.tv_email_user)
    EditText tvEmailUser;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 2)
    @Bind(R.id.tv_password_user)
    EditText tvPasswordUser;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(max = 50, message = "Cantidad de dígitos no permitida", sequence = 3)
    @Bind(R.id.tv_name_user)
    EditText tvNameUser;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(max = 50, message = "Cantidad de dígitos no permitida", sequence = 4)
    @Bind(R.id.tv_last_name_user)
    EditText tvLastNameUser;

    @Bind(R.id.tv_phone_user)
    EditText tvPhoneUser;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(min = 8, max = 8, message = "DNI inválido", sequence = 5)
    @Bind(R.id.tv_dni_user)
    EditText tvDniUser;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Past(message = "La fecha no puede ser futura", dateFormat = "yyyy-MM-dd", sequence = 6)
    @Bind(R.id.tv_birth_date)
    EditText tvBirthDate;


    @Bind(R.id.rb_rigth)
    RadioButton rbRigth;
    @Bind(R.id.rb_left)
    RadioButton rbLeft;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.layout_password)
    LinearLayout layoutPassword;

    private Validator validator;
    private Bitmap bitmap;
    private File photo;
    private ProgressDialog progressDialog;
    private DatePickerDialog datePicker;
    private UserEntity userEntity;
    private UserPresenter userPresenter;

    /**
     * Constructor
     */
    public EditAccountFragment() {
        setIdLayout(R.layout.layout_create_account);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static EditAccountFragment newInstance(String tag) {
        EditAccountFragment createAccountFragment = new EditAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        createAccountFragment.setArguments(bundle);

        return createAccountFragment;
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
        actionMiddle.setText("Editar cuenta");
        actionRight.setVisibility(View.GONE);

    }

    @Override
    protected void onBackActions() {
        isLoading = false;
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {
        tvBirthDate.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvPhoneUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvDniUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvEmailUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvError.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvLastNameUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvNameUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvPasswordUser.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvBirthDate.setInputType(InputType.TYPE_NULL);
        layoutPassword.setVisibility(View.GONE);
        tvBirthDate.requestFocus();
        tvEmailUser.setEnabled(false);
        tvEmailUser.setFocusable(false);
        btnRegister.setText("Actualizar Datos");

    }

    @Override
    protected void createEntities() {
        ViewGroup viewGroup = getLayoutLoader();
        searchEditTextAndImplementsKey(viewGroup);
        userPresenter = new UserPresenter(getMainActivity(), this);
        userEntity = new UserEntity();
        Calendar newCalendar = Calendar.getInstance();

        datePicker = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvBirthDate.setText(year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth));
            }


        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        validator = new Validator(this);
        validator.setValidationListener(this);

        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();

        try {
            userEntity.setId(sessionManager.getUserEntity().getId());
            tvNameUser.setText(sessionManager.getUserEntity().getFirst_name());
            tvLastNameUser.setText(sessionManager.getUserEntity().getLast_name());
            tvBirthDate.setText(sessionManager.getUserEntity().getBirthdate());
            if (sessionManager.getUserEntity().getCelphone() != null) {
                if (!sessionManager.getUserEntity().getCelphone().equals(""))
                    tvPhoneUser.setText(sessionManager.getUserEntity().getCelphone().substring(3, sessionManager.getUserEntity().getCelphone().length()));
            }
            tvEmailUser.setText(sessionManager.getUserEntity().getEmail());


            if (sessionManager.getUserEntity().getGender().equals("M")) {
                rbLeft.setChecked(false);
                rbRigth.setChecked(true);
            } else {
                rbLeft.setChecked(true);
                rbRigth.setChecked(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


    @OnClick({R.id.rb_left, R.id.rb_rigth})
    @SuppressWarnings("unused")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_left:
                if (!checked) {
                    rbLeft.setChecked(true);
                    rbRigth.setChecked(false);
                }
                break;
            case R.id.rb_rigth:
                if (!checked) {
                    rbLeft.setChecked(false);
                    rbRigth.setChecked(true);
                }
                break;
        }
    }

    @Override
    public void ErroRegister(String msg) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
    }

    @Override
    public void succesLogin(UserEntity userEntity) {
    }

    @Override
    public void editUser(UserEntity userEntity) {

        tvError.setVisibility(View.GONE);
        tvNameUser.setText(userEntity.getFirst_name());
        tvLastNameUser.setText(userEntity.getLast_name());
        tvBirthDate.setText(userEntity.getBirthdate());
        tvPhoneUser.setText(userEntity.getCelphone().toString().substring(3, userEntity.getCelphone().toString().length()));
        tvEmailUser.setText(userEntity.getEmail());


        if (userEntity.getGender().equals("M")) {
            rbLeft.setChecked(false);
            rbRigth.setChecked(true);
        } else {
            rbLeft.setChecked(true);
            rbRigth.setChecked(false);
        }
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        sessionManager.setUser(userEntity);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        MyAccountFragment baseFragment = (MyAccountFragment) manager.findFragmentByTag(lastTag);
        baseFragment.onResume();
        ((MainActivity)getActivity()).onResume();
        manager.popBackStack();

        deleteFragment();

    }

    @Override
    public void setmsg(String msg) {
        Toast.makeText(getMainActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        btnRegister.setText("Guardando...");
        btnRegister.setEnabled(false);
        this.viewMessage.showMessage(Messages.LOAD);
    }

    @Override
    public void hideLoad() {
        if (viewMessage != null) {
            btnRegister.setText("Actualizar Datos");
            btnRegister.setEnabled(true);
            viewMessage.hideMessage();
        }
        isLoading = false;
        active = true;
    }

    @Override
    public void setError(String msg) {
        tvError.setVisibility(View.GONE);
        tvError.setText(msg);
        btnRegister.setEnabled(true);
        btnRegister.setText("Actualizar Datos");
    }


    @OnClick(R.id.btn_register)
    @SuppressWarnings("unused")
    public void gotoPresenter() {
        tvError.setVisibility(View.GONE);
        btnRegister.setEnabled(false);
        validator.validate();


    }

    @Override
    public void onValidationSucceeded() {


        userEntity.setFirst_name(tvNameUser.getText().toString());
        userEntity.setLast_name(tvLastNameUser.getText().toString());
        userEntity.setCelphone(tvPhoneUser.getText().toString());
        userEntity.setBirthdate(tvBirthDate.getText().toString());

        if (rbLeft.isChecked()) {
            userEntity.setGender("F");
        } else {
            userEntity.setGender("M");
        }

        sendUser(userEntity);

    }

    public void sendUser(UserEntity userEntity) {
        tvError.setVisibility(View.GONE);
        isLoading = true;
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        userPresenter.EditUser(sessionManager.getUserToken(), userEntity.getId(), userEntity);
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
        btnRegister.setEnabled(false);
    }


    @OnClick(R.id.tv_birth_date)
    public void onClick() {
        datePicker.show();
    }
}
