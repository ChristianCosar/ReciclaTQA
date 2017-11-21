package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.UserEntity;
import com.kodevian.reciclapp.presenter.MeRecyclePresenter;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.adapters.RecycleItemsAdapter;
import com.kodevian.reciclapp.views.commons.CategoriesRecycleView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 31/03/16.
 */
public class MeRecycleFragment extends BaseFragment implements CategoriesRecycleView {


    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.tv_recycle_me)
    TextView tvRecycleMe;
    @Bind(R.id.grid_items)
    RecyclerView gridItems;
    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;

    private static final int NUM_OF_COLUMNS = 4;
    private GridLayoutManager gridLayoutManager;
    private MeRecyclePresenter meRecyclePresenter;
    private RecycleItemsAdapter recycleItemsAdapter;
    private UserEntity userEntity;
    private ArrayList<RecycleItemEntity> recycleItemEntities;

    /**
     * Constructor
     */
    public MeRecycleFragment() {
        setIdLayout(R.layout.layout_me_recycle);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static MeRecycleFragment newInstance(String tag) {
        MeRecycleFragment meRecycleFragment = new MeRecycleFragment();
        Bundle bundle = new Bundle();

        bundle.putString("tag", tag);
        meRecycleFragment.setArguments(bundle);
        return meRecycleFragment;
    }

    public static MeRecycleFragment newInstance(String user, String tag) {
        MeRecycleFragment meRecycleFragment = new MeRecycleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        bundle.putString("tag", tag);
        meRecycleFragment.setArguments(bundle);
        return meRecycleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        lastTag = arg.getString("tag");
        String user = arg.getString("user");
        if (user != null) {
            userEntity = new Gson().fromJson(user, UserEntity.class);
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
        actionMiddle.setText("Registro de usuario");
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
        tvRecycleMe.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
    }


    @Override
    protected void createEntities() {
        recycleItemEntities = new ArrayList<>();
        meRecyclePresenter = new MeRecyclePresenter(this, getMainActivity());
        gridLayoutManager = new GridLayoutManager(getMainActivity(), NUM_OF_COLUMNS);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridItems.setLayoutManager(gridLayoutManager);
      //  recycleItemsAdapter = new RecycleItemsAdapter(getMainActivity(), new ArrayList<RecycleItemEntity>(), meRecyclePresenter);
        recycleItemsAdapter = new RecycleItemsAdapter(getMainActivity(), new ArrayList<RecycleItemEntity>(), meRecyclePresenter);
        gridItems.setAdapter(recycleItemsAdapter);


    }

    @Override
    protected void requestServices() {
        if (!isLoading) {
            meRecyclePresenter.getCategories();
            isLoading = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.action_right)
    public void onClick() {

        if (!lastTag.equals("com.kodevian.reciclapp.views.fragments.MyAccountFragment")) {
            Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
            startActivity(i);
            getMainActivity().finish();
        } else {
            deleteFragment();
        }

    }

    @OnClick(R.id.action_left)
    @SuppressWarnings("unused")
    public void backToFragmet() {
        deleteFragment();
    }

    @Override
    public void selectItem(RecycleItemEntity recycleItemEntity) {
        if(recycleItemEntity !=null){
            if(recycleItemEntities.size()!=0) {
                for (int i = 0; i < recycleItemEntities.size(); i++) {
                    if (recycleItemEntities.get(i).getId().equals(recycleItemEntity.getId())){
                        recycleItemEntities.remove(i);
                        return;
                    }

                }
                recycleItemEntities.add(recycleItemEntity);
            } else
                recycleItemEntities.add(recycleItemEntity);
        }


    }


    /**
     *
     * @param recycleItemEntities
     * @return String with id categories recyling with the form : "1,2,3,4 "
     */
    public String generateCategories(ArrayList<RecycleItemEntity> recycleItemEntities){
        String id_Categories="";
        if(recycleItemEntities!=null){
            if(recycleItemEntities.size()!=0)
                for (int i = 0; i <recycleItemEntities.size() ; i++) {
                        id_Categories=id_Categories+recycleItemEntities.get(i).getId()+",";
                }
            else
                return "";
        }

        return id_Categories;
    }

    @Override
    public void populate(ArrayList<RecycleItemEntity> arrayList) {
        recycleItemsAdapter = new RecycleItemsAdapter(getMainActivity(), arrayList, meRecyclePresenter);
        if (gridItems != null)
            gridItems.setAdapter(recycleItemsAdapter);
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
        Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
        i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY
                |Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i);
        getMainActivity().finish();
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

    public ArrayList<Integer> converterId(ArrayList<RecycleItemEntity> recycleItemEntities){
        ArrayList<Integer> integers= new ArrayList<>();
        for (int i = 0; i < recycleItemEntities.size(); i++) {
            integers.add(Integer.valueOf(recycleItemEntities.get(i).getId()));

        }

        return integers;
    }
    @OnClick(R.id.btn_create_account)
    public void createAccount() {

        if(!isLoading && active){
            if(userEntity!=null && recycleItemEntities!=null){
               // userEntity.setId_categories(generateCategories(recycleItemEntities));
                userEntity.setCategories(converterId(recycleItemEntities));
                meRecyclePresenter.RegisterUser(userEntity);
            }
            active=false;

        }


      /*  Intent i = new Intent(getMainActivity().getBaseContext(), MainActivity.class);
        startActivity(i);
        getMainActivity().finish();*/
    }


}
