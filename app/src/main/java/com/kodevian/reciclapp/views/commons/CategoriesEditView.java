package com.kodevian.reciclapp.views.commons;

import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.RecycleItemTrack;
import com.kodevian.reciclapp.model.RecycleItemTrackCategoryAdd;

import java.util.ArrayList;

/**
 * Created by junior on 04/05/16.
 */
public interface CategoriesEditView extends MasterView {

    public void deleteCategorie(RecycleItemCategorieTrack recycleItemCategorieTrack);
    public void editCategorie(RecycleItemCategorieTrack recycleItemCategorieTrack );
    public void addCategories(RecycleItemTrackCategoryAdd recycleItemCategorieTrack);
    public void addCartegory(RecycleItemTrack recycleItemTrack);
    public void populate(ArrayList<RecycleItemCategorieTrack> recycleItemCategorieTracks);
    public void showDialogDeleteConfirmation(int position);
    public void showDialogEditCategorie(int position);
    public void editPrice(RecycleItemCategorieTrack recycleItemCategorieTrack);
    public void populateDialogSearch();
    public void getCategories(ArrayList<RecycleItemEntity> recycleItemEntities);

}
