package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.kodevian.reciclapp.model.CompanyJuridicEntity;
import com.kodevian.reciclapp.model.CompanyReciclappEntity;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.MeRecyclePresenter;
import com.kodevian.reciclapp.presenter.RegisterCompanyPresenter;
import com.kodevian.reciclapp.util.DividerItemDecorator;
import com.kodevian.reciclapp.util.SessionManager;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.adapters.DefinePriceAdapter;
import com.kodevian.reciclapp.views.commons.CategoriesRecycleView;
import com.kodevian.reciclapp.views.commons.RegisterCompanyView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 31/03/16.
 */
public class CompanyDefinePriceFragment extends BaseFragment implements CategoriesRecycleView,RegisterCompanyView {


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
    private RegisterCompanyPresenter registerCompanyPresenter;
    private DefinePriceAdapter definePriceAdapter;
    private CompanyEntity companyEntity;
    private ArrayList<RecycleItemEntity> recycleItemEntities;
    private String typeCompany;

    /**
     * Constructor
     */
    public CompanyDefinePriceFragment() {
        setIdLayout(R.layout.layout_define_price);
        setIdContainer(R.id.frame_container);
    }

    public static CompanyDefinePriceFragment newInstance(String tag, String company) {
        CompanyDefinePriceFragment companyDefinePriceFragment = new CompanyDefinePriceFragment();
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
        actionMiddle.setText("Definir Precios");
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
        tvDefinePrice.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        recycleItemEntities = new ArrayList<>();
        registerCompanyPresenter = new RegisterCompanyPresenter(getMainActivity(),this);
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecorator(getMainActivity(), DividerItemDecorator.VERTICAL_LIST));
        definePriceAdapter = new DefinePriceAdapter(getMainActivity(), companyEntity.getCategories(), registerCompanyPresenter);
        recyclerView.setAdapter(definePriceAdapter);


    }

    @Override
    protected void requestServices() {

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

    @Override
    public void selectItem(RecycleItemEntity recycleItemEntity) {
        if (recycleItemEntity != null) {
            if (recycleItemEntities.size() != 0) {
                for (int i = 0; i < recycleItemEntities.size(); i++) {
                    if (recycleItemEntities.get(i).getId().equals(recycleItemEntity.getId())) {
                        recycleItemEntities.remove(i);
                        return;
                    }

                }
                recycleItemEntities.add(recycleItemEntity);
            } else
                recycleItemEntities.add(recycleItemEntity);
        }


    }



    @Override
    public void populate(ArrayList<RecycleItemEntity> arrayList) {
        definePriceAdapter = new DefinePriceAdapter(getMainActivity(), arrayList,registerCompanyPresenter);
        if (recyclerView != null)
            recyclerView.setAdapter(definePriceAdapter);
        isLoading = false;
    }

    @Override
    public void setMore(ArrayList<RecycleItemEntity> arrayList) {

    }

    @Override
    public void ErroRegister(String msg) {
        Toast.makeText(getMainActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void succesLogin(UserEntity userEntity) {

    }

    @Override
    public void setmsg(String msg) {
        Toast.makeText(getMainActivity(), msg, Toast.LENGTH_SHORT).show();
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
    @SuppressWarnings("unused")
    public void createCompany() {

            if(definePriceAdapter.reviseEditTextNull()){
                SessionManager sessionManager = getMainActivity().getApplicationMaven().getSessionManager();
                companyEntity.setCategories(definePriceAdapter.getData());
                if((companyEntity).getRuc()!=null){
                    registerCompanyPresenter.RegisterCompanyJuridic(sessionManager.getUserToken(), companyEntity);
                }else{
                    registerCompanyPresenter.RegisterCompanyReciclapp(sessionManager.getUserToken(),companyEntity);
                }

            }else{
                Toast.makeText(getMainActivity(), "Debe llenar el precio de todas las categorías", Toast.LENGTH_SHORT).show();
            }

    }






    @Override
    public void registerJuridicCompany(CompanyEntity companyJuridicEntity) {
        if(companyJuridicEntity!=null){
            Toast.makeText(getMainActivity(), "Empresa Jurídica registrada exitosamente", Toast.LENGTH_SHORT).show();
            Intent i = getMainActivity().getIntent();
            Gson gson = new Gson();
            String str = gson.toJson(companyJuridicEntity);
            i.putExtra("company", str);
            getMainActivity().setResult(getMainActivity().RESULT_OK, i);
            getMainActivity().finish();

        }else{
            Toast.makeText(getMainActivity(), "Ocurrio un problema al seguir conectandose, vuelva a sus empresas" +
                    " para verificar su registro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerReciclappCompany(CompanyEntity companyReciclappEntity) {
        if(companyReciclappEntity!=null){
            Toast.makeText(getMainActivity(), "Empresa Reciclapp registrada exitosamente", Toast.LENGTH_SHORT).show();

                Intent i = getMainActivity().getIntent();
                Gson gson = new Gson();
                String str = gson.toJson(companyReciclappEntity);
                i.putExtra("company", str);
                getMainActivity().setResult(getMainActivity().RESULT_OK, i);
                getMainActivity().finish();

        }else{
            Toast.makeText(getMainActivity(), "Ocurrió un problema al seguir conectandose, vuelva a sus empresas" +
                    " para verificar su registro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void focusEditText() {
        onBackActions();
        deleteFragment();
    }

    @Override
    public void focusEditTextScroll() {
        onBackActions();
    }

    @Override
    public void failureUploadImage(CompanyEntity companyEntity) {
        if(companyEntity!=null){
            Intent i = getMainActivity().getIntent();
            Gson gson = new Gson();
            String str = gson.toJson(companyEntity);
            i.putExtra("company", str);
            getMainActivity().setResult(getMainActivity().RESULT_OK, i);
            getMainActivity().finish();
        }
    }
}
