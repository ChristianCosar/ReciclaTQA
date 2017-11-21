package com.kodevian.reciclapp.presenter;

import android.content.Context;

import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.RecycleItemTrackCategoryAdd;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterEditDefinePrice;
import com.kodevian.reciclapp.request.CategoriesRecycleRequest;
import com.kodevian.reciclapp.request.CompanyRequest;
import com.kodevian.reciclapp.request.generators.ServiceGeneratorSimple;
import com.kodevian.reciclapp.request.tracks.TrackCompanyHolder;
import com.kodevian.reciclapp.request.tracks.TrackItemRecycleHolder;
import com.kodevian.reciclapp.views.commons.CategoriesEditView;
import com.kodevian.reciclapp.views.commons.EditCompanyView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by junior on 04/05/16.
 */
public class EditCategoriesPresenter implements CommunicatePresenterEditDefinePrice {


    private Context context;
    private CategoriesEditView categoriesEditView;
    String next;

    public EditCategoriesPresenter(Context context, CategoriesEditView categoriesEditView) {
        this.context = context;
        this.categoriesEditView = categoriesEditView;
    }

    public void getCategories(String token,String id){
        final CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        categoriesEditView.showLoad();
        Call<TrackItemRecycleHolder> call = categoriesRecycleRequest.getCategoriesNotInCompany("Token "+token,id);
        call.enqueue(new Callback<TrackItemRecycleHolder>() {
            @Override
            public void onResponse(Response<TrackItemRecycleHolder> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    TrackItemRecycleHolder trackItemRecycleHolder= response.body();
                    next = trackItemRecycleHolder.getNext();
                    populateDialog(trackItemRecycleHolder.getResults());
                } else {
                    categoriesEditView.hideLoad();
                    categoriesEditView.setError("Ocurrio un error al traer la información");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categoriesEditView.hideLoad();
                categoriesEditView.setError("Ocurrio un error en la conexión al servidor");
            }
        });
    }
    private void populateDialog(List<RecycleItemEntity> recycleItemEntities)
    {
        categoriesEditView.hideLoad();
        categoriesEditView.getCategories(new ArrayList<RecycleItemEntity>(recycleItemEntities));
    }


    public void deleteCategorie(String token, final RecycleItemCategorieTrack recycleItemCategorieTrack){
        CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        categoriesEditView.showLoad();
        Call<Void> call = categoriesRecycleRequest.deleteCategorie("Token " + token, recycleItemCategorieTrack.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categoriesEditView.hideLoad();
                    categoriesEditView.deleteCategorie(recycleItemCategorieTrack);

                } else {
                    categoriesEditView.hideLoad();
                    categoriesEditView.setError("Ocurrió un problema al eliminar, inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categoriesEditView.hideLoad();
                categoriesEditView.setError("Ocurrió un problema al eliminar, inténtelo nuevamente");

            }
        });

    }
    public void addCategories(String token,String idCategory,String idCompany,String price){
        CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        categoriesEditView.showLoad();
        Call<RecycleItemTrackCategoryAdd> call = categoriesRecycleRequest.addCategory("Token " + token,
                idCategory, idCompany, price);
        call.enqueue(new Callback<RecycleItemTrackCategoryAdd>() {
            @Override
            public void onResponse(Response<RecycleItemTrackCategoryAdd> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categoriesEditView.hideLoad();
                    categoriesEditView.addCategories(response.body());

                } else {
                    categoriesEditView.hideLoad();
                    categoriesEditView.setError("Ocurrió un problema al agregar categoría, inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categoriesEditView.hideLoad();
                categoriesEditView.setError("Ocurrió un problema al agregar categoría, inténtelo nuevamente");

            }
        });

    }

    public void editCategorie(String token,  RecycleItemCategorieTrack recycleItemCategorieTrack){

        CategoriesRecycleRequest categoriesRecycleRequest =
                ServiceGeneratorSimple.createService(CategoriesRecycleRequest.class);
        categoriesEditView.showLoad();
        Call<RecycleItemCategorieTrack> call = categoriesRecycleRequest.editPrice("Token " + token, recycleItemCategorieTrack.getId(),
                recycleItemCategorieTrack.getPrice());
        call.enqueue(new Callback<RecycleItemCategorieTrack>() {
            @Override
            public void onResponse(Response<RecycleItemCategorieTrack> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categoriesEditView.hideLoad();
                   // response.body().setId(recycleItemCategorieTrack.getId());
                    categoriesEditView.editCategorie(response.body());

                } else {
                    categoriesEditView.hideLoad();
                    categoriesEditView.setError("Ocurrió un problema al editar precio, inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categoriesEditView.hideLoad();
                categoriesEditView.setError("Ocurrió un problema al editar precio, inténtelo nuevamente");

            }
        });

    }


    @Override
    public void editCategories(int position) {
        this.categoriesEditView.showDialogEditCategorie(position);
    }

    @Override
    public void deleteCategories(int position) {
        this.categoriesEditView.showDialogDeleteConfirmation(position);
    }
}
