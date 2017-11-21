package com.kodevian.reciclapp.views.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MapChoisePointActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by junior on 19/03/16.
 */
public class CreateCompanyFragment extends BaseFragment implements Validator.ValidationListener,
        EasyPermissions.PermissionCallbacks {

    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int POINT_CHOICE = 1;
    private static final int PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE = 180;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 181;
    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.iv_company_perfil)
    ImageView ivCompanyPerfil;
    @Bind(R.id.fab_photo_company)
    FloatingActionButton fabPhotoCompany;
    @Bind(R.id.tv_error)
    TextView tvError;


    @Bind(R.id.tv_ruc_company)
    EditText tvRucCompany;
    @Bind(R.id.layout_ruc)
    LinearLayout layoutRuc;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Bind(R.id.tv_name_company)
    EditText tvNameCompany;

    @Bind(R.id.tv_company_location)
    EditText tvCompanyLocation;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(max = 59, message = "Longitd máxima de 59 caracteres")
    @Bind(R.id.tv_company_direction)
    EditText tvCompanyDirection;


    // @Length(min = 6, max = 10, message = "Número de teléfono inválido", sequence = 2)
    @Bind(R.id.tv_company_phone)
    EditText tvCompanyPhone;

    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 3)
    @Length(min = 9, max = 9, message = "Número de celular inválido")
    @Bind(R.id.tv_company_cellphone)
    EditText tvCompanyCellphone;

    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 4)
    @Length(max = 24, message = "Longitd máxima de 24 caracteres")
    @Bind(R.id.tv_atention_l_v)
    EditText tvAtentionLV;

    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 5)
    @Length(max = 24, message = "Longitd máxima de 24 caracteres")
    @Bind(R.id.tv_atention_m_f_h)
    EditText tvAtentionMFH;

    // @Url(message = "Url inválido")
    @Bind(R.id.tv_company_web)
    EditText tvCompanyWeb;


    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.btn_select_point)
    Button btnSelectPoint;
    @Bind(R.id.input_Ruc)
    TextInputLayout inputRuc;
    @Bind(R.id.input_name_company)
    TextInputLayout inputNameCompany;
    @Bind(R.id.input_direction)
    TextInputLayout inputDirection;
    @Bind(R.id.input_cellphone)
    TextInputLayout inputCellphone;
    @Bind(R.id.input_phone)
    TextInputLayout inputPhone;
    @Bind(R.id.input_Atention_l_v)
    TextInputLayout inputAtentionLV;
    @Bind(R.id.input_Atention_m_f_h)
    TextInputLayout inputAtentionMFH;
    @Bind(R.id.input_web)
    TextInputLayout inputWeb;


    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 1)
    @Bind(R.id.tv_name_social)
    EditText tvNameSocial;
    @Bind(R.id.scrollView)
    ScrollView scrollView;


    private Validator validator;
    private Bitmap bitmap;
    private File photo;
    private String type;
    private CompanyEntity companyEntity;
    private Location locationCompany;
    private boolean isOpenGallery = false;
    private boolean isOpenCamera = false;


    /**
     * Constructor
     */
    public CreateCompanyFragment() {
        setIdLayout(R.layout.layout_create_company);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static CreateCompanyFragment newInstance(String tag, String company, String type) {
        CreateCompanyFragment createCompanyFragment = new CreateCompanyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("company", company);
        bundle.putString("tag", tag);
        createCompanyFragment.setArguments(bundle);
        return createCompanyFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        lastTag = arg.getString("tag");
        type = arg.getString("type");
        String company = arg.getString("company");
        companyEntity = new Gson().fromJson(company, CompanyEntity.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initActionBar() {
        if (type.equals("Reciclapp")) {
            actionMiddle.setText("Registro de PyMe");
        } else {
            actionMiddle.setText("Registro de Punto");
        }
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
        tvAtentionLV.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvAtentionMFH.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvCompanyCellphone.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvError.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvCompanyDirection.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvCompanyLocation.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvCompanyPhone.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvCompanyWeb.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvRucCompany.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvRucCompany.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputAtentionLV.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputAtentionMFH.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputCellphone.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputPhone.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputDirection.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputNameCompany.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputWeb.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputRuc.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvNameSocial.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        inputRuc.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        btnSelectPoint.setTypeface(Util_Fonts.setFontLight(getMainActivity()));


        if (type.equals("Reciclapp")) {
            layoutRuc.setVisibility(View.GONE);
            inputNameCompany.setHint("Nombre (*)");
        } else {
            layoutRuc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void createEntities() {
        ViewGroup viewGroup = getLayoutLoader();
        searchEditTextAndImplementsKey(viewGroup);
        companyEntity = new CompanyEntity();
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
        deleteFragment();

    }


    @OnClick(R.id.btn_register)
    @SuppressWarnings("unused")
    public void goToFragmentSelectRecycling() {
        tvError.setVisibility(View.GONE);
        if (companyEntity.getLocation() != null) {
            if (type.equals("Reciclapp")) {
                validator.validate();
            } else {
                if (!tvRucCompany.getText().toString().equals("")) {

                    if (tvRucCompany.length() == 11) {

                        if (tvCompanyPhone.getText() != null) {
                            if (!tvCompanyPhone.getText().equals("")) {

                                if (tvCompanyPhone.getText().toString().length() <= 10) {
                                    validator.validate();

                                } else {
                                    tvError.setVisibility(View.VISIBLE);
                                    tvError.setText("El teléfono debe tener máximo 10 dígitos ");
                                }
                            }
                        } else {
                            validator.validate();
                        }

                    } else {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("EL RUC debe tener 11 dígitos ");
                        scrollView.fullScroll(View.FOCUS_UP);
                    }
                } else {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("EL RUC no puede ser vacio ");
                    scrollView.fullScroll(View.FOCUS_UP);
                }
            }
        } else {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Debe seleccionar un punto en el mapa");
            scrollView.fullScroll(View.FOCUS_UP);
        }
    }

    @Override
    public void onValidationSucceeded() {

        companyEntity.setName(tvNameSocial.getText().toString());
        companyEntity.setAddress(tvCompanyDirection.getText().toString());
        companyEntity.setMonday_to_friday(tvAtentionLV.getText().toString());
        companyEntity.setSaturday_sunday_holidays(tvAtentionMFH.getText().toString());
        companyEntity.setTelephone(tvCompanyPhone.getText().toString());
        companyEntity.setCelphone(tvCompanyCellphone.getText().toString());
        companyEntity.setWeb(tvCompanyWeb.getText().toString());
        if (photo != null) {
            companyEntity.setImage(photo);
        }
        companyEntity.setLocation(locationCompany);
        if (!type.equals("Reciclapp"))
            companyEntity.setRuc(tvRucCompany.getText().toString());


        sendCompany(companyEntity);

    }

    public void sendCompany(CompanyEntity companyEntity) {
        tvError.setVisibility(View.GONE);


        if (!isLoading && active) {
            Gson gson = new Gson();
            String str = gson.toJson(companyEntity);


            getMainActivity().getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                    add(R.id.container,
                            CompanyRecycleFragment.newInstance(CreateCompanyFragment.class.getName(),
                                    str),
                            CompanyRecycleFragment.class.getName()).
                    addToBackStack(null)
                    .commit();
            active = false;
        }

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

    @OnClick(R.id.fab_photo_company)
    public void showDialog() {

        selectImage();
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == PERMISSION_READ_EXTERNAL_STORAGE) {
            if (!isOpenGallery) {
                galleryIntent();
                isOpenGallery = true;
            }
        }
        if (requestCode == PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE) {
            if (!isOpenCamera) {
                cameraIntent();
                isOpenCamera = true;
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.accept),
                R.string.action_settings, R.string.cancel, perms);
    }

    @AfterPermissionGranted(PERMISSION_READ_EXTERNAL_STORAGE)
    private void methodRequiresPermissionExternalStorage() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            if (!isOpenGallery) {
                galleryIntent();
                isOpenGallery = true;
            }
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_external_storage),
                    PERMISSION_READ_EXTERNAL_STORAGE, perms);
        }

    }

    @AfterPermissionGranted(PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            if (!isOpenCamera) {
                cameraIntent();
                isOpenCamera = true;
            }
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_camera),
                    PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE, perms);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void selectImage() {
        final CharSequence[] items = {"Cámara", "Galería",
                "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Adjuntar Imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Cámara")) {
                    methodRequiresTwoPermission();
                } else if (items[item].equals("Galería")) {
                    methodRequiresPermissionExternalStorage();
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void cameraIntent() {
        Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photo = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.jpg");
        intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intentImage, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }


    private void galleryIntent() {
        Intent intentGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGalery, RESULT_LOAD_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isOpenGallery = false;
        isOpenCamera = false;
        active = true;
        if (resultCode == -1) {
            switch (requestCode) {

                case RESULT_LOAD_IMAGE:

                    if (data != null) {

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getMainActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        File gallery = new File(picturePath);
                        photo = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.jpg");
                        try {
                            bitmap = decodeBitmapFromFile(gallery.getAbsolutePath(), 700, 700);
                            photo.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(photo);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, ostream);
                            ostream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Glide.with(this).load(photo.getAbsolutePath())
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(ivCompanyPerfil);
                        isLoading = false;
                    } else {

                        isLoading = false;
                    }
                    break;
                case CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE:

                    if (isAdded()) {
                        photo = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.jpg");
                        try {
                            bitmap = decodeBitmapFromFile(photo.getAbsolutePath(), 700, 700);
                            photo.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(photo);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, ostream);
                            ostream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Glide.with(this).load(photo.getAbsolutePath())
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(ivCompanyPerfil);

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.no_memory),
                                Toast.LENGTH_LONG).show();
                    }


                    break;
                case POINT_CHOICE:
                    if (isAdded()) {
                        String resultado = data.getExtras().getString("point_choice");
                        locationCompany = new Gson().fromJson(resultado, Location.class);
                        companyEntity.setLocation(locationCompany);
                        // tvCompanyLocation.setText(locationCompany.getLatitude() + " " + locationCompany.getLongitude());
                        btnSelectPoint.setText("Punto definido con éxito");
                    } else {
                        Toast.makeText(getContext(), "Si dispositivo no pudo mantener la vista activa debido a que no se contó " +
                                " con memoria suficiente para realizar esta tarea", Toast.LENGTH_LONG).show();
                    }

                    break;
            }

        } else {
            isLoading = false;

        }

    }

    //camera
    public static Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }


    @OnClick(R.id.tv_company_location)
    public void onClick() {
        active = false;
        Intent intent = new Intent(getMainActivity(), MapChoisePointActivity.class);
        startActivityForResult(intent, POINT_CHOICE);
    }

    @OnClick(R.id.btn_select_point)
    public void select_position() {

        Gson gson = new Gson();
        String str = gson.toJson(locationCompany);
        Intent intent = new Intent(getMainActivity(), MapChoisePointActivity.class);
        intent.putExtra("location", str);
        startActivityForResult(intent, POINT_CHOICE);


    }


}
