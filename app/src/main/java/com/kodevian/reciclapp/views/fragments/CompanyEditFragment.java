package com.kodevian.reciclapp.views.fragments;




import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.Location;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.presenter.EditCompanyPresenter;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.util.UtilityClass;
import com.kodevian.reciclapp.views.activities.MapChoisePointActivity;
import com.kodevian.reciclapp.views.adapters.CategoriesListAdapter;
import com.kodevian.reciclapp.views.commons.EditCompanyView;
import com.kodevian.reciclapp.views.fragments.dialog.AddressDialog;
import com.kodevian.reciclapp.views.fragments.dialog.CellphoneDialog;
import com.kodevian.reciclapp.views.fragments.dialog.EmailDialog;
import com.kodevian.reciclapp.views.fragments.dialog.HolidayDialog;
import com.kodevian.reciclapp.views.fragments.dialog.MondayFridayDialog;
import com.kodevian.reciclapp.views.fragments.dialog.PhoneDialog;
import com.kodevian.reciclapp.views.fragments.dialog.RucDialog;
import com.mobsandgeeks.saripaar.Validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by junior on 24/04/16.
 */
public class CompanyEditFragment extends BaseFragment implements EditCompanyView,
        EasyPermissions.PermissionCallbacks{

    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int POINT_CHOICE = 1;
    private static final int PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE = 180;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 181;

    public String type;
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
    @Bind(R.id.tv_company_name)
    TextView tvCompanyName;
    @Bind(R.id.tv_company_name_title)
    TextView tvCompanyNameTitle;
    @Bind(R.id.ib_name_company)
    ImageButton ibNameCompany;
    @Bind(R.id.tv_company_ruc)
    TextView tvCompanyRuc;
    @Bind(R.id.tv_company_ruc_title)
    TextView tvCompanyRucTitle;
    @Bind(R.id.ib_ruc_company)
    ImageButton ibRucCompany;
    @Bind(R.id.layout_ruc)
    LinearLayout layoutRuc;
    @Bind(R.id.tv_company_direction)
    TextView tvCompanyDirection;
    @Bind(R.id.tv_company_direction_title)
    TextView tvCompanyDirectionTitle;
    @Bind(R.id.ib_direction_company)
    ImageButton ibDirectionCompany;
    @Bind(R.id.tv_company_point)
    TextView tvCompanyPoint;
    @Bind(R.id.tv_company_point_title)
    TextView tvCompanyPointTitle;
    @Bind(R.id.ib_point_company)
    ImageButton ibPointCompany;
    @Bind(R.id.tv_company_cellphone)
    TextView tvCompanyCellphone;
    @Bind(R.id.tv_company_cellphone_title)
    TextView tvCompanyCellphoneTitle;
    @Bind(R.id.ib_cellphone_company)
    ImageButton ibCellphoneCompany;
    @Bind(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @Bind(R.id.tv_company_phone_title)
    TextView tvCompanyPhoneTitle;
    @Bind(R.id.ib_phone_company)
    ImageButton ibPhoneCompany;
    @Bind(R.id.tv_company_m_f)
    TextView tvCompanyMF;
    @Bind(R.id.tv_company_m_f_title)
    TextView tvCompanyMFTitle;
    @Bind(R.id.ib_l_v_company)
    ImageButton ibLVCompany;
    @Bind(R.id.tv_company_holyday)
    TextView tvCompanyHolyday;
    @Bind(R.id.tv_company_holyday_title)
    TextView tvCompanyHolydayTitle;
    @Bind(R.id.ib_holiday_company)
    ImageButton ibHolidayCompany;
    @Bind(R.id.list_item_recycling)
    RecyclerView listItemRecycling;
    @Bind(R.id.tv_company_recycling_title)
    TextView tvCompanyRecyclingTitle;
    @Bind(R.id.ib_add_recycling_company)
    ImageButton ibAddRecyclingCompany;
    @Bind(R.id.ib_add_category_company)
    ImageButton ibAddCategoryCompany;
    @Bind(R.id.tv_company_email)
    TextView tvCompanyEmail;
    @Bind(R.id.tv_company_email_title)
    TextView tvCompanyEmailTitle;
    @Bind(R.id.ib_email_company)
    ImageButton ibEmailCompany;
    @Bind(R.id.separator_decorator)
    View separatorDecorator;

    private Validator validator;
    private Bitmap bitmap;
    private File photo;
    private Location locationCompany;
    private CompanyEntity companyEntity;

    private AddressDialog addressDialog;
    private CellphoneDialog cellphoneDialog;
    private EmailDialog emailDialog;
    private HolidayDialog holidayDialog;
    private MondayFridayDialog mondayFridayDialog;
    private RucDialog rucDialog;
    private PhoneDialog phoneDialog;
    private CategoriesListAdapter categoriesListAdapter;
    private GridLayoutManager gridLayoutManager;
    private EditCompanyPresenter editCompanyPresenter;
    private static final int NUM_OF_COLUMNS = 6;
    private AlertDialog builder;
    private boolean isOpenGallery = false;
    private boolean isOpenCamera = false;

    public CompanyEditFragment() {
        setIdLayout(R.layout.layout_edit_company);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static CompanyEditFragment newInstance(String tag, String company, String type) {
        CompanyEditFragment companyEditFragment = new CompanyEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("company", company);
        bundle.putString("tag", tag);
        companyEditFragment.setArguments(bundle);

        return companyEditFragment;
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
    protected void initActionBar() {
        actionMiddle.setText(companyEntity.getName());
        actionRight.setImageResource(R.drawable.ic_action_trash_white);
    }

    @Override
    protected void onBackActions() {
        isLoading = false;
        active = true;
        activeActionsBack();
    }

    @Override
    protected void initView() {
        tvCompanyCellphone.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyDirection.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyHolyday.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyMF.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyName.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyPoint.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyPhone.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyCellphone.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyRuc.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyCellphoneTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyDirectionTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyHolydayTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyRecyclingTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyPointTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyMFTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyPhoneTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyRucTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyNameTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyEmail.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyEmailTitle.setTypeface(Util_Fonts.setFontLight(getContext()));


        if (companyEntity.getC_type().equals("R")) {
            layoutRuc.setVisibility(View.GONE);
            separatorDecorator.setVisibility(View.GONE);
        }
    }

    @Override
    protected void createEntities() {

        showLoad();
        Glide.with(this).load(companyEntity.getPhoto())
                .into(new GlideDrawableImageViewTarget(ivCompanyPerfil) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        hideLoad();
                    }
                });
        editCompanyPresenter = new EditCompanyPresenter(getMainActivity(), this);
        categoriesListAdapter = new CategoriesListAdapter(getMainActivity(), companyEntity.getCategories_companies());
        gridLayoutManager = new GridLayoutManager(getMainActivity(), NUM_OF_COLUMNS);
        listItemRecycling.setLayoutManager(gridLayoutManager);
        listItemRecycling.setHasFixedSize(true);
        listItemRecycling.setAdapter(categoriesListAdapter);
        tvCompanyName.setText(companyEntity.getName());
        tvCompanyDirection.setText(companyEntity.getAddress());
        tvCompanyEmail.setText(companyEntity.getWeb());
        tvCompanyPoint.setText(companyEntity.getLocation().getLatitude() + " Lat. - " + companyEntity.getLocation().getLongitude() + " Long.");

        if (companyEntity.getTelephone() != null) {
            tvCompanyPhone.setText(companyEntity.getTelephone());
        } else {
            tvCompanyPhone.setText("");
        }
        tvCompanyMF.setText(companyEntity.getMonday_to_friday());
        tvCompanyHolyday.setText(companyEntity.getSaturday_sunday_holidays());
        tvCompanyCellphone.setText(companyEntity.getCelphone());

        if (!companyEntity.getC_type().equals("R")) {
            tvCompanyRuc.setText(companyEntity.getRuc());

        }

        builder = AskOption();
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

    @OnClick({R.id.action_left, R.id.fab_photo_company, R.id.ib_name_company, R.id.ib_ruc_company, R.id.ib_direction_company, R.id.ib_point_company, R.id.ib_cellphone_company, R.id.ib_phone_company, R.id.ib_l_v_company, R.id.ib_holiday_company, R.id.ib_add_recycling_company, R.id.ib_add_category_company, R.id.ib_email_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_left:
                deleteFragment();
                break;
            case R.id.fab_photo_company:
                selectImage();
                break;
            case R.id.ib_name_company:

                break;
            case R.id.ib_ruc_company:
                rucDialog = new RucDialog(getMainActivity(), this);
                rucDialog.show();
                break;
            case R.id.ib_direction_company:
                addressDialog = new AddressDialog(getMainActivity(), this, companyEntity.getAddress());
                addressDialog.show();
                break;
            case R.id.ib_point_company:
                Gson gson = new Gson();
                String str = gson.toJson(companyEntity.getLocation());
                Intent intent = new Intent(getMainActivity(), MapChoisePointActivity.class);
                intent.putExtra("location", str);
                startActivityForResult(intent, POINT_CHOICE);
                break;
            case R.id.ib_cellphone_company:
                cellphoneDialog = new CellphoneDialog(getMainActivity(), this, companyEntity.getCelphone());
                cellphoneDialog.show();
                break;
            case R.id.ib_phone_company:
                phoneDialog = new PhoneDialog(getMainActivity(), this, companyEntity.getTelephone());
                phoneDialog.show();
                break;
            case R.id.ib_l_v_company:
                mondayFridayDialog = new MondayFridayDialog(getMainActivity(), this, companyEntity.getMonday_to_friday());
                mondayFridayDialog.show();
                break;
            case R.id.ib_holiday_company:
                holidayDialog = new HolidayDialog(getMainActivity(), this, companyEntity.getSaturday_sunday_holidays());
                holidayDialog.show();
                break;
            case R.id.ib_email_company:
                emailDialog = new EmailDialog(getMainActivity(), this, companyEntity.getWeb());
                emailDialog.show();
                break;
            case R.id.ib_add_recycling_company:
                if (!isLoading && active) {


                }
                break;
            case R.id.ib_add_category_company:
                if (!isLoading && active) {

                    Gson gson3 = new Gson();
                    String str3 = gson3.toJson(companyEntity);
                    getMainActivity().getSupportFragmentManager().beginTransaction().
                            setCustomAnimations(R.anim.left_right_b, R.anim.left_right_b).
                            add(R.id.container, EditCompanyDefinePriceFragment.newInstance(CompanyEditFragment.class.getName(), str3), EditCompanyDefinePriceFragment.class.getName()).
                            addToBackStack(null)
                            .commit();
                    active = false;
                }
                break;
        }
    }


    DialogInterface.OnClickListener mDialogListener = new Dialog.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which) {
                case 0:
                    Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photo = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.jpg");
                    intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                    startActivityForResult(intentImage, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
                    break;
                default:
                    Intent intentGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentGalery, RESULT_LOAD_IMAGE);
                    break;

            }
        }
    };

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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        active = true;
        isOpenGallery = false;
        isOpenCamera = false;
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

                        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
                        if (photo != null)
                            editCompanyPresenter.uploadPhoto(sessionManager.getUserToken(), companyEntity.getId(), photo);
                        isLoading=false;
                    } else {

                        isLoading = false;
                    }
                    break;
                case CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE:



                    if(isAdded()){
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
                        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
                        if (photo != null)
                            editCompanyPresenter.uploadPhoto(sessionManager.getUserToken(), companyEntity.getId(), photo);
                        isLoading = false;
                    }else{
                        Toast.makeText(getContext(),getResources().getString(R.string.no_memory),
                                Toast.LENGTH_LONG).show();
                        isLoading = false;
                    }

                    break;
                case POINT_CHOICE:
                    SessionManager sessionManager2 = getMainActivity().getApplicationMaven().getSessionManager();
                    String resultado = data.getExtras().getString("point_choice");
                    locationCompany = new Gson().fromJson(resultado, Location.class);
                    companyEntity.setLocation(locationCompany);

                    editCompanyPresenter.updateLocation(sessionManager2.getUserToken(), companyEntity.getId(), companyEntity.getLocation());
                    // tvCompanyLocation.setText(locationCompany.getLatitude() + " " + locationCompany.getLongitude());

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

    @Override
    public void editEmail(String email) {
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        editCompanyPresenter.updateEmail(sessionManager.getUserToken(), companyEntity.getId(), email);
    }

    @Override
    public void editName(String name) {


    }

    @Override
    public void editAddress(String Address) {
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        editCompanyPresenter.updateAddress(sessionManager.getUserToken(), companyEntity.getId(), Address);
    }

    @Override
    public void editCellphone(String cellphone) {
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        editCompanyPresenter.updateCellphone(sessionManager.getUserToken(), companyEntity.getId(), cellphone);
    }

    @Override
    public void editPhone(String phone) {
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        editCompanyPresenter.updatePhone(sessionManager.getUserToken(), companyEntity.getId(), phone);
    }

    @Override
    public void atention_monday_friday(String atention_monday_friday) {
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        editCompanyPresenter.update_L_V(sessionManager.getUserToken(), companyEntity.getId(), atention_monday_friday);
    }

    @Override
    public void atention_holiday(String atention_holiday) {
        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
        editCompanyPresenter.update_S_D(sessionManager.getUserToken(), companyEntity.getId(), atention_holiday);
    }

    @Override
    public void editRuc(String web) {

    }

    @Override
    public void uploadImage() {
        Glide.with(this).load(photo.getAbsolutePath())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivCompanyPerfil);

        Toast.makeText(getMainActivity(), "Foto actualizada exitosamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateCompany(CompanyEntity companyEntity) {
        Toast.makeText(getMainActivity(), "Dato actualizado exitosamente", Toast.LENGTH_SHORT).show();
        tvCompanyDirection.setText(companyEntity.getAddress());
        tvCompanyPhone.setText(companyEntity.getTelephone());
        tvCompanyPoint.setText(companyEntity.getLocation().getLatitude() + " Lat. - " + companyEntity.getLocation().getLatitude() + " Long.");
        tvCompanyMF.setText(companyEntity.getMonday_to_friday());
        tvCompanyHolyday.setText(companyEntity.getSaturday_sunday_holidays());
        tvCompanyCellphone.setText(companyEntity.getCelphone());
        tvCompanyPhone.setText(companyEntity.getTelephone());
        tvCompanyEmail.setText(companyEntity.getWeb());
    }

    @Override
    public void deleteCompany() {
        Toast.makeText(getMainActivity(), "Empresa eliminada con éxito", Toast.LENGTH_SHORT).show();
        deleteFragment();
    }

    @Override
    public void showLoad() {
        if (viewMessage != null) {
            viewMessage.showMessage(Messages.LOAD);
        }
    }


    public void updateCategories(ArrayList<RecycleItemCategorieTrack> recycleItemEntities){
        companyEntity.setCategories_companies(recycleItemEntities);
        categoriesListAdapter=new CategoriesListAdapter(getMainActivity(), recycleItemEntities);
        listItemRecycling.setAdapter(categoriesListAdapter);
    }
    @Override
    public void hideLoad() {
        if (viewMessage != null) {
            viewMessage.hideMessage();
        }
        isLoading = false;
        active = true;
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(getMainActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.action_right)
    public void onClick() {
        builder.show();
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getMainActivity())
                //set message, title, and icon
                .setTitle("Eliminar compañia")
                .setMessage("¿Desea eliminar todos los registros de esta empresa? Recuerde que una vez eliminada la empresa no podrá volver a revertir el cambio")
                .setIcon(R.drawable.ic_action_warning)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
                        editCompanyPresenter.deleteCompany(sessionManager.getUserToken(), companyEntity.getId());
                    }

                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;

    }
}
