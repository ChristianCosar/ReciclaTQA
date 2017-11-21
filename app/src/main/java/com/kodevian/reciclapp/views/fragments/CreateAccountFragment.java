package com.kodevian.reciclapp.views.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Past;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 19/03/16.
 */
public class CreateAccountFragment extends BaseFragment implements Validator.ValidationListener {

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

   // @Length(min = 9, max = 9, message = "Número de celular inválido", sequence = 4)
    @Bind(R.id.tv_phone_user)
    EditText tvPhoneUser;


    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(min = 8, max = 8, message = "DNI inválido", sequence = 5)
    @Bind(R.id.tv_dni_user)
    EditText tvDniUser;

   // @Past(message = "La fecha no puede ser futura", dateFormat = "yyyy-MM-dd", sequence = 6)
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
    @Bind(R.id.input_email)
    TextInputLayout inputEmail;
    @Bind(R.id.input_password)
    TextInputLayout inputPassword;
    @Bind(R.id.input_first_name)
    TextInputLayout inputFirstName;
    @Bind(R.id.input_last_name)
    TextInputLayout inputLastName;
    @Bind(R.id.input_phone)
    TextInputLayout inputPhone;
    @Bind(R.id.input_birthdate)
    TextInputLayout inputBirthdate;

    private Validator validator;
    private Bitmap bitmap;
    private File photo;
    private ProgressDialog progressDialog;
    private DatePickerDialog datePicker;
    private UserEntity userEntity;

    /**
     * Constructor
     */
    public CreateAccountFragment() {
        setIdLayout(R.layout.layout_create_account);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static CreateAccountFragment newInstance(String tag) {
        CreateAccountFragment createAccountFragment = new CreateAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        createAccountFragment.setArguments(bundle);
        return createAccountFragment;
    }

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment createAccountFragment = new CreateAccountFragment();
        return createAccountFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        //lastTag = arg.getString("tag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initActionBar() {
        actionMiddle.setText("Registro de Usuario");
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

        inputBirthdate.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputEmail.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputPassword.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputLastName.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputFirstName.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputPhone.setTypeface(Util_Fonts.setFontLight(getMainActivity()));


        tvBirthDate.setInputType(InputType.TYPE_NULL);
        tvBirthDate.requestFocus();
    }

    @Override
    protected void createEntities() {
        ViewGroup viewGroup = getLayoutLoader();
        searchEditTextAndImplementsKey(viewGroup);
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
        getMainActivity().finish();

    }

    public void deleteFragment() {
        getMainActivity().finish();
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


    @OnClick(R.id.btn_register)
    @SuppressWarnings("unused")
    public void goToFragmentSelectRecycling() {
        tvError.setVisibility(View.GONE);
        validator.validate();
      /*  Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
        startActivity(i);
        getMainActivity().finish();*/


       /* getMainActivity().getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                add(R.id.container, MeRecycleFragment.newInstance(CreateAccountFragment.class.getName()), MeRecycleFragment.class.getName()).
                addToBackStack(null)
                .commit();*/

    }

    @Override
    public void onValidationSucceeded() {

        userEntity.setEmail(tvEmailUser.getText().toString());
        userEntity.setPassword(tvPasswordUser.getText().toString());
        userEntity.setFirst_name(tvNameUser.getText().toString());
        userEntity.setLast_name(tvLastNameUser.getText().toString());
        userEntity.setCelphone(tvPhoneUser.getText().toString());
        userEntity.setDni(tvDniUser.getText().toString());
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
        Gson gson = new Gson();
        String str = gson.toJson(userEntity);

        getMainActivity().getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                add(R.id.container, MeRecycleFragment.newInstance(str, CreateAccountFragment.class.getName()), MeRecycleFragment.class.getName()).
                addToBackStack(null)
                .commit();
        active = false;

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
    }

    @OnClick(R.id.tv_birth_date)
    public void onClick() {
        datePicker.show();
    }
}
