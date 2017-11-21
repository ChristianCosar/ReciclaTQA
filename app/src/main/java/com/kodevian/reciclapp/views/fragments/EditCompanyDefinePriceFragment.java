package com.kodevian.reciclapp.views.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.RecycleItemTrack;
import com.kodevian.reciclapp.model.RecycleItemTrackCategoryAdd;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.EditCategoriesPresenter;
import com.kodevian.reciclapp.presenter.RegisterCompanyPresenter;
import com.kodevian.reciclapp.util.DividerItemDecorator;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.adapters.CategoriesSearchAdapter;
import com.kodevian.reciclapp.views.adapters.CompanyEditCategorieAdapter;
import com.kodevian.reciclapp.views.adapters.DefinePriceAdapter;
import com.kodevian.reciclapp.views.commons.CategoriesEditView;
import com.kodevian.reciclapp.views.commons.CategoriesRecycleView;
import com.kodevian.reciclapp.views.commons.RegisterCompanyView;
import com.kodevian.reciclapp.views.fragments.dialog.AddCategorieDialog;
import com.kodevian.reciclapp.views.fragments.dialog.EditPriceDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 31/03/16.
 */
public class EditCompanyDefinePriceFragment extends BaseFragment implements CategoriesEditView{


    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.tv_define_price)
    TextView tvDefinePrice;
    @Bind(R.id.grid_items)
    RecyclerView recyclerView;
    @Bind(R.id.btn_company_categories_create)
    Button btnCompanyCategoriesCreate;


    private LinearLayoutManager linearLayoutManager;
    private EditCategoriesPresenter editCategoriesPresenter;
    private CompanyEditCategorieAdapter companyEditCategorieAdapter;
    private CompanyEntity companyEntity;
    private ArrayList<RecycleItemEntity> recycleItemEntities;
    private EditPriceDialog editPriceDialog;
    private AlertDialog alertDialog;
    private AddCategorieDialog addCategorieDialog;
    private RecycleItemCategorieTrack recycleItemCategorieTrackAux;

    /**
     * Constructor
     */
    public EditCompanyDefinePriceFragment() {
        setIdLayout(R.layout.layout_define_price);
        setIdContainer(R.id.frame_container);
    }

    public static EditCompanyDefinePriceFragment newInstance(String tag, String company) {
        EditCompanyDefinePriceFragment companyDefinePriceFragment = new EditCompanyDefinePriceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        bundle.putString("company", company);
        companyDefinePriceFragment.setArguments(bundle);
        return companyDefinePriceFragment;
    }

    /**
     * @return Fragment with container and layout
     */
    /*public static CompanyDefinePriceFragment newInstace(String tag, String company,int type) {

    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        lastTag = arg.getString("tag");
        String user = arg.getString("company");
        if (user != null) {
            companyEntity = new Gson().fromJson(user, CompanyEntity.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initActionBar() {
        actionMiddle.setText("Administrar precios de categorías");
        actionRight.setVisibility(View.GONE);

    }

    @Override
    public void onBackActions() {
        isLoading = false;
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    int totalItemCount = linearLayoutManager.getItemCount();

                    if (!isLoading && totalItemCount > 0) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            activeActionsBack();

                        }
                    }
                }
            }
        });


    }


    @Override
    protected void createEntities() {

        btnCompanyCategoriesCreate.setText("Agregar nueva categoría");
        tvDefinePrice.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        recycleItemEntities = new ArrayList<>();
        editCategoriesPresenter = new EditCategoriesPresenter(getMainActivity(),this);
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        alertDialog=AskOption();

        recyclerView.addItemDecoration(new DividerItemDecorator(getMainActivity(), DividerItemDecorator.VERTICAL_LIST));
        companyEditCategorieAdapter = new CompanyEditCategorieAdapter(getMainActivity(), companyEntity.getCategories_companies(), editCategoriesPresenter);
        recyclerView.setAdapter(companyEditCategorieAdapter);


    }

    @Override
    protected void requestServices() {
        /*if (!isLoading) {
            meRecyclePresenter.getCategories();
            isLoading = true;
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.action_right)
    @SuppressWarnings("unused")
    public void onClick() {

        deleteFragment();

    }

    @OnClick(R.id.action_left)
    @SuppressWarnings("unused")
    public void backToFragmet() {

        deleteFragment();
    }

    public void deleteFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.setCustomAnimations(R.anim.left_right, R.anim.right_left);
        trans.remove(this);
        trans.commit();
        active = true;
        closeKeyboard();
        if (!lastTag.isEmpty()) {
            CompanyEditFragment companyEditFragment=(CompanyEditFragment)manager.findFragmentByTag(lastTag);
            companyEditFragment.updateCategories(companyEditCategorieAdapter.getData());
            BaseFragment baseFragment = (BaseFragment) manager.findFragmentByTag(lastTag);
            if (baseFragment != null)
                baseFragment.OnbackAc(baseFragment);
        }
        manager.popBackStack();
    }




    @Override
    public void populate(ArrayList<RecycleItemCategorieTrack> arrayList) {
        companyEditCategorieAdapter = new CompanyEditCategorieAdapter(getMainActivity(), arrayList,editCategoriesPresenter);
        if (recyclerView != null)
            recyclerView.setAdapter(companyEditCategorieAdapter);
        isLoading = false;
    }

    @Override
    public void showDialogDeleteConfirmation(int position) {
        alertDialog.show();
        recycleItemCategorieTrackAux=companyEntity.getCategories_companies().get(position);
    }

    @Override
    public void showDialogEditCategorie(int position) {
            editPriceDialog = new EditPriceDialog(getMainActivity(),this,companyEntity.getCategories_companies().get(position));
            editPriceDialog.show();
    }

    @Override
    public void editPrice(RecycleItemCategorieTrack recycleItemCategorieTrack) {
        SessionManager sessionManager= getMainActivity().getApplicationMaven().getSessionManager();
        editCategoriesPresenter.editCategorie(sessionManager.getUserToken(), recycleItemCategorieTrack);
    }

    @Override
    public void populateDialogSearch() {
        SessionManager sessionManager= getMainActivity().getApplicationMaven().getSessionManager();
        editCategoriesPresenter.getCategories(sessionManager.getUserToken(), companyEntity.getId());
    }

    @Override
    public void getCategories(ArrayList<RecycleItemEntity> recycleItemEntities) {
        if (addCategorieDialog != null) {
            if (recycleItemEntities != null) {
                addCategorieDialog.categoriesListAdapter = new CategoriesSearchAdapter(getMainActivity(), recycleItemEntities);
                addCategorieDialog.gridItems.setAdapter(addCategorieDialog.categoriesListAdapter);

            }

        }
    }


    @Override
    public void showLoad() {
        if (viewMessage != null) {
            viewMessage.showMessage(Messages.LOAD);
        }
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


    @OnClick(R.id.btn_company_categories_create)
    public void addCategorie() {
        addCategorieDialog= new AddCategorieDialog(getMainActivity(),this);
        addCategorieDialog.show();
    }


    @Override
    public void deleteCategorie(RecycleItemCategorieTrack recycleItemCategorieTrack) {
        Toast.makeText(getMainActivity(), "Categoría eliminada exitosamente", Toast.LENGTH_SHORT).show();
        companyEditCategorieAdapter.deleteCategorie(recycleItemCategorieTrack.getId());
    }

    @Override
    public void editCategorie(RecycleItemCategorieTrack recycleItemCategorieTrack) {
        Toast.makeText(getMainActivity(), "Precio editado exitosamente", Toast.LENGTH_SHORT).show();
        companyEditCategorieAdapter.updateCategory(recycleItemCategorieTrack);

    }

    @Override
    public void addCategories(RecycleItemTrackCategoryAdd recycleItemTrackCategoryAdd) {
        if(recycleItemTrackCategoryAdd!=null){

            RecycleItemCategorieTrack recycleItemCategorieTrack= new RecycleItemCategorieTrack();
            recycleItemCategorieTrack.setPrice(recycleItemTrackCategoryAdd.getPrice());
            recycleItemCategorieTrack.setId(recycleItemTrackCategoryAdd.getId());
            RecycleItemEntity recycleItemEntity= new RecycleItemEntity(recycleItemTrackCategoryAdd.getCategory_name(),
                    recycleItemTrackCategoryAdd.getCategory_img(),recycleItemTrackCategoryAdd.getCategory());
            recycleItemCategorieTrack.setCategory(recycleItemEntity);
            companyEditCategorieAdapter.addCategorie(recycleItemCategorieTrack);
            Toast.makeText(getMainActivity(), "Categoría agregada exitosamente", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void addCartegory(RecycleItemTrack recycleItemTrack) {
        SessionManager sessionManager= getMainActivity().getApplicationMaven().getSessionManager();
        editCategoriesPresenter.addCategories(sessionManager.getUserToken(),recycleItemTrack.getId(),companyEntity.getId(),
                recycleItemTrack.getPrice());
    }



    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getMainActivity())
                //set message, title, and icon
                .setTitle("Eliminar categoría")
                .setMessage("¿Desea eliminar esta categoría?")
                .setIcon(R.drawable.ic_action_warning)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
                        editCategoriesPresenter.deleteCategorie(sessionManager.getUserToken(),recycleItemCategorieTrackAux);

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
